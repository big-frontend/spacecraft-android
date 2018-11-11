&emsp;&emsp;MVP的出现是为了解决MVC中C出现的问题，随着代码量膨胀，Activity、Fragment、Dialog这些个控制器代码量越来越臃肿，需要将部分的代码抽离出来进行分离。

&emsp;&emsp;在新的架构MVP中，V层发生了变化，并不是单纯的View控件，Activity、Fragment、Dialog，逐渐变成了View层里面的元素，而原来的Activity、Fragment、Dialog之所以是属于C层，是其有何M层交互的逻辑，这部分逻辑在MVP中被移到了P层，所以Activity、Fragment、Dialog就被变成很纯粹的V层。

&emsp;&emsp;了解MVC到MVP演变的缘由，再来看看框架的设计。

MVC：
```
V  <--->  C
^         ^
|__> M <__|
```

MVP:
```
  V <=> P <=> M
```
在交互中，三者发生了变化，由原来的闭环变成了双向交互。

&emsp;&emsp;V要访问M,需要通过P,所以需要为V提供访问接口；P要响应V，所以需要V的响应接口。故，用一个Contract类，规定两者的访问/响应规则，比如在P层规定大量的网络访问接口，在V层规定大量即将更新的UI。但是随着访问和响应的接口膨胀，接口的维护就变得很繁琐。明明可以通过私聊的形式解决问题，却要使用契约来制订规则，有利有弊吧。而MVVM就不用规定这些大量的接口，在V层中注入大量的ViewModel，从而实现自动更新，所以在VM层必然会存在大量的UI需要的数据，在Android中会延长其生命周期LiveData，而P层依然需要定义接口给V层访问。