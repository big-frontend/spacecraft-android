# 内存(Memory)

JVM内存

引用类型

- 强可及对象（strongly reachable）：可以通过强引用访问的对象。
- 软可及对象（softly reachable）：不是强可及对象，并且能够通过软引用访问的对象。
- 弱可及对象（weakly reachable）：不是强可及对象也不是软可及对象，并且能够通过弱引用访问的对象。
- 虚可及对象（phantomly reachable）：不是强可及对象、软可及对象，也不是弱可及对象，已经结束的，可以通过虚引用访问的对象。

清除：将引用对象的 referent 域设置为 null ，并将引用类在堆中引用的对象声明为 可结束的。

内存安全问题
- 内存泄漏
- OOM




## 分析工具
- [利用Android Studio、MAT对Android进行内存泄漏检测](https://joyrun.github.io/2016/08/08/AndroidMemoryLeak/)
- [内存分析工具 MAT 的使用](http://www.cnblogs.com/tianzhijiexian/p/4268131.html)
- [LeakCanary](https://github.com/square/leakcanary)


## 案例
- 静态变量持有短生命的对象
- 单例模式导致
- 无限循环属性动画导致。
