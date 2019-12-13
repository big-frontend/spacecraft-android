package com.hawksjamesf.spacecraft.ui;

import android.app.ActivityOptions;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.perf.FirebasePerformance;
import com.google.firebase.perf.metrics.AddTrace;
import com.google.firebase.perf.metrics.HttpMetric;
import com.google.firebase.perf.metrics.Trace;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.hawksjamesf.common.util.ActivityUtil;
import com.hawksjamesf.spacecraft.App;
import com.hawksjamesf.spacecraft.R;
import com.hawksjamesf.uicomponent.BottomUpActivity;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/Spacecraft
 *
 * @author: hawks jamesf
 * @since: 2017/7/4
 */
public class SplashActivity extends BaseActivity {
    public static final String TAG = "SplashActivity";
    private FirebaseAnalytics mFirebaseAnalytics;
    private Trace myTrace;
    private HttpMetric mHttpMetric;
    private FirebaseRemoteConfig mFirebaseRemoteConfig= App.getFirebaseRemoteConfig();

    @AddTrace(name = "_splashActivity_initComponentTrace", enabled = true /* optional */)
    @Override
    protected void initComponent(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash);

    }


    @Override
    protected void handleCallback(@NotNull Function1<? super Disposable, Unit> autoDisposable) {

    }

    @Override
    protected void loadData(@NotNull Function1<? super Disposable, Unit> autoDisposable) {
        myTrace = FirebasePerformance.getInstance().newTrace("loadData");
        myTrace.start();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mHttpMetric = FirebasePerformance.getInstance().newHttpMetric("https://www.google.com", FirebasePerformance.HttpMethod.GET);
        mHttpMetric.start();
        onDestroyDisposable.add(Observable.timer(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        //todo:需要通过refresh token来判断进入那个界面
                        myTrace.incrementMetric("started activity", 1);
                        mHttpMetric.setHttpResponseCode(200);
                        mHttpMetric.setResponseContentType("application/x-protobuf");

                        Bundle bundle = new Bundle();
                        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "id");
                        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "name");
                        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
                        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                        ActivityUtil.startActivity(BottomUpActivity.class, ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this).toBundle());
//                        TransitionForActivityActivity.startActivity(SplashActivity.this);
                        finish();
                    }
                }));
        fetchWelcome();
        myTrace.stop();
        mHttpMetric.stop();

//        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event);
    }

    // Remote Config keys
    private static final String LOADING_PHRASE_CONFIG_KEY = "loading_phrase";
    private static final String WELCOME_MESSAGE_KEY = "welcome_message";

    private void fetchWelcome() {
        Log.i(TAG, "LOADING_PHRASE_CONFIG_KEY:" + mFirebaseRemoteConfig.getString(LOADING_PHRASE_CONFIG_KEY));
        mFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if (task.isSuccessful()) {
                            boolean updated = task.getResult();
                            Log.i(TAG, "Config params updated: " + updated);
                            Toast.makeText(SplashActivity.this, "Fetch and activate succeeded",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(SplashActivity.this, "Fetch failed",
                                    Toast.LENGTH_SHORT).show();
                        }

                        String welcomeMessage = mFirebaseRemoteConfig.getString(WELCOME_MESSAGE_KEY);
                        Log.i(TAG, "WELCOME_MESSAGE_KEY: " + welcomeMessage);

                    }
                });
    }

}
