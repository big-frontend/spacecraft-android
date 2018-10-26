package com.hawksjamesf.mockserver;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import androidx.annotation.Nullable;

import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.mockwebserver.MockWebServer;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawskjamesf
 * @since: Sep/25/2018  Tue
 *
 * MockService的启动一定要快与有网络请求的组件，不然有网络请求的组件在连接MockServer时会报错导致crash
 */
public class MockService extends IntentService {
    private static final String TAG = "MockService";


    MockWebServer mockWebServer;
    IMockApiImpl mBinder = new IMockApiImpl();
    IMockServerCallback callback;
    DispatcherImpl dispatcher;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MockService() {
        super("mock_service");
        mockWebServer = new MockWebServer();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        dispatcher = DispatcherImpl.getInstance(getApplicationContext());
        Logger.t(TAG).d("");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Logger.t(TAG).d("");
        return mBinder;
    }

    //work thread
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
//            mockWebServer.start();
            mockWebServer.start(50195);
            Logger.t(TAG).i(mockWebServer.url("/").toString());
            mockWebServer.url("/");
//            SPUtils.getInstance().clear();
//            SPUtils.getInstance().put(Constants.PRE_BASE_URL, mockWebServer.url("/").toString());

            if (callback != null) {
                callback.onStartMockServer();
            }
            mockWebServer.setDispatcher(dispatcher);
//            mockWebServer.enqueue();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     *
     * @param intent
     * @param flags
     *
     * int: Additional data about this start request.
     * Value is either 0 or combination of START_FLAG_REDELIVERY or START_FLAG_RETRY.
     *  0：
     *
     *  START_FLAG_REDELIVERY：
     *
     *  START_FLAG_RETRY：
     *
     * @param startId
     * @return
     *
     * Value is START_STICKY_COMPATIBILITY, START_STICKY, START_NOT_STICKY or START_REDELIVER_INTENT.
     *
     * START_STICKY- tells the system to create a fresh copy of the service, when sufficient memory is available, after it recovers from low memory. Here you will lose the results that might have computed before.
     *
     * START_NOT_STICKY- tells the system not to bother to restart the service, even when it has sufficient memory.
     *
     * START_REDELIVER_INTENT- tells the system to restart the service after the crash and also redeliver the intents that were present at the time of crash.
     *
     */
//    @Override
//    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
//        return Service.START_STICKY;
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public class IMockApiImpl extends IMockApi.Stub {

        @Override
        public void register(IMockServerCallback callback) throws RemoteException {
            MockService.this.callback = callback;
        }


    }
}
