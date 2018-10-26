package com.hawksjamesf.simpleweather.ui;

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.hawksjamesf.simpleweather.R;
import com.hawksjamesf.simpleweather.ui.login.SignInActivity;
import com.hawksjamesf.simpleweather.util.ActivityUtil;

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
 * Code Link : https://github.com/HawksJamesf/SimpleWeather
 *
 * @author: hawks jamesf
 * @since: 2017/7/4
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void initComponent(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash);

    }


    @Override
    protected void handleCallback(@NotNull Function1<? super Disposable, Unit> autoDisposable) {

    }

    @Override
    protected void loadData(@NotNull Function1<? super Disposable, Unit> autoDisposable) {
        onDestroyDisposable.add(Observable.timer(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        //todo:需要通过refresh token来判断进入那个界面
                        ActivityUtil.openActivity(SplashActivity.this, SignInActivity.class, true);
                    }
                }));
    }

}
