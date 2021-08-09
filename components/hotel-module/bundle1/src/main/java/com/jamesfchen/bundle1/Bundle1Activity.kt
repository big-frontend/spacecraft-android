package com.jamesfchen.bundle1

import android.R.attr
import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import android.view.ViewGroup
import android.widget.TextView
import android.app.Instrumentation
import kotlinx.coroutines.delay
import java.lang.Thread.sleep
import kotlin.concurrent.thread
import android.view.InputDevice

import android.view.KeyCharacterMap

import android.R.attr.keycode
import android.app.AppComponentFactory
import android.os.AsyncTask
import android.os.SystemClock
import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.loader.content.AsyncTaskLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.concurrent.ThreadPoolExecutor


/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Jun/13/2021  Sun
 */
class Bundle1Activity : ComponentActivity() {
    val data: Flow<String> = flow {
        emit("cjf")
//        emitAll()
    }
    val data2 = callbackFlow<String> {  }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//
//            }

            data.map {

            }
                .onEach { }
                .flowOn(Dispatchers.IO)
                .onEach {

                }.collect { }
        }


        val tv = CustomEditText(this)
        tv.setText("hotel bundle1")
        tv.setTextColor(Color.BLACK)
        tv.gravity = Gravity.CENTER
        tv.imeOptions = EditorInfo.IME_ACTION_SEARCH
        tv.setSingleLine()
        val lp = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        lp.gravity = Gravity.CENTER
        tv.setOnKeyListener { v: View?, keyCode: Int, event: KeyEvent? ->
            Log.d("cjf", "onKeyListener:$keyCode")
            false
        }
        tv.setFocusableInTouchMode(true);
        tv.setFocusable(true);
        tv.requestFocus();
        tv.setOnEditorActionListener { v: TextView?, actionId: Int, event: KeyEvent? ->
            Log.d("cjf", "onEditorAction:$actionId $event")
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                return@setOnEditorActionListener false
            }
            false
        }
        setContentView(tv, lp)
        thread {
            sleep(5000L)
            val inst = Instrumentation()
//            inst.sendKeyDownUpSync(KeyEvent.KEYCODE_ENTER)
            var eventTime = SystemClock.uptimeMillis()
            var keyEvent = KeyEvent(
                eventTime,
                eventTime,
                KeyEvent.ACTION_DOWN,
                KeyEvent.KEYCODE_ENTER,
                0,
                0,
                KeyCharacterMap.VIRTUAL_KEYBOARD,
                0,
                22,
                InputDevice.SOURCE_KEYBOARD
            )
            inst.sendKeySync(keyEvent)
            eventTime = SystemClock.uptimeMillis() + 5
            keyEvent = KeyEvent(
                eventTime,
                eventTime,
                KeyEvent.ACTION_UP,
                KeyEvent.KEYCODE_ENTER,
                0,
                0,
                KeyCharacterMap.VIRTUAL_KEYBOARD,
                0,
                22,
                InputDevice.SOURCE_KEYBOARD
            )
            inst.sendKeySync(keyEvent)
        }
        tv.onEditorAction(EditorInfo.IME_ACTION_SEARCH)
    }
}