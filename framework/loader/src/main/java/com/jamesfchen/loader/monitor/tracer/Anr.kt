package com.jamesfchen.loader.monitor.tracer

import com.jamesfchen.loader.monitor.ILifecycleObserver
import com.jamesfchen.loader.monitor.MonitoredItem

/**
 * 对于Activity的超过5s启动就会发送anr，然后从data/trace.txt获取anr日志
 */
const val TAG_ANR_MONITOR = "anr-monitor"
@MonitoredItem
class AnrItem :ILifecycleObserver{
}