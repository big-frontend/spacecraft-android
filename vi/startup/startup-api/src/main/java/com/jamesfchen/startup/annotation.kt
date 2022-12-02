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
annotation class Job(
    val name: String,
    val attachProcesses: Array<Process>,
    val policy: RunnablePolicy,
    val appPhase: Phase,
    val priority: Int,//pri越小优先级越高
    val deps: Array<String>
) {

}
enum class Process{
    MAIN,
    OTHER
}
enum class RunnablePolicy {
    INSTANT,
    DELAY,
    IF_IDLE,
    Dep
}

enum class Phase {
    ONATTACHBASECONTEXT,
    ONCREATE
}
