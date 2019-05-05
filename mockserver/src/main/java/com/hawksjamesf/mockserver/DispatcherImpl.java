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
 * <p>
 * 当mock 包中的Dispatcher和网络请求接口在同一个进程A中，那么其他进程的Dispatcher就不能连接到A;
 * 如果网络请求接口在A进程中，各个Dispatcher在其他相对于的进程中，那么都可以使用。
 * 比如该项目中的mockserver模块被放入：mock_server进程中，测试进程中的Dispatcher就可以使用。
 */
public class DispatcherImpl extends Dispatcher {
    public static final String TAG = "DispatcherImpl";

    Context context;

    private static DispatcherImpl dispatcher;

    /**
     * 由于DispatcherImpl运行在:mock_server进程所以其他进程使用该对象时并不是单例。
     *
     * @param context
     * @return
     */
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
        Logger.t(TAG).i(url.getPath());

        String fileName;
        int code = 200;
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
                fileName = Constants.ERROR_JSON;
                code = 404;
        }
        Logger.t(TAG).d(fileName);
        String stringFromFile = "";
        try {
            stringFromFile = RestServiceTestHelper.getStringFromFile(context, fileName);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new MockResponse()
                .setResponseCode(code)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("Cache-Control", "no-cache")
                .setBody(stringFromFile);
    }
}
