package com.hawksjamesf.mockserver;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.mockwebserver.MockWebServer;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawskjamesf
 * @since: Sep/25/2018  Tue
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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class IMockApiImpl extends IMockApi.Stub {

        @Override
        public void register(IMockServerCallback callback) throws RemoteException {
            MockService.this.callback = callback;
        }


    }
}
