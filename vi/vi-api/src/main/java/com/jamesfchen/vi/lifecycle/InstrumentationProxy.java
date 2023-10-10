package com.jamesfchen.vi.lifecycle;

import android.app.Activity;
import android.app.Instrumentation;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import java.util.HashSet;

public class InstrumentationProxy extends Instrumentation {
    private final Instrumentation mInstrumentation;
    private final HashSet<AbsActivitiesLifecycleObserver> mActivitiesListeners;

    public InstrumentationProxy(Instrumentation instrumentation) {
        mInstrumentation = instrumentation;
        mActivitiesListeners = Lifecycle.getActivitiesListeners();
    }

    @Override
    public void callActivityOnCreate(Activity activity, Bundle icicle) {
        synchronized (mActivitiesListeners) {
            for (AbsActivitiesLifecycleObserver listener : mActivitiesListeners) {
                listener.onActivityPreCreated(activity, icicle);
                activity.getWindow().getDecorView().getViewTreeObserver().addOnWindowFocusChangeListener(hasFocus -> {
                    listener.onActivityWindowFocusChanged(activity, hasFocus);
                });
                if (activity instanceof FragmentActivity) {
                    ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(listener, false);
                } else {
                    Log.e("Lifecycle",activity+" must extend FragmentActivity");
                }
            }
        }
        mInstrumentation.callActivityOnCreate(activity, icicle);
        synchronized (mActivitiesListeners) {
            for (AbsActivitiesLifecycleObserver listener : mActivitiesListeners) {
                listener.onActivityPostCreated(activity, icicle);
            }
        }
    }

    @Override
    public void callActivityOnCreate(Activity activity, Bundle icicle, PersistableBundle persistentState) {
        mInstrumentation.callActivityOnCreate(activity, icicle, persistentState);
    }

    @Override
    public void callActivityOnRestart(Activity activity) {
        mInstrumentation.callActivityOnRestart(activity);
    }

    @Override
    public void callActivityOnStart(Activity activity) {
        synchronized (mActivitiesListeners) {
            for (AbsActivitiesLifecycleObserver listener : mActivitiesListeners) {
                listener.onActivityPreStarted(activity);
            }
        }
        mInstrumentation.callActivityOnStart(activity);
        synchronized (mActivitiesListeners) {
            for (AbsActivitiesLifecycleObserver listener : mActivitiesListeners) {
                listener.onActivityPostStarted(activity);
            }
        }
    }

    @Override
    public void callActivityOnResume(Activity activity) {
        synchronized (mActivitiesListeners) {
            for (AbsActivitiesLifecycleObserver listener : mActivitiesListeners) {
                listener.onActivityPreResumed(activity);
            }
        }
        mInstrumentation.callActivityOnResume(activity);
        synchronized (mActivitiesListeners) {
            for (AbsActivitiesLifecycleObserver listener : mActivitiesListeners) {
                listener.onActivityPostResumed(activity);
            }
        }
    }

    @Override
    public void callActivityOnPause(Activity activity) {
        synchronized (mActivitiesListeners) {
            for (AbsActivitiesLifecycleObserver listener : mActivitiesListeners) {
                listener.onActivityPrePaused(activity);
            }
        }
        mInstrumentation.callActivityOnPause(activity);
        synchronized (mActivitiesListeners) {
            for (AbsActivitiesLifecycleObserver listener : mActivitiesListeners) {
                listener.onActivityPostPaused(activity);
            }
        }
    }

    @Override
    public void callActivityOnStop(Activity activity) {
        synchronized (mActivitiesListeners) {
            for (AbsActivitiesLifecycleObserver listener : mActivitiesListeners) {
                listener.onActivityPreStopped(activity);
            }
        }
        mInstrumentation.callActivityOnStop(activity);
        synchronized (mActivitiesListeners) {
            for (AbsActivitiesLifecycleObserver listener : mActivitiesListeners) {
                listener.onActivityPostStopped(activity);
            }
        }
    }

    @Override
    public void callActivityOnDestroy(Activity activity) {
        synchronized (mActivitiesListeners) {
            for (AbsActivitiesLifecycleObserver listener : mActivitiesListeners) {
                listener.onActivityPreDestroyed(activity);
            }
        }
        mInstrumentation.callActivityOnDestroy(activity);
        synchronized (mActivitiesListeners) {
            for (AbsActivitiesLifecycleObserver listener : mActivitiesListeners) {
                listener.onActivityPostDestroyed(activity);
                if (activity instanceof FragmentActivity) {
                    ((FragmentActivity) activity).getSupportFragmentManager().unregisterFragmentLifecycleCallbacks(listener);
                } else {
                    Log.e("Lifecycle",activity+" must extend FragmentActivity");
                }
            }
        }
    }

}