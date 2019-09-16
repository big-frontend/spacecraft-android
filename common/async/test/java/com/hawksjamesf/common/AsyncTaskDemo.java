package com.hawksjamesf.common;

import android.os.AsyncTask;

import junit.framework.TestCase;

import java.util.concurrent.ExecutionException;

import okhttp3.HttpUrl;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Sep/11/2019  Wed
 */
public class AsyncTaskDemo extends TestCase {

    public void testAsyncTask() {
        //一个SimpleAsyncTask对象单线程执行任务，多个SimpleAsyncTask对象才能多线程执行任务
        SimpleAsyncTask simpleAsyncTask = new SimpleAsyncTask();
        simpleAsyncTask.execute(HttpUrl.parse("https://api.github.com/user"));
        simpleAsyncTask.executeOnExecutor(SimpleAsyncTask.THREAD_POOL_EXECUTOR, HttpUrl.parse("https://api.github.com/user"));

        // 1. 执行20个task,这20个会按照顺序执行,没有多线程并发
        for (int i = 0; i < 10; i++) {
            final int finalI = i;
            SimpleAsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("task:" + finalI);

                }
            });

            SimpleAsyncTask serialTask = new SimpleAsyncTask();
            serialTask.execute(HttpUrl.parse("https://api.github.com/user"), HttpUrl.parse("https://api.github.com/user"));
            try {
                System.out.println("serialTask/ " + i + " :" + serialTask.get());
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        //2.执行10个真正的并发线程,并发的核心线程数为2-4个cpu count，最大可以支持并发的线程数为2倍的cpu count+1，线程的存活时间为30s
        for (int j = 0; j < 10; j++) {
            AsyncTask<HttpUrl, Integer, String> concurrentTask = new AsyncTask<HttpUrl, Integer, String>() {

                @Override
                protected String doInBackground(HttpUrl... httpUrls) {
                    //通过多个url得到一个结果
                    StringBuilder sb = new StringBuilder();
                    for (HttpUrl httpUrl : httpUrls) {
                        sb.append(httpUrl.pathSegments());
                    }
                    return sb.toString();
                }
            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, HttpUrl.parse("https://api.github.com/user"));

            try {
                System.out.println("concurrentTask/ " + j + " :" + concurrentTask.get());
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    static class SimpleAsyncTask extends AsyncTask<HttpUrl, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String aLong) {
            super.onPostExecute(aLong);
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
        protected String doInBackground(HttpUrl... urls) {
            //通过多个url得到一个结果
            StringBuffer sb = new StringBuffer();
            for (HttpUrl httpUrl : urls) {
                sb.append(httpUrl.pathSegments());
            }
            return sb.toString();
        }
    }
}
