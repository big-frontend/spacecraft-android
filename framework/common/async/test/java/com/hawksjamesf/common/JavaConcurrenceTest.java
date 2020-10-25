package com.hawksjamesf.common;


import androidx.annotation.MainThread;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Lock;

public class JavaConcurrenceTest {
    ExecutorService executorService;
    public static final boolean usingCachePool = true;
    public static final boolean usingFixedPool = false;
    public static final boolean usingSinglePool = false;
    public static final boolean usingScheduledPool = false;
    public static final boolean usingWorkStealingPool = false;
    private int runnableCount = 0;
    private int callableCount = 0;
    private Object object = new Object();
    //=========================================task start
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            try {
//                object.wait(4000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            System.out.println("runnable:" + (++runnableCount));
        }
    };
    private Callable callable = new Callable<Integer>() {
        @Override
        public Integer call() throws Exception {
            System.out.println("callable:" + (++callableCount));
            return callableCount;
        }
    };

    //=========================================task end
    //Future是为了实现异步定义的接口，其中对于task的控制，提供了get cancel的接口
    class SimpleFuture implements Future {
        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            return false;
        }

        @Override
        public boolean isCancelled() {
            return false;
        }

        @Override
        public boolean isDone() {
            return false;
        }

        @Override
        public Object get() throws ExecutionException, InterruptedException {
            return null;
        }

        @Override
        public Object get(long timeout, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
            return null;
        }
    }

    //FutureTask是task(Runnable Callable)和控制task(Future)的结合
    static class SimpleFutureTask<Result> extends FutureTask<Result> {
        SimpleFutureTask(Callable callable) {
            super(callable);
        }

        public SimpleFutureTask(Runnable runnable, Result result) {
            super(runnable, result);
        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            System.out.println("FutureTask-cancel:" + (mayInterruptIfRunning));
            return super.cancel(mayInterruptIfRunning);
        }

        @Override
        protected void done() {
            super.done();
            System.out.println("FutureTask-done");
        }
    }

    ;

    @Before
    public void setUp() {
        if (usingCachePool) {
            //缓存池：池子最大可容纳Integer最大值，每个线程keepalive为1分钟
            executorService = Executors.newCachedThreadPool();
        } else if (usingFixedPool) {
            //固定核心数的池子，线程常驻
            executorService = Executors.newFixedThreadPool(20);
        } else if (usingSinglePool) {
            //只有一个线程
            executorService = Executors.newSingleThreadExecutor();
        } else if (usingScheduledPool) {
            //调度池子：最大可容纳Integer最大值，核心数为20的池子，10毫秒的存活
            executorService = Executors.newScheduledThreadPool(20);
        } else if (usingWorkStealingPool) {
            executorService = Executors.newWorkStealingPool();
        }

    }

    @After
    public void tearDown() {

    }


    @Test
    @MainThread
    public void testJavaConcurrence() {
        for (int i = 0; i < 10; i++) {
            FutureTask<Integer> futureTask1 = new SimpleFutureTask<Integer>(callable);
            executorService.submit(futureTask1);
//            try {
//                System.out.println("waiting");
//                if (futureTask1.get() == 4) {
//                    futureTask1.cancel(true);
//                }
//            } catch (ExecutionException | InterruptedException e) {
//                e.printStackTrace();
//            }

        }
        FutureTask futureTask0 = new SimpleFutureTask<Integer>(runnable, null);
        Future<?> submit = executorService.submit(futureTask0);
        futureTask0.cancel(false);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //可中断 sleep、join 、 Lock.lockInterruptibly(Lock.tryLock(long time, TimeUnit unit))
    //不可中断 synchronized 、 Lock.lock 、 InputStream.read
    private Lock lock ;
    public synchronized void  getValue(){
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
