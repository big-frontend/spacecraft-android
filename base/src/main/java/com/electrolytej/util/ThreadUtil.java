package com.electrolytej.util;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 当要处理的任务都是io密集型，则可以申请超过cpu线程数(cpu核数*2)的线程，让cpu中的每一条线程都在处理任务。
 * 当要处理的任务都是cpu密集型，则可以申请cpu线程数的一半线程，溜一半用来快速切换，从而提高高并发的能力，快速响应任务，快速切换任务，快速解决
 */

public class ThreadUtil {

}
