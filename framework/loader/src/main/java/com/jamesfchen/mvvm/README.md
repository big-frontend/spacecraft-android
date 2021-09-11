viewbinding
简化findviewbyid的方案有
- kotlin的合成方法(类型不安全，空不安全，只支持kotlin)
- viewbinding

在编译期通过扫描layout布局文件生成一个对应的ViewBinding类，编译期的时候会通过layout对代码做空安全检查与类型安全检查,检查不合法会发生编译报错，这也是其两大优点。


databinding


[如何构建Android MVVM 应用框架](https://tech.meituan.com/2016/11/11/android-mvvm.html)