package com.hawksjamesf.mockserver;

import android.app.ActivityManager;
import android.app.Application;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.hawksjamesf.mockserver.service.MockIntentService;
import com.hawksjamesf.mockserver.service.MockJobService;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawskjamesf
 * @since: Sep/27/2018  Thu
 * <p>
 * <p>
 * 由于MockManager和MockService不在用一个进程，所以MockManager中的静态方法不能给MockService使用。java的单例只存在于单个进程中，多进程不能使用。
 */
public class MockManager {
    private static final String TAG = Constants.TAG + "/MockManager";
    MockServiceCon connection = new MockServiceCon();
    private IMockApi iMockApi;

    //eager load
    public static MockManager manager = new MockManager();
    private static JobScheduler mJobScheduler;

    public static JobScheduler getJobScheduler() {
        return mJobScheduler;
    }

    private static final boolean USE_JOBSCHEDULER = false;

    private MockManager() {
    }

    public static MockManager getInstance() {
        return manager;
    }

    public static void init(Context context) {
        init(context, true);
    }

    public static void init(Context context, boolean debugable) {
        if (!debugable) return;
//        String processName = getProcessName(context);
        mJobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        getInstance().bindAndStartService(context);
    }

    public static void clear(Application instance, boolean debugable) {
        getInstance().unbindAndStopService(instance);

    }

    //由于多进程架构中，Application会被调用两次
    void bindAndStartService(Context context) {
        if (USE_JOBSCHEDULER && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            MockJobService.startService(context);
        } else {
            MockIntentService.bindAndStartService(context, connection);
        }
//        MockJobService.startService(context);
//        MockIntentService.bindAndStartService(context, connection);
//        MockForegroundService.bindAndStartService(context, connection);
        Toast.makeText(context, "start &  bind  service", Toast.LENGTH_LONG).show();
    }

    void unbindAndStopService(Context context) {
        MockIntentService.unbindAndStopService(context, connection);
        Toast.makeText(context, "stop & unbind Service", Toast.LENGTH_LONG).show();

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


    public static String getProcessName(Context context) {
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
