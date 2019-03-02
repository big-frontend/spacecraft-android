package com.hawksjamesf.spacecraft.ui.home;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.gson.Gson;
import com.hawksjamesf.common.mvp.RxActivity;
import com.hawksjamesf.common.util.BarUtil;
import com.hawksjamesf.network.data.bean.ListRes;
import com.hawksjamesf.network.data.bean.home.WeatherData;
import com.hawksjamesf.network.data.source.WeatherDataSource;
import com.hawksjamesf.spacecraft.App;
import com.hawksjamesf.spacecraft.R;
import com.orhanobut.logger.Logger;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.functions.Consumer;

/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/Spacecraft
 *
 * @author: hawks jamesf
 * @since: 2017/7/4
 */
public class HomeActivity extends RxActivity<HomePresenter> implements HomeContract.View {

    private static final String TAG = "HomeActivity---";
//    private RecyclerView mrvHome;

    WeatherDataSource source;
    RecyclerView rv;

    @Override
    public HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            BarUtil.setStatusBarTransparent(this);
            BarUtil.setNavBarImmersive(this);
//            BarUtil.setBarsTransparent(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        presenter.load();

        source = App.getNetComponet().getWeatherDataSource();
        source.getCurrentWeatherDate("London")
                .subscribe(new Consumer<WeatherData>() {
                    @Override
                    public void accept(WeatherData weatherData) throws Exception {
                        //onSuccess
                        Logger.t(TAG).json(new Gson().toJson(weatherData));

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

        source.getFiveData("London")
                .subscribe(new Consumer<ListRes<WeatherData>>() {
                    @Override
                    public void accept(ListRes<WeatherData> weatherDataListRes) throws Exception {
                        Logger.t(TAG).json(new Gson().toJson(weatherDataListRes));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

//        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentByTag("tag_navigation_host");
//        if (navHostFragment == null) {
//            navHostFragment = NavHostFragment.create(R.navigation.nav_graph);
//        }
//
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.fl_nav_host, navHostFragment, "tag_navigation_host")
//                .setPrimaryNavigationFragment(navHostFragment)// this is the equivalent to app:defaultNavHost="true"
//                .commitNowAllowingStateLoss();
        AppBarLayout abl = findViewById(R.id.abl);
        abl.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            }
        });
        CollapsingToolbarLayout ctbl=findViewById(R.id.ctbl);
        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setAdapter(new MyAdapter());
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext(), RecyclerView.VERTICAL, false));
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) rv.getLayoutParams();
        AppBarLayout.ScrollingViewBehavior behavior = (AppBarLayout.ScrollingViewBehavior) layoutParams.getBehavior();
        behavior.setOverlayTop(120);
    }

    static class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my, parent, false)
            );
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.tvText.setText("asfasdfas");
        }

        @Override
        public int getItemCount() {
            return 13;
        }
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.tv_text);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
//        return Navigation.findNavController(R.id.).navigateUp();
        return super.onSupportNavigateUp();
    }


}
