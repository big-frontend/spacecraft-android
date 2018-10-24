package com.hawksjamesf.simpleweather.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hawksjamesf.simpleweather.ui.mvp.RxActivity;
import com.hawksjamesf.simpleweather.ui.mvp.RxPresenter;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/24/2018  Wed
 *
 * BaseActivity接口方便后续切换mvvm结构
 */
public class BaseActivity<P extends RxPresenter> extends RxActivity<P> {
    protected BaseActivity baseActivity;
    protected P presenter;

    @Override
    public P createPresenter() {
        return presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseActivity = this;

    }
}
