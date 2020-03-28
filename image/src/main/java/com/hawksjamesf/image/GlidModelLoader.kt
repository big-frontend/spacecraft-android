package com.hawksjamesf.image

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Mar/28/2020  Sat
 */
//class GlidModelLoader private constructor(
//        urlLoader: ModelLoader<GlideUrl, InputStream>, modelCache: ModelCache<Photo, GlideUrl>) : BaseGlideUrlLoader<Photo?>(urlLoader, modelCache) {
//    /** The default factory for [com.bumptech.glide.samples.flickr.FlickrModelLoader]s.  */
//    class Factory : ModelLoaderFactory<Photo, InputStream> {
//        private val modelCache: ModelCache<Photo, GlideUrl> = ModelCache<Photo, GlideUrl>(500)
//        override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<Photo, InputStream> {
//            return FlickrModelLoader(
//                    multiFactory.build(GlideUrl::class.java, InputStream::class.java), modelCache)
//        }
//
//        override fun teardown() {}
//    }
//
//    override fun handles(model: Photo): Boolean {
//        return true
//    }
//
//    override fun getUrl(model: Photo?, width: Int, height: Int, options: Options?): String {
//        return Api.getPhotoURL(model, width, height)
//    }
//
//    override fun getAlternateUrls(photo: Photo?, width: Int, height: Int, options: Options?): List<String> {
//        return Api.getAlternateUrls(photo, width, height)
//    }
//}