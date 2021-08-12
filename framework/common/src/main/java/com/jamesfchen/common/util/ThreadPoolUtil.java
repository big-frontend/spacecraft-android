package com.jamesfchen.common.util;

import androidx.annotation.GuardedBy;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Aug/08/2021  Sun
 */
public class ThreadPoolUtil {
    public static final int TYPE_IO = 0;
    public static final int TYPE_CPU = 1;
    public static final int TYPE_SINGLE = 2;
    public static final int TYPE_CACHED = 3;
    public static final int TYPE_FIXED = 4;

    @IntDef(value = {TYPE_IO, TYPE_CPU, TYPE_SINGLE, TYPE_CACHED, TYPE_FIXED})
    private @interface Type {
    }

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    //只能放128个，再多久放不下丢弃
    private static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingQueue<>(128);

    @NonNull
    private static ThreadPoolExecutor createPool(@Type final int type) throws IllegalAccessException {
        return createPool(type, -1);
    }

    @NonNull
    private static ThreadPoolExecutor createPool(@Type final int type, int nThreads) throws IllegalAccessException {
        switch (type) {
            case TYPE_IO:
                return new ThreadPoolExecutor(2 * CPU_COUNT + 1, CPU_COUNT * 2 + 1, 30, TimeUnit.SECONDS, sPoolWorkQueue, new ThreadFactory() {
                    private final AtomicInteger mCount = new AtomicInteger(1);

                    public Thread newThread(Runnable r) {
                        return new Thread(r, "io thread #" + mCount.getAndIncrement());
                    }
                });
            case TYPE_CPU:
                return new ThreadPoolExecutor(CPU_COUNT + 1, 2 * CPU_COUNT + 1, 30, TimeUnit.SECONDS, sPoolWorkQueue, new ThreadFactory() {
                    private final AtomicInteger mCount = new AtomicInteger(1);

                    public Thread newThread(Runnable r) {
                        return new Thread(r, "cpu thread #" + mCount.getAndIncrement());
                    }
                });
            case TYPE_SINGLE:
//                Executors.newSingleThreadExecutor();
                return new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), new ThreadFactory() {
                    private final AtomicInteger mCount = new AtomicInteger(1);

                    public Thread newThread(Runnable r) {
                        return new Thread(r, "single thread #" + mCount.getAndIncrement());
                    }
                });
            case TYPE_CACHED:
                //对于SynchronousQueue，将任务直接poll/take 给线程处理(如果没有核心线程就new 非核心线程)，如果线程不能被创建(到达总线程数)，则会抛出异常(RejectedExecutionException)，所以一般maximumPoolSizes 为整数最大值
//                Executors.newCachedThreadPool();
                throw new IllegalAccessException("目前还没有支持");
//                return new ThreadPoolExecutor(1, Math.max(CPU_COUNT * 8, 64), 60L, TimeUnit.SECONDS, new SynchronousQueue<>(), new ThreadFactory() {
//                    private final AtomicInteger mCount = new AtomicInteger(1);
//
//                    public Thread newThread(Runnable r) {
//                        return new Thread(r, "cache thread #" + mCount.getAndIncrement());
//                    }
//                },new ThreadPoolExecutor.CallerRunsPolicy());
            case TYPE_FIXED:
                return new ThreadPoolExecutor(nThreads, nThreads,
                        0L, TimeUnit.MILLISECONDS,
                        sPoolWorkQueue,
                        new ThreadFactory() {
                            private final AtomicInteger mCount = new AtomicInteger(1);

                            public Thread newThread(Runnable r) {
                                return new Thread(r, "fixed thread #" + mCount.getAndIncrement());
                            }
                        }, new ThreadPoolExecutor.DiscardOldestPolicy());
            default:
                throw new IllegalAccessException("type不匹配："+type);
        }

    }

    /**
     * 当要处理的任务都是io密集型，则可以申请超过cpu线程数(cpu核数*2)的线程，让cpu中的每一条线程都在处理任务。
     * 当要处理的任务都是cpu密集型，则可以申请cpu线程数的一半线程，溜一半用来快速切换，从而提高高并发的能力，快速响应任务，快速切换任务，快速解决
     */
    final static Object cpuLock = new Object();
    @GuardedBy("cpuLock")
    public static ThreadPoolExecutor sCpuTPool;
    final static Object ioLock = new Object();
    @GuardedBy("ioLock")
    public static ThreadPoolExecutor sIoTPool;

    public static ThreadPoolExecutor getIOPool() throws IllegalAccessException {
        if (sIoTPool == null) {
            synchronized (ioLock) {
                if (sIoTPool == null) {
                    sIoTPool = createPool(TYPE_IO);
//                    sIoTPool.allowCoreThreadTimeOut(true);
                }
            }
        }
        return sIoTPool;

    }

    public static ThreadPoolExecutor getCpuPool() throws IllegalAccessException {
        if (sCpuTPool == null) {
            synchronized (cpuLock) {
                if (sCpuTPool == null) {
                    sCpuTPool = createPool(TYPE_CPU);
//                    sCpuTPool.allowCoreThreadTimeOut(true);
                }
            }
        }
        return sCpuTPool;

    }

    public static Future<?> runIOTask(Runnable runnable) throws IllegalAccessException {
        return getIOPool().submit(runnable);
    }

    public static <T> Future<T> runIOTask(Callable<T> callable) throws IllegalAccessException {
        return getIOPool().submit(callable);
    }

    public static <T> Future<?> runIOTask(Runnable runnable, T result) throws IllegalAccessException {
        return getIOPool().submit(runnable, result);
    }

    public static Future<?> runCpuTask(Runnable runnable) throws IllegalAccessException {
        return getCpuPool().submit(runnable);
    }

    public static <T> Future<T> runCpuTask(Callable<T> callable) throws IllegalAccessException {
        return getCpuPool().submit(callable);
    }

    public static <T> Future<T> runCpuTask(Runnable runnable, T result) throws IllegalAccessException {
        return getCpuPool().submit(runnable, result);
    }
}
