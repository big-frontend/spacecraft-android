package com.jamesfchen.myhome.image

import android.content.Context
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.LibraryGlideModule
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.jamesfchen.myhome.image.model.Photo
import java.io.InputStream


@GlideModule(glideName = "HomeGlide")
class HomeGlideModule : LibraryGlideModule() {

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry.apply {
            //处理Glide.load(Photo对象)
            append(
                Photo::class.java, InputStream::class.java, HomeGlideUrlModuleLoader.Factory()
            )
        }
    }
}