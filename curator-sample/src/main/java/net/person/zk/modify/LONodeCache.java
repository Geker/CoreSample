package net.person.zk.modify;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.WatcherRemoveCuratorFramework;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.listen.ListenerContainer;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.utils.PathUtils;
import org.apache.curator.utils.ThreadUtils;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

/**
 * <p>
 * Title: LONodeCache
 * </p>
 * <p>
 * Description:
 * 对org.apache.curator.framework.recipes.cache.NodeCache修改，解决bug：
 * 当启动创建标志是true时，nodeCache会先获取zk数据，然后再后台注册watcher，如果这期间node发生变化（data没变，但是修改时间等发生变化了），这时会再次出发一次通知。
 * 1. 增加initFlag和 startSynchronizer，在start时阻塞，待第一次通知回来时start方法再返回
 * </p>
 * <p>
 * Copyright: openlo.cn Copyright (C) 2016
 * </p>
 *
 * @author brokeneggs
 * @version
 * @since 2016年8月19日
 */
public class LONodeCache implements Closeable {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final WatcherRemoveCuratorFramework client;
    private final String path;
    private final boolean dataIsCompressed;
    private final AtomicReference<ChildData> data = new AtomicReference<>(null);
    private final AtomicReference<State> state = new AtomicReference<>(State.LATENT);
    private final ListenerContainer<NodeCacheListener> listeners = new ListenerContainer<>();
    private final AtomicBoolean isConnected = new AtomicBoolean(true);

    private final Lock startLock = new ReentrantLock();
    private final Condition startSynchronizer = startLock.newCondition();

    private static final long START_WAIT_TIME_MILLIS = 3 * 1000;
    private volatile boolean initFlag = false;

    private ConnectionStateListener connectionStateListener = new ConnectionStateListener() {
        @Override
        public void stateChanged(CuratorFramework client, ConnectionState newState) {
            if ((newState == ConnectionState.CONNECTED) || (newState == ConnectionState.RECONNECTED)) {
                if (isConnected.compareAndSet(false, true)) {
                    try {
                        reset();
                    }
                    catch (Exception e) {
                        ThreadUtils.checkInterrupted(e);
                        log.error("Trying to reset after reconnection", e);
                    }
                }
            }
            else {
                isConnected.set(false);
            }
        }
    };

    private Watcher watcher = new Watcher() {
        @Override
        public void process(WatchedEvent event) {
            try {
                reset();
            }
            catch (Exception e) {
                ThreadUtils.checkInterrupted(e);
                handleException(e);
            }
        }
    };

    private enum State {
        LATENT,
        STARTED,
        CLOSED
    }

