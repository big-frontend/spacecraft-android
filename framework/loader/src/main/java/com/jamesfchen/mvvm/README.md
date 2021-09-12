viewbinding
简化findviewbyid的方案有
- kotlin的合成方法(类型不安全，空不安全，只支持kotlin)
- viewbinding

在编译期通过扫描layout布局文件生成一个对应的ViewBinding类，编译期的时候会通过layout对代码做空安全检查与类型安全检查,检查不合法会发生编译报错，这也是其两大优点。


## MVVM

mvvm中最为重要的是viewmodel的实现，如何实现数据与视图的绑定。这里有两个方案一个利用databinding工具，一个自己用kotlin flow(rxjava)实现自动响应框架

- databinding
databinding工具在编译期会解析layout文件，然后生成两个布局文件，一个是android系统渲染的布局文件，一个是databing响应框架的配置文件，并且生成 android系统渲染布局文件对应的ViewBinding。
ViewBinding调用executeBindings进行数据绑定。

通过改变ObservableInt数据，监听该数据的ViewDataBinding$WeakPropertyListener会将dirty flag标脏然后通知ViewDataBinding#requestRebind然后进行下一帧重绘

[如何构建Android MVVM 应用框架](https://tech.meituan.com/2016/11/11/android-mvvm.html)
- kotli flow(Rxjava) + ViewBinding 约等于 databinding