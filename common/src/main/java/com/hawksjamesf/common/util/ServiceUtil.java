package com.hawksjamesf.common.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/10/2018  Sat
 */
public class ServiceUtil {
    public static Set getAllRunningService() {
        ActivityManager am = (ActivityManager) Util.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) return Collections.emptySet();
        List<ActivityManager.RunningServiceInfo> infos = am.getRunningServices(Integer.MAX_VALUE);
        Set<String> names = new HashSet<>();
        if (infos == null || infos.size() == 0) return null;
        for (ActivityManager.RunningServiceInfo info : infos) {
            names.add(info.service.getClassName());
        }

        return names;
    }

    public static void startService(final String className) {
        try {
            startService(Class.forName(className));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void startService(final Class<?> clz) {
        Util.getApp().startService(new Intent(
                Util.getApp(), clz
        ));

    }

    public static boolean stopService(final String className) {
        try {
            return stopService(Class.forName(className));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean stopService(final Class<?> clz) {
        return Util.getApp().stopService(new Intent(
                Util.getApp(), clz
        ));
    }

    public static void bindService(final String className,
                                   final ServiceConnection connection,
                                   final int flag) {
        try {
            bindService(Class.forName(className), connection, flag);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void bindService(final Class<?> clz,
                                   final ServiceConnection connection,
                                   final int flag) {
        /**
         * flag:
         * int: Operation options for the binding. May be 0, BIND_AUTO_CREATE, BIND_DEBUG_UNBIND, BIND_NOT_FOREGROUND, BIND_ABOVE_CLIENT, BIND_ALLOW_OOM_MANAGEMENT, or BIND_WAIVE_PRIORITY.
         *
         * May be 0, 使用场景为Service已经running，Activity只是需要重新连接Service就可以用flag=0
         * BIND_AUTO_CREATE,
         *
         * 1）
         * bind Service时会自动创建Service，当Activity处于foreground时，为了raise Service进程的优先级，必须标明BIND_ADJUST_WITH_ACTIVITY；为了兼容老版本，
         * 没有使用BIND_AUTO_CREATE将被BIND_WAIVE_PRIORITY|BIND_ADJUST_WITH_ACTIVITY
         *
         * BIND_DEBUG_UNBIND,
         *用于debug 调用unbindService
         *
         * BIND_NOT_FOREGROUND,
         * 不允许Service所在的进程被设置为foreground
         *
         *
         * BIND_ABOVE_CLIENT,
         * 客户端先死
         *
         * BIND_ALLOW_OOM_MANAGEMENT,
         * 允许被进入oom的列表
         *
         * BIND_WAIVE_PRIORITY.
         * 对于有Service的进程来说，放弃了优先级策略，被放入background lru列表，就跟普通的应用一样，不会被加入oom的列表
         *
         *
         * 2）
         * BIND_ADJUST_WITH_ACTIVITY
         * 如果binding 是Activity，那么Service所在的进程的重要性将是基于Activity是否是对用户可见，可见客户端能提升服务端的优先级
         *
         * BIND_IMPORTANT 服务端进程可以被提升到foreground
         *
         *
         * Value is either 0 or combination of BIND_AUTO_CREATE, BIND_DEBUG_UNBIND, BIND_NOT_FOREGROUND（不能为foreground）,BIND_IMPORTANT（为foreground）， BIND_ABOVE_CLIENT（客户端先死）, BIND_ADJUST_WITH_ACTIVITY（客户端帮助服务端提升优先级，基于客户端的优先级），BIND_ALLOW_OOM_MANAGEMENT（允许加入oom管理）, BIND_WAIVE_PRIORITY（放弃加入oom管理，变成普通进程）.
         *
         */
        Util.getApp().bindService(new Intent(Util.getApp(), clz),
                connection, flag);
    }

    public static void unbindService(final ServiceConnection connection) {
        Util.getApp().unbindService(connection);
    }

    public static boolean isServiceRunning(final String className) {
        ActivityManager am = (ActivityManager) Util.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) return false;
        List<ActivityManager.RunningServiceInfo> infors = am.getRunningServices(Integer.MAX_VALUE);
        if (infors == null || infors.size() == 0) return false;
        for (ActivityManager.RunningServiceInfo info :
                infors) {
            if (className.equals(info.service.getClassName())) return true;
        }
        return false;
    }


}
