package com.jamesfchen.startup

/**
 * job
 * - attach在哪个进程
 * - 立即进行 or 延迟执行 or 根据条件执行 or MQ idle执行
 * - 启动优先级
 * - 依赖模块
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class Job() {}