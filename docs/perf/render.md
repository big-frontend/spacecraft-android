# 渲染(Render)/UI

对于react native这样通过桥实现的跨平台框架，渲染中常常出现白屏、文字渲染错位问题。

## 指标

可交互时间(Time to Interactive,TTI)

shopify's TTI
> At this point, you might start wondering what is considered "rendered"? The thing is, React Native's render doesn't guarantee that the screen is already interactive. Our library injects an invisible marker view with a native counterpart that reports that the view actually did move to the window on the native side. It allows measuring actual end-to-end performance, including React Native to native bridge communication time. Therefore, this is what we consider TTI—the time a screen takes to get rendered on the native side and become interactive to the user.

携程TTI
> 一般在收到统计页面性能需求的时候，开发人员最常规的做法是在页面初始化的时候，设置一个时间点，然后在渲染所需的一个(组)服务发送完，页面渲染之后，设置一个结束点，两者相减，就是页面的可交互时间。


## 监控

- [携程页面性能](https://mp.weixin.qq.com/s?__biz=MjM5MDI3MjA5MQ==&mid=2697269379&idx=1&sn=1227a77caf29ae0e732d976f3f909540&scene=21#wechat_redirect)
- [shopify TTI](https://shopify.engineering/measuring-react-native-rendering-times)

携程的TTI只针对页面，shopify则更广，包括应用启动TTI、页面切换TTI、页面再次刷新TTI。携程页面初始化为开始时间，TTI计算携程认为掐头去尾的页面区域存在大于两组的文字则是截止时间。

shopifyTTI 开始时间

- 应用启动TTI：Application#onCreate为开始时间
- 页面切换TTI：用户点击跳转事件为开始时间
- 页面再次刷新TTI: 刷新时间触发为开始时间，比如用户下拉

shopify认为完全显示出来才是截止时间。

## 案例
布局层级过深 、过度绘制等问题


## 参考资料
[Analyze with Profile GPU Rendering](https://developer.android.com/topic/performance/rendering/profile-gpu)

[Inspect GPU rendering speed and overdraw](https://developer.android.com/topic/performance/rendering/inspect-gpu-rendering#profile_rendering)

[Time to interactive](https://developer.mozilla.org/en-US/docs/Glossary/Time_to_interactive)