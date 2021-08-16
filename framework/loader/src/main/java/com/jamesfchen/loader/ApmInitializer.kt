package com.jamesfchen.loader

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ProcessUtils
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.perf.metrics.AddTrace
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.bugly.crashreport.CrashReport.UserStrategy
import com.tencent.matrix.Matrix
import com.tencent.matrix.iocanary.IOCanaryPlugin
import com.tencent.matrix.iocanary.config.IOConfig
import com.tencent.matrix.resource.ResourcePlugin
import com.tencent.matrix.resource.config.ResourceConfig
import com.tencent.matrix.trace.TracePlugin
import com.tencent.matrix.trace.config.TraceConfig
import com.tencent.matrix.util.MatrixLog
import com.tencent.sqlitelint.SQLiteLint
import com.tencent.sqlitelint.SQLiteLintPlugin
import com.tencent.sqlitelint.config.SQLiteLintConfig
import com.jamesfchen.common.DynamicConfigImplDemo

class ApmInitializer : Initializer<Unit> {
    @AddTrace(name = "Apminitializer#create",enabled = true)
    override fun create(context: Context) {
        Log.d("cjf", "ApmInitializer#create")

        val strategy = UserStrategy(context)
//        strategy.setAppVersion(BuildConfig.VERSION_NAME);
        //        strategy.setAppVersion(BuildConfig.VERSION_NAME);
        strategy.isBuglyLogUpload = true
        val packageName: String = context.packageName
        strategy.appPackageName = packageName
        val processName = ProcessUtils.getCurrentProcessName()
        strategy.isUploadProcess = processName == null || processName == packageName
//        strategy.setAppReportDelay(20000);   //改为20s
        //        strategy.setAppReportDelay(20000);   //改为20s
        strategy.appChannel = "huawei"
//        CrashReport.setUserSceneTag(context, 9527); // 上报后的Crash会显示该标签
        //        CrashReport.setUserSceneTag(context, 9527); // 上报后的Crash会显示该标签
        CrashReport.setIsDevelopmentDevice(context, BuildConfig.DEBUG)
        CrashReport.initCrashReport(context, strategy)
//        val firebaseOptions =  FirebaseOptions.Builder()
//                .setProjectId("spacecraft-22dc1")
//                .setApplicationId(BuildConfig.APPLICATION_ID)
//                .setApiKey("AIzaSyC17Cg6xF-jk_ABR3_6OtYD3VBWFeoXKWY")
//                .setStorageBucket("spacecraft-22dc1.appspot.com")
//                .build()
//        FirebaseApp.initializeApp(context,firebaseOptions);
        val crashlytics = FirebaseCrashlytics.getInstance()
        crashlytics.setCrashlyticsCollectionEnabled(true)

        startMatrix()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }

    private fun startMatrix() {
        val dynamicConfig = DynamicConfigImplDemo()
        val matrixEnable: Boolean = dynamicConfig.isMatrixEnable()
        val fpsEnable: Boolean = dynamicConfig.isFPSEnable()
        val traceEnable: Boolean = dynamicConfig.isTraceEnable()
        MatrixLog.i(App.TAG, "MatrixApplication.onCreate")
//        val builder = Matrix.Builder(this)
//        builder.patchListener(TestPluginListener(this))
//
//        //trace
//        val traceConfig = TraceConfig.Builder()
//            .dynamicConfig(dynamicConfig)
//            .enableFPS(fpsEnable)
//            .enableEvilMethodTrace(traceEnable)
//            .enableAnrTrace(traceEnable)
//            .enableStartup(traceEnable)
//            .splashActivities("com.hawksjamesf.myhome.ui.SplashActivity;")
//            .isDebug(true)
//            .isDevEnv(false)
//            .build()
//        val tracePlugin = TracePlugin(traceConfig)
//        builder.plugin(tracePlugin)
//        if (matrixEnable) {
//
//            //resource
//            builder.plugin(
//                ResourcePlugin(
//                    ResourceConfig.Builder()
//                        .dynamicConfig(dynamicConfig)
//                        .setDumpHprof(false)
//                        .setDetectDebuger(true) //only set true when in sample, not in your app
//                        .build()
//                )
//            )
//            ResourcePlugin.activityLeakFixer(this)
//
//            //io
//            val ioCanaryPlugin = IOCanaryPlugin(
//                IOConfig.Builder()
//                    .dynamicConfig(dynamicConfig)
//                    .build()
//            )
//            builder.plugin(ioCanaryPlugin)
//
//
//            // prevent api 19 UnsatisfiedLinkError
//            //sqlite
//            val config = initSQLiteLintConfig()
//            val sqLiteLintPlugin = SQLiteLintPlugin(config)
//            builder.plugin(sqLiteLintPlugin)
//
////            ThreadWatcher threadWatcher = new ThreadWatcher(new ThreadConfig.Builder().dynamicConfig(dynamicConfig).build());
////            builder.plugin(threadWatcher);
//        }
//        Matrix.init(builder.build())
//
//        //start only startup tracer, close other tracer.
//        tracePlugin.start()
//        //        Matrix.with().getPluginByClass(ThreadWatcher.class).start();
//        MatrixLog.i("Matrix.HackCallback", "end:%s", System.currentTimeMillis())
    }

    private fun initSQLiteLintConfig(): SQLiteLintConfig? {
        return try {
            /**
             * HOOK模式下，SQLiteLint会自己去获取所有已执行的sql语句及其耗时(by hooking sqlite3_profile)
             * @see 而另一个模式：SQLiteLint.SqlExecutionCallbackMode.CUSTOM_NOTIFY , 则需要调用 {@link SQLiteLint.notifySqlExecution
             * @see TestSQLiteLintActivity.doTest
             */
            SQLiteLintConfig(SQLiteLint.SqlExecutionCallbackMode.HOOK)
        } catch (t: Throwable) {
            SQLiteLintConfig(SQLiteLint.SqlExecutionCallbackMode.HOOK)
        }
    }
}