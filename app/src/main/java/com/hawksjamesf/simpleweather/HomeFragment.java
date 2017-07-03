package com.hawksjamesf.simpleweather;

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
import com.hawksjamesf.simpleweather.bean.RealTimeBean;
import com.hawksjamesf.simpleweather.bean.fifteendaysbean.SkyConBean;
import com.hawksjamesf.simpleweather.bean.fifteendaysbean.TempeBean;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.matteobattilana.library.Common.Constants;
import xyz.matteobattilana.library.WeatherView;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    @BindView(R.id.rlv_pull_refresh)
    RefreshListView mRlvPullRefresh;
    @BindView(R.id.wv_weather_status)
    WeatherView mWvWeatherStatus;
    private Activity mActivity;
    private List<TempeBean> mTempeBeans;
    private List<SkyConBean> mSkyconBeans;
    private RefreshAdapter mAdapter;

    public static final  int EVENT_GET_DATA_REFRESH_ERROR=-1;
    public static final  int EVENT_GET_DATA_FIFTEEN_DAYS_ERROR=0;
    public static final  int EVENT_GET_DATA_REFRESH_OK=1;
    public static final  int EVENT_GET_DATA_FIFTEEN_DAYS_OK=2;
    private int refresh_flag=11;
    private int fifteendays_flag=12;


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
        mRlvPullRefresh.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestDatasFromServer(refresh_flag);


            }
        });
//        mActivity.startService(new Intent(mActivity,HomeService.class));


    }

    private void requestDatasFromServer(final int flag) {
        // retrofit style
//        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.caiyunapp.com/").addConverterFactory(GsonConverterFactory.create()).build();
//        WebApiInterface webApiInterface = retrofit.create(WebApiInterface.class);
//        retrofit2.Call<RealTimeBean> beanCall = webApiInterface.getData("realtime");
//        beanCall.enqueue(new retrofit2.Callback<RealTimeBean>() {
//            @Override
//            public void onResponse(retrofit2.Call<RealTimeBean> call, retrofit2.Response<RealTimeBean> response) {
//                Logger.d(response.body());
//            }
//
//            @Override
//            public void onFailure(retrofit2.Call<RealTimeBean> call, Throwable t) {
//                Logger.d("fail");
//            }
//        });

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.caiyunapp.com/").addConverterFactory(GsonConverterFactory.create()).build();
        WebApiInterface webApiInterface = retrofit.create(WebApiInterface.class);
        retrofit2.Call<RealTimeBean> fifteenBeanCall = webApiInterface.getData("realtime");
        fifteenBeanCall.enqueue(new retrofit2.Callback<RealTimeBean>() {
            @Override
            public void onResponse(retrofit2.Call<RealTimeBean> call, retrofit2.Response<RealTimeBean> response) {
                Logger.d(response.toString());
            }

            @Override
            public void onFailure(retrofit2.Call<RealTimeBean> call, Throwable t) {
                Logger.d("fail");
            }
        });


        new Thread(new Runnable() {
            @Override
            public void run() {
                Retrofit retrofittest = new Retrofit.Builder().baseUrl("https://api.caiyunapp.com/").build();
                WebApiInterface webApiInterfacetest = retrofittest.create(WebApiInterface.class);
                retrofit2.Call<ResponseBody> fifteenDaysData = webApiInterfacetest.getFifteenDaysData("forecast");
                retrofit2.Response<ResponseBody> execute = null;
                try {
                    execute = fifteenDaysData.execute();
                    Logger.d(execute.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //get server datas
        final MessageEvent event=new MessageEvent();
        new OkHttpClient().newCall(new Request.Builder().url("https://api.caiyunapp.com/v2/TAkhjf8d1nlSlspN/121.6544,25.1552/forecast.json").build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (flag==11){
                    EventBus.getDefault().post(event.setValueReturnEvent(EVENT_GET_DATA_REFRESH_ERROR));
                }else if (flag==12){
                    EventBus.getDefault().post(event.setValueReturnEvent(EVENT_GET_DATA_FIFTEEN_DAYS_ERROR));
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
                    mTempeBeans = new Gson().fromJson(tempeArray.toString(), tempeType);
//                    for (TempeBean tempeBean : tempeBeans) {
//                        Logger.d(tempeBean);
//
//                    }
                    JSONArray skyconArry = dailyObj.getJSONArray("skycon");
                    Type skyconType = new TypeToken<List<SkyConBean>>() {
                    }.getType();
                    mSkyconBeans = new Gson().fromJson(skyconArry.toString(), skyconType);
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
                    if (flag==11){

                        EventBus.getDefault().post(event.setValueReturnEvent(EVENT_GET_DATA_REFRESH_OK));
                    }else if (flag==12){
                        EventBus.getDefault().post(event.setValueReturnEvent(EVENT_GET_DATA_FIFTEEN_DAYS_OK));
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        requestDatasFromServer(fifteendays_flag);




    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event){
        switch (event.valueReturnEvent){
            case EVENT_GET_DATA_FIFTEEN_DAYS_ERROR:
                Toast.makeText(mActivity, "network fail", Toast.LENGTH_SHORT).show();
                break;
            case  EVENT_GET_DATA_REFRESH_ERROR :
//                Toast.makeText(mActivity, "network fail", Toast.LENGTH_SHORT).show();
                mRlvPullRefresh.onRefreshComplete(false);
                break;

            case EVENT_GET_DATA_FIFTEEN_DAYS_OK:
                mAdapter.setData(mTempeBeans, mSkyconBeans);
                mAdapter.notifyDataSetChanged();
                break;
            case EVENT_GET_DATA_REFRESH_OK:
                mRlvPullRefresh.onRefreshComplete(true);
                mAdapter.setData(mTempeBeans, mSkyconBeans);
                mAdapter.notifyDataSetChanged();
                Toast.makeText(mActivity,"upated",Toast.LENGTH_SHORT).show();
                break;

        }

    }


    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    private class MessageEvent {
        private int valueReturnEvent;

        public MessageEvent setValueReturnEvent(int valueReturnEvent) {
            this.valueReturnEvent = valueReturnEvent;
            return this;
        }
    }
}
