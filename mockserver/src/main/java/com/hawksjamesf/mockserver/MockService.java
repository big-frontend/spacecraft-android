package com.hawksjamesf.mockserver;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.blankj.utilcode.util.SPUtils;

import java.io.IOException;
import java.net.URL;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawskjamesf
 * @since: Sep/25/2018  Tue
 */
public class MockService extends IntentService {
    private static final String TAG = "MockService---";
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    MockWebServer mockWebServer;
    IMockApiImpl mBinder = new IMockApiImpl();
    IMockServerCallback callback;

    public MockService() {
        super("mock_service");
        mockWebServer = new MockWebServer();
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    //work thread
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
//            mockWebServer.start();
            mockWebServer.start(50195);
            SPUtils.getInstance().clear();
            SPUtils.getInstance().put(Constants.PRE_BASE_URL, mockWebServer.url("/").toString());
            Log.d(TAG,mockWebServer.url("/").toString());

            if (callback != null) {
                callback.onStartMockServer();
            }
            RecordedRequest recordedRequest = mockWebServer.takeRequest();
            URL url = recordedRequest.getRequestUrl().url();
            url.getPath();
            String fileName = "current_data.json";
            mockWebServer.enqueue(new MockResponse()
                    .setResponseCode(200)
                    .setBody(RestServiceTestHelper.getStringFromFile(getApplicationContext(), fileName)));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
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
