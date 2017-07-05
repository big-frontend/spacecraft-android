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

import com.hawksjamesf.simpleweather.BuildConfig;
import com.hawksjamesf.simpleweather.MessageEvent;
import com.hawksjamesf.simpleweather.R;
import com.hawksjamesf.simpleweather.bean.RealTimeBean;
import com.hawksjamesf.simpleweather.bean.fifteendaysbean.SkyConBean;
import com.hawksjamesf.simpleweather.bean.fifteendaysbean.TempeBean;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    private RealTimeBean mRLBean;
    private RefreshAdapter mAdapter;

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
        EventBus.getDefault().register(this);
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
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.getValueReturnEvent()) {
            case EVENT_GET_DATA_REFRESH_ERROR:
                Toast.makeText(mActivity, "network fail", Toast.LENGTH_SHORT).show();
                mRlvPullRefresh.onRefreshComplete(false);
                break;

            case EVENT_GET_DATA_REFRESH_OK:
                mRlvPullRefresh.onRefreshComplete(true);
//                mAdapter.setFifteenData();
                mAdapter.notifyDataSetChanged();
                Toast.makeText(mActivity, "upated", Toast.LENGTH_SHORT).show();
                break;


            case EVENT_GET_DATA_FIFTEEN_DAYS_OK:
                Map<List<TempeBean>, List<SkyConBean>> datas = EventBus.getDefault().getStickyEvent(MessageEvent.class).getMapWithFifteen();

//                if (datas == null) break;
                Iterator<Map.Entry<List<TempeBean>, List<SkyConBean>>> iterator = datas.entrySet().iterator();

                while (iterator.hasNext()) {
                    mTempeBeans = iterator.next().getKey();
                    mSkyconBeans = iterator.next().getValue();
                }
                if (BuildConfig.DEBUG) {
                    Logger.d(mTempeBeans);
                    Logger.d(mSkyconBeans);
                }
                mAdapter.setFifteenData(mTempeBeans,mSkyconBeans);
                mAdapter.notifyDataSetChanged();
                break;
            case EVENT_GET_DATA_FIFTEEN_DAYS_ERROR:
                Toast.makeText(mActivity, "network fail fifteen", Toast.LENGTH_SHORT).show();
                break;

            case EVENT_GET_DATA_REALTIME_OK:
                mRLBean = EventBus.getDefault().getStickyEvent(MessageEvent.class).getVauleWithRealTime();
                mAdapter.setRealTimeData(mRLBean);
                mAdapter.notifyDataSetChanged();
                break;
            case EVENT_GET_DATA_REALTIME_ERROR:
                Toast.makeText(mActivity, "network fail realtime", Toast.LENGTH_SHORT).show();
                break;

        }

    }


}
