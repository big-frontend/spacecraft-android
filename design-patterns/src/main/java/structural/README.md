Structural
==========
结构型模式主要用来表示类与类之前的关系，有三角恋、同性恋、异性恋。Adapter和Proxy就是属于中间人的角色

## Adapter

ListView、RecylerView和数据结构的关系就是使用了适配器。通过适配器将数据结构中的数据转换成子view。就好比，高压电通过电源适配器被转换成了电子设备所需要的220V。

## Proxy

Binder通信的实现。被代理对象和代理对象都实现同一个接口。而且代理对象拥有被代理对象的依赖，方便操作数据

## Bridge

## Facade

为外部提供统一接口。如果把第三方库看成子系统，那么平时我们根据自己的业务去封装第三方接口的就是一种外观模式

## Composite

## Decorate
能为类增加一些新的功能

## Flyweight
图片缓存是常常持有一个url和图片的映射表缓存池，这样可以节省内存消耗提高响应速度。

<!--ContextWrapper和ContextImpl的关系。ContextWrapper和ContextImpl实现同一个接口Context。ContextWrapper对象拥有Context对象（实质是ContextImpl）的依赖，方便操作数据-->