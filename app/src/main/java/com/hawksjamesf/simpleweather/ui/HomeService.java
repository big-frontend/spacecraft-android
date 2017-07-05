package com.hawksjamesf.simpleweather.ui;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hawksjamesf.simpleweather.MessageEvent;
import com.hawksjamesf.simpleweather.SimpleWeatherApplication;
import com.hawksjamesf.simpleweather.bean.RealTimeBean;
import com.hawksjamesf.simpleweather.bean.fifteendaysbean.SkyConBean;
import com.hawksjamesf.simpleweather.bean.fifteendaysbean.TempeBean;
import com.hawksjamesf.simpleweather.network.WeatherAPIInterface;

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
import okhttp3.Response;
import retrofit2.Retrofit;

/**
 * Created by Hawks93JF on 6/28/2017.
 */
public class HomeService extends IntentService {
    @Inject
    Retrofit mRetrofit;
    @Inject
    Call mFifteenCall;

    @Inject
    MessageEvent mEvent;
    public static final int EVENT_GET_DATA_FIFTEEN_DAYS_ERROR = 0;
    public static final int EVENT_GET_DATA_FIFTEEN_DAYS_OK = 2;
    public static final int EVENT_GET_DATA_REALTIME_OK = 3;
    public static final int EVENT_GET_DATA_REALTIME_ERROR = 4;

    public HomeService() {
        super("HomeService");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SimpleWeatherApplication.getAppComponent().inject(this);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        /**
         * retrofit2 style and get realtime data
         */
        WeatherAPIInterface apiInterface = mRetrofit.create(WeatherAPIInterface.class);
        apiInterface.getRealTimeData("realtime").enqueue(new retrofit2.Callback<RealTimeBean>() {
            @Override
            public void onResponse(retrofit2.Call<RealTimeBean> call, retrofit2.Response<RealTimeBean> response) {
                EventBus.getDefault().postSticky(mEvent.setValueReturnEvent(EVENT_GET_DATA_REALTIME_OK).setVauleWithRealTime(response.body()));
            }

            @Override
            public void onFailure(retrofit2.Call<RealTimeBean> call, Throwable t) {
                EventBus.getDefault().postSticky(mEvent.setValueReturnEvent(EVENT_GET_DATA_REALTIME_ERROR));
            }
        });


        /**
         * okhttp style and get fifteen data
         */
        mFifteenCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().postSticky(mEvent.setValueReturnEvent(EVENT_GET_DATA_FIFTEEN_DAYS_ERROR));
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
                    List<TempeBean> tempeBeans = new Gson().fromJson(tempeArray.toString(), tempeType);
//                    for (TempeBean tempeBean : tempeBeans) {
//                        Logger.d(tempeBean);
//
//                    }
                    JSONArray skyconArry = dailyObj.getJSONArray("skycon");
                    Type skyconType = new TypeToken<List<SkyConBean>>() {
                    }.getType();
                    List<SkyConBean> skyconBeans = new Gson().fromJson(skyconArry.toString(), skyconType);
//                    for (SkyConBean skyconBean : mSkyconBeans) {
//                        Logger.d(skyconArry);
//                    }
                    /**
                     * replace runOnUiThread method by eventbus  mode to update UI thread
                     */

//                    mActivity.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            mAdapter.setFifteenData(mTempeBeans, mSkyconBeans);
//                            mAdapter.notifyDataSetChanged();
//                        }
//                    });
                    EventBus.getDefault().postSticky(mEvent.setValueReturnEvent(EVENT_GET_DATA_FIFTEEN_DAYS_OK).setMapWithFifteen(tempeBeans, skyconBeans));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


    }
}

