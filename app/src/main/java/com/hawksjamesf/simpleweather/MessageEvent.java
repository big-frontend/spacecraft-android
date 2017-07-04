package com.hawksjamesf.simpleweather;

import com.hawksjamesf.simpleweather.bean.RealTimeBean;

/**
 * Copyright ® 2017
 * Shanghai wind-mobi
 * All right reserved.
 *
 * @author:chenjinfa
 * @since:2017/7/4
 */

public class MessageEvent {
    public int getValueReturnEvent() {
        return valueReturnEvent;
    }

    private int valueReturnEvent;
    private RealTimeBean rtBean;

    public MessageEvent setValueReturnEvent(int valueReturnEvent) {
        this.valueReturnEvent = valueReturnEvent;
        return this;
    }
//        public MessageEvent setVauleWithRealTime(RealTimeBean bean){
//            this.rtBean=bean;
//            return this;
//        }
//        public RealTimeBean getVauleWithRealTime(){
//            return rtBean;
//        }
}
