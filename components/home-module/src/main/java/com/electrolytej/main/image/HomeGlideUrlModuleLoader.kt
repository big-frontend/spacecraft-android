package com.electrolytej.main.image

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.*
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader
import com.electrolytej.main.image.model.Photo
import java.io.InputStream

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Mar/28/2020  Sat
 *
 */
class HomeGlideUrlModuleLoader private constructor(
    urlLoader: ModelLoader<GlideUrl, InputStream>,
    modelCache: ModelCache<com.electrolytej.main.image.model.Photo, GlideUrl>
) : BaseGlideUrlLoader<com.electrolytej.main.image.model.Photo>(urlLoader, modelCache) {
    override fun handles(model: com.electrolytej.main.image.model.Photo): Boolean {
        return true
    }

    override fun getUrl(model: com.electrolytej.main.image.model.Photo?, width: Int, height: Int, options: Options?): String {
        //利用远程切图优化网络下载图片的性能
        return if ("https" == model?.uri?.scheme || "http" == model?.uri?.scheme) {
            "${model.uri}?w=${width}&h=${height}&q=${model.quality}"
        } else {
            model?.uri.toString()
        }
    }
    override fun getAlternateUrls(
        model: com.electrolytej.main.image.model.Photo?,
        width: Int,
        height: Int,
        options: Options?
    ): MutableList<String> {
        return super.getAlternateUrls(model, width, height, options)
    }
    class Factory : ModelLoaderFactory<com.electrolytej.main.image.model.Photo, InputStream> {
        private val modelCache: ModelCache<com.electrolytej.main.image.model.Photo, GlideUrl> = ModelCache<com.electrolytej.main.image.model.Photo, GlideUrl>(500)
        override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<com.electrolytej.main.image.model.Photo, InputStream> {
            return HomeGlideUrlModuleLoader(multiFactory.build(GlideUrl::class.java, InputStream::class.java), modelCache)
        }
        override fun teardown() {}
    }
}