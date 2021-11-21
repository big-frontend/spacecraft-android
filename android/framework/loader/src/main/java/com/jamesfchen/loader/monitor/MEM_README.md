## 为什么要做内存分析
当内存不足会造成两个问题异常(oom,内存分配失败，llk,设备重启等)与卡顿(触发频繁的gc)，这个时候就需要分析内存从中找出优化点

### 术语
Shallow Size：对象自身占用的内存大小，不包括它引用的对象
Retained Size：被GC后Heap上释放的内存大小，即当前对象大小+当前对象可直接或间接引用到的对象的大小总和
outgoing reference(当前对象引用的外部对象)：查看对象为什么耗内存，我们看到一个线程池占用了>25mb的内存
ingoing reference(直接引用了当前对象的对象)：查看对象被谁引用

gc root对象：
- 类(方法区静态成员变量引用的对象)
- 活动的Thread实例
- 局部变量或者方法参数变量持有的对象(java 虚拟机堆栈 、 本地方法堆栈)
- JNILocalReference引用的对象
- JNIGlobalReference引用的对象
- synchronize关键字用到的对象

## 分代垃圾算法
大部分的对象都是"朝生夕死"，经过几次gc之后就会被转移到老年代。新生代占内存区域的1/3而老年代占内存区域的2/3，新生代使用MinorGC，老年代使用MajorGC/FullGC回收整块堆
- 新生代
gc:MinorGC
采用算法 ：标记复制法(浪费预留区域的空间，如果遇到大量存活的对象，就需要频繁复制才能释放少量的空间，所以更适合新生代)
分区：Eden区 、S0区(survivor)、S1区(survivor) 比例为8：1：1，压缩了预留空间，从原来的1/2到1/10

存活的对象会在S0与S1之间来回移动，并且年龄累加1
- 老年代
gc:MajorGC
采用的算法：标记清除算法(遇到大部分需要回收的对象时，执行效率不高；容易产生碎片化) or 标记整理算法(若存在大量存活对象，就需要移动更多的对象来换取少量的空间)

### 分析的工具
- mat
- memory profile
- LeakCanary

mat 的坑
- android hprof 转为mat hprof：hprof-conv android.hprof mat.hprof
- 新版mat必须支持jdk11 ，在ini文件首行配置：
```
window:
-vm
D:/android-studio/jre/bin/javaw.exe

macos:
-vm
/Applications/dev/Android Studio.app/Contents/jre/Contents/Home/bin
```

### 分析思路
分析
- 静息态内存分析(优化app在后台时的内存，防止被llk):做内存分析，统计大图加载；动画播放；内存泄露；数据结构不合理
- 运行时动态内存分析(优化OOM问题)：

### 内存优化
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
### 内存监控

- 内存异常率
- 内存触顶率:超过最大堆的85%限制，gc就会频繁，容易oom与卡顿

[Android 内存暴减的秘密](https://cloud.tencent.com/developer/article/1013705)
[探索 Android 内存优化方法](https://juejin.cn/post/6844903897958449166#heading-35)
[Matrix ResourceCanary](https://github.com/Tencent/matrix/wiki/Matrix-Android-ResourceCanary)
[图解 Java 垃圾回收算法及详细过程！](https://xie.infoq.cn/article/9d4830f6c0c1e2df0753f9858)
[使用内存性能分析器查看应用的内存使用情况](https://developer.android.com/studio/profile/memory-profiler)
[JVM 内存分析工具 MAT 的深度讲解与实践——进阶篇](https://juejin.cn/post/6911624328472133646#heading-24)
