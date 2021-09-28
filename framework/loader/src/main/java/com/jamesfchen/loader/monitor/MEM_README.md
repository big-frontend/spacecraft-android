内存不足会造成两个问题异常(oom,内存分配失败，llk,设备重启等)与卡顿(触发频繁的gc)

内存优化
- 设备分级
    1. 设备分级:对于低端设备可以关闭动画；使用RBG_565
    2. 缓存管理:设计一套统一的缓存管理机制
    3. 进程模型：一个空进程占用10m，减少应用启动的进程数，减少常驻进程
    4. 安装包大小：推出lite版app
- 图片优化
    1. 统一图片库：低端机使用使用RBG_565、更加严格的缩放算法，使用Glide、Fresco
    2. 统一监控：大图监控(图片长度超过View的大小,及时弹出提醒弹窗，超宽率)、重复图片监控(bitmap的像素完全相同，但是有多个对象存在，通过Hprof分析检测)、图片总内存(更加不同系统、屏幕分辨率等不同维度统计图片占用的内存，便于在oom知道图片内存占用量)
    3. 内存泄漏：同一个对象泄漏、新对象泄漏。
            java内存泄漏：难做线上监控，因为hprof内存快照文件太大，可以对其进行裁剪压缩。比如一个 100MB 的文件裁剪后一般只剩下 30MB 左右，使用 7zip 压缩最后小于 10MB，增加了文件上传的成功率。
            oom监控：美团的Probe，风险较大，可能会造成二次崩溃
            native内存监控：malloc debug or malloc hook，不稳定
            针对无法重编 so 的情况：
            针对可重编的 so 情况
内存监控：
    - 内存异常率
    - 内存触顶率:超过最大堆的85%限制，gc就会频繁，容易oom与卡顿

## 内存分析(mat 、 )
mat 的坑
- android hprof 转为mat hprof：hprof-conv android.hprof mat.hprof
- 新版mat必须支持jdk11 ，在ini文件首行配置：
```
-vm
D:/android-studio/jre/bin/javaw.exe
```

Shallow Size：对象自身占用的内存大小，不包括它引用的对象
Retained Size：被GC后Heap上释放的内存大小，即当前对象大小+当前对象可直接或间接引用到的对象的大小总和
out going：查看对象为什么耗内存，我们看到一个线程池占用了>25mb的内存
in going：查看对象被谁引用

分析
- 静息态内存分析
- 运行时动态内存分析

## 总共发生了2743次oom影响了2529个用户
oom原因
- 内存泄露：
    - Activity被ApplicationProxy持有
    - 视频控件没有调用release，导致注册事件没有被注销
    - Webview被H5Plugin持有，没有被释放
- bitmap图片


[使用内存性能分析器查看应用的内存使用情况](https://developer.android.com/studio/profile/memory-profiler)
[Android 内存暴减的秘密](https://cloud.tencent.com/developer/article/1013705)