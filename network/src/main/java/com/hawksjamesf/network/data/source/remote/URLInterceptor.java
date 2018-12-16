package com.hawksjamesf.network.data.source.remote;

import com.hawksjamesf.network.BuildConfig;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawskjamesf
 * @since: Sep/25/2018  Tue
 */
public class URLInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
//        Request request = chain.request().newBuilder()
//                .url(BuildConfig.WEATHER_APP_ID)
//                .build();
        Request request = chain.request();
        HttpUrl url = request.url().newBuilder()
                .addQueryParameter("appid", BuildConfig.WEATHER_APP_ID)
                .build();
        Request newRequest = request.newBuilder()
                .url(url)
                .build();
//        return chain.proceed(chain.request());
        return chain.proceed(newRequest);
    }
}
