package com.electrolytej.loader.matrix

import android.content.Context
import android.util.Log
import com.electrolytej.loader.SApp
import com.tencent.matrix.iocanary.IOCanaryPlugin
import com.tencent.matrix.iocanary.config.IOConfig
import com.tencent.matrix.trace.TracePlugin
import com.tencent.matrix.trace.config.TraceConfig
import com.tencent.matrix.trace.tracer.SignalAnrTracer
import com.tencent.matrix.util.MatrixLog
import com.tencent.sqlitelint.SQLiteLint
import com.tencent.sqlitelint.SQLiteLintPlugin
import com.tencent.sqlitelint.config.SQLiteLintConfig
import java.io.File
import java.util.*

const val APM_TAG = "ApmInitializer"
fun configureTracePlugin(context: Context, dynamicConfig: DynamicConfigImpl): TracePlugin {
    val fpsEnable = dynamicConfig.isFPSEnable
    val traceEnable = dynamicConfig.isTraceEnable
    val signalAnrTraceEnable: Boolean = dynamicConfig.isSignalAnrTraceEnable
    val traceFileDir = File(context.filesDir, "matrix_trace")
    if (!traceFileDir.exists()) {
        if (!traceFileDir.mkdirs()) {
            MatrixLog.e(APM_TAG, "failed to create traceFileDir")
        }
    }
    val file = File("/data/user/0/com.jamesfchen.spacecraft.debug")
    Log.d(APM_TAG,"file:${Arrays.toString(context.filesDir.list())}")
    // path : /data/user/0/sample.tencent.matrix/files/matrix_trace/anr_trace
    val anrTraceFile = File(traceFileDir, "anr_trace")
    // path : /data/user/0/sample.tencent.matrix/files/matrix_trace/print_trace
    val printTraceFile = File(traceFileDir, "print_trace")
    val traceConfig: TraceConfig = TraceConfig.Builder()
        .dynamicConfig(dynamicConfig)
        .enableFPS(fpsEnable)
        .enableEvilMethodTrace(traceEnable)
        .enableAnrTrace(traceEnable)
        .enableStartup(traceEnable)
        .enableIdleHandlerTrace(traceEnable) // Introduced in Matrix 2.0
        .enableMainThreadPriorityTrace(true) // Introduced in Matrix 2.0
        .enableSignalAnrTrace(signalAnrTraceEnable) // Introduced in Matrix 2.0
        .anrTracePath(anrTraceFile.absolutePath)
        .printTracePath(printTraceFile.absolutePath)
        .splashActivities("com.jamesfchen.myhome.SplashActivity;")
        .isDebug(true)
        .isDevEnv(false)
        .build()

    //Another way to use SignalAnrTracer separately
    //useSignalAnrTraceAlone(anrTraceFile.getAbsolutePath(), printTraceFile.getAbsolutePath());
    return TracePlugin(traceConfig)
}

fun useSignalAnrTraceAlone(anrFilePath: String, printTraceFile: String) {
    val signalAnrTracer = SignalAnrTracer(SApp.getInstance(), anrFilePath, printTraceFile)
    signalAnrTracer.setSignalAnrDetectedListener { stackTrace, mMessageString, mMessageWhen, fromProcessErrorState ->
        // got an ANR
    }
    signalAnrTracer.onStartTrace()
}

//fun configureResourcePlugin(
//    context: Context,
//    dynamicConfig: DynamicConfigImplDemo
//): ResourcePlugin {
//    val intent = Intent()
//    val mode = DumpMode.MANUAL_DUMP
//    MatrixLog.i(APM_TAG, "Dump Activity Leak Mode=%s", mode)
//    intent.setClassName(context.packageName, "com.tencent.mm.ui.matrix.ManualDumpActivity")
//    val resourceConfig = ResourceConfig.Builder()
//        .dynamicConfig(dynamicConfig)
//        .setAutoDumpHprofMode(mode)
//        .setManualDumpTargetActivity(ManualDumpActivity::class.java.name)
//        .build()
//    ResourcePlugin.activityLeakFixer(SApp.getInstance())
//    return ResourcePlugin(resourceConfig)
//}

fun configureIOCanaryPlugin(
    context: Context,
    dynamicConfig: DynamicConfigImpl
): IOCanaryPlugin {
    return IOCanaryPlugin(
        IOConfig.Builder()
            .dynamicConfig(dynamicConfig)
            .build()
    )
}

fun configureSQLiteLintPlugin(): SQLiteLintPlugin {
    val sqlLiteConfig: SQLiteLintConfig

    /*
         * HOOK模式下，SQLiteLint会自己去获取所有已执行的sql语句及其耗时(by hooking sqlite3_profile)
         * @see 而另一个模式：SQLiteLint.SqlExecutionCallbackMode.CUSTOM_NOTIFY , 则需要调用 {@link SQLiteLint#notifySqlExecution(String, String, int)}来通知
         * SQLiteLint 需要分析的、已执行的sql语句及其耗时
         * @see TestSQLiteLintActivity#doTest()
         */
    // sqlLiteConfig = new SQLiteLintConfig(SQLiteLint.SqlExecutionCallbackMode.HOOK);
    sqlLiteConfig = SQLiteLintConfig(SQLiteLint.SqlExecutionCallbackMode.CUSTOM_NOTIFY)
    return SQLiteLintPlugin(sqlLiteConfig)
}

//fun configureBatteryCanary(): BatteryMonitorPlugin {
// Configuration of battery plugin is really complicated.
// See it in BatteryCanaryInitHelper.
//    return BatteryCanaryInitHelper.createMonitor()
//}