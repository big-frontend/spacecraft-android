package com.jamesfchen.appupdate

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import java.io.File
import java.lang.Exception


class UpdateActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bt = Button(this)
        bt.text = "update app"
        bt.isAllCaps = false
        setContentView(
            bt,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}