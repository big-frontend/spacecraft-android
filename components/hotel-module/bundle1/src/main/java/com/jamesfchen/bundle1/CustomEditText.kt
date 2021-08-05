package com.jamesfchen.bundle1

import android.content.Context
import android.util.Log
import android.view.KeyEvent
import android.widget.EditText

class CustomEditText constructor( cxt:Context):EditText(cxt) {
    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
//        Log.d("cjf","dispatchKeyEvent:${event}")
        return super.dispatchKeyEvent(event)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        Log.d("cjf","onKeyDown:$keyCode ${event}")
        return super.onKeyDown(keyCode, event)
    }
}