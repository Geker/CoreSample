package org.zkTutorial;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WatcherTest {

    // 同一个watcher在同一个path，只会触发一次；
    // 同一个path的不同watcher对象，可以触发多次；
    private static final Logger logger = LoggerFactory.getLogger(WatcherTest.class);
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 3000, new CustomWatcher()); // 设置一个watcher
        CustomWatcher watcher = new CustomWatcher("one");
        Stat s = zk.exists("/test", watcher);
        byte[] ss = zk.getData("/test", watcher, new Stat());
        logger.debug(new String(ss));
        System.in.read();

    }
}
