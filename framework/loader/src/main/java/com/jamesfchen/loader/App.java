package com.jamesfchen.loader;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.os.SystemClock;
import android.os.Trace;
import android.util.Log;

import com.google.firebase.perf.metrics.AddTrace;
import com.jamesfchen.common.util.Util;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;
import androidx.work.Configuration;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author:  jamesfchen
 * @since: 2017/7/4
 *
 * App启动分为冷启动、热启动
 * 冷启动：
 *  - 基础组件 amp bundle av image storage network 必须放在主线程初始
 *  - 其他组件 map 可以异步初始化
 * 热启动：
 *
 * App#attachBaseContext --> ContentProvider#attachInfo --> ContentProvider#onCreate--->App#onCreate
 *
 * Android团队提供的startup库，主要是在ContentProvider#onContext初始化各个Initializer
 * ps: 关于startup库实现的吐槽点，用ContentProvider去做初始化，感觉没啥意义，多了一次跨进程创建ContentProvider(空ContentProvider耗时2ms)，
 * 对于追求极致的启动速度，不应该减少这个操作嘛。为什么就不放在Application这个类中做，我觉得可以把InitializationProvider的代码整合到Application中。
 *
 * 总之一句话startup库就是垃圾，应该解决的是各个启动项谁可以同步初始化，谁可以异步初始化。fast start up.
 *
 *
 * matrix：
 *  * firstMethod.i       LAUNCH_ACTIVITY   onWindowFocusChange   LAUNCH_ACTIVITY    onWindowFocusChange
 *  * ^                         ^                   ^                     ^                  ^
 *  * |                         |                   |                     |                  |
 *  * |---------app---------|---|---firstActivity---|---------...---------|---careActivity---|
 *  * |<--applicationCost-->|
 *  * |<----firstScreenCost---->|
 *  * |<---------------------------allCost(cold)------------------------->|
 *  * .                         |<--allCost(warm)-->|
 *
 *  优化点：异步任务可以在splash页面onWindowFocusChange也就是出现窗口时候await，等任务都执行完成，才让其进入正在的main页面
 *
 *
 *  关于启动优化：
 *  - 闪屏优化：高端机(Android 6.0 或者 Android 7.0)去掉预览窗口 ； 合并splash与main页面，少一个activity节省100ms左右，但是会增加管理业务的复杂度
 *  - 业务梳理：(注意懒加载集中化，容易造成首页出现后无法交互)
 *  1. 根据业务的优先级加载,哪些是必须加载，哪些是可以懒加载
 *  2. 根据业务场景，通过扫一扫启动，只加载几个模块
 *  3. 推动产品做功能取舍
 *  - 业务优化
 *  1. 最理想的方式通过算法优化，比如加解密 1s被优化为10ms
 *  2. 异步预加载，要注意过得的线程预加载会让业务逻辑变得复杂
 *  - 线程优化(主要减少cpu的调度带来的波动，让启动更稳定)
 *  1. 需要控制线程的数量，线程太多会互相竞争cpu资源，可以采用线程池统一管理线程，并且根据机器性能来控制数量(cpu核心数，io密集型2*core+1,cpu密集新core+1)
 *  2. 检查线程间的锁，避免主线程长时间等待其他线程释放锁
 *  3. 使用优秀的启动库阿里alpha 微信mmkernel
 *  - GC 优化
 *  1. 减少gc次数，避免主线程长时间卡顿。通过Debug.startAllocCounting监控gc的耗时情况，特别是阻塞式同步 GC 的总次数和耗时。
 *  如果存在gc同步等待，就需要用Allocation工具分析内存，有可能存在这样的情况：使用大量的字符串拼接创建大量对象，特别是序列化与反序列化过程；
 *  频繁创建对象，比如网络路与图片库中的Byte数组、Buffer数组可以复用，可以考虑移到native
 *  2. java对象的逃逸(一个对象的指针被多个方法或者线程引用)，保证一个对象生命周期尽量短，在栈上就被销毁
 *  - 系统调用优化(通过systrace查看System Server cpu的使用情况)
 *  1. 尽量不做系统调用pms操作、binder调用等待
 *  2. 不要过早启动其他进程，回合System Server相互竞争cpu资源，当系统资源不足时，就会触发low memory killer，导致系统杀死或者拉起(保活)大量进程，从而影响前台进程
 *
 *
 *  进阶：
 *  - io优化：使用随机读写的数据结构
 *  - 数据重排
 *  - 类重排:使用redex重排dex中的类顺序
 *  - 资源文件重排：修改7zip支持传入列表顺序
 *  - 类加载：通过hook java虚拟机，去掉verify class的过程，在类加载的过程可以提升50%的速度
 *  - 保活：Hyper Boost or Hardcoder
 *  - 插件化与热修复：不行了
 */
