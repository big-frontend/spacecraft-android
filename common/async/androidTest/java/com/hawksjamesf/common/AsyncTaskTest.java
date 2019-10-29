package com.hawksjamesf.common;

import android.os.AsyncTask;
import android.util.Log;

import androidx.test.runner.AndroidJUnit4;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class AsyncTaskTest {

    @Test
    public void testAsyncTask() {
        // Context of the app under test.
//        Context appContext = InstrumentationRegistry.getTargetContext();

//        assertEquals("com.hawksjamesf.common.test", appContext.getPackageName());
        //一个SimpleAsyncTask对象单线程执行任务，多个SimpleAsyncTask对象才能多线程执行任务
        //task相关于一个请求，如果请求之间不存在依赖则可以创建多个task，如果请求之前有依赖关系，则将有依赖关系的请求放入一个task处理。
        // 1. 顺序任务：执行20个task,这20个会按照顺序执行,没有多线程并发
        for (int i = 0; i < 10; i++) {
            final int finalI = i;
            SimpleAsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    Log.i(SimpleAsyncTask.TAG, "task:" + finalI);

                }
            });

            SimpleAsyncTask serialTask = new SimpleAsyncTask();
            serialTask.i = i;
            serialTask.suffix = "serialTask";
            serialTask.execute(HttpUrl.parse("https://api.github.com/user"), HttpUrl.parse("https://api.github.com/user"));
        }

        //2.并发任务：执行10个真正的并发线程,并发的核心线程数为2-4个cpu count，最大可以支持并发的线程数为2倍的cpu count+1，线程的存活时间为30s
        for (int j = 0; j < 10; j++) {
            SimpleAsyncTask concurrentTask = new SimpleAsyncTask();
            concurrentTask.i = j;
            concurrentTask.suffix = "concurrentTask";
            concurrentTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, HttpUrl.parse("https://api.github.com/user"));
        }


        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public OkHttpClient client = new OkHttpClient.Builder().build();

    class SimpleAsyncTask extends AsyncTask<HttpUrl, Integer, String> {
        public static final String TAG = "SimpleAsyncTaskTest";
        public String suffix = "";
        public volatile int i;

        SimpleAsyncTask() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String aLong) {
            try {
                Log.i(TAG, suffix + "_onPostExecute/ " + i + " :" + get());
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(String aLong) {
            super.onCancelled(aLong);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected String doInBackground(final HttpUrl... urls) {
            StringBuffer sb = new StringBuffer();
            //1.多个url相互依赖，按照顺序发送，组合成最后一个结果
            //请求的关系：如果多个request存在相互依赖，则必须按照顺序发送；如果request不存在相互依赖
            Request request = new Request.Builder()
                    .url(urls[0])
                    .build();
            Call call1 = client.newCall(request);
            try (Response response = call1.execute()) {
                String string = response.body().string();
                String message = new JSONObject(string).getString("message");
                sb.append(message);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                call1.cancel();
            }
            Call call2 = client.newCall(request.newBuilder().url(urls[1]).build());
            try (Response response = call2.execute()) {
                String string = response.body().string();
                String message = new JSONObject(string).getString("message");
                sb.append(message+"-->2");

            } catch (IOException | JSONException e) {
                e.printStackTrace();
                call2.cancel();
            }
            return sb.toString();

//            StringBuffer sb = new StringBuffer();
//            for (HttpUrl httpUrl : urls) {
//
//                sb.append(httpUrl.pathSegments());
//            }
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return sb.toString();
        }
    }

    @Before
    public void setup() {

    }

    @After
    public void tearDown() {

    }
}
