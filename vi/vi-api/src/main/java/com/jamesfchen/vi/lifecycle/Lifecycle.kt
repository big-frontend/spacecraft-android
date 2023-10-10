@file:JvmName("Lifecycle")

package com.jamesfchen.vi.lifecycle

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.app.Instrumentation
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner

abstract class AbsFragmentLifecycleCallbacks : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
    }

    override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
    }

    override fun onFragmentActivityCreated(
        fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?
    ) {
    }

    override fun onFragmentViewCreated(
        fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?
    ) {
    }

    override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
    }

    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
    }

    override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
    }

    override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
    }

    override fun onFragmentSaveInstanceState(fm: FragmentManager, f: Fragment, outState: Bundle) {
    }

    override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
    }

    override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
    }
}

/**
 * 全部activity什么周期
 */
abstract class AbsActivitiesLifecycleObserver : AbsFragmentLifecycleCallbacks() {

    open fun onActivityPreCreated(activity: Activity, savedInstanceState: Bundle?) {}

    open fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

    open fun onActivityPostCreated(activity: Activity, savedInstanceState: Bundle?) {}

    open fun onActivityPreStarted(activity: Activity) {}

    open fun onActivityForeground(activity: Activity) {}

    open fun onActivityPostStarted(activity: Activity) {}

    open fun onActivityPreResumed(activity: Activity) {}

    open fun onActivityResumed(activity: Activity) {}

    open fun onActivityPostResumed(activity: Activity) {}
    open fun onActivityWindowFocusChanged(activity: Activity, hasFocus: Boolean) {}
    open fun onActivityPrePaused(activity: Activity) {}

    open fun onActivityPaused(activity: Activity) {}

    open fun onActivityPostPaused(activity: Activity) {}

    open fun onActivityPreStopped(activity: Activity) {}

    open fun onActivityBackground(activity: Activity) {}

    open fun onActivityPostStopped(activity: Activity) {}

    open fun onActivityPreSaveInstanceState(activity: Activity, outState: Bundle) {}

    open fun onActivitySaveInstanceState(activity: Activity, savedInstanceState: Bundle) {}

    open fun onActivityPostSaveInstanceState(activity: Activity, outState: Bundle) {}

    open fun onActivityPreDestroyed(activity: Activity) {}

    open fun onActivityDestroyed(activity: Activity) {}

    open fun onActivityPostDestroyed(activity: Activity) {}
}

/**
 * 应用进程生命周期
 */
interface AbsAppLifecycleObserver : DefaultLifecycleObserver {
    @Deprecated("换了个脸面，改了个名字", ReplaceWith("onAppCreate()"))
    override fun onCreate(owner: LifecycleOwner) = onAppCreate()

    @Deprecated("换了个脸面，改了个名字", ReplaceWith("onAppForeground()"))
    override fun onStart(owner: LifecycleOwner) = onAppForeground()

    @Deprecated("废弃该回调")
    override fun onResume(owner: LifecycleOwner) {
    }

    @Deprecated("废弃该回调")
    override fun onPause(owner: LifecycleOwner) {
    }

    @Deprecated("换了个脸面，改了个名字", ReplaceWith("onAppBackground()"))
    override fun onStop(owner: LifecycleOwner) = onAppBackground()

    @Deprecated("App进程销毁不会调用该回调")
    override fun onDestroy(owner: LifecycleOwner) {
    }

    //App#attachBaseContext --> ContentProvider#onCreate--->App#onCreate
    //onAppCreate 主要在 ContentProvider#onCreate 发送事件，该事件属于stick，当有观察者被注册，立马就会将stick事件发送出去
    fun onAppCreate() {}
    fun onAppForeground() {}
    fun onAppBackground() {}
}

private lateinit var mApp: Application
internal val activitiesListeners = HashSet<AbsActivitiesLifecycleObserver>()

