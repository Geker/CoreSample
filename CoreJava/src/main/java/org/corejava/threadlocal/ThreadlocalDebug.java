package org.corejava.threadlocal;

import java.util.concurrent.Executors;

public class ThreadlocalDebug {
    public static void main(String[] args) {
        ThreadRun run=new ThreadRun();
        run.run();

        Executors.newSingleThreadExecutor().submit(run);

    }
}
