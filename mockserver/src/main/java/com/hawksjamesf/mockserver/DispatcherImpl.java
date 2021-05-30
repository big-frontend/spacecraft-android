package com.hawksjamesf.mockserver;

import android.content.Context;

import com.hawksjamesf.mockserver.control.WeatherControl;
import com.hawksjamesf.mockserver.util.RestServiceTestHelper;
import com.orhanobut.logger.Logger;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Sep/28/2018  Fri
 * <p>
 * 当mock包中的Dispatcher服务和客户端在同一个进程A中，那么其他进程的Dispatcher就不能连接到A进程;
 * 如果客户端在A进程中，各个Dispatcher在其他进程中，那么都可以使用。
 * <p>
 * 比如该项目中的mockserver模块中的Dispatcher在mock_server进程，测试模块中的Dispatcher在测试进程中，客户端在A进程，测试模块和mockserver模块中的Dispatcher就都可以使用。
 */
public class DispatcherImpl extends Dispatcher {
    public static final String TAG = Constants.TAG + "/DispatcherImpl";

    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

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

    Map<String, Method> annotationMap = new HashMap<>();
    WeatherControl mWeatherControl = new WeatherControl();

    private DispatcherImpl(Context context) {

        for (Method method : WeatherControl.class.getDeclaredMethods()) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof GET) {
                    String value = ((GET) annotation).value();
                    annotationMap.put(value, method);
                } else if (annotation instanceof POST) {
                    String value = ((POST) annotation).value();
                    annotationMap.put(value, method);
                }
            }
        }
        mContext = context;
    }

    @NotNull
    @Override
    public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
        MockResponse errorMockResponse = genErrorMockResponse();
        if (request == null || request.getRequestUrl() == null) {
            return errorMockResponse;
        }
        URL url = request.getRequestUrl().url();
        Logger.t(TAG).i(url.getPath());
        Method method = annotationMap.get(url.getPath().trim());
        if (method != null) {
            method.setAccessible(true);
            try {
                MockResponse mockResponse = (MockResponse) method.invoke(mWeatherControl, request);
                Logger.t(TAG).i("method:" + method.getName());
                if (mockResponse == null) {
                    return errorMockResponse;
                } else {

                    return mockResponse;
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return errorMockResponse;

    }


    private MockResponse genErrorMockResponse() {
        String stringFromFile = "";
        try {
            stringFromFile = RestServiceTestHelper.getStringFromFile(Constants.ERROR_JSON);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new MockResponse()
                .setResponseCode(404)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("Cache-Control", "no-cache")
                .setBody(stringFromFile);

    }
}
