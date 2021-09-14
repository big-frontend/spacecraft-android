## 协程
- 基于线程池api
- 使用阻塞的方法写出非阻塞式的代码，解决并发中的地狱回调，关键字suspend

CoroutineScope(管理协程，持有协程CoroutineContext)
    - MainScope
    - GlobalScope
CoroutineContext(Element)
    - Job
        - JobSupport
    - ContinuationInterceptor(可以拦截Continuation，比如线程切换)
        - CoroutineDispatcher

Continuation(程续体，协程通过Continuation将线程切回原来的线程)
    - BaseContinuationImpl


### 协程作用域
- runBlocking：顶层函数 与 coroutineScope不同，会阻塞当前线程等待
- GlobalScope:全局协程作用域，对应整个应用
- 自定义作用域：自定义作用域可以绑定组件的生命周期防止内存泄露

### 调度器
- Main:android中的主线程
- IO
- Default：Cpu密集型
- UnConfined:非限制调度器，指定线程可能会随着挂起的函数变化

### 协程构建器
- launch:启动一个协程会返回一个Job对象，通过Job#cancel可以取消协程
- async：启动协程之后放回Deferred对象，通过Deferred#await获取结果，类似java的Future,相比较普通的挂起函数，async是可以并发执行任务的

### 挂起函数(suspend)
suspend是一个函数的关键字，仅仅起着提醒的作用，一般挂起函数的使用场景有：1.耗时操作(io,cpu) 2. 等待操作(delay方法)


## 协程通信(channel)
Channel是一个面向协程之间数据传输的BlockQueue

- 创建Channel
    - 直接创建对象
    - 扩展函数produce
- 发送数据：send
- 接收数据：receive

## 多协程并发
为了解决原子性问题，提供了Mutex，锁可以挂起非阻塞