# 卡顿(jank)

每秒帧数(帧率,60fps) 或者每帧耗时(16ms/frame))都可以用来衡量是否发生掉帧。

## 指标

慢帧(16ms ~ 700ms)、冻帧(700ms ~ 5s)、ANR(5s，Android平台特有指标)

## 监控

- 监控Looper#loop前后的Printer#println方法:开源项目matrix
- 计算ChoreographerCompat.FrameCallback两次时间差：react natvie平台提供的FpsView

react native提供监控js thread 与 ui thread帧率指标类FpsView，每次500ms就会重新从FpsDebugFrameCallback获取帧数据。
```java
public class FpsDebugFrameCallback extends ChoreographerCompat.FrameCallback {
  @Override
  public void doFrame(long l) {
    ...
    //react框架帧数
    if (mDidJSUpdateUiDuringFrameDetector.getDidJSHitFrameAndCleanup(lastFrameStartTime, l)) {
      mNumFrameCallbacksWithBatchDispatches++;
    }
    //android平台帧数
    mNumFrameCallbacks++;
    ...
    if (mChoreographer != null) {
      mChoreographer.postFrameCallback(this);
    }
    ...
  }
}
  public double getFPS() {
    if (mLastFrameTime == mFirstFrameTime) {
      return 0;
    }
    //getNumFrames:mNumFrameCallbacks
    return ((double) (getNumFrames()) * 1e9) / (mLastFrameTime - mFirstFrameTime);
  }

  public double getJSFPS() {
    if (mLastFrameTime == mFirstFrameTime) {
      return 0;
    }
    //getNumJSFrames:mNumFrameCallbacksWithBatchDispatches
    return ((double) (getNumJSFrames()) * 1e9) / (mLastFrameTime - mFirstFrameTime);
  }
```
ui thread帧率主要通过两次postFrameCallback，能计算出时间差、帧率。而js thread帧率主要通过DidJSUpdateUiDuringFrameDetector#getDidJSHitFrameAndCleanup判断一帧是否完成。

```java
  public synchronized boolean getDidJSHitFrameAndCleanup(
      long frameStartTimeNanos, long frameEndTimeNanos) {
    // Case 1: We dispatched a UI update
    boolean finishedUiUpdate =
        hasEventBetweenTimestamps(
            mViewHierarchyUpdateFinishedEvents, frameStartTimeNanos, frameEndTimeNanos);
    boolean didEndFrameIdle = didEndFrameIdle(frameStartTimeNanos, frameEndTimeNanos);

    boolean hitFrame;
    if (finishedUiUpdate) {
      hitFrame = true;
    } else {
      // Case 2: Ended idle but no UI was enqueued during that frame
      hitFrame =
          didEndFrameIdle
              && !hasEventBetweenTimestamps(
                  mViewHierarchyUpdateEnqueuedEvents, frameStartTimeNanos, frameEndTimeNanos);
    }

    cleanUp(mTransitionToIdleEvents, frameEndTimeNanos);
    cleanUp(mTransitionToBusyEvents, frameEndTimeNanos);
    cleanUp(mViewHierarchyUpdateEnqueuedEvents, frameEndTimeNanos);
    cleanUp(mViewHierarchyUpdateFinishedEvents, frameEndTimeNanos);

    mWasIdleAtEndOfLastFrame = didEndFrameIdle;

    return hitFrame;
  }
```

我们知道从js bundle加载到页面渲染完成，会有大量js <--> cpp <--> java 操作。react native在这里做了细分

- onTransitionToBridgeBusy/onTransitionToBridgeIdle: 大量js <--> cpp <--> java 桥操作，onTransitionToBridgeBusy/onTransitionToBridgeIdle每次回调都会记录时间到mTransitionToIdleEvents/mTransitionToBusyEvents
- onViewHierarchyUpdateEnqueued/onViewHierarchyUpdateFinished:纯java代码操作，从全部绘制指令入栈到完成绘制。onViewHierarchyUpdateEnqueued/onViewHierarchyUpdateFinished的每次回调都会记录到mViewHierarchyUpdateEnqueuedEvents/mViewHierarchyUpdateFinishedEvents

了解这些我们再来看看getDidJSHitFrameAndCleanup函数，react框架一帧的完成存在这样情况。

1. 如果 上一帧vsync的时间 <=mViewHierarchyUpdateFinishedEvents获取的时间 < 当前帧vsync的时间,那么说明一帧完成。
2. 如果条件一不成立，说明绘制指令没执行。这个时候如果bridge处于idle(从mTransitionToIdleEvents获取的最新时间 > 从mTransitionToBusyEvents获取的最新时间) 并且 所有绘制指令已经入队列，等待vsync到来执行绘制指令(从mViewHierarchyUpdateEnqueuedEvents获取的时间不在上一帧vsync的时间与当前帧vsync的时间的区间内)，则说明js thread一帧完成。

react native 认为framesDropped >= 4 才是算掉帧，小于4都算容错区间可宽容理解。每次framesDropped >= 4 就口吃一次,也就是掉帧了得注意了(mTotal4PlusFrameStutters++)。

造成卡顿的原因有很多

- 内存问题：内存不足触发频繁gc导致主线程阻塞
- 启动问题：启动过程中存在慢函数或者锁等待
- 渲染问题：ui布局层级深或者过度重绘

所以监控系统在发生卡顿时卡顿帧率信息、内存信息、函数调用堆栈等信息都要采集。

## 分析
Tracer分析耗时函数 + mat分析内存紧张时情况+ android Profile GPU Rendering or Profile HWUI rendering

## 参考资料
[Analyze with Profile GPU Rendering](https://developer.android.com/topic/performance/rendering/profile-gpu)

[Inspect GPU rendering speed and overdraw](https://developer.android.com/topic/performance/rendering/inspect-gpu-rendering#profile_rendering)

[Tracking jank](https://developer.android.com/topic/performance/vitals/tracking_jank)