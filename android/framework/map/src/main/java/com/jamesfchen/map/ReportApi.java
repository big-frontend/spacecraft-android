package com.jamesfchen.map;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.jamesfchen.common.util.Util;
import com.jamesfchen.map.model.LBS;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

class ReportApi {
    public static final String urlStr = "https://appdownload.coctopus.cn/parse/classes/AndroidGPSv2";
    public static final String applicationIdValue = "Bms5ZwgoXFRpTBN0bqB1kDSIp81eBrlzecRO4vKA";
    public static final String contentTypeValue = "application/json";
    public static final String TAG = "ReportApi";

    public static void reportLocation(String auth, LBS lbs, Callback cb) {
        if (lbs == null ||
                lbs.appLocation() == null || lbs.appLocation().isMockData
                || lbs.appCellInfo() == null || lbs.appCellInfo().isMockData) return;
        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder.append("{");
        contentBuilder.append("\"index\":" + lbs.index() + ",");
        contentBuilder.append("\"auth\":" + "\"" + auth + "\"" + ",");
        contentBuilder.append("\"address\":" + "\"" + lbs.add() + "\"" + ",");
        contentBuilder.append(lbs.appCellInfo());
        contentBuilder.append(",");
        contentBuilder.append(lbs.appLocation());
        contentBuilder.append("}");
        ReportApi.reportLocation(contentBuilder.toString(), cb);
    }
    public static void reportLocation(final String content) {
        reportLocation(content);
    }
    public static void reportLocation(final String content, final Callback cb) {
        if (content == null || content.length() <= 0) {
            return;
        }
        AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlStr);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.addRequestProperty("x-parse-application-id", applicationIdValue);
                    urlConnection.addRequestProperty("Content-Type", contentTypeValue);
                    urlConnection.setDoOutput(true);
                    urlConnection.setConnectTimeout(2000);
                    urlConnection.setReadTimeout(2000);
                    urlConnection.setUseCaches(false);
                    urlConnection.connect();
                    OutputStream outputStream = urlConnection.getOutputStream();
                    Log.d(TAG, "request: body>>>" + content);
                    FileIOUtils.readString2OS(content, outputStream);
                    InputStream inputStream = urlConnection.getInputStream();
                    String response = FileIOUtils.readIS(inputStream);
                    int responseCode = urlConnection.getResponseCode();
                    String responseMessage = urlConnection.getResponseMessage();
                    Log.d(TAG, "response: code" + responseCode + " message:" + responseMessage + " \nbody:\n" + response);
                    urlConnection.disconnect();
                    if (response.contains("createdAt") && response.contains("objectId")) {
                        cb.onResponse();
                    } else {
                        cb.onFailure();
                    }
//            InputStream inputStream = urlConnection.getInputStream();

                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        });
    }

    public static void reportLocation(File lbsFile) {
        if (!lbsFile.exists()) {
            Toast.makeText(Util.getApp(), "file no exists,report error", Toast.LENGTH_SHORT).show();
            return;
        }

        AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlStr);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.addRequestProperty("x-parse-application-id", applicationIdValue);
                    urlConnection.addRequestProperty("Content-Type", contentTypeValue);
                    urlConnection.setDoOutput(true);
                    urlConnection.setConnectTimeout(2000);
                    urlConnection.setReadTimeout(2000);
                    urlConnection.setUseCaches(false);
                    urlConnection.connect();
                    OutputStream outputStream = urlConnection.getOutputStream();
                    FileIOUtils.readFile2OS(lbsFile, outputStream);
                    outputStream.close();
//                    InputStream inputStream = urlConnection.getInputStream();
//                    String response = FileUtils.readIS(inputStream);
                    int responseCode = urlConnection.getResponseCode();
                    String responseMessage = urlConnection.getResponseMessage();
                    Log.d(TAG, "response: code" + responseCode + " message:" + responseMessage);
                    urlConnection.disconnect();

//            InputStream inputStream = urlConnection.getInputStream();

                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        });
    }

    public interface Callback {
        void onFailure();

        void onResponse();
    }

}
