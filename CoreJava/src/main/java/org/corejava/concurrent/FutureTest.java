package org.corejava.concurrent;

import static org.junit.Assert.assertSame;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FutureTest {

    private static final Logger logger = LoggerFactory.getLogger(FutureTest.class);
    ExecutorService es = Executors.newFixedThreadPool(5);
    Callable<String> cal = new Callable<String>() {

        @Override
        public String call() throws Exception {
            logger.debug("inner Callable");
            return "future";
        }
    };

    Runnable run = new Runnable() {
        AtomicInteger i = new AtomicInteger(1);
        @Override
        public void run() {
            int j = i.incrementAndGet();
            try {
                Thread.sleep(RandomUtils.nextInt(1, 1000));
            }
            catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            logger.debug("inner Runnable:" + j);
        }
    };

    /**
     *
     * Future 普通的代表返回的对象；
     *
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    public void testFuture() throws InterruptedException, ExecutionException {
        Future<String> f = es.submit(cal);
        String s = f.get();
        assertSame(s, "future");
    }

    /**
     * FutureTask 可执行，可取消的任务，且只能执行一次；
     * 一般使用execute执行，其结果通过本身的get方法返回；
     * 如果使用submit的返回的future的get方法，无法获得对应的返回值；
     *
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    public void testFutureTask() throws InterruptedException, ExecutionException {

        FutureTask<String> ft = new FutureTask<>(cal);
        // ft.run();
        // Future<?> f = es.submit(ft);
        es.execute(ft);
        // String s = (String) f.get();
        String s = ft.get();
        assertSame(s, "future");
    }

    // CompletionService 提交一批任务之后，能够逐个处理完成的结果。
    @Test
    public void testCompletionService() throws InterruptedException, ExecutionException {
        ExecutorService exec = Executors.newCachedThreadPool();
        CompletionService<Integer> execcomp = new ExecutorCompletionService<>(exec);
        for (int i = 0; i < 10; i++) {
            execcomp.submit(getTask());
        }
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            // 检索并移除表示下一个已完成任务的 Future，如果目前不存在这样的任务，则等待。
            Future<Integer> future = execcomp.take();
            sum += future.get();
        }
        System.out.println("总数为：" + sum);
        exec.shutdown();
    }

    @SuppressWarnings("static-access")
    @Test
    public void testCompletableFuture() throws Exception {
        CompletableFuture com = CompletableFuture.runAsync(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub

            }
        });

        com.thenRun(run).thenRun(run).thenRun(run).thenRun(run).thenRun(run).get();

        Thread.sleep(1000);
    }

    // 得到一个任务
    private Callable<Integer> getTask() {
        final Random rand = new Random();
        Callable<Integer> task = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int i = rand.nextInt(10);
                int j = rand.nextInt(10);
                int sum = i * j;
                System.out.print(sum + "\t");
                return sum;
            }
        };
        return task;
    }

}
