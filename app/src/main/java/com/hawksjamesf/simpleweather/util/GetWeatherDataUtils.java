package com.hawksjamesf.simpleweather.util;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hawksjamesf.simpleweather.bean.fifteendaysbean.SkyConBean;
import com.hawksjamesf.simpleweather.bean.fifteendaysbean.TempeBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/SimpleWeather
 *
 * @author: hawks.jamesf
 * @since: 9/19/17
 */

public class GetWeatherDataUtils {
    public static final String REALTIMES = "realtime.json";
    public static final String FORECAST = "forecast.json";

    /*
      get real-time data from local
     */
    public static <T> T requestDataFromLocal(Class<T> c, Context ctx, String string) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(ctx.getAssets().open(string)));
            String line = "";
            StringBuilder sb = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }


            return new Gson().fromJson(sb.toString(), c);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /*
     get fifteen data from local
     */
    public static <T1, T2> Map<List<T1>,List<T2>> requestDataFromLocal(Class<T1> c1,  Class<T2> c2, Context ctx, String string) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(ctx.getAssets().open(string)));
            String line = "";
            StringBuilder sb = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            JSONObject rootObj = new JSONObject(sb.toString());

            JSONObject resultObj = rootObj.getJSONObject("result");
            JSONObject dailyObj = resultObj.getJSONObject("daily");

            JSONArray tempeArray = dailyObj.getJSONArray("temperature");
            Type tempeType = new TypeToken<List<TempeBean>>() {
            }.getType();
            List<T1> tempeBeans = new Gson().fromJson(tempeArray.toString(), tempeType);

            JSONArray skyconArry = dailyObj.getJSONArray("skycon");
            Type skyconType = new TypeToken<List<SkyConBean>>() {
            }.getType();
            List<T2> skyconBeans = new Gson().fromJson(skyconArry.toString(), skyconType);

            Hashtable<List<T1>, List<T2>> map = new Hashtable<>();
            map.put(tempeBeans,skyconBeans);
            return map;
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


//    @Inject
//    Retrofit mRetrofit;
//    @Inject
//    Call mFifteenCall;
//    @Inject
//    RealtimeEvent mEvent;
//  public  static String requestDataFromServer(){
//
//      /*
//          retrofit2 style and get realtime data
//         */
//      WeatherAPIInterface apiInterface = mRetrofit.create(WeatherAPIInterface.class);
//      apiInterface.getRealTimeData("realtime").enqueue(new retrofit2.Callback<RealTimeBean>() {
//          @Override
//          public void onResponse(retrofit2.Call<RealTimeBean> call, retrofit2.Response<RealTimeBean> response) {
////                Logger.t(TAG).d(response);
//              EventBus.getDefault().postSticky(mEvent.setValueReturnEvent(EVENT_GET_DATA_REALTIME_OK).setVauleWithRealTime(response.body()));
//          }
//
//          @Override
//          public void onFailure(retrofit2.Call<RealTimeBean> call, Throwable t) {
//              EventBus.getDefault().postSticky(mEvent.setValueReturnEvent(EVENT_GET_DATA_REALTIME_ERROR));
//          }
//      });
//
//
//        /*
//          okhttp style and get fifteen data
//         */
//      mFifteenCall.enqueue(new Callback() {
//          @Override
//          public void onFailure(Call call, IOException e) {
//              EventBus.getDefault().postSticky(mEvent.setValueReturnEvent(EVENT_GET_DATA_FIFTEEN_DAYS_ERROR));
//          }
//          @Override
//          public void onResponse(Call call, Response response) throws IOException {
////                Logger.t(TAG).d(response);
//              try {
    //JSONObject rootObj = new JSONObject(response.body().string());
//                  JSONObject resultObj = rootObj.getJSONObject("result");
//                  JSONObject dailyObj = resultObj.getJSONObject("daily");
//
//                  JSONArray tempeArray = dailyObj.getJSONArray("temperature");
//                  Type tempeType = new TypeToken<List<TempeBean>>() {
//                  }.getType();
//                  List<TempeBean> tempeBeans = new Gson().fromJson(tempeArray.toString(), tempeType);
//                  JSONArray skyconArry = dailyObj.getJSONArray("skycon");
//                  Type skyconType = new TypeToken<List<SkyConBean>>() {
//                  }.getType();
//                  List<SkyConBean> skyconBeans = new Gson().fromJson(skyconArry.toString(), skyconType);
//                    /*
//                      replace runOnUiThread method by eventbus  mode to update UI thread
//                     */
//
////                    mActivity.runOnUiThread(new Runnable() {
////                        @Override
////                        public void run() {
////                            mAdapter.setFifteenData(mTempeBeans, mSkyconBeans);
////                            mAdapter.notifyDataSetChanged();
////                        }
////                    });
//                  EventBus.getDefault().postSticky(mEvent.setValueReturnEvent(EVENT_GET_DATA_FIFTEEN_DAYS_OK).setMapWithFifteen(tempeBeans, skyconBeans));
//
//              } catch (JSONException e) {
//                  e.printStackTrace();
//              }
//
//          }
//      });
//
//
//  }

}
