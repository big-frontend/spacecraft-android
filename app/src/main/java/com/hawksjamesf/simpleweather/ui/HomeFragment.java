package com.hawksjamesf.simpleweather.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hawksjamesf.simpleweather.R;
import com.hawksjamesf.simpleweather.SimpleWeatherApplication;
import com.hawksjamesf.simpleweather.bean.RealTimeBean;
import com.hawksjamesf.simpleweather.bean.fifteendaysbean.SkyConBean;
import com.hawksjamesf.simpleweather.bean.fifteendaysbean.TempeBean;
import com.hawksjamesf.simpleweather.event.FifteenEvent;
import com.hawksjamesf.simpleweather.event.RealtimeEvent;
import com.hawksjamesf.simpleweather.event.RefreshEvent;
import com.hawksjamesf.simpleweather.ui.view.refresh.RefreshAdapter;
import com.hawksjamesf.simpleweather.ui.view.refresh.RefreshListView;
import com.hawksjamesf.simpleweather.util.GetWeatherDataUtils;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.Printer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import retrofit2.Retrofit;
import xyz.matteobattilana.library.Common.Constants;
import xyz.matteobattilana.library.WeatherView;

/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/SimpleWeather
 *
 * @author: hawks jamesf
 * @since: 2017/7/4
 */
public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    @BindView(R.id.rlv_pull_refresh)
    RefreshListView mRlvPullRefresh;
    @BindView(R.id.wv_weather_status)
    WeatherView mWvWeatherStatus;
    private Activity mActivity;
    private RefreshAdapter mAdapter;
    @Inject
    RealtimeEvent mEvent;
    @Inject
    FifteenEvent mFifteenEvent;
    @Inject
    RefreshEvent mRefreshEvent;
    @Inject
    Retrofit mRetrofit;
    @Inject
    Call mFifteenCall;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SimpleWeatherApplication.getAppComponent().inject(this);