@com.jamesfchen.lifecycle.App
public class App extends Application implements Configuration.Provider {
//    private static AppComponent sAppComponent;
//    private static NetComponent sNetComponent;
    private static App app;
    public static App getInstance() {
        if (app == null) {
            throw new IllegalStateException("app is null");
        }
        return app;
    }

    private static final String PROCESS_1 = "com.hawksjamesf.spacecraft.debug";
    private static final String PROCESS_2 = ":mock_server";
    private static final String PROCESS_3 = ":mock_jobserver";
    private long start = 0;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        app = this;
        Log.d("cjf","App#attachBaseContext");
        for (PackageInfo pack : getPackageManager().getInstalledPackages(PackageManager.GET_PROVIDERS)) {
            ProviderInfo[] providers = pack.providers;
            if (providers == null) continue;
            for (ProviderInfo provider : providers) {
                if ((provider.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                    if (provider.packageName.equals(getPackageName())){
                        Log.d("cjf", "当前provider package: "+provider.packageName+" authority: " + provider.authority+" name: "+provider.name);
                    }else{
//                        Log.d("cjf", "第三方provider package: "+provider.packageName+" authority: " + provider.authority+" name: "+provider.name);
                    }
                }else {
//                    Log.d("cjf", "系统provider package: "+provider.packageName+" authority: " + provider.authority+" name: "+provider.name);

                }
            }
        }
        start = SystemClock.elapsedRealtime();
//        Debug.startMethodTracing(getExternalCacheDir().getParent()+"/contentprovidertrace");
        Trace.beginSection("contentprovidertrace");
    }

    /**
     * 性能优化App#attachBaseContext --> ContentProvider#onCreate--->App#onCreate
     * 一个空的ContentProvider耗时2ms
     * 2830ms优化到两位位数
     */
    @AddTrace(name = "App#onCreate",enabled = true)
    @Override
    public void onCreate() {
        Log.d("cjf","ContentProvider#onCreate消耗时间："+(SystemClock.elapsedRealtime()-start)+"ms");
//        Debug.stopMethodTracing();
        Trace.endSection();
        super.onCreate();

//        sNetComponent = DaggerNetComponent.builder()
//                .netModule(new NetModule())
//                .build();

//        sAppComponent = DaggerAppComponent.builder()
////                .netComponent(sNetComponent)
//                .appModule(new AppModule(this))
//                .sigInModule(new SigInModule())
//                .build();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//        Utils.init(this);
        Util.init(this);
//        ProcessLifecycleOwner.get().getLifecycle().addObserver(new AppLifecycleObserver());
    }

//    public static AppComponent getAppComponent() {
//        return sAppComponent;
//    }

//    public static NetComponent getNetComponet() {
//        return sNetComponent;
//    }

    @NonNull
    @Override
    public Configuration getWorkManagerConfiguration() {
        return new Configuration.Builder()
                .setMinimumLoggingLevel(Log.VERBOSE)
                .build();
    }
}