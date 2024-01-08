package com.cmic.sso.tokenValidate;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;


/**
 * @author haoxin
 */
public class HttpUtils {
    private static final int CONNECT_TIMEOUT_TIME = 15000;
    private static final int SO_TIMEOUT_TIME = 19000;
    private static final int FORCE_TIMEOUT_TIME = 20000;
    private static final String TAG = "HttpUtils";
    private Timer timer;
    private int responseCode = -1;
    private boolean isCallback = false;


    public void requestHttp(final String url, final String params, final Response response) {
        new Thread() {
            @Override
            public void run() {



                    requestHttp2(url, params, response);

            }
        }.start();
    }

    /**
     * http request
     */
    public void requestHttp2(final String url, final String params, final Response response) {
        HttpURLConnection httpConn;
        String result = "";
        timer = new Timer();
        try {
            Log.i(TAG, "http reqeust, url: " + url);

            final URL serverUrl = new URL(url);


                httpConn = (HttpURLConnection) serverUrl.openConnection();

            httpConn.setConnectTimeout(CONNECT_TIMEOUT_TIME);
            httpConn.setReadTimeout(SO_TIMEOUT_TIME);

            httpConn.setRequestMethod("POST");

            //是否允许输入输出
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            //设置请求头里面的数据，以下设置用于解决http请求code415的问题
//                    httpConn1.setRequestProperty("Content-Type",
//                            "application/json");
            //链接地址

            httpConn.setUseCaches(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            httpConn.connect();
            startOverdueTimer(response);
            //POST请求
            DataOutputStream out = new DataOutputStream(httpConn.getOutputStream());

            byte[] datas = params.getBytes("UTF-8");
            out.write(datas, 0, datas.length);

            out.flush();
            out.close();

            //读取响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    httpConn.getInputStream()));
            String lines;
            StringBuffer sb = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            result = sb.toString();
            reader.close();
            // 断开连接
            httpConn.disconnect();
            responseCode = httpConn.getResponseCode();

        } catch (Exception e) {
            e.printStackTrace();
            parseHttpResult(null, responseCode, response);
            return;
        }
        parseHttpResult(result, responseCode, response);
    }


    /*
     * 解析网络请求结果
     */
    private void parseHttpResult(String result, int responseCode, Response response) {
        try {
            timer.cancel();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (!isCallback) {
            isCallback = true;
            if (responseCode != HttpURLConnection.HTTP_OK) {
                Log.e(TAG, "http response code is not 200 ---" + responseCode);
                if (responseCode == 0) {
                    response.onError(responseCode + "", "请求出错");
                } else if (responseCode == 12345) {
                    response.onError(responseCode + "", result);
                } else {
                    response.onError(200028 + "", TextUtils.isEmpty(result) ? "网络异常" : result);
                }
                return;
            } else {
                response.onSuccess(result);
                return;
            }
        } else {
            Log.e(TAG, "过期的请求" + "result==" + result);
        }
    }



    // 网络请求超时器
    private void startOverdueTimer(final Response response) {
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                parseHttpResult("网络请求超时", 12345, response);
            }

        }, FORCE_TIMEOUT_TIME);
    }


    public interface Response {
        /**
         * @param result
         */
        void onSuccess(String result);

        void onError(String errorCode, String msg);
    }

}
