package com.jamesfchen.network.slack;

import okhttp3.HttpUrl;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Jun/29/2019  Sat
 * See https://api.slack.com/methods/rtm.start.
 * {
 * ok: true,
 * url: "wss://cerberus-xxxx.lb.slack-msgs.com/websocket/arAh4NacaqlKk7sd-scS9d-sWr13BdQpcqn3x5ZOQcxiO2UGtMNAxSHNEpo30TNwZddA_6roWeEjLlDqwmvASyM3falBmiBDVDU60Lvgioo=",
 * team: {
 * id: "THJE5K9R9",
 * name: "spacecraft",
 * domain: "spacecraft-co"
 * },
 * self: {
 * id: "UL23GAY15",
 * name: "sa-bot"
 * }
 * }
 */
public class RtmConnectResponse {

    public HttpUrl url;
    public Object self;
    public Object team;
//    List<Object> users;
//    List<Object> channels;
//    List<Object> groups;
//    List<Object> mpims;
//    List<Object> ims;
//    List<Object> bots;


    @Override
    public String toString() {
        return "RtmConnectResponse{" +
                "url=" + url +
                ", self=" + self +
                ", team=" + team +
                '}';
    }
}
