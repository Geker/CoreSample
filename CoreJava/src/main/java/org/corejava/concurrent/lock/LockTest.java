package org.corejava.concurrent.lock;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LockTest {

    private static final Logger logger = LoggerFactory.getLogger(LockTest.class);

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ReentrantLock lock = new ReentrantLock();
        Condition conn = lock.newCondition();
        ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();

        CompletableFuture<Void> futureA = CompletableFuture.runAsync(() -> {
            logger.debug("doing...");
        });
        futureA.get();
        logger.debug("outter");

    }
}
