package com.jamesfchen.myhome.image

import android.content.Context
import androidx.annotation.NonNull
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.LibraryGlideModule
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.jamesfchen.myhome.screen.photo.model.Photo
import java.io.InputStream


@GlideModule(glideName = "HomeGlide")
class HomeModule : LibraryGlideModule() {
    /**
     *
     * ModelLoader, 用于加载自定义的 Model(Url, Uri,任意的 POJO )和 Data(InputStreams, FileDescriptors)。
     * ResourceDecoder, 用于对新的 Resources(Drawables, Bitmaps)或新的 Data 类型(InputStreams, FileDescriptors)进行解码。
     * Encoder, 用于向 Glide 的磁盘缓存写 Data (InputStreams, FileDesciptors)。
     * ResourceTranscoder，用于在不同的资源类型之间做转换，例如，从 BitmapResource 转换为 DrawableResource 。
     * ResourceEncoder，用于向 Glide 的磁盘缓存写 Resources(BitmapResource, DrawableResource)。
     */
    override fun registerComponents(
        @NonNull context: Context,
        @NonNull glide: Glide,
        @NonNull registry: Registry
    ) {
//        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory())
        registry.append(Photo::class.java, InputStream::class.java, HomeLoader.Factory());
    }
}