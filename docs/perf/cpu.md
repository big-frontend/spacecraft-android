# cpu

Tracer是trace cpu工具的统称，可用来分析卡顿、启动、渲染问题，帮忙我们快速找出慢函数。

名词解释

- wall duration:函数执行时间(包含等待时间)，函数wall时间 = 运行时间+其他时间(阻塞(io、加锁)、就绪等情况)
- wall self time:函数执行时间(不包含等待时间)
- cpu duration:cpu执行时间 = cpu在用户态运行时间+cpu在内核态运行时间
- cpu self time

[墙上时钟时间 ，用户cpu时间 ，系统cpu时间](https://www.cnblogs.com/vinozly/p/5078755.html)

android平台的trace分析工具
- Android Studio CPU Profiler:整合了 systrace(命令行：`py -2 systrace.py --help`) 、traceview、simpleperf
- Perfetto 命令行工具（Android 10 及更高版本）
- System tracing utility:android手机设置中开发者模式提供的“系统跟踪”功能

| trace | trace函数 |类型|图表类型|性能损耗
| --- | --- |--- | ---|---|
| traceview | java代码使用Debug#startMethodTracing/stopMethodTracing |instrument|Call Chart|基于android runtime函数调用的event，性能损耗大
| traceview | java代码调用Debug#startMethodTracingSampling/stopMethodTracing  |sample|Call Chart|traceview提供的sample类型采集trace，性能损耗比instrument小
Nanoscope| ... |instrument|Call Chart|在ArtMethod执行入口和执行结束位置增加埋点代码，性能损耗小
| systrace | java代码使用Trace#beginSection/endSection, cpp代码使用ATrace_beginSection/ATrace_endSection|sample|Call Chart|systrace 封装linux的ftrace，性能损耗小
| simpleperf |... |sample|Frame Chart|部分功能封装systrace |sample|利用 CPU 的性能监控单元（PMU）提供的硬件 perf 事件，性能损耗小

> ps:instrument类型的trace工具，既可以通过命令行脚本start/stop录制下trace文件，也可以通过代码中的Debug#startMethodTracing/stopMethodTracing录制下两函数范围内的trace文件。sample类型的trace工具，仅通过命令行脚本start/stop录制下埋了Trace#beginSection/endSection的trace文件。


React Native框架提供的trace类是为systrace工具埋入trace数据
| lang  |trace    |
| --- | --- 
|  javascript | Systrace.js |  
| cpp | ... |  
|java|SystraceMessage.java/Systrace.java|

## 参考资料
[Profiling](https://reactnative.dev/docs/profiling)

[Understanding Systrace](https://source.android.com/docs/core/tests/debug/systrace)

[Overview of system tracing](https://developer.android.com/topic/performance/tracing/)

[Perfetto](https://perfetto.dev/docs/)

[Catapult](https://chromium.googlesource.com/catapult/+/HEAD/README.md)

