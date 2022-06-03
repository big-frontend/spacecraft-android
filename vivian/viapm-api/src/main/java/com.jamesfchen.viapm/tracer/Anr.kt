package com.jamesfchen.viapm.tracer

import com.jamesfchen.lifecycle.AppLifecycle
import com.jamesfchen.lifecycle.IAppLifecycleObserver

/**
 * 对于Activity的超过5s启动就会发送anr，然后从data/trace.txt获取anr日志
 */
const val TAG_ANR_MONITOR = "anr-monitor"
@AppLifecycle
class AnrItem : IAppLifecycleObserver {
}