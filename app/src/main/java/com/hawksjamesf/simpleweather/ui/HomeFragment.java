package com.hawksjamesf.simpleweather.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hawksjamesf.simpleweather.R;
import com.hawksjamesf.simpleweather.SimpleWeatherApplication;
import com.hawksjamesf.simpleweather.bean.RealTimeBean;
import com.hawksjamesf.simpleweather.bean.fifteendaysbean.SkyConBean;
import com.hawksjamesf.simpleweather.bean.fifteendaysbean.TempeBean;
import com.hawksjamesf.simpleweather.event.FifteenEvent;
import com.hawksjamesf.simpleweather.event.RealtimeEvent;
import com.hawksjamesf.simpleweather.event.RefreshEvent;
import com.hawksjamesf.simpleweather.network.WeatherAPIInterface;
import com.hawksjamesf.simpleweather.ui.view.refresh.RefreshAdapter;
import com.hawksjamesf.simpleweather.ui.view.refresh.RefreshListView;
import com.hawksjamesf.simpleweather.util.GetWeatherDataUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/SimpleWeather
 *
 * @author: hawks jamesf
 * @since: 2017/7/4
 */
public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment---";
    @BindView(R.id.rlv_pull_refresh)
    RefreshListView mRlvPullRefresh;
    //    @BindView(R.id.wv_weather_status)
//    WeatherView mWvWeatherStatus;
    private Activity mActivity;
    private RefreshAdapter mAdapter;
    @Inject
    RealtimeEvent mEvent;
    @Inject
    FifteenEvent mFifteenEvent;
    @Inject
    RefreshEvent mRefreshEvent;
    @Inject
    WeatherAPIInterface api;

    public Fragment getInstance() {
        Bundle bundle = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static final int EVENT_GET_DATA_REFRESH_ERROR = -1;
//    public static final int EVENT_GET_DATA_FIFTEEN_DAYS_ERROR = 0;

    public static final int EVENT_GET_DATA_REFRESH_OK = 1;
//    public static final int EVENT_GET_DATA_FIFTEEN_DAYS_OK = 2;
//    public static final int EVENT_GET_DATA_REALTIME_OK = 3;
//    public static final int EVENT_GET_DATA_REALTIME_ERROR = 4;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SimpleWeatherApplication.getAppComponent().inject(this);
//        EventBus.getDefault().register(this);

        mAdapter = new RefreshAdapter(mActivity);

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
//        mWvWeatherStatus.setWeather(Constants.weatherStatus.RAIN)
//                .setCurrentLifeTime(2000)
//                .setCurrentFadeOutTime(1000)
//                .setCurrentParticles(43)
//                .setFPS(84)
//                .setCurrentAngle(-3)
//                .setOrientationMode(Constants.orientationStatus.ENABLE)
//                .startAnimation();
        //set up pull-refresh view

        mRlvPullRefresh.setAdapter(mAdapter);

//        api.getCurrentWeatherDate("Shanghai")
//                .subscribe(new Consumer<WeatherData>() {
//                    @Override
//                    public void accept(WeatherData weatherData) throws Exception {
//
//                    }
//                });


    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

        mRlvPullRefresh.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                io.reactivex.Observable.create(new ObservableOnSubscribe<Hashtable>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Hashtable> e) throws Exception {
                         /*
                         get fifteen data from local
                        */
                        Map<List<TempeBean>, List<SkyConBean>> forecast = GetWeatherDataUtils.requestDataFromLocal(TempeBean.class, SkyConBean.class, mActivity, GetWeatherDataUtils.FORECAST);
                        /*
                         get RealTimeBean data from local
                         */
                        RealTimeBean rlBean = GetWeatherDataUtils.requestDataFromLocal(RealTimeBean.class, mActivity, GetWeatherDataUtils.REALTIMES);
                        Thread.sleep(2000L);

                        if (forecast == null || rlBean == null) {
                            e.onError(new Throwable());
                        } else {
                            Hashtable<RealTimeBean, Map<List<TempeBean>, List<SkyConBean>>> wrapper = new Hashtable<>();
                            wrapper.put(rlBean, forecast);
                            e.onNext(wrapper);
                        }

                        e.onComplete();
                    }
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.single())
                        .subscribe(new Observer<Hashtable>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(Hashtable o) {
                                @SuppressWarnings("unchecked") Hashtable.Entry<RealTimeBean, Map<List<TempeBean>, List<SkyConBean>>> entry = (Hashtable.Entry<RealTimeBean, Map<List<TempeBean>, List<SkyConBean>>>) o.entrySet().iterator().next();

                                Map<List<TempeBean>, List<SkyConBean>> map = entry.getValue();
                                Map.Entry<List<TempeBean>, List<SkyConBean>> next = map.entrySet().iterator().next();

                                EventBus.getDefault().post(mRefreshEvent
                                        .setValueReturnEvent(EVENT_GET_DATA_REFRESH_OK)
                                        .setVauleWithRealTime(entry.getKey())
                                        .setMapWithFifteen(next.getKey(), next.getValue()));
                            }

                            @Override
                            public void onError(Throwable e) {
                                EventBus.getDefault().post(mRefreshEvent.setValueReturnEvent(EVENT_GET_DATA_REFRESH_ERROR));

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
//                requsetDataFromServer();

//                mRlvPullRefresh.onRefreshComplete(true);

            }
        });
    }
}
