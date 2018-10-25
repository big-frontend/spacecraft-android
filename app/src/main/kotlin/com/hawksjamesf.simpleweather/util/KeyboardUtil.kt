package com.hawksjamesf.simpleweather.util

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/24/2018  Wed
 */
fun Activity.hideSoftInput() {
    currentFocus?.windowToken?.let {
        val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(it, 0)
    }
}