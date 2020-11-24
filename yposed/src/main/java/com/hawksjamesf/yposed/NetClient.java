package com.hawksjamesf.yposed;

import android.util.Log;

import androidx.annotation.Keep;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Dec/28/2019  Sat
 */
@Keep
public class NetClient {
    private static NetClient INSTANCE = new NetClient();
    public static NetClient getInstance() {
        return INSTANCE;
    }
    private NetClient(){}
    @Keep
    public void sendRequest(){
        Log.d("cjf","sendRequest");
    }

}
