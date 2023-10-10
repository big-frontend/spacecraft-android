package com.jamesfchen.vi.startup

import android.app.Activity
import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.os.SystemClock
import android.text.TextUtils
import android.util.ArrayMap
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.Keep
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatViewInflater
import androidx.core.view.LayoutInflaterCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.jamesfchen.vi.ActivityThreadHacker
import com.jamesfchen.vi.lifecycle.AbsActivitiesLifecycleObserver
import com.jamesfchen.vi.lifecycle.AbsAppLifecycleObserver
import com.jamesfchen.vi.util.CpuUtil
import com.jamesfchen.vi.util.DeviceUtil
import com.jamesfchen.vi.util.MemoryUtil
import java.lang.ref.WeakReference
import java.util.Locale

/**
 *
 * 如果启动过程中存在转场动画需要扣除
 * app start up
 * - 冷启动
 * - 温启动
 *
 * activity start up
 *  - 页面最早渲染出2个文本控件的时间(tti)
 *  - onCreate 到 onWindowFocusChanged 的时间
 *
 *  service start up
 *
 *  ContentProvider start up
 *  - App#attachBaseContext --> ContentProvider#attachInfo --> ContentProvider#onCreate--->App#onCreate
 */
const val TAG_STARTUP_MONITOR = "startup-monitor"

@Keep
class StartupItem(val app: Application) : AbsActivitiesLifecycleObserver(),
    AbsAppLifecycleObserver {
    companion object {
        private val sFocusActivitySet = mutableSetOf<String>()
    }

    private val arrayMap = ArrayMap<ComponentName, Long>()
    private val activityState = ArrayMap<ComponentName, Int>()
    private var mStartupView = StartupView(app)
    private var mColdCost = 0L
    private var mWarmCost = 0L
    private var mHotCost = 0L
    private var mApplicationCost = 0L
    private var mIsColdStartUp = false
    private var mIsWarmStartUp = false
    private var mIsHotStartUp = false
    private var mActiveActivityCount = 0
    private var mLastCreateActivity = 0L
    private var mLastForegroundActivity = 0L
    private var mFirstScreenCost: Long = 0

    override fun onAppCreate() {
        Log.e(TAG_STARTUP_MONITOR, "onAppCreate")
        mStartupView.setVisible(true)
        mIsColdStartUp = true
    }

    override fun onActivityPreCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (mActiveActivityCount == 0 && !mIsColdStartUp) {//温启动
            mIsWarmStartUp = true
            mLastCreateActivity = SystemClock.uptimeMillis()
        }
        arrayMap[activity.componentName] = SystemClock.uptimeMillis()
        activityState[activity.componentName] = 1
//        Log.e(TAG_STARTUP_MONITOR, "onActivityPreCreated")

    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        mActiveActivityCount++
        Log.e(TAG_STARTUP_MONITOR, "onActivityCreated ${mActiveActivityCount}")
    }

    override fun onFragmentPreAttached(fm: FragmentManager, f: Fragment, context: Context) {
//        Log.e(TAG_STARTUP_MONITOR, "onFragmentPreAttached ${f}")
    }

    override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
//        Log.e(TAG_STARTUP_MONITOR, "onFragmentAttached ${f}")
    }

    override fun onFragmentPreCreated(
        fm: FragmentManager,
        f: Fragment,
        savedInstanceState: Bundle?
    ) {
//        Log.e(TAG_STARTUP_MONITOR, "onFragmentPreCreated ${f}")
    }

    override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
//        Log.e(TAG_STARTUP_MONITOR, "onFragmentCreated ${f}")
    }

    /**
     * 有时在Activity#create阶段，有时在Activity#start阶段
     */
    override fun onFragmentViewCreated(
        fm: FragmentManager,
        f: Fragment,
        v: View,
        savedInstanceState: Bundle?
    ) {
//        Log.e(TAG_STARTUP_MONITOR, "onFragmentViewCreated ${f}")
    }

    override fun onActivityPostCreated(activity: Activity, savedInstanceState: Bundle?){
//        Log.e(TAG_STARTUP_MONITOR, "onActivityPostCreated ${activity}")
    }

    override fun onActivityPreStarted(activity: Activity) {
        if (!mIsColdStartUp && !mIsWarmStartUp) {
            mIsHotStartUp = true
            mLastForegroundActivity = SystemClock.uptimeMillis()
        }
        if (!activityState.contains(activity.componentName)){
            activityState[activity.componentName] = 2
            arrayMap[activity.componentName] = SystemClock.uptimeMillis()
        }
//        Log.e(TAG_STARTUP_MONITOR, "onActivityPreStarted")
    }
    override fun onActivityForeground(activity: Activity) {
//        Log.e(TAG_STARTUP_MONITOR, "onActivityForeground")
    }
    override fun onFragmentActivityCreated(
        fm: FragmentManager,
        f: Fragment,
        savedInstanceState: Bundle?
    ) {
//        Log.e(TAG_STARTUP_MONITOR, "onFragmentActivityCreated ${f}")
    }

    override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
