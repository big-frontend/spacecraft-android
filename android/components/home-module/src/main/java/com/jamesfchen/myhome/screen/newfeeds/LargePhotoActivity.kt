package com.jamesfchen.myhome.screen.newfeeds

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.jamesfchen.myhome.R
import com.jamesfchen.myhome.widget.PhotoView

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Dec/03/2019  Tue
 */
class LargePhotoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val photoview = PhotoView(this)
        val lp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        photoview.setRawRes(R.raw.wechatimg212)
        setContentView(photoview, lp)
    }
}