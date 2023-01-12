# 启动(Startup)

## 指标
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

## 监控

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
    
Matrix中Android端实现

- 冷启动：启动Application 到 Activity#onWindowFocusChange
- 温启动：应用进程还没有从lruprocess移除时，启动Activity 到 Activity#onWindowFocusChange

## 案例

- [Be aware of common issues](https://developer.android.com/topic/performance/vitals/launch-time#common)
