package com.hawksjamesf.simpleweather;

import com.hawksjamesf.simpleweather.bean.RealTimeBean;
import com.hawksjamesf.simpleweather.bean.fifteendaysbean.SkyConBean;
import com.hawksjamesf.simpleweather.bean.fifteendaysbean.TempeBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Copyright ® 2017
 * Shanghai wind-mobi
 * All right reserved.
 *
 * @author:chenjinfa
 * @since:2017/7/4
 */

public class MessageEvent {

    @Inject
    public MessageEvent() {
    }

    private Map<List<TempeBean>, List<SkyConBean>> datas;
    private int valueReturnEvent;
    private RealTimeBean rtBean;


    public MessageEvent setMapWithFifteen(List<TempeBean> tpBeans,List<SkyConBean> scBeans) {
        datas = new HashMap<>();
        datas.put(tpBeans, scBeans);
        return this;
    }

    public Map<List<TempeBean>, List<SkyConBean>> getMapWithFifteen() {
        return datas;
    }

    public int getValueReturnEvent() {
        return valueReturnEvent;
    }

    public MessageEvent setValueReturnEvent(int valueReturnEvent) {
        this.valueReturnEvent = valueReturnEvent;
        return this;
    }

    public MessageEvent setVauleWithRealTime(RealTimeBean bean) {
        this.rtBean = bean;
        return this;
    }

    public RealTimeBean getVauleWithRealTime() {
        return rtBean;
    }

}
