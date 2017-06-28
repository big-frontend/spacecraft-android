package com.hawksjamesf.simpleweather;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hawksjamesf.simpleweather.bean.DailyBean;
import com.hawksjamesf.simpleweather.bean.SkyConBean;
import com.hawksjamesf.simpleweather.bean.TempeBean;
import com.orhanobut.logger.Logger;

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

public class HomeFragment extends Fragment implements LoaderManager.LoaderCallbacks<DailyBean> {
    private static final String TAG = "HomeFragment";

    @BindView(R.id.wv_weather_status)
    WeatherView mWvWeatherStatus;
    @BindView(R.id.tv_city)
    TextView mTvCity;
    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.tv_week)
    TextView mTvWeek;
    @BindView(R.id.tv_temperature)
    TextView mTvTemperature;
    @BindView(R.id.rv_fiften_days_forecast)
    RecyclerView mRvFiftenDaysForecast;
    private Activity mActivity;

    private FiftenDaysAdapter mFiftenDaysAdapter;


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
        //Optional
        mWvWeatherStatus.setWeather(Constants.weatherStatus.RAIN)
                .setCurrentLifeTime(2000)
                .setCurrentFadeOutTime(1000)
                .setCurrentParticles(43)
                .setFPS(84)
                .setCurrentAngle(-3)
                .setOrientationMode(Constants.orientationStatus.ENABLE)
                .startAnimation();
        mRvFiftenDaysForecast.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mFiftenDaysAdapter = new FiftenDaysAdapter();
        mRvFiftenDaysForecast.setAdapter(mFiftenDaysAdapter);


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
                    Type tempeType = new TypeToken<List<TempeBean>>() {}.getType();
                    List<TempeBean> tempeBeans = new Gson().fromJson(tempeArray.toString(), tempeType);
                    for (TempeBean tempeBean : tempeBeans) {
                        Logger.d(tempeBean);

                    }
                    JSONArray skyconArry =  dailyObj.getJSONArray("skycon");
                    Type skyconType = new TypeToken<List<SkyConBean>>() {}.getType();
                    List<SkyConBean> skyconBeans =   new Gson().fromJson(skyconArry.toString(), skyconType);
                    for (SkyConBean skyconBean : skyconBeans) {
                        Logger.d(skyconBean);
                    }
                    mFiftenDaysAdapter.setData(tempeBeans,skyconBeans);
//                    mFiftenDaysAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
//        getLoaderManager().initLoader(2, null, this);
    }

    @Override
    public Loader<DailyBean> onCreateLoader(int id, Bundle args) {

        return new AsyncTaskLoader<DailyBean>(mActivity) {
            @Override
            public DailyBean loadInBackground() {
                Logger.d("loadInBackground");
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<DailyBean> loader, DailyBean data) {
                Log.d(TAG,"onLoadFinished");
        Logger.d(data);


    }


    @Override
    public void onLoaderReset(Loader<DailyBean> loader) {

    }


    public class FiftenDaysAdapter extends RecyclerView.Adapter<FiftenDaysAdapter.FiftenDaysHolder> {
        private List<SkyConBean> mSkyConBeans;
        private List<TempeBean> mTempeBeans;
        @Override
        public FiftenDaysHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new FiftenDaysHolder(View.inflate(mActivity, R.layout.item_weather, null));
        }

        @Override
        public void onBindViewHolder(FiftenDaysHolder holder, int position) {
        }


        @Override
        public int getItemCount() {
            return 7;
        }

        public void setData(List<TempeBean> tempeBeans, List<SkyConBean> skyconBeans) {
            mTempeBeans = tempeBeans;
            mSkyConBeans=skyconBeans;


        }

        public class FiftenDaysHolder extends RecyclerView.ViewHolder {


            public FiftenDaysHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
    }

}
