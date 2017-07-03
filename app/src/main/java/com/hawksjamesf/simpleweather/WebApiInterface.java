package com.hawksjamesf.simpleweather;

import com.hawksjamesf.simpleweather.bean.RealTimeBean;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by hawks.jamesf on 7/2/17.
 */

public interface WebApiInterface {
    @GET("v2/TAkhjf8d1nlSlspN/121.6544,25.1552/{filename}.json")
    Call <RealTimeBean> getData(@Path("filename") String filename);

    @GET("v2/TAkhjf8d1nlSlspN/121.6544,25.1552/{filename}.json")
    Call <ResponseBody> getFifteenDaysData(@Path("filename") String filename);
}
