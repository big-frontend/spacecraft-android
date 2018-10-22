package com.hawksjamesf.simpleweather.event;

import com.hawksjamesf.simpleweather.data.bean.RealTimeBean;
import com.hawksjamesf.simpleweather.data.bean.fifteendaysbean.SkyConBean;
import com.hawksjamesf.simpleweather.data.bean.fifteendaysbean.TempeBean;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/SimpleWeather
 *
 * @author: hawks.jamesf
 * @since: 9/20/17
 */

public  class RefreshEvent {
    @Inject
    public RefreshEvent() {
    }
    private Map<List<TempeBean>, List<SkyConBean>> fifteenDatas;
    private int valueReturnEvent;
    private RealTimeBean rtBean;


    public RefreshEvent setMapWithFifteen(List<TempeBean> tpBeans, List<SkyConBean> scBeans) {
        fifteenDatas = new Hashtable<>();
        fifteenDatas.put(tpBeans, scBeans);
        return this;
    }

    public Map<List<TempeBean>, List<SkyConBean>> getMapWithFifteen() {
        return fifteenDatas;
    }

    public int getValueReturnEvent() {
        return valueReturnEvent;
    }

    public RefreshEvent setValueReturnEvent(int valueReturnEvent) {
        this.valueReturnEvent = valueReturnEvent;
        return this;
    }

    public RefreshEvent setVauleWithRealTime(RealTimeBean bean) {
        this.rtBean = bean;
        return this;
    }

    public RealTimeBean getVauleWithRealTime() {
        return rtBean;
    }
}
