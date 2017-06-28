package com.hawksjamesf.simpleweather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.viewpagerindicator.LinePageIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity  {

    private static final String TAG = "HomeActivity";

    @BindView(R.id.vp_weather_background)
    ViewPager mVpWeatherBackground;
    @BindView(R.id.lpi_indicator)
    LinePageIndicator mLpiIndicator;

    private Fragment[] contentFragment={new HomeFragment(),new HomeFragment(),new HomeFragment(),new HomeFragment()};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        getWindow().getDecorView().setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        ButterKnife.bind(this);
        mVpWeatherBackground.setAdapter(new ContentFragmentPagerAdapter(getSupportFragmentManager()));
        mLpiIndicator.setViewPager(mVpWeatherBackground,0);

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
