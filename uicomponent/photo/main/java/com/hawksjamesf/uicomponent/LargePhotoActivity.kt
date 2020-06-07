package com.hawksjamesf.uicomponent

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapRegionDecoder
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
        photoview.setRaw(R.raw.wechatimg211)
        val openRawResource = resources.openRawResource(R.raw.wechatimg211)
        val bitmapRegionDecoder=BitmapRegionDecoder.newInstance(openRawResource,false)

        val options=BitmapFactory.Options()
        options.inPreferredConfig =Bitmap.Config.RGB_565
//        bitmapRegionDecoder.decodeRegion(options)
    }
}