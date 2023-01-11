# 启动(startup)


启动时间：初步显示所用时间(The time to initial display,TTID)、完全显示所用时间 (The Time to full display,TTFD)

启动场景：冷启动(cold)、温启动(warn)、热启动(hot)

anr指标

- Input dispatching timed out：输入时间分发超过5s，包括按键和触屏事件。

- Broadcast of Intent：前台广播需要在10s内完成，后台广播需要在60s内完成。

- executing service：前台服务需要在20s内完成，后台则需要在200s内完成。

- ContentProvider：几乎非常少见，publish执行未在10s内完成。

- Context.startForegroundService() did not then call Service.startForeground()：应用调用startForegroundService，然后5s内未调用startForeground出现ANR或者Crash，此问题属于应用未适配Android版本sdk。

## 监控

js

- to od 
    
android

- 应用冷启动：启动Application 到 Activity#onWindowFocusChange
- 应用温启动：应用进程还没有从lruprocess移除时，启动Activity 到 Activity#onWindowFocusChange
- 应用热启动：在后台变前台时，Activity#onStart 到 Activity#onWindowFocusChange
- 初步显示所用时间：渲染首帧回调OnPreDrawListener
- 完全显示所用时间：

## 参考资料
[App startup time](https://developer.android.com/topic/performance/vitals/launch-time)

