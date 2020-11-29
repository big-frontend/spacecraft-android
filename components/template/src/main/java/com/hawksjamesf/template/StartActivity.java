package com.hawksjamesf.template;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.hawksjamesf.annotations.TraceTime;

import androidx.annotation.Nullable;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/25/2020  Sun
 */
public class StartActivity extends Activity {
    final ServiceConnection con = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("cjf", " " + name + " " + service);
            //在跨进程，远程service将获得BinderProxy;在同一个进程，本地service将获得binder实体。
            if (name.getClassName().equals(RemoteServices.class.getCanonicalName())) {
                IMyAidlInterface iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
                Log.d("cjf", "iMyAidlInterface:"+iMyAidlInterface);

            } else if (name.getClassName().equals(LocalServices.class.getCanonicalName())) {
//                BinderEntry.asInterface(service);
                Log.d("cjf", "BinderEntry");

            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @TraceTime
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Button button = findViewById(R.id.bt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Router.select("module1_page").go(StartActivity.this,new Callback(){
//
//                });
            }
        });
        RemoteServices.startAndBindService(this, con);
        LocalServices.startAndBindService(this, con);


    }

    @TraceTime
    @Override
    protected void onStart() {
        super.onStart();
//        long start = System.currentTimeMillis();
        for (int i = 0; i < 5; ++i) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        Log.d("cjf", "耗时:" + (System.currentTimeMillis() - start));

    }

    @TraceTime
    @Override
    protected void onResume() {
        super.onResume();
        for (int i = 0; i < 5; ++i) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        startActivity(new Intent(this, YPosedActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RemoteServices.stopAndUnbindService(this, con);
        LocalServices.stopAndUnbindService(this, con);
    }
}
