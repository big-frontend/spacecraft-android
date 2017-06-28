package com.hawksjamesf.simpleweather;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import xyz.matteobattilana.library.Common.Constants;
import xyz.matteobattilana.library.WeatherView;

//import me.zhouzhuo.zzweatherview.AirLevel;
//import me.zhouzhuo.zzweatherview.WeatherModel;


public class HomeFragment extends Fragment {
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
        mRvFiftenDaysForecast.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
        //get server datas
        new OkHttpClient().newCall(new Request.Builder().url("https://api.caiyunapp.com/v2/TAkhjf8d1nlSlspN/121.6544,25.1552/forecast.json").build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(mActivity, "network fail", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Logger.json(response.body().string());
                try {
                    JSONObject rootObj= new JSONObject(response.body().string());
                    JSONObject resultObj = jobj.getJSONObject("result");
                    JSONObject dailyObj = resultObj.getJSONObject("daily");
                    JSONObject temperatureObj = dailyObj.getJSONObject("temperature");
                    JSONObject skyconObj = dailyObj.getJSONObject("skycon");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        mRvFiftenDaysForecast.setAdapter(new FiftenDaysAdapter());


    }


    public class FiftenDaysAdapter extends RecyclerView.Adapter<FiftenDaysAdapter.FiftenDaysHolder> {


//        public FiftenDaysAdapter(List<WeatherData>) {
//
//
//        }

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

        public class FiftenDaysHolder extends RecyclerView.ViewHolder {


            public FiftenDaysHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }


//    private List<WeatherModel> generateData() {
//        List<WeatherModel> list = new ArrayList<WeatherModel>();
//        WeatherModel model = new WeatherModel();
//        model.setDate("12/07");
//        model.setWeek("昨天");
//        model.setDayWeather("大雪");
//        model.setDayTemp(11);
//        model.setNightTemp(5);
//        model.setNightWeather("晴");
//        model.setWindOrientation("西南风");
//        model.setWindLevel("3级");
//        model.setAirLevel(AirLevel.EXCELLENT);
//        list.add(model);
//
//        WeatherModel model1 = new WeatherModel();
//        model1.setDate("12/08");
//        model1.setWeek("今天");
//        model1.setDayWeather("晴");
//        model1.setDayTemp(8);
//        model1.setNightTemp(5);
//        model1.setNightWeather("晴");
//        model1.setWindOrientation("西南风");
//        model1.setWindLevel("3级");
//        model1.setAirLevel(AirLevel.HIGH);
//        list.add(model1);
//
//        WeatherModel model2 = new WeatherModel();
//        model2.setDate("12/09");
//        model2.setWeek("明天");
//        model2.setDayWeather("晴");
//        model2.setDayTemp(9);
//        model2.setNightTemp(8);
//        model2.setNightWeather("晴");
//        model2.setWindOrientation("东南风");
//        model2.setWindLevel("3级");
//        model2.setAirLevel(AirLevel.POISONOUS);
//        list.add(model2);
//
//        WeatherModel model3 = new WeatherModel();
//        model3.setDate("12/10");
//        model3.setWeek("周六");
//        model3.setDayWeather("晴");
//        model3.setDayTemp(12);
//        model3.setNightTemp(9);
//        model3.setDayPic(R.drawable.w0);
//        model3.setNightPic(R.drawable.w1);
//        model3.setNightWeather("晴");
//        model3.setWindOrientation("东北风");
//        model3.setWindLevel("3级");
//        model3.setAirLevel(AirLevel.GOOD);
//        list.add(model3);
//
//        WeatherModel model4 = new WeatherModel();
//        model4.setDate("12/11");
//        model4.setWeek("周日");
//        model4.setDayWeather("多云");
//        model4.setDayTemp(13);
//        model4.setNightTemp(7);
//        model4.setDayPic(R.drawable.w2);
//        model4.setNightPic(R.drawable.w4);
//        model4.setNightWeather("多云");
//        model4.setWindOrientation("东北风");
//        model4.setWindLevel("3级");
//        model4.setAirLevel(AirLevel.LIGHT);
//        list.add(model4);
//
//        WeatherModel model5 = new WeatherModel();
//        model5.setDate("12/12");
//        model5.setWeek("周一");
//        model5.setDayWeather("多云");
//        model5.setDayTemp(17);
//        model5.setNightTemp(8);
//        model5.setDayPic(R.drawable.w3);
//        model5.setNightPic(R.drawable.w4);
//        model5.setNightWeather("多云");
//        model5.setWindOrientation("西南风");
//        model5.setWindLevel("3级");
//        model5.setAirLevel(AirLevel.LIGHT);
//        list.add(model5);
//
//        WeatherModel model6 = new WeatherModel();
//        model6.setDate("12/13");
//        model6.setWeek("周二");
//        model6.setDayWeather("晴");
//        model6.setDayTemp(13);
//        model6.setNightTemp(6);
//        model6.setDayPic(R.drawable.w5);
//        model6.setNightPic(R.drawable.w6);
//        model6.setNightWeather("晴");
//        model6.setWindOrientation("西南风");
//        model6.setWindLevel("3级");
//        model6.setAirLevel(AirLevel.POISONOUS);
//        list.add(model6);
//
//
//        return list;
//    }

}
