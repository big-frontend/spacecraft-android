package com.hawksjamesf.yposedplugin;

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
public class NetClient_sendRequest {
    public static final String className="com.hawksjamesf.yposed.NetClient";
    public static final String methodName="sendRequest";
    public static final String methodSignature="()V";

    public static void hook(Object instance){
        Log.d("cjf","hook sendRequest");

    }
    @Keep
    public void sendRequest(){
        Log.d("hawks","NetClient_sendRequest");
    }

}
