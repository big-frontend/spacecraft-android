package com.hawksjamesf.mockserver;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawskjamesf
 * @since: Sep/27/2018  Thu
 */
public class MockManager {
    private static final String TAG = "MockManager";
    MockServiceCon connection = new MockServiceCon();
    private IMockApi iMockApi;
    private static final String PROCESS_1 = "com.hawksjamesf.spacecraft";
    private static final String PROCESS_2 = "com.hawksjamesf.spacecraft:mock_service";

    //eager load
    public static MockManager manager = new MockManager();


    public static MockManager getInstance() {
        return manager;
    }

    public static void init(Context context) {
        String processName = getProcessName(context);
        Logger.t(TAG).d(processName);
        if (!TextUtils.isEmpty(processName)) {
            if (processName.equals(PROCESS_1)) {

                getInstance().bindAndStartService(context);
            } else if (processName.equals(PROCESS_2)) {
            }
        }
    }

    //由于多进程架构中，Application会被调用两次
    void bindAndStartService(Context context) {
        Intent intent = new Intent(context, MockService.class);
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
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE|Context.BIND_ADJUST_WITH_ACTIVITY);
        context.startService(intent);
    }

    private class MockServiceCon implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iMockApi = IMockApi.Stub.asInterface(service);
            try {
                iMockApi.register(new IMockServerCallback.Stub() {
                    @Override
                    public void onStartMockServer() throws RemoteException {
                        Logger.t(TAG).d("start server:root dir-" + SPUtils.getInstance().getString(Constants.PRE_BASE_URL));


                    }
                });
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }


    private static String getProcessName(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return "";
        }

        for (ActivityManager.RunningAppProcessInfo processInfo :
                runningAppProcesses) {
            if (processInfo.pid == Process.myPid() && processInfo.processName != null) {
                return processInfo.processName;
            }
        }

        return "";

    }
}
