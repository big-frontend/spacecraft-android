package com.electrolytej.main.image

import android.content.Context
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.LibraryGlideModule
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.ModelCache
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader
import com.electrolytej.main.image.model.Photo
import java.io.InputStream


@GlideModule(glideName = "MainGlideModule")
class MainGlideModule : LibraryGlideModule() {

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry.apply {
            //处理Glide.load(Photo对象)
            append(
                Photo::class.java, InputStream::class.java, MainGlideUrlModuleLoader.Factory()
            )
        }
    }

    /**
     * Glide.load(photo)
     */
    class MainGlideUrlModuleLoader private constructor(
        urlLoader: ModelLoader<GlideUrl, InputStream>,
        modelCache: ModelCache<Photo, GlideUrl>
    ) : BaseGlideUrlLoader<Photo>(urlLoader, modelCache) {
        override fun handles(model: Photo): Boolean {
            return true
        }

        override fun getUrl(model: Photo?, width: Int, height: Int, options: Options?): String {
            //利用远程切图优化网络下载图片的性能
            return if ("https" == model?.uri?.scheme || "http" == model?.uri?.scheme) {
                "${model.uri}?w=${width}&h=${height}&q=${model.quality}"
            } else {
                model?.uri.toString()
            }
        }

        override fun getAlternateUrls(
            model: Photo?,
            width: Int,
            height: Int,
            options: Options?
        ): MutableList<String> {
            return super.getAlternateUrls(model, width, height, options)
        }

        class Factory : ModelLoaderFactory<Photo, InputStream> {
            private val modelCache: ModelCache<Photo, GlideUrl> = ModelCache<Photo, GlideUrl>(500)
            override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<Photo, InputStream> {
                return MainGlideUrlModuleLoader(
                    multiFactory.build(
                        GlideUrl::class.java,
                        InputStream::class.java
                    ), modelCache
                )
            }

            override fun teardown() {}
        }
    }
}