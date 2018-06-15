package cn.geker.core.tools;

public class ThreadTools {
    /**
     * 获得当前的所有类
     *
     * @return
     */
    public static Thread[] getAllCurrentThreads() {
        ThreadGroup rootGroup = Thread.currentThread().getThreadGroup();
        ThreadGroup parentGroup;
        while ((parentGroup = rootGroup.getParent()) != null) {
            rootGroup = parentGroup;
        }

        Thread[] threads = new Thread[rootGroup.activeCount()];
        while (rootGroup.enumerate(threads, true) == threads.length) {
            threads = new Thread[threads.length * 2];
        }
        return threads;
    }
}