@Throws(NoSuchFieldException::class, IllegalAccessException::class)
private fun hookInstrumentation(activityThread: Any) {
    val mInstrumentationField = activityThread.javaClass.getDeclaredField("mInstrumentation")
    mInstrumentationField.isAccessible = true
    val instrumentationValue = mInstrumentationField[activityThread]
    val instrumentationProxy = InstrumentationProxy(instrumentationValue as Instrumentation)
    mInstrumentationField[activityThread] = instrumentationProxy
}

fun initLifecycle(application: Application) {
    mApp = application
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        mApp.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityPreCreated(activity: Activity, savedInstanceState: Bundle?) {
                synchronized(activitiesListeners) {
                    for (listener in activitiesListeners) {
                        listener.onActivityPreCreated(activity, savedInstanceState)
                        activity.window.decorView.viewTreeObserver.addOnWindowFocusChangeListener { hasFocus ->
                            listener.onActivityWindowFocusChanged(activity, hasFocus)
                        }
                        if (activity is FragmentActivity) {
                            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
                                listener,
                                false
                            )
                        } else {
                            Log.e("Lifecycle", "$activity must extend FragmentActivity")
                        }
                    }
                }
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                synchronized(activitiesListeners) {
                    for (listener in activitiesListeners) {
                        listener.onActivityCreated(activity, savedInstanceState)
                    }
                }
            }

            override fun onActivityPostCreated(activity: Activity, savedInstanceState: Bundle?) {
                synchronized(activitiesListeners) {
                    for (listener in activitiesListeners) {
                        listener.onActivityPostCreated(activity, savedInstanceState)
                    }
                }
            }

            override fun onActivityPreStarted(activity: Activity) {
                synchronized(activitiesListeners) {
                    for (listener in activitiesListeners) {
                        listener.onActivityPreStarted(activity)
                    }
                }
            }

            override fun onActivityStarted(activity: Activity) {
                synchronized(activitiesListeners) {
                    for (listener in activitiesListeners) {
                        listener.onActivityForeground(activity)
                    }
                }
            }

            override fun onActivityPostStarted(activity: Activity) {
                synchronized(activitiesListeners) {
                    for (listener in activitiesListeners) {
                        listener.onActivityPostStarted(activity)
                    }
                }
            }

            override fun onActivityPreResumed(activity: Activity) {
                synchronized(activitiesListeners) {
                    for (listener in activitiesListeners) {
                        listener.onActivityPreResumed(activity)
                    }
                }

            }

            override fun onActivityResumed(activity: Activity) {
                synchronized(activitiesListeners) {
                    for (listener in activitiesListeners) {
                        listener.onActivityResumed(activity)
                    }
                }
            }

            override fun onActivityPostResumed(activity: Activity) {
                synchronized(activitiesListeners) {
                    for (listener in activitiesListeners) {
                        listener.onActivityPostResumed(activity)
                    }
                }
            }

            override fun onActivityPrePaused(activity: Activity) {
                synchronized(activitiesListeners) {
                    for (listener in activitiesListeners) {
                        listener.onActivityPrePaused(activity)
                    }
                }
            }

            override fun onActivityPaused(activity: Activity) {
                synchronized(activitiesListeners) {
                    for (listener in activitiesListeners) {
                        listener.onActivityPaused(activity)
                    }
                }
            }

            override fun onActivityPostPaused(activity: Activity) {
                synchronized(activitiesListeners) {
                    for (listener in activitiesListeners) {
                        listener.onActivityPostPaused(activity)
                    }
                }
            }

            override fun onActivityPreStopped(activity: Activity) {
                synchronized(activitiesListeners) {
                    for (listener in activitiesListeners) {
                        listener.onActivityPreStopped(activity)
                    }
                }
            }

            override fun onActivityStopped(activity: Activity) {
                synchronized(activitiesListeners) {
                    for (listener in activitiesListeners) {
                        listener.onActivityBackground(activity)
                    }
                }
            }

            override fun onActivityPostStopped(activity: Activity) {
                synchronized(activitiesListeners) {
                    for (listener in activitiesListeners) {
                        listener.onActivityPostStopped(activity)
                    }
                }
            }

            override fun onActivityPreSaveInstanceState(activity: Activity, outState: Bundle) {
                synchronized(activitiesListeners) {
                    for (listener in activitiesListeners) {
                        listener.onActivityPreSaveInstanceState(activity, outState)
                    }
                }
            }

            override fun onActivitySaveInstanceState(
                activity: Activity,
                savedInstanceState: Bundle
            ) {
                synchronized(activitiesListeners) {
                    for (listener in activitiesListeners) {
                        listener.onActivitySaveInstanceState(activity, savedInstanceState)
                    }
                }
            }

            override fun onActivityPostSaveInstanceState(activity: Activity, outState: Bundle) {
                synchronized(activitiesListeners) {
                    for (listener in activitiesListeners) {
                        listener.onActivityPostSaveInstanceState(activity, outState)
                    }
                }
            }

            override fun onActivityPreDestroyed(activity: Activity) {
                synchronized(activitiesListeners) {
                    for (listener in activitiesListeners) {
                        listener.onActivityPreDestroyed(activity)
                    }
                }
            }

            override fun onActivityDestroyed(activity: Activity) {
                synchronized(activitiesListeners) {
                    for (listener in activitiesListeners) {
                        listener.onActivityDestroyed(activity)
                    }
                }
            }

            override fun onActivityPostDestroyed(activity: Activity) {
                synchronized(activitiesListeners) {
                    for (listener in activitiesListeners) {
                        listener.onActivityPostDestroyed(activity)
                        if (activity is FragmentActivity) {
                            activity.supportFragmentManager.unregisterFragmentLifecycleCallbacks(
                                listener
                            )
                        } else {
                            Log.e("Lifecycle", "$activity must extend FragmentActivity")
                        }
                    }
                }
            }
        })
    } else {
        val forName = Class.forName("android.app.ActivityThread")
        val field = forName.getDeclaredField("sCurrentActivityThread")
        field.isAccessible = true
        val activityThreadValue = field[forName]
        hookInstrumentation(activityThreadValue)
        mApp.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                synchronized(activitiesListeners) {
                    for (listener in activitiesListeners) {
                        listener.onActivityCreated(activity, savedInstanceState)
                    }
                }
            }

            override fun onActivityStarted(activity: Activity) {
                synchronized(activitiesListeners) {
                    for (listener in activitiesListeners) {
                        listener.onActivityForeground(activity)
                    }
                }
            }

            override fun onActivityResumed(activity: Activity) {
                synchronized(activitiesListeners) {
                    for (listener in activitiesListeners) {
                        listener.onActivityResumed(activity)
                    }
                }
            }

            override fun onActivityPaused(activity: Activity) {
                synchronized(activitiesListeners) {
                    for (listener in activitiesListeners) {
                        listener.onActivityPaused(activity)
                    }
                }
            }

            override fun onActivityStopped(activity: Activity) {
                synchronized(activitiesListeners) {
                    for (listener in activitiesListeners) {
                        listener.onActivityBackground(activity)
                    }
                }
            }

            override fun onActivitySaveInstanceState(
                activity: Activity,
                savedInstanceState: Bundle
            ) {
                synchronized(activitiesListeners) {
                    for (listener in activitiesListeners) {
                        listener.onActivitySaveInstanceState(activity, savedInstanceState)
                    }
                }
            }

            override fun onActivityDestroyed(activity: Activity) {
                synchronized(activitiesListeners) {
                    for (listener in activitiesListeners) {
                        listener.onActivityDestroyed(activity)
                    }
                }
            }
        })
    }
}

fun registerLifecycle(clz: Class<*>) {
    try {
        val constructor = clz.getConstructor(Application::class.java)
        val observer: Any = constructor.newInstance(mApp) as LifecycleObserver
        ProcessLifecycleOwner.get().lifecycle.addObserver((observer as DefaultLifecycleObserver))
        activitiesListeners.add(observer as AbsActivitiesLifecycleObserver)
    } catch (e: InstantiationException) {
        e.printStackTrace()
    } catch (e: IllegalAccessException) {
        e.printStackTrace()
    } catch (e: Exception) {
        e.printStackTrace()

    }
}