//        EventBus.getDefault().register(this);

        mAdapter = new RefreshAdapter(mActivity);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Debug.startMethodTracing("cjf");
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

        mRlvPullRefresh.setAdapter(mAdapter);
        Debug.stopMethodTracing();


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
                final Printer t = Logger.t(TAG);
                Observable.create(new ObservableOnSubscribe<Hashtable>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Hashtable> e) throws Exception {
                        t.d("subscribe");
                         /*
                         get fifteen data from local
                        */
                        Map<List<TempeBean>, List<SkyConBean>> forecast = GetWeatherDataUtils.requestDataFromLocal(TempeBean.class, SkyConBean.class,mActivity, GetWeatherDataUtils.FORECAST);
                        /*
                         get RealTimeBean data from local
                         */
                        RealTimeBean rlBean = GetWeatherDataUtils.requestDataFromLocal(RealTimeBean.class, mActivity, GetWeatherDataUtils.REALTIMES);
                        Thread.sleep(2000L);

                        if (forecast == null || rlBean == null) {
                            e.onError(new Throwable());
                        } else {
                            Hashtable<RealTimeBean, Map<List<TempeBean>, List<SkyConBean>>> wrapper = new Hashtable<>();
                            wrapper.put(rlBean,forecast);
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
                                 Hashtable.Entry<RealTimeBean,Map<List<TempeBean>, List<SkyConBean>>> entry = (Hashtable.Entry<RealTimeBean, Map<List<TempeBean>, List<SkyConBean>>>) o.entrySet().iterator().next();

                                Map<List<TempeBean>, List<SkyConBean>> map = entry.getValue();
                                Map.Entry<List<TempeBean>, List<SkyConBean>> next = map.entrySet().iterator().next();

                                t.d("onNext:"+entry.getKey()+"\n---"+next.getKey()+"\n---"+next.getValue());
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

    //    @Subscribe(threadMode =ThreadMode.POSTING)
//    public void  onMessageEventPosting(RealtimeEvent event){
//        Logger.t(TAG).i("post"+event.toString());
//    }
//    @Subscribe(threadMode =ThreadMode.ASYNC)
//    public void  onMessageEventAsync(RealtimeEvent event){
//        Logger.t(TAG).i("post"+event.toString());
//    }
//    @Subscribe(threadMode =ThreadMode.BACKGROUND)
//    public void  onMessgaeEventBackgroud(RealtimeEvent event){
//        Logger.t(TAG).w("backgroud:"+event.toString());
//
//    }
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onFifteenEvent(FifteenEvent event) {
        switch (event.getValueReturnEvent()) {

            case EVENT_GET_DATA_FIFTEEN_DAYS_OK:
                updateFifteenDaysDatas(EventBus.getDefault().getStickyEvent(FifteenEvent.class).getMapWithFifteen());
                mAdapter.notifyDataSetChanged();
                break;
            case EVENT_GET_DATA_FIFTEEN_DAYS_ERROR:
                Toast.makeText(mActivity, "network fail fifteen", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onRealtimeEvent(RealtimeEvent event) {
        switch (event.getValueReturnEvent()) {

            case EVENT_GET_DATA_REALTIME_OK:
                updateRealtimeDatas(EventBus.getDefault().getStickyEvent(RealtimeEvent.class).getVauleWithRealTime());
                mAdapter.notifyDataSetChanged();
                break;
            case EVENT_GET_DATA_REALTIME_ERROR:
                Toast.makeText(mActivity, "network fail realtime", Toast.LENGTH_SHORT).show();
                break;

        }
    }


        @Subscribe(threadMode = ThreadMode.MAIN)
        public void onRefreshEvent(RefreshEvent event){
            switch (event.getValueReturnEvent()) {
                case EVENT_GET_DATA_REFRESH_ERROR:
                    Toast.makeText(mActivity, "network fail", Toast.LENGTH_SHORT).show();
                    mRlvPullRefresh.onRefreshComplete(false);
                    break;

                case EVENT_GET_DATA_REFRESH_OK:
                    Map<List<TempeBean>, List<SkyConBean>> mapWithFifteen = event.getMapWithFifteen();
                    RealTimeBean vauleWithRealTime = event.getVauleWithRealTime();
                    if (mapWithFifteen == null || vauleWithRealTime == null) return;
                    updateRealtimeDatas(vauleWithRealTime);
                    updateFifteenDaysDatas(mapWithFifteen);
                    Logger.t(TAG).d("REFRESH_FLAG:\n" + vauleWithRealTime + "\n" + mapWithFifteen);
                    mAdapter.notifyDataSetChanged();
                    mRlvPullRefresh.onRefreshComplete(true);
                    Toast.makeText(mActivity, "upated", Toast.LENGTH_SHORT).show();
                    break;


            }


        }


    private void updateRealtimeDatas(RealTimeBean rlBean) {
        Logger.t(TAG).d(rlBean);
        mAdapter.setRealTimeData(rlBean);
    }

    private void updateFifteenDaysDatas(Map<List<TempeBean>, List<SkyConBean>> datas) {
        List<TempeBean> tempeBeans = null;
        List<SkyConBean> skyConBeans = null;
        for (Map.Entry<List<TempeBean>, List<SkyConBean>> listListEntry : datas.entrySet()) {
            tempeBeans = listListEntry.getKey();
            skyConBeans = listListEntry.getValue();
            Logger.t(TAG).d(tempeBeans + "\n" + skyConBeans);

        }
//                Iterator<Map.Entry<List<TempeBean>, List<SkyConBean>>> iterator = datas.entrySet().iterator();
//                while (iterator.hasNext()) {
//                    mTempeBeans = iterator.next().getKey();
//                    mSkyconBeans = iterator.next().getValue();
//                    Logger.d();
//                }
//                Logger.d(iterator.next().getKey());
//                List<TempeBean> tempeBeans=new ArrayList<>();;
//                List<SkyConBean> skyConBeen = iterator.next().getValue();

        mAdapter.setFifteenData(tempeBeans, skyConBeans);
    }


}
