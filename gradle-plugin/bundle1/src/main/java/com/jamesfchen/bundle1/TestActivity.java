package com.jamesfchen.bundle1;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jamesfchen.annotations.TraceTime;
import com.jamesfchen.bundle1.aidl.BinderEntry;
import com.jamesfchen.bundle1.aidl.BinderShadow;
import com.jamesfchen.bundle1.aidl.LocalService;
import com.jamesfchen.bundle1.aidl.MessengerService;
import com.jamesfchen.bundle1.aidl.RemoteService;

/**
 * Copyright ® $ 2021
 * All right reserved.
 *
 * author jamesfchen
 * email: hawksjamesf@gmail.com
 * since 六月/24/2021  星期四
 */
public class TestActivity extends Activity {
    final ServiceConnection con = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
//            Log.d("cjf", " " + name + " " + service);
            //在跨进程，远程service将获得BinderProxy;在同一个进程，本地service将获得binder实体。
            if (name.getClassName().equals(LocalService.class.getCanonicalName())) {
                IMyAidlInterface iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
                Log.d("cjf", "iMyAidlInterface:" + iMyAidlInterface);
            } else if (name.getClassName().equals(RemoteService.class.getCanonicalName())) {
//                BinderEntry.asInterface(service);
                Log.d("cjf", "BinderEntry");
                if (service instanceof BinderEntry) {//local
                    ((BinderEntry) service).basicTypes(1, 1, true, 1.0f, 1.0d, "1");
                } else {//remote
                    BinderShadow binderShadow = new BinderShadow(service);
                    try {
                        binderShadow.basicTypes(1, 1, true, 1.0f, 1.0d, "1");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                }

            } else if (name.getClassName().equals(MessengerService.class.getCanonicalName())) {
                try {
                    Messenger messenger = new Messenger(service);
                    Message msg = Message.obtain();
                    Bundle b = new Bundle();
                    b.putString("cjf", "client ");
                    msg.setData(b);
                    msg.replyTo = clientMessage;
                    messenger.send(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private Messenger clientMessage = new Messenger(new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Log.d("cjf","print "+msg.getData().getString("cjf"));
        }
    });
    @TraceTime
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions(new String[]{Manifest.permission.FOREGROUND_SERVICE},1);
        setContentView(R.layout.activity_test);
        findViewById(R.id.bt_test_bundle2_aidl).setOnClickListener(v -> {
            RemoteService.startAndBindService(TestActivity.this, new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    //如果是远程服务就会返回BinderProxy对象，如果是本地服务直接返回BinderEntry对象
                    if (service instanceof BinderEntry) {//local
                        ((BinderEntry) service).basicTypes(1, 1, true, 1.0f, 1.0d, "1");
                    } else {//remote
                        BinderShadow binderShadow = new BinderShadow(service);
                        try {
                            binderShadow.basicTypes(1, 1, true, 1.0f, 1.0d, "1");
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {

                }
            });

        });
//        RemoteService.startAndBindService(this, con);
//        LocalService.startAndBindService(this, con);
//        MessengerService.startAndBindService(this, con);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        RemoteService.stopAndUnbindService(this, con);
//        LocalService.stopAndUnbindService(this, con);
//        MessengerService.stopAndUnbindService(this, con);
    }
}