//        Log.e(TAG_STARTUP_MONITOR, "onFragmentStarted ${f}")
    }
    override fun onActivityPostStarted(activity: Activity) {
//        Log.e(TAG_STARTUP_MONITOR, "onActivityPostStarted ${activity}")
    }

    override fun onAppForeground() {
        Log.e(TAG_STARTUP_MONITOR, "onAppForeground")
    }

    override fun onActivityPreResumed(activity: Activity) {
        if (!activityState.contains(activity.componentName)){
            activityState[activity.componentName] = 3
            arrayMap[activity.componentName] = SystemClock.uptimeMillis()
        }
//        Log.e(TAG_STARTUP_MONITOR, "onActivityPreResumed ${activity}")
    }

    override fun onActivityResumed(activity: Activity) {

    }
    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
//        Log.e(TAG_STARTUP_MONITOR, "onFragmentResumed ${f}")
    }
    override fun onActivityPostResumed(activity: Activity) {
//        Log.e(TAG_STARTUP_MONITOR, "onActivityPostResumed ${activity}")
    }

    override fun onActivityWindowFocusChanged(activity: Activity, hasFocus: Boolean) {
        Log.e(TAG_STARTUP_MONITOR, "onActivityWindowFocusChanged:${hasFocus}  $sFocusActivitySet  ${activity}")
        if (hasFocus) {
            if (sFocusActivitySet.add(activity.javaClass.name)) {
                val s = "${getAppStartupTime()}\n${getActivityStartupTime(activity)}"
                Log.e(TAG_STARTUP_MONITOR, ">>> device level:${DeviceUtil.getLevel()}\n${CpuUtil.getAppCpuInfo()}\n${MemoryUtil.getAppMemoryInfo()}\n$s")
                mStartupView.setText(s)
            }
        } else {
            sFocusActivitySet.remove(activity.javaClass.name)
        }
    }

    private fun getActivityStartupTime(activity: Activity): String {
        val ret = activityState.remove(activity.componentName) ?: 0
        // resume两个场景
        // 1. activity 返回 activity 调用 onResume
        // 2. activity 从后台切换到前台 调用 onStart onResume
        return if (ret == 1) {
            val cost = SystemClock.uptimeMillis() - (arrayMap.remove(activity.componentName)?: 0)
            String.format(
                Locale.US,
                "Activity(${activity.componentName.shortClassName})\ncreate cost:%.3fs",
                cost / 1000f
            )
        } else if(ret == 2){
            val cost = SystemClock.uptimeMillis() - (arrayMap.remove(activity.componentName)?:0)
            String.format(
                Locale.US,
                "Activity(${activity.componentName.shortClassName})\n后台切前台resume cost:%.3fs",
                cost / 1000f
            )
        } else if(ret == 3){
            val cost = SystemClock.uptimeMillis() - (arrayMap.remove(activity.componentName)?:0)
            String.format(
                Locale.US,
                "Activity(${activity.componentName.shortClassName})\nactivity回退栈resume cost:%.3fs",
                cost / 1000f
            )
        } else {
            ""
        }
    }

    private fun getAppStartupTime(): String {
        if (mIsColdStartUp && mColdCost == 0L) {//冷启动
            if (mFirstScreenCost == 0L) {
                mFirstScreenCost =
                    SystemClock.uptimeMillis() - ActivityThreadHacker.getEggBrokenTime()
            }
            if (ActivityThreadHacker.isCreatedByLaunchActivity()) {
                mColdCost = mFirstScreenCost
            } else {
                mFirstScreenCost = 0
                mColdCost = SystemClock.uptimeMillis() - ActivityThreadHacker.getEggBrokenTime()
            }
            mApplicationCost = ActivityThreadHacker.getApplicationCost()
        } else if (mIsWarmStartUp && mWarmCost == 0L) {//温启动
            mWarmCost = SystemClock.uptimeMillis() - mLastCreateActivity
        } else if (mIsHotStartUp && mHotCost == 0L) {
            mHotCost = SystemClock.uptimeMillis() - mLastForegroundActivity
        }
        Log.e(TAG_STARTUP_MONITOR, "getAppStartupTime --> $mIsColdStartUp $mIsWarmStartUp $mIsHotStartUp")
        return if (mIsColdStartUp) {
            String.format(
                Locale.US,
                "[cold startup] total:%.3fs\nApplication cost:%.3fs",
                mColdCost / 1000f,
                mApplicationCost / 1000f,
            )
        } else if (mIsWarmStartUp) {
            String.format(
                Locale.US,
                "[warn startup] total:%.3fs",
                mWarmCost / 1000f,
            )
        } else {
            String.format(
                Locale.US,
                "[hot startup] total:%.3fs",
                mHotCost / 1000f
            )
        }
    }

    override fun onActivityBackground(activity: Activity) {
        Log.e(TAG_STARTUP_MONITOR, "onActivityBackground")
        if (mActiveActivityCount == 1){//进入进程后台
            reset()
        }
    }

    override fun onAppBackground() {
        Log.e(TAG_STARTUP_MONITOR, "onAppBackground")
        reset()
    }
    private fun reset(){
        mIsWarmStartUp = false
        mIsColdStartUp = false
        mIsHotStartUp = false
        mColdCost = 0L
        mWarmCost = 0L
        mHotCost = 0L
    }

    override fun onActivityDestroyed(activity: Activity) {
        mActiveActivityCount--
        Log.e(TAG_STARTUP_MONITOR, "onActivityDestroyed ${mActiveActivityCount}")
    }

}