    private final BackgroundCallback backgroundCallback = new BackgroundCallback() {
        @Override
        public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
            processBackgroundResult(event);
        }
    };

    /**
     * @param client curztor client
     * @param path the full path to the node to cache
     */
    public LONodeCache(CuratorFramework client, String path) {
        this(client, path, false);
    }

    /**
     * @param client curztor client
     * @param path the full path to the node to cache
     * @param dataIsCompressed if true, data in the path is compressed
     */
    public LONodeCache(CuratorFramework client, String path, boolean dataIsCompressed) {
        this.client = client.newWatcherRemoveCuratorFramework();
        this.path = PathUtils.validatePath(path);
        this.dataIsCompressed = dataIsCompressed;
    }

    /**
     * Start the cache. The cache is not started automatically. You must call this method.
     *
     * @throws Exception errors
     */
    public void start() throws Exception {
        start(false);
    }

    /**
     * Same as {@link #start()} but gives the option of doing an initial build
     *
     * @param buildInitial if true, {@link #rebuild()} will be called before this method
     *            returns in order to get an initial view of the node
     * @throws Exception errors
     */
    public void start(boolean buildInitial) throws Exception {
        Preconditions.checkState(state.compareAndSet(State.LATENT, State.STARTED), "Cannot be started more than once");

        client.getConnectionStateListenable().addListener(connectionStateListener);

        if (buildInitial) {
            client.checkExists().creatingParentContainersIfNeeded().forPath(path);
            internalRebuild();
            reset();
            waitInitReset();
        }
        else {
            reset();
        }

    }

    private void waitInitReset() throws InterruptedException {
        try {
            startLock.lockInterruptibly();
            if (!initFlag) {
                long startTime = System.currentTimeMillis();
                LogUtil.debug(log, "waiting reset , path:{}", path);
                boolean notified = startSynchronizer.await(START_WAIT_TIME_MILLIS, TimeUnit.MILLISECONDS);
                long costTime = System.currentTimeMillis() - startTime;
                LogUtil.debug(log, "reset finished , path:{} , cost:#{}#", path, costTime);
                if (!notified) {
                    if (!initFlag) {
                        LogUtil.warn(log, "LONodeCache " + this.path + " started in timeout status");
                    }
                }
            }
        }
        finally {
            startLock.unlock();
        }
    }

    private void notifyInitResetComplete() throws InterruptedException {
        try {
            startLock.lock();
            startSynchronizer.signalAll();
            initFlag = true;
        }
        finally {
            startLock.unlock();
        }
    }

    @Override
    public void close() throws IOException {
        if (state.compareAndSet(State.STARTED, State.CLOSED)) {
            listeners.clear();
            // client.clearWatcherReferences(watcher);
            // 清理removalfacade的watchers，这个watchers
            client.removeWatchers();
            client.getConnectionStateListenable().removeListener(connectionStateListener);
            connectionStateListener = null;
            watcher = null;
            initFlag = false;
        }
    }

    /**
     * Return the cache listenable
     *
     * @return listenable
     */
    public ListenerContainer<NodeCacheListener> getListenable() {
        Preconditions.checkState(state.get() != State.CLOSED, "Closed");

        return listeners;
    }

    /**
     * NOTE: this is a BLOCKING method. Completely rebuild the internal cache by querying
     * for all needed data WITHOUT generating any events to send to listeners.
     *
     * @throws Exception errors
     */
    public void rebuild() throws Exception {
        Preconditions.checkState(state.get() == State.STARTED, "Not started");
        initFlag = false;
        internalRebuild();
        reset();
        waitInitReset();

    }

    /**
     * Return the current data. There are no guarantees of accuracy. This is
     * merely the most recent view of the data. If the node does not exist,
     * this returns null
     *
     * @return data or null
     */
    public ChildData getCurrentData() {
        return data.get();
    }

    @VisibleForTesting
    volatile Exchanger<Object> rebuildTestExchanger;

    private void reset() throws Exception {
        if ((state.get() == State.STARTED) && isConnected.get()) {
            client.checkExists().creatingParentContainersIfNeeded().usingWatcher(watcher).inBackground(backgroundCallback).forPath(path);
        }
    }

    private void internalRebuild() throws Exception {
        try {
            Stat stat = new Stat();
            byte[] bytes = dataIsCompressed ? client.getData().decompressed().storingStatIn(stat).forPath(path)
                    : client.getData().storingStatIn(stat).forPath(path);
            data.set(new ChildData(path, stat, bytes));
        }
        catch (KeeperException.NoNodeException e) {
            data.set(null);
        }
    }

    private void processBackgroundResult(CuratorEvent event) throws Exception {
        switch (event.getType()) {
        case GET_DATA: {
            if (event.getResultCode() == KeeperException.Code.OK.intValue()) {
                ChildData childData = new ChildData(path, event.getStat(), event.getData());
                setNewData(childData);

                if (!initFlag) {
                    notifyInitResetComplete();
                }
            }
            break;
        }

        case EXISTS: {
            if (event.getResultCode() == KeeperException.Code.NONODE.intValue()) {
                setNewData(null);
            }
            else if (event.getResultCode() == KeeperException.Code.OK.intValue()) {
                if (dataIsCompressed) {
                    client.getData().decompressed().usingWatcher(watcher).inBackground(backgroundCallback).forPath(path);
                }
                else {
                    client.getData().usingWatcher(watcher).inBackground(backgroundCallback).forPath(path);
                }
            }
            break;
        }
        default:
            break;
        }
    }

    private void setNewData(ChildData newData) throws InterruptedException {
        ChildData previousData = data.getAndSet(newData);
        if (!Objects.equal(previousData, newData)) {
            listeners.forEach(new Function<NodeCacheListener, Void>() {
                @Override
                public Void apply(NodeCacheListener listener) {
                    try {
                        listener.nodeChanged();
                    }
                    catch (Exception e) {
                        ThreadUtils.checkInterrupted(e);
                        log.error("Calling listener", e);
                    }
                    return null;
                }
            });

            if (rebuildTestExchanger != null) {
                try {
                    rebuildTestExchanger.exchange(new Object());
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    /**
     * Default behavior is just to log the exception
     *
     * @param e the exception
     */
    protected void handleException(Throwable e) {
        log.error("", e);
    }

}
