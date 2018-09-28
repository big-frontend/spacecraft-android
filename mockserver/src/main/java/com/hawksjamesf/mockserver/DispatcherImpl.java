package com.hawksjamesf.mockserver;

import android.content.Context;

import com.orhanobut.logger.Logger;

import java.net.URL;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Sep/28/2018  Fri
 */
public class DispatcherImpl extends Dispatcher {
    public static final String TAG = "DispatcherImpl";

    Context context;

    private static DispatcherImpl dispatcher;

    public static DispatcherImpl getInstance(Context context) {
        if (dispatcher == null) {
            synchronized (DispatcherImpl.class) {
                if (dispatcher == null) {
                    dispatcher = new DispatcherImpl(context);
                    return dispatcher;
                }

            }
        }
        return dispatcher;

    }

    private DispatcherImpl(Context context) {
        this.context = context;
    }

    @Override
    public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
        URL url = request.getRequestUrl().url();

        String fileName;
        switch (url.getPath()) {
            case Constants.CURRENT_DATA_URL_PATH: {
                fileName = Constants.CURRENT_DATA_JSON;
                break;
            }

            case Constants.FIVE_DATA_URL_PATH: {
                fileName = Constants.FIVE_DATA_JSON;
                break;
            }

            default:
                Logger.t(TAG).e(url.getPath());
                return new MockResponse()
                        .setResponseCode(404);
        }
        Logger.t(TAG).d(fileName);
        String stringFromFile = "";
        try {
            stringFromFile = RestServiceTestHelper.getStringFromFile(context, fileName);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new MockResponse()
                .setResponseCode(200)
                .setBody(stringFromFile);
    }
}
