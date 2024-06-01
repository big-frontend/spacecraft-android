@file:JvmName("KeyboardUtil")
package com.electrolytej.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager


fun Activity.hideSoftInput() {
    val token = if (currentFocus?.windowToken !=null) currentFocus?.windowToken else View(this).windowToken
        val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(token, 0)

}