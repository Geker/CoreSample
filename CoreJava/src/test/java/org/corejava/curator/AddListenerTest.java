package org.corejava.curator;

import java.io.IOException;
import java.lang.reflect.Field;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.WatcherRemoveCuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddListenerTest {

    private static final Logger logger = LoggerFactory.getLogger(AddListenerTest.class);

    @Test
    public void TestCacheClose() throws Exception
    {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
                CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", retryPolicy);
                client.start();
        final NodeCache cache = new NodeCache(client, "/zookeeper/hello");
        cache.start(true);
        for (int i = 0; i < 100; i++)
            cache(client, cache);
    }

    private void cache(CuratorFramework client, NodeCache cache)
            throws Exception, NoSuchFieldException, IllegalAccessException, IOException {

        NodeCacheListener listener = new NodeCacheListener() {

            @Override
            public void nodeChanged() throws Exception {
                System.out.println(new String(cache.getCurrentData().getData()));

            }
        };
        cache.getListenable().addListener(listener);
        Field f = NodeCache.class.getDeclaredField("client");
        f.setAccessible(true);
        WatcherRemoveCuratorFramework c = (WatcherRemoveCuratorFramework) f.get(cache);
        logger.error("add watcher /zookeeper/hello");
        // System.in.read();
        cache.getListenable().removeListener(listener);
        cache.close();
        // c.removeWatchers();
        // c.removeWatchers();
        // cache.close();
        // cache.close();

        // System.in.read();
        //
        // System.in.read();
        // System.in.read();
    }
}
