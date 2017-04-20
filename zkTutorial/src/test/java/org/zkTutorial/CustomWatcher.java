package org.zkTutorial;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomWatcher implements Watcher {

    public CustomWatcher() {
        name = "default";
     }

    public CustomWatcher(String name) {
        this.name = name;
     }
    private String name;
    private static final Logger logger = LoggerFactory.getLogger(CustomWatcher.class);
    @Override
    public void process(WatchedEvent event) {
        logger.warn(name + " event trigger:" + event.toString());
    }

}
