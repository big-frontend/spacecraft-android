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
import com.hawksjamesf.simpleweather.bean.SkyConBean;
import com.hawksjamesf.simpleweather.bean.TempeBean;

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
//        mActivity.startService(new Intent(mActivity,HomeService.class));


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //get server datas
        new OkHttpClient().newCall(new Request.Builder().url("https://api.caiyunapp.com/v2/TAkhjf8d1nlSlspN/121.6544,25.1552/forecast.json").build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(mActivity, "network fail", Toast.LENGTH_SHORT).show();
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
                    Type skyconType = new TypeToken<List<SkyConBean>>() {}.getType();
                    mSkyconBeans = new Gson().fromJson(skyconArry.toString(), skyconType);
//                    for (SkyConBean skyconBean : mSkyconBeans) {
//                        Logger.d(skyconArry);
//                    }
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.setData(mTempeBeans,mSkyconBeans);
                            mAdapter.notifyDataSetChanged();
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


    }





}