//<item name="viewInflaterClass">com.jamesfchen.vi.MyViewInflater</item>
class MyViewInflater : AppCompatViewInflater() {
    override fun createView(context: Context?, name: String?, attrs: AttributeSet?): View? {
        return super.createView(context, name, attrs)
    }
}

/**
 *
 *  布局加载耗时
 *  每个控件加载耗时：LayoutInflater.Factory2
 */
const val TAG_LAYOUT_MONITOR = "layoutInflater-monitor"

class LayoutInflateItem : AbsAppLifecycleObserver, AbsActivitiesLifecycleObserver() {
    val f = MyLayoutInflaterFactoryV2()
    override fun onAppCreate() {
        super.onAppCreate()
    }

    override fun onActivityPreCreated(activity: Activity, savedInstanceState: Bundle?) {
        super.onActivityPreCreated(activity, savedInstanceState)
        if (activity is AppCompatActivity) {
            f.activityRef = WeakReference<AppCompatActivity>(activity)
            LayoutInflaterCompat.setFactory2(activity.layoutInflater, f)
        } else {
            Log.e(TAG_LAYOUT_MONITOR, "${activity}不是appcompat activity，无法hook")
        }
    }


    inner class MyLayoutInflaterFactoryV2 : LayoutInflater.Factory2 {
        lateinit var activityRef: WeakReference<AppCompatActivity>
        override fun onCreateView(
            parent: View?,
            name: String,
            context: Context,
            attrs: AttributeSet
        ): View? {
            if ("FrameLayout" == name) {
                val count = attrs.attributeCount
                for (i in 0 until count) {
                    val attrName = attrs.getAttributeName(i)
                    val attrValue = attrs.getAttributeValue(i)
                    if (TextUtils.equals(attrName, "id")) {
                        val id = attrValue.substring(1).toInt()
//                        val idValue: String = SApp.getInstance().getResources().getResourceName(id)
//                        Log.e(TAG_LAYOUT_MONITOR, "idValue:${idValue}")
//                        if ("android:id/content" == idValue) {
//                            val grayFrameLayout = FrameLayout(context, attrs)
//                            SystemFilter.setGrayLayer(grayFrameLayout)
//                            return grayFrameLayout
//                        }
                    }
                }
            }
            val activity = activityRef.get() ?: return null
            val view = activity.delegate.createView(parent, name, context, attrs)
            val start = System.currentTimeMillis()
            Log.d(
                TAG_LAYOUT_MONITOR,
                "onCreateView 1 parent:$parent name:$name 初始化耗时:${System.currentTimeMillis() - start}ms"
            )
            return view
        }

        override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
            Log.d(TAG_LAYOUT_MONITOR, "onCreateView 2:$name")
            return null
        }
    }
}
