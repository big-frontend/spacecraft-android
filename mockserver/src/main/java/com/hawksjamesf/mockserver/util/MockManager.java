package com.hawksjamesf.mockserver.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.SPUtils;
import com.hawksjamesf.mockserver.Constants;
import com.hawksjamesf.mockserver.IMockApi;
import com.hawksjamesf.mockserver.IMockServerCallback;
import com.hawksjamesf.mockserver.MockService;

import java.util.List;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawskjamesf
 * @since: Sep/27/2018  Thu
 */
public class MockManager {
    private static final String TAG = "MockManager---";
    MockServiceCon connection = new MockServiceCon();
    private IMockApi iMockApi;
    private static final String PROCESS_1 = "com.hawksjamesf.simpleweather";
    private static final String PROCESS_2 = "com.hawksjamesf.simpleweather:mock_service";

    //eager load
    public static MockManager manager = new MockManager();


    public static MockManager getInstance() {
        return manager;
    }

    public static void init(Context context) {
        String processName = getProcessName(context);
        Log.d(TAG,processName);
        if (!TextUtils.isEmpty(processName)) {
            if (processName.equals(PROCESS_1)) {

            } else if (processName.equals(PROCESS_2)) {
                getInstance().bindAndStartService(context);
            }
        }
    }

    //由于多进程架构中，Application会被调用两次
    void bindAndStartService(Context context) {
        Intent intent = new Intent(context, MockService.class);
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
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
                        Log.d(TAG, "start server:root dir-" + SPUtils.getInstance().getString(Constants.PRE_BASE_URL));


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
