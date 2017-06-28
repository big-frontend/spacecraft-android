package com.hawksjamesf.simpleweather;

import android.app.IntentService;
import android.content.Intent;

import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Hawks93JF on 6/28/2017.
 */
public class HomeService extends IntentService {

    public HomeService() {
        super("HomeService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        new OkHttpClient().newCall(new Request.Builder().url("https://api.caiyunapp.com/v2/TAkhjf8d1nlSlspN/121.6544,25.1552/forecast.json").build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.d("network fail");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Logger.json(response.body().string());
//                try {
//                    JSONObject rootObj = new JSONObject(response.body().string());
//                    JSONObject resultObj = rootObj.getJSONObject("result");
//                    JSONObject dailyObj = resultObj.getJSONObject("daily");
//                    JSONObject temperatureObj = dailyObj.getJSONObject("temperature");
//                    JSONObject skyconObj = dailyObj.getJSONObject("skycon");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

            }
        });
    }
}
