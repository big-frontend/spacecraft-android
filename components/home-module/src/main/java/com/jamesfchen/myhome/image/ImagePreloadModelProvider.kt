package com.jamesfchen.myhome.image

import android.app.Activity
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.TextUtils
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import java.util.*

class ImagePreloadModelProvider : ListPreloader.PreloadModelProvider<String> {
    private var activity: Activity? = null
    private var fragment: Fragment? = null
    var myUrls: List<String>

    constructor(activity: Activity, myUrls: List<String>) {
        this.activity = activity
        this.myUrls = myUrls

    }

    constructor(fragment: Fragment, myUrls: List<String>) {
        this.fragment = fragment
        this.myUrls = myUrls

    }

    companion object {
        private const val imageWidthPixels = 1024
        private const val imageHeightPixels = 768
    }

    override fun getPreloadItems(position: Int): List<String> {
        val url: String = myUrls.get(position)
        return if (TextUtils.isEmpty(url)) {
            Collections.emptyList()
        } else Collections.singletonList(url)
    }

    override fun getPreloadRequestBuilder(url: String): RequestBuilder<Drawable> =
        fragment?.let {
            Glide.with(it)
                .load(url)
                .override(imageWidthPixels, imageHeightPixels)
        } ?: activity?.let {
            Glide.with(it)
                .load(url)
                .override(imageWidthPixels, imageHeightPixels)
        } ?: throw IllegalArgumentException("activit fragment都为null")

}