package com.jamesfchen.myhome.image

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.*
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader
import com.jamesfchen.myhome.screen.photo.model.Photo
import java.io.InputStream

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Mar/28/2020  Sat
 */
class HomeLoader private constructor(
    urlLoader: ModelLoader<GlideUrl, InputStream>,
    modelCache: ModelCache<Photo, GlideUrl>
) : BaseGlideUrlLoader<Photo>(urlLoader, modelCache) {
    class Factory : ModelLoaderFactory<Photo, InputStream> {
        private val modelCache: ModelCache<Photo, GlideUrl> = ModelCache<Photo, GlideUrl>(500)
        override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<Photo, InputStream> {
            return HomeLoader(
                multiFactory.build(GlideUrl::class.java, InputStream::class.java), modelCache
            )
        }
        override fun teardown() {}
    }

    override fun handles(model: Photo): Boolean {
        return true
    }

    override fun getUrl(model: Photo?, width: Int, height: Int, options: Options?): String {
        TODO("Not yet implemented")
    }
    override fun getAlternateUrls(
        model: Photo?,
        width: Int,
        height: Int,
        options: Options?
    ): MutableList<String> {
        return super.getAlternateUrls(model, width, height, options)
    }
}