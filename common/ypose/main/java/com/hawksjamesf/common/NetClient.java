package com.hawksjamesf.common;

import android.util.Log;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Dec/28/2019  Sat
 */
public class NetClient {
    private static NetClient INSTANCE = new NetClient();
    public static NetClient getInstance() {
        return INSTANCE;
    }
    private NetClient(){}
    public void sendRequest(){
        Log.d("hawks","sendRequest");
    }

}
