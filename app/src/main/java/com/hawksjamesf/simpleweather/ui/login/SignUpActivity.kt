package com.hawksjamesf.simpleweather.ui.login

import android.os.Bundle
import com.hawksjamesf.simpleweather.R
import com.hawksjamesf.simpleweather.ui.BaseActivity
import com.hawksjamesf.simpleweather.ui.mvp.AutoDisposable

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/23/2018  Tue
 */
class SignUpActivity : BaseActivity() {
    override fun initComponent(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_signup)
    }

    override fun handleCallback(autoDisposable: AutoDisposable) {
    }

    override fun loadData(autoDisposable: AutoDisposable) {
    }

}