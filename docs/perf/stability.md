# 稳定性(crash & anr)
应用退出的情况:
1. 主动退出，Process.killProcess,exit等
2. 崩溃
3. 系统重启:系统出现异常、断点、用户主动重启等
4. 被系统杀死:被lmk杀掉，从系统任务栏划掉
5. ANR

## 指标
- UV崩溃率=发生崩溃的UV/登录UV
- PV崩溃率/启动崩溃率/重复崩溃率
- UV异常率(包括应用退出的情况3,4,5) = 发生异常退出或崩溃的 UV / 登录 UV

## 监控

js crasher| desc
|---|---|
ErrorUtils.js|react native提供的捕获类
[Sentry](https://develop.sentry.dev/)|js code,sentry提供server+client采集方案

| java/kotlin crasher| desc
|---|---|
bugly |业界标杆
[xCrash](https://github.com/iqiyi/xCrash)| 
[acra](https://github.com/ACRA/acra)| 捕获前端与收集后端功能完整
Dropbox|Android系统
[Sentry](https://develop.sentry.dev/)| java sentry,sentry提供server+client采集方案

| c/cc crasher| desc
|---|---|
[breakpad](https://github.com/google/breakpad)|权威，跨平台，代码量大
[coffeecatch](https://github.com/xroche/coffeecatch)|实现简介，兼容性问题
[xCrash](https://github.com/iqiyi/xCrash)| 
Dropbox|Android系统
[Sentry](https://develop.sentry.dev/)| cpp sentry,sentry提供server+client采集方案

## 案例
anr常见根源
- 应用在主线程上非常缓慢地执行涉及 I/O 的操作。
- 应用在主线程上进行长时间的计算。
- 主线程在对另一个进程进行同步 binder 调用，而后者需要很长时间才能返回。
- 主线程处于阻塞状态，为发生在另一个线程上的长操作等待同步的块。
- 主线程在进程中或通过 binder 调用与另一个线程之间发生死锁。主线程不只是在等待长操作执行完毕，而且处于死锁状态。


## 参考资料
[得物App Android Crash治理演进](https://juejin.cn/post/7001060315056046117)

[得物App ANR监控平台设计](https://juejin.cn/post/7009297034440081422)

[ANR](https://developer.android.com/topic/performance/vitals/anr)