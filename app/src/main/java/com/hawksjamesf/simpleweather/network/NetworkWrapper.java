package com.hawksjamesf.simpleweather.network;

import com.google.gson.reflect.TypeToken;
import com.hawksjamesf.simpleweather.MessageEvent;
import com.hawksjamesf.simpleweather.bean.RealTimeBean;
import com.hawksjamesf.simpleweather.bean.fifteendaysbean.SkyConBean;
import com.hawksjamesf.simpleweather.bean.fifteendaysbean.TempeBean;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.hawksjamesf.simpleweather.ui.HomeFragment.EVENT_GET_DATA_FIFTEEN_DAYS_ERROR;
import static com.hawksjamesf.simpleweather.ui.HomeFragment.EVENT_GET_DATA_FIFTEEN_DAYS_OK;
import static com.hawksjamesf.simpleweather.ui.HomeFragment.EVENT_GET_DATA_REALTIME_ERROR;
import static com.hawksjamesf.simpleweather.ui.HomeFragment.EVENT_GET_DATA_REFRESH_ERROR;
import static com.hawksjamesf.simpleweather.ui.HomeFragment.EVENT_GET_DATA_REFRESH_OK;

/**
 * Copyright ® 2017
 * Shanghai wind-mobi
 * All right reserved.
 *
 * @author:chenjinfa
 * @since:2017/7/4
 */

public class NetworkWrapper {
//    private static final NetworkWrapper ourInstance = new NetworkWrapper();
    private MessageEvent mEvent;

//    public static NetworkWrapper getInstance() {
//        return ourInstance;
//    }

//    private NetworkWrapper() {
//        mEvent = new MessageEvent();
//    }
    @Inject
    public NetworkWrapper() {
//        this.mEvent = mEvent;
    }

    /**
     * retrofit2 style and get realtime data
     * @param flag
     */
    public void reqwithRestful(final int flag,String baseurl,String filename) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.caiyunapp.com/").addConverterFactory(GsonConverterFactory.create()).build();
        WeatherAPIInterface apiInterface= retrofit.create(WeatherAPIInterface.class);
        retrofit2.Call<RealTimeBean> dataCall = apiInterface.getRealTimeData("realtime");
        dataCall.enqueue(new retrofit2.Callback<RealTimeBean>() {
            @Override
            public void onResponse(retrofit2.Call<RealTimeBean> call, retrofit2.Response<RealTimeBean> response) {

//                Logger.d(response.body());
//                if (flag == 11) {
//                    EventBus.getDefault().post(mEvent.setValueReturnEvent(EVENT_GET_DATA_REFRESH_OK));
//                } else if (flag == 12) {
//                    EventBus.getDefault().post(mEvent.setValueReturnEvent(EVENT_GET_DATA_REALTIME_OK).setVauleWithRealTime(response.body()));
//                }
//                mRealTimeBean = response.body();
                if (flag == 11) {

                    EventBus.getDefault().post(mEvent.setValueReturnEvent(EVENT_GET_DATA_REFRESH_OK));
                } else if (flag == 12) {
                    EventBus.getDefault().post(mEvent.setValueReturnEvent(EVENT_GET_DATA_FIFTEEN_DAYS_OK));
                }
            }

            @Override
            public void onFailure(retrofit2.Call<RealTimeBean> call, Throwable t) {
                if (flag == 11) {
                    EventBus.getDefault().post(mEvent.setValueReturnEvent(EVENT_GET_DATA_REFRESH_ERROR));
                } else if (flag == 12) {
                    EventBus.getDefault().post(mEvent.setValueReturnEvent(EVENT_GET_DATA_REALTIME_ERROR));
                }

            }
        });



    }

    /**
     * okhttp style and fifteen data
     */
    public void reqwitOkHttpStyle(final int flag,String url){

        new OkHttpClient().newCall(new Request.Builder().url("https://api.caiyunapp.com/v2/TAkhjf8d1nlSlspN/121.6544,25.1552/forecast.json").build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (flag == 11) {
                    EventBus.getDefault().post(mEvent.setValueReturnEvent(EVENT_GET_DATA_REFRESH_ERROR));
                } else if (flag == 12) {
                    EventBus.getDefault().post(mEvent.setValueReturnEvent(EVENT_GET_DATA_FIFTEEN_DAYS_ERROR));
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject rootObj = new JSONObject(response.body().string());
                    JSONObject resultObj = rootObj.getJSONObject("result");
                    JSONObject dailyObj = resultObj.getJSONObject("daily");

                    JSONArray tempeArray = dailyObj.getJSONArray("temperature");
                    Type tempeType = new TypeToken<List<TempeBean>>() {
                    }.getType();
//                    mTempeBeans = new Gson().fromJson(tempeArray.toString(), tempeType);
//                    for (TempeBean tempeBean : tempeBeans) {
//                        Logger.d(tempeBean);
//
//                    }
                    JSONArray skyconArry = dailyObj.getJSONArray("skycon");
                    Type skyconType = new TypeToken<List<SkyConBean>>() {
                    }.getType();
//                    mSkyconBeans = new Gson().fromJson(skyconArry.toString(), skyconType);
//                    for (SkyConBean skyconBean : mSkyconBeans) {
//                        Logger.d(skyconArry);
//                    }
                    /**
                     * replace runOnUiThread method by eventbus  mode to update UI thread
                     */

//                    mActivity.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            mAdapter.setData(mTempeBeans, mSkyconBeans);
//                            mAdapter.notifyDataSetChanged();
//                        }
//                    });
                    if (flag == 11) {

                        EventBus.getDefault().post(mEvent.setValueReturnEvent(EVENT_GET_DATA_REFRESH_OK));
                    } else if (flag == 12) {
                        EventBus.getDefault().post(mEvent.setValueReturnEvent(EVENT_GET_DATA_FIFTEEN_DAYS_OK));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
