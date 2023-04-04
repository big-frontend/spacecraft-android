package com.jamesfchen.loader.androidxstartup

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import com.blankj.utilcode.util.ProcessUtils
import com.google.firebase.perf.metrics.AddTrace
import com.jamesfchen.base.BuildConfig
import com.jamesfchen.loader.SApp
import com.jamesfchen.loader.matrix.*
import com.jamesfchen.viapm.ViMonitor
import com.pgyer.pgyersdk.PgyerSDKManager
import com.pgyer.pgyersdk.pgyerenum.Features
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.bugly.crashreport.CrashReport.UserStrategy
import com.tencent.matrix.Matrix
import com.tencent.matrix.trace.TracePlugin
import com.tencent.matrix.util.MatrixLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ApmInitializer : Initializer<Unit> {

    @AddTrace(name = "Apminitializer#create", enabled = true)
    override fun create(context: Context) {
        Log.d(APM_TAG, "ApmInitializer#start")

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
//        CrashReport.setUserSceneAPM_TAG(context, 9527); // 上报后的Crash会显示该标签
        //        CrashReport.setUserSceneAPM_TAG(context, 9527); // 上报后的Crash会显示该标签
        CrashReport.setIsDevelopmentDevice(context, BuildConfig.DEBUG)
        CrashReport.initCrashReport(context, strategy)
        startFirebase(context)
//        startMatrix(context)
        GlobalScope.launch {
//            startAndroidGodEye(context)
        }
        ViMonitor.start(context as Application)
        Log.d(APM_TAG, "ApmInitializer#end")
        initPgyerSDK(context as Application);
    }
    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }

    private fun startFirebase(context: Context) {
        //        val firebaseOptions =  FirebaseOptions.Builder()
//                .setProjectId("spacecraft-22dc1")
//                .setApplicationId(BuildConfig.APPLICATION_ID)
//                .setApiKey("AIzaSyC17Cg6xF-jk_ABR3_6OtYD3VBWFeoXKWY")
//                .setStorageBucket("spacecraft-22dc1.appspot.com")
//                .build()
//        FirebaseApp.initializeApp(context,firebaseOptions);
//        val crashlytics = FirebaseCrashlytics.getInstance()
//        crashlytics.setCrashlyticsCollectionEnabled(true)
    }

    private fun startMatrix(context: Context) {
        // Switch.
        val dynamicConfig = DynamicConfigImpl()
        MatrixLog.i(APM_TAG, "Start Matrix configurations.")
        // Builder. Not necessary while some plugins can be configured separately.
        val builder = Matrix.Builder(SApp.getInstance())

        // Reporter. Matrix will callback this listener when found issue then emitting it.
        builder.pluginListener(MyPluginListener(context))

        // Configure trace canary.
        val tracePlugin: TracePlugin = configureTracePlugin(context, dynamicConfig)
        builder.plugin(tracePlugin)

        // Configure resource canary.
//        val resourcePlugin = configureResourcePlugin(context, dynamicConfig)
//        builder.plugin(resourcePlugin)

        // Configure io canary.
        val ioCanaryPlugin = configureIOCanaryPlugin(context, dynamicConfig)
        builder.plugin(ioCanaryPlugin)

        // Configure SQLite lint plugin.
        val sqLiteLintPlugin = configureSQLiteLintPlugin()
        builder.plugin(sqLiteLintPlugin)

        // Configure battery canary.
//        val batteryMonitorPlugin = configureBatteryCanary()
//        builder.plugin(batteryMonitorPlugin)

        Matrix.init(builder.build())
            .startAllPlugins()
        // Trace Plugin need call start() at the beginning.
//        tracePlugin.start()

        MatrixLog.i(APM_TAG, "Matrix configurations done.");
    }

    private suspend fun startAndroidGodEye(cxt: Context) = withContext(Dispatchers.Default) {
        Log.d(APM_TAG, "godeye configurations start.");
//        GodEye.instance().init(cxt as Application)
//        GodEyeMonitor.work(cxt, 5391)
//        GodEyeMonitor.setClassPrefixOfAppProcess(listOf("com.jamesfchen"))
//        GodEye.instance()
//            .install(GodEyeConfig.fromAssets("android-godeye-config/install.config"))
        Log.d(APM_TAG, "godeye configurations end.");
    }

    /**
     *  初始化蒲公英SDK
     * @param application
     */
    private fun initPgyerSDK(application: Application) {
        PgyerSDKManager.Init()
            .setContext(application) //设置上下问对象
            .enable(Features.CHECK_UPDATE)//开启自动更新检测（不设置默认功能关闭 ，AndroidManifest中也可以设置该属性的开关）
            .start()
    }

}