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

import butterknife.BindView;
import butterknife.ButterKnife;

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
        mVpWeatherBackground.setAdapter(new ContentFragmentPagerAdapter(getSupportFragmentManager()));
        mLpiIndicator.setViewPager(mVpWeatherBackground,0);


//        startService(new Intent(HomeActivity.this,HomeService.class));

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
}
