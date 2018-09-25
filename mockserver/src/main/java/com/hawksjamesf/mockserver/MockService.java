package com.hawksjamesf.mockserver;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.net.URL;

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
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    MockWebServer mockWebServer;

    public MockService(String name) {
        super(name);
        mockWebServer = new MockWebServer();
        try {
            mockWebServer.start();
            Constants.BASE_URL = mockWebServer.url("/").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //work thread
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            RecordedRequest recordedRequest = mockWebServer.takeRequest();
            URL url = recordedRequest.getRequestUrl().url();
            url.getPath();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
