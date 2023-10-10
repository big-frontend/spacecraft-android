package com.jamesfchen.vi;

import android.app.Application;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ActivityThreadHacker {
    private static long sApplicationCreateBeginTime = 0L;
    private static long sApplicationCreateEndTime = 0L;
    public static int sApplicationCreateScene = Integer.MIN_VALUE;
    private static final HashSet<IApplicationCreateListener> listeners = new HashSet<>();
    private static final HashSet<Application.ActivityLifecycleCallbacks> activitiesListeners = new HashSet<>();
    private static boolean sIsCreatedByLaunchActivity = false;

    public static void addListener(IApplicationCreateListener listener) {
        synchronized (listeners) {
            listeners.add(listener);
        }
    }

    public static void removeListener(IApplicationCreateListener listener) {
        synchronized (listeners) {
            listeners.remove(listener);
        }
    }

    public static void addActivitiesListener(Application.ActivityLifecycleCallbacks listener) {
        synchronized (activitiesListeners) {
            activitiesListeners.add(listener);
        }
    }

    public static void removeActivitiesListener(Application.ActivityLifecycleCallbacks listener) {
        synchronized (activitiesListeners) {
            activitiesListeners.remove(listener);
        }
    }

    public interface IApplicationCreateListener {
        void onApplicationCreateEnd();
    }

    public static void hackSysHandlerCallback() {
        try {
            sApplicationCreateBeginTime = SystemClock.uptimeMillis();
            Class<?> forName = Class.forName("android.app.ActivityThread");
            Field field = forName.getDeclaredField("sCurrentActivityThread");
            field.setAccessible(true);
            Object activityThreadValue = field.get(forName);
            Field mH = forName.getDeclaredField("mH");
            mH.setAccessible(true);
            Object handler = mH.get(activityThreadValue);
            Class<?> handlerClass = handler.getClass().getSuperclass();
            if (null != handlerClass) {
                Field callbackField = handlerClass.getDeclaredField("mCallback");
                callbackField.setAccessible(true);
                Handler.Callback originalCallback = (Handler.Callback) callbackField.get(handler);
                HackCallback callback = new HackCallback(originalCallback);
                callbackField.set(handler, callback);
            }
        } catch (Exception e) {
            Log.e("ActivityThreadHacker", Log.getStackTraceString(e));
        }
    }

    public static long getApplicationCost() {
        return ActivityThreadHacker.sApplicationCreateEndTime - ActivityThreadHacker.sApplicationCreateBeginTime;
    }

    public static long getEggBrokenTime() {
        return ActivityThreadHacker.sApplicationCreateBeginTime;
    }

    public static boolean isCreatedByLaunchActivity() {
        return sIsCreatedByLaunchActivity;
    }


    private final static class HackCallback implements Handler.Callback {
        private static final int LAUNCH_ACTIVITY = 100;
        private static final int CREATE_SERVICE = 114;
        private static final int RELAUNCH_ACTIVITY = 126;
        private static final int RECEIVER = 113;
        private static final int EXECUTE_TRANSACTION = 159; // for Android 9.0
        private static boolean isCreated = false;
        private static int hasPrint = Integer.MAX_VALUE;

        private final Handler.Callback mOriginalCallback;

        private static final int SERIVCE_ARGS = 115;
        private static final int STOP_SERVICE = 116;
        private static final int STOP_ACTIVITY_SHOW = 103;
        private static final int STOP_ACTIVITY_HIDE = 104;
        private static final int SLEEPING = 137;

        HackCallback(Handler.Callback callback) {
            this.mOriginalCallback = callback;
        }

        @Override
        public boolean handleMessage(Message msg) {
//            if (IssueFixConfig.getsInstance().isEnableFixSpApply()) {
//                if (Build.VERSION.SDK_INT >= 21 && Build.VERSION.SDK_INT <= 25) {
//                    if (msg.what == SERIVCE_ARGS || msg.what == STOP_SERVICE
//                            || msg.what == STOP_ACTIVITY_SHOW || msg.what == STOP_ACTIVITY_HIDE
//                            || msg.what == SLEEPING) {
//                            fix();
//                        }
//                }
//            }

//            if (!AppMethodBeat.isRealTrace()) {
//                return null != mOriginalCallback && mOriginalCallback.handleMessage(msg);
//            }

            boolean isLaunchActivity = isLaunchActivity(msg);

            if (hasPrint > 0) {
                hasPrint--;
            }

            if (!isCreated) {
                if (isLaunchActivity || msg.what == CREATE_SERVICE || msg.what == RECEIVER) { // todo for provider
                    ActivityThreadHacker.sApplicationCreateEndTime = SystemClock.uptimeMillis();
                    ActivityThreadHacker.sApplicationCreateScene = msg.what;
                    isCreated = true;
                    sIsCreatedByLaunchActivity = isLaunchActivity;
                    synchronized (listeners) {
                        for (IApplicationCreateListener listener : listeners) {
                            listener.onApplicationCreateEnd();
                        }
                    }
                }
            }
            return null != mOriginalCallback && mOriginalCallback.handleMessage(msg);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        private void fix() {
            try {
                Class cls = Class.forName("android.app.QueuedWork");
                Field field = cls.getDeclaredField("sPendingWorkFinishers");
                if (field != null) {
                    field.setAccessible(true);
                    ConcurrentLinkedQueue<Runnable> runnables = (ConcurrentLinkedQueue<Runnable>) field.get(null);
                    runnables.clear();
                }
            } catch (ClassNotFoundException e) {

                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        private Method method = null;

        private boolean isLaunchActivity(Message msg) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O_MR1) {
                if (msg.what == EXECUTE_TRANSACTION && msg.obj != null) {
                    try {
                        if (null == method) {
                            Class clazz = Class.forName("android.app.servertransaction.ClientTransaction");
                            method = clazz.getDeclaredMethod("getCallbacks");
                            method.setAccessible(true);
                        }
                        List list = (List) method.invoke(msg.obj);
                        if (!list.isEmpty()) {
                            return list.get(0).getClass().getName().endsWith(".LaunchActivityItem");
                        }
                    } catch (Exception e) {
                    }
                }
                return msg.what == LAUNCH_ACTIVITY;
            } else {
                return msg.what == LAUNCH_ACTIVITY || msg.what == RELAUNCH_ACTIVITY;
            }
        }
    }






}
