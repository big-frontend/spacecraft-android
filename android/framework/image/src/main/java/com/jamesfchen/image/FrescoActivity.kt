package com.jamesfchen.image

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Dec/10/2019  Tue
 */
class FrescoActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.MATRIX
        imageView.setBackgroundColor(Color.BLUE)
        val lp = ViewGroup.LayoutParams(40, 40)
        setContentView(imageView, lp)
    }

}