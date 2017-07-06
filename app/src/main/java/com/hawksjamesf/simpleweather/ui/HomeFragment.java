package com.hawksjamesf.simpleweather.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hawksjamesf.simpleweather.BuildConfig;
import com.hawksjamesf.simpleweather.MessageEvent;
import com.hawksjamesf.simpleweather.R;
import com.hawksjamesf.simpleweather.SimpleWeatherApplication;
import com.hawksjamesf.simpleweather.bean.RealTimeBean;
import com.hawksjamesf.simpleweather.bean.fifteendaysbean.SkyConBean;
import com.hawksjamesf.simpleweather.bean.fifteendaysbean.TempeBean;
import com.hawksjamesf.simpleweather.network.WeatherAPIInterface;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.matteobattilana.library.Common.Constants;
import xyz.matteobattilana.library.WeatherView;
/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/SimpleWeather
 *  @author: hawks jamesf
 *  @since: 2017/7/4
 */
public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    @BindView(R.id.rlv_pull_refresh)
    RefreshListView mRlvPullRefresh;
    @BindView(R.id.wv_weather_status)
    WeatherView mWvWeatherStatus;
    private Activity mActivity;
    private RefreshAdapter mAdapter;
    @Inject
    MessageEvent mEvent;

    private static final int REFRESH_FLAG = 11;


    public static final int EVENT_GET_DATA_REFRESH_ERROR = -1;
    public static final int EVENT_GET_DATA_FIFTEEN_DAYS_ERROR = 0;

    public static final int EVENT_GET_DATA_REFRESH_OK = 1;
    public static final int EVENT_GET_DATA_FIFTEEN_DAYS_OK = 2;
    public static final int EVENT_GET_DATA_REALTIME_OK = 3;
    public static final int EVENT_GET_DATA_REALTIME_ERROR = 4;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SimpleWeatherApplication.getAppComponent().inject(this);

        mWvWeatherStatus.setWeather(Constants.weatherStatus.RAIN)
                .setCurrentLifeTime(2000)
                .setCurrentFadeOutTime(1000)
                .setCurrentParticles(43)
                .setFPS(84)
                .setCurrentAngle(-3)
                .setOrientationMode(Constants.orientationStatus.ENABLE)
                .startAnimation();

        //set up pull-refresh view
        mAdapter = new RefreshAdapter(mActivity);

        mRlvPullRefresh.setAdapter(mAdapter);




    }

    private void requsetDataFromServer() {
         /*
          retrofit2 style and get realtime data
         */
        WeatherAPIInterface apiInterface = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(WeatherAPIInterface.class);
        apiInterface.getRealTimeData("realtime").enqueue(new retrofit2.Callback<RealTimeBean>() {
            MessageEvent event=new MessageEvent();
            @Override
            public void onResponse(retrofit2.Call<RealTimeBean> call, retrofit2.Response<RealTimeBean> response) {
                EventBus.getDefault().post(mEvent.setValueReturnEvent(EVENT_GET_DATA_REFRESH_OK).setVauleWithRealTime(response.body()));
            }

            @Override
            public void onFailure(retrofit2.Call<RealTimeBean> call, Throwable t) {
                EventBus.getDefault().post(mEvent.setValueReturnEvent(EVENT_GET_DATA_REALTIME_ERROR));
            }
        });


        /*
          okhttp style and get fifteen data
         */
        new OkHttpClient().newCall(new Request.Builder().url("https://api.caiyunapp.com/v2/TAkhjf8d1nlSlspN/121.6544,25.1552/forecast.json").build()).enqueue(new Callback() {
            MessageEvent event=new MessageEvent();
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(mEvent.setValueReturnEvent(EVENT_GET_DATA_REALTIME_ERROR));
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
                    JSONArray skyconArry = dailyObj.getJSONArray("skycon");
                    Type skyconType = new TypeToken<List<SkyConBean>>() {
                    }.getType();
                    List<SkyConBean> skyconBeans = new Gson().fromJson(skyconArry.toString(), skyconType);
                    /*
                      replace runOnUiThread method by eventbus  mode to update UI thread
                     */

//                    mActivity.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            mAdapter.setFifteenData(mTempeBeans, mSkyconBeans);
//                            mAdapter.notifyDataSetChanged();
//                        }
//                    });
                    EventBus.getDefault().post(mEvent.setValueReturnEvent(EVENT_GET_DATA_REFRESH_OK).setMapWithFifteen(tempeBeans, skyconBeans));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();



    }

    @Override
    public void onResume() {
        super.onResume();
        mRlvPullRefresh.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requsetDataFromServer();

            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.getValueReturnEvent()) {
            case EVENT_GET_DATA_REFRESH_ERROR:
                Toast.makeText(mActivity, "network fail", Toast.LENGTH_SHORT).show();
                mRlvPullRefresh.onRefreshComplete(false);
                break;

            case EVENT_GET_DATA_REFRESH_OK:
                updateRealtimeDatas(event.getVauleWithRealTime());
                updateFifteenDaysDatas(event.getMapWithFifteen());
                Logger.d(event.getVauleWithRealTime());
                Logger.d(event.getMapWithFifteen());
                mAdapter.notifyDataSetChanged();
                mRlvPullRefresh.onRefreshComplete(true);
                Toast.makeText(mActivity, "upated", Toast.LENGTH_SHORT).show();
                break;


            case EVENT_GET_DATA_FIFTEEN_DAYS_OK:
                updateFifteenDaysDatas(EventBus.getDefault().getStickyEvent(MessageEvent.class).getMapWithFifteen());
                mAdapter.notifyDataSetChanged();
                break;
            case EVENT_GET_DATA_FIFTEEN_DAYS_ERROR:
                Toast.makeText(mActivity, "network fail fifteen", Toast.LENGTH_SHORT).show();
                break;

            case EVENT_GET_DATA_REALTIME_OK:
                updateRealtimeDatas(EventBus.getDefault().getStickyEvent(MessageEvent.class).getVauleWithRealTime());

                mAdapter.notifyDataSetChanged();
                break;
            case EVENT_GET_DATA_REALTIME_ERROR:
                Toast.makeText(mActivity, "network fail realtime", Toast.LENGTH_SHORT).show();
                break;

        }



    }

    private void updateRealtimeDatas(RealTimeBean rlBean) {
        mAdapter.setRealTimeData(rlBean);
    }

    private void updateFifteenDaysDatas(Map<List<TempeBean>, List<SkyConBean>> datas) {
List<TempeBean> tempeBeans=null;
        List<SkyConBean> skyConBeans=null;
        for (Map.Entry<List<TempeBean>, List<SkyConBean>> listListEntry : datas.entrySet()) {
             tempeBeans = listListEntry.getKey();
             skyConBeans = listListEntry.getValue();
            Logger.d(tempeBeans +"\n"+skyConBeans);

        }
//                Iterator<Map.Entry<List<TempeBean>, List<SkyConBean>>> iterator = datas.entrySet().iterator();
//                while (iterator.hasNext()) {
//                    mTempeBeans = iterator.next().getKey();
//                    mSkyconBeans = iterator.next().getValue();
//                    Logger.d();
//                }
//                Logger.d(iterator.next().getKey());
//                List<TempeBean> tempeBeans=new ArrayList<>();;
//                List<SkyConBean> skyConBeen = iterator.next().getValue();

        mAdapter.setFifteenData(tempeBeans,skyConBeans);
    }



}
