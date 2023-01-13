# 启动(Startup)/渲染(Render)
对于react native这样通过桥实现的跨平台框架，启动到渲染过程常常出现白屏、文字渲染错位问题，在加上启动与渲染又是紧密相关，所以两者的监控指标都很重要。

## 启动指标

Vitals提供指标(指标存疑，[App startup time](https://developer.android.com/topic/performance/vitals/launch-time))

- 冷启动(cold)：大于5s温启动需优化
- 温启动(warn)：大于2s温启动需优化
- 热启动(hot)：大于1.5s温启动需优化
- 初步显示所用时间(The time to initial display,TTID)：第一帧之前的启动耗时
- 完全显示所用时间 (The Time to full display,TTFD)：全部展示帧之前的启动耗时

Matrix中指标

- 冷启动(applicationCost、firstScreenCost、coldCost)：大于10冷启动需优化
- 温启动(warmCost)：大于4s温启动需优化

> ps:Android官网定义的启动与Matrix定义的启动有点不同，前者把页面measure、layout部分也算进启动，认为初次draw之前都算启动一部分。

## 启动监控

React Native启动监控

- 冷启动：SceneTracker
```javascript
//启动时间
import  SceneTracker from 'react-native/Libraries/Utilities/SceneTracker';
SceneTracker.addActiveSceneChangedListener((scene: Scene)=>{
    var startMs = new Date().getTime();
    console.log('runApplication start', scene);
});
```

- 启动时间监控：[react-native-startup-time](https://github.com/doomsower/react-native-startup-time)

- 基于Flipper的性能监控：[shopify/react-native-performance](https://github.com/Shopify/react-native-performance)
    
Matrix中Android端实现

- 冷启动：启动Application 到 Activity#onWindowFocusChange
- 温启动：应用进程还没有从lruprocess移除时，启动Activity 到 Activity#onWindowFocusChange

## 启动案例

- [Be aware of common issues](https://developer.android.com/topic/performance/vitals/launch-time#common)

### React Native

- 分包(code split)：[react-native-multibundler](https://github.com/smallnew/react-native-multibundler)、[metro-code-split](https://github.com/wuba/metro-code-split)
- 模块懒加载模块require:[react-native-bundle-splitter](https://github.com/kirillzyusko/react-native-bundle-splitter)


### React

- 分包：[代码分离](https://webpack.docschina.org/guides/code-splitting/)

## 启动+渲染指标：TTI

在Android端启动与渲染的指标都是分开，但是前端在定义一个页面的性能是有的指标会将启动+渲染看做一体比如TTI,还有分开算有启动之后开始首次渲染F &&FCP，渲染出主要内容的时间FMP && LCP && SI，可交互的时间TTI && TBT。

先来看看可交互时间(Time to Interactive,TTI)在react native中一些公司对其定义。

shopify's TTI
> At this point, you might start wondering what is considered "rendered"? The thing is, React Native's render doesn't guarantee that the screen is already interactive. Our library injects an invisible marker view with a native counterpart that reports that the view actually did move to the window on the native side. It allows measuring actual end-to-end performance, including React Native to native bridge communication time. Therefore, this is what we consider TTI—the time a screen takes to get rendered on the native side and become interactive to the user.

携程TTI
> 一般在收到统计页面性能需求的时候，开发人员最常规的做法是在页面初始化的时候，设置一个时间点，然后在渲染所需的一个(组)服务发送完，页面渲染之后，设置一个结束点，两者相减，就是页面的可交互时间。


## TTI 监控

- [携程页面性能](https://mp.weixin.qq.com/s?__biz=MjM5MDI3MjA5MQ==&mid=2697269379&idx=1&sn=1227a77caf29ae0e732d976f3f909540&scene=21#wechat_redirect)
- [shopify TTI](https://shopify.engineering/measuring-react-native-rendering-times)

携程的TTI只针对页面，shopify则更广，包括应用启动TTI、页面切换TTI、页面再次刷新TTI。携程页面初始化为开始时间，TTI计算携程认为掐头去尾的页面区域存在大于两组的文字则是截止时间,这个计算方式更像FCP指标而不是TTI指标。

shopify TTI 开始时间

- 应用启动TTI：Application#onCreate为开始时间
- 页面切换TTI：用户点击跳转事件为开始时间
- 页面再次刷新TTI: 刷新时间触发为开始时间，比如用户下拉

shopify认为完全显示出来才是截止时间。

## TTI：案例

- 布局层级过深 、过度绘制等问题

## 参考资料

[Analyze with Profile GPU Rendering](https://developer.android.com/topic/performance/rendering/profile-gpu)

[Inspect GPU rendering speed and overdraw](https://developer.android.com/topic/performance/rendering/inspect-gpu-rendering#profile_rendering)

[Time to interactive](https://developer.mozilla.org/en-US/docs/Glossary/Time_to_interactive)

[前端监控系列3 ｜ 如何衡量一个站点的性能好坏](https://juejin.cn/post/7143201009781702687)
