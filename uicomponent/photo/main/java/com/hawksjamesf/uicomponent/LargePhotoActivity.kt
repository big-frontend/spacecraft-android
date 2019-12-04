package com.hawksjamesf.uicomponent

import android.os.Bundle
import android.os.PersistableBundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Dec/03/2019  Tue
 */
class LargePhotoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        val photoview = PhotoView(this)
        val lp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        setContentView(photoview, lp)
        photoview.setRaw(R.raw.WechatIMG211)
    }
}