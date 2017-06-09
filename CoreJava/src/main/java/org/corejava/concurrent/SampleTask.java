package org.corejava.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SampleTask implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(SampleTask.class);
    @Override
    public void run() {
        logger.info(" in run() ");

    }

}
