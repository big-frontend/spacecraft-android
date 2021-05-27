package com.hawksjamesf.common;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

import androidx.annotation.MainThread;

/**
 * https://juejin.cn/post/6844903796636663815#heading-34
 * 1. 线程任务(Runnable和Callable)
 * Runnable执行的任务并不会有返回值,Callable弥补了这个问题，FutureTask是Runnable/Callable的生命周期监控类
 * 2. 线程同步(锁与信号量)
 *  - 锁分为乐观锁(读取的时候不会加锁，写入的时候加锁更新，cas 实现)和悲观锁(读写的时候都会加锁，synchrozied,aqs采用先cas去乐观锁，获取不到则取悲观锁)，对于锁来说存在这样的特性，可重入和公平性(synchronized不公平)
 *      -
 *  - 信号量
 */
public class ConcurrenceTest {
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
        public Integer call() throws Exception{
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
    public void testTask() {
        for (int i = 0; i < 10; i++) {
            FutureTask<Integer> futureTask1 = new SimpleFutureTask<Integer>(callable);
            Future<?> submit = executorService.submit(futureTask1);
            try {
                System.out.println( futureTask1.get());
            } catch (ExecutionException e) {
                e.printStackTrace();
                System.out.println(e.getStackTrace());
            } catch (InterruptedException e) {
                System.out.println(e.getStackTrace());
                e.printStackTrace();
            }
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
        try {
            System.out.println( futureTask0.get());
        } catch (ExecutionException e) {
            e.printStackTrace();
            System.out.println(e.getStackTrace());
        } catch (InterruptedException e) {
            System.out.println(e.getStackTrace());
            e.printStackTrace();
        }
//        futureTask0.cancel(false);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //可中断 sleep、join 、 Lock.lockInterruptibly(Lock.tryLock(long time, TimeUnit unit))
    //不可中断 synchronized 、 Lock.lock 、 InputStream.read
    private AtomicInteger gorun = new AtomicInteger(0);
    @Test
    public void testSync() {
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new Runnable() {
            @Override
            public void run() {
                //围栏放行的逻辑
                try {
                    Thread.sleep(20);
                    System.out.println("cyclicBarrier end");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        final CountDownLatch countDownLatch = new CountDownLatch(2);
        final StampedLock stampedLock = new StampedLock();
        final ReentrantLock lock = new ReentrantLock();
        lock.lock();
        lock.tryLock();
        lock.unlock();
        Condition condition = lock.newCondition();
        try {
            condition.await();
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final ReentrantReadWriteLock rdlock = new ReentrantReadWriteLock();
        final Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                countDownLatch.countDown();
                gorun.incrementAndGet();
                System.out.println("t1 run end");

            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
//                countDownLatch.countDown();
                gorun.incrementAndGet();
                System.out.println("t2 run end");
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
//                try {
//                    cyclicBarrier.await();
//                } catch (BrokenBarrierException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                System.out.println("t3 run end");
            }
        });
        t1.start();
        t2.start();
        //两个并行完在执行第三个
//        while (t1.isAlive() || t2.isAlive()) {
//        }
        while (gorun.get() != 2) {
        }
//        try {
//            countDownLatch.await();
//            System.out.println("test end");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        t3.start();
        //三个线程串行
//        try {
//            t1.start();
//            t1.join();
//            t2.start();
//            t2.join();
//            t3.start();
//            t3.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
