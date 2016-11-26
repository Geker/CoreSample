package org.corejava.concurrent.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockTest {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Condition conn = lock.newCondition();
        ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();

    }
}
