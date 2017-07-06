package com.hawksjamesf.simpleweather.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hawksjamesf.simpleweather.R;
import com.viewpagerindicator.LinePageIndicator;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/SimpleWeather
 *  @author: hawks jamesf
 *  @since: 2017/7/4
 */
public class HomeActivity extends AppCompatActivity  {

    private static final String TAG = "HomeActivity";
    @Nullable
    @BindView(R.id.vp_weather_background)
    ViewPager mVpWeatherBackground;
    @Nullable
    @BindView(R.id.lpi_indicator)
    LinePageIndicator mLpiIndicator;

    private Fragment[] contentFragment={new HomeFragment()/*,new HomeFragment(),new HomeFragment(),new HomeFragment()*/};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        EventBus.getDefault().register(contentFragment[0]);


    }

    private class ContentFragmentPagerAdapter extends FragmentPagerAdapter{

        public ContentFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return contentFragment[position];
        }

        @Override
        public int getCount() {
            return contentFragment.length;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mVpWeatherBackground.setAdapter(new ContentFragmentPagerAdapter(getSupportFragmentManager()));
        mLpiIndicator.setViewPager(mVpWeatherBackground,0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(contentFragment[0]);
    }
}
