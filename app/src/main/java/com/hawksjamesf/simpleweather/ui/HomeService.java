package com.hawksjamesf.simpleweather.ui;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.hawksjamesf.simpleweather.SimpleWeatherApplication;
import com.hawksjamesf.simpleweather.bean.RealTimeBean;
import com.hawksjamesf.simpleweather.bean.fifteendaysbean.SkyConBean;
import com.hawksjamesf.simpleweather.bean.fifteendaysbean.TempeBean;
import com.hawksjamesf.simpleweather.event.FifteenEvent;
import com.hawksjamesf.simpleweather.event.RealtimeEvent;
import com.hawksjamesf.simpleweather.util.GetWeatherDataUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/SimpleWeather
 *
 * @author: hawks jamesf
 * @since: 2017/7/4
 */
public class HomeService extends IntentService {
    private static final String TAG = "HomeService";

    @Inject
    RealtimeEvent mEvent;
    @Inject
    FifteenEvent fifteenEvent;
    public static final int EVENT_GET_DATA_FIFTEEN_DAYS_ERROR = 0;
    public static final int EVENT_GET_DATA_FIFTEEN_DAYS_OK = 2;
    public static final int EVENT_GET_DATA_REALTIME_OK = 3;
    public static final int EVENT_GET_DATA_REALTIME_ERROR = 4;


    public HomeService() {
        super(TAG);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SimpleWeatherApplication.getAppComponent().inject(this);
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        /*
        get fifteen data from local
         */
        Map<List<TempeBean>, List<SkyConBean>> forecast = GetWeatherDataUtils.requestDataFromLocal(TempeBean.class,SkyConBean.class,this, GetWeatherDataUtils.FORECAST);
        for (Map.Entry<List<TempeBean>, List<SkyConBean>> next : forecast.entrySet()) {
            if (next != null) {
                EventBus.getDefault().postSticky(fifteenEvent.setValueReturnEvent(EVENT_GET_DATA_FIFTEEN_DAYS_OK).setMapWithFifteen(next.getKey(), next.getValue()));

            } else {
                EventBus.getDefault().postSticky(fifteenEvent.setValueReturnEvent(EVENT_GET_DATA_FIFTEEN_DAYS_ERROR));
            }
        }
        /*
        get RealTimeBean data from local
         */
        RealTimeBean rlBean = GetWeatherDataUtils.requestDataFromLocal(RealTimeBean.class,this, GetWeatherDataUtils.REALTIMES);
        if (rlBean != null) {
            //success

            EventBus.getDefault().postSticky(mEvent.setValueReturnEvent(EVENT_GET_DATA_REALTIME_OK).setVauleWithRealTime(rlBean));
        } else {
            //error
            EventBus.getDefault().postSticky(mEvent.setValueReturnEvent(EVENT_GET_DATA_REALTIME_ERROR));
        }
    }
}

