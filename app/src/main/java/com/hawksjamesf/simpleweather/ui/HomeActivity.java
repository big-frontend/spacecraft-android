package com.hawksjamesf.simpleweather.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.hawksjamesf.simpleweather.R;
import com.hawksjamesf.simpleweather.SimpleWeatherApplication;
import com.hawksjamesf.simpleweather.bean.WeatherData;
import com.hawksjamesf.simpleweather.network.WeatherAPIInterface;
import com.hawksjamesf.simpleweather.ui.mvp.RxActivity;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/SimpleWeather
 *
 * @author: hawks jamesf
 * @since: 2017/7/4
 */
public class HomeActivity extends RxActivity<HomePresenter> implements HomeContract.View {

    private static final String TAG = "HomeActivity---";
    private RecyclerView mrvHome;

    @Inject
    WeatherAPIInterface api;


    @Override
    public HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_home);
        presenter.load();
        mrvHome = (RecyclerView) findViewById(R.id.rv_home);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mrvHome.setLayoutManager(manager);
        HomeAdapter adapter = new HomeAdapter();
        mrvHome.setAdapter(adapter);



        SimpleWeatherApplication.getAppComponent().inject(this);

        api.getCurrentWeatherDate("Shanghai")
                .subscribe(new Consumer<WeatherData>() {
                    @Override
                    public void accept(WeatherData weatherData) throws Exception {
                        Log.d(TAG, "get current data");

                    }
                });

    }



    private class HomeAdapter extends RecyclerView.Adapter<HomeViewHolder> {
        @Override
        public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(HomeViewHolder holder, int position) {

        }

        @Override

        public int getItemCount() {
            return 0;
        }
    }

    private class HomeViewHolder extends RecyclerView.ViewHolder {

        public HomeViewHolder(View itemView) {
            super(itemView);
        }
    }


}
