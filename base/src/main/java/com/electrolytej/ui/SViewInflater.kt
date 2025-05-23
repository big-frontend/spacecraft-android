package com.electrolytej.ui

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.Keep
import androidx.appcompat.app.AppCompatViewInflater
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.button.MaterialButton

@Keep
class SViewInflater : AppCompatViewInflater() {
    override fun createButton(context: Context, attrs: AttributeSet?): AppCompatButton {
        return MaterialButton(context, attrs)
    }
}