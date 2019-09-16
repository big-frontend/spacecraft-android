## 什么是异步？什么是并发？什么是并行？
进程是系统进行资源分配和调度的基本单位。

1. 并行(parallax)是对于进程来说的，多个进程在同一时间运行，期间可能会有资源的相互交换。多进程的模式常见于框架，比如Android，Framework层运行着一个叫做systemserver的进程，其拥有这AMS PMS这样的线程，在App层运行着各个App进程，这些进程通过Binder进程IPC通讯（rpc）。多进程能让各个App像沙盒一样安全，并且单独运行不受其他进程影响。
2. 并发(concurrence)是对于线程来说的，也是其Java语言的特点。但是并发只是看似在同一时间运行，其利用的是cpu的切片。线程中的资源可以共享，不同于进程是隔离的。所以对于系统来说，线程带来的开销可以比进程小。在systemserver进程中AMS PMS都是线程,它们在Binder线程池中。
3. 异步(asynchronization)是线程中的回调,由于JavaScript语言的特性，其运行在单线程中，这样过就会出现问题，在其之中不能执行过于耗时的任务这样带来的就是UI卡顿,比如发送100个不相互依赖的请求，在单线程中这100个请求会按照顺序执行，可想而知如果第50个一直在等待，那么后面的也会一直在等待，后50请求对应的UI就会展示很慢给用户一种错觉，页面卡顿，而这一点像及了Java的单进程。面对这样的问题，异步突然杀出。在Android系统中，通过Handler Looper MessageQueue这三个铁三角实现了异步调度，本质上是利用了事件驱动，通过线程池并发执行耗时的任务，当其中的任务完成就会通过Handler通知UI线程更新。kotli在语言层面实现了异步，并命名为协程。

并发，说的是多个线程同时运行。并发过程中可能使用同一块内存，所以并发要考虑同步，即内存io的一致性。

异步，说的是调用者所在线程不会被调用的函数block，实现non block的方式可以使进程或者线程池。Rxjava、kotlin-coroutines

|   |  Java |  Android  | Kotlin
|---|---|---|---|
异步|   java nio 、okio、Rxjava(Scheduler) | AsyncTask(Loop Handler) | coroutines
并发|   Executor| NA|NA
并行| NA|||




----
