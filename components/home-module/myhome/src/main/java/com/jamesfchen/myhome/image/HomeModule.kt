package com.jamesfchen.myhome.image

import android.content.Context
import androidx.annotation.NonNull
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.LibraryGlideModule
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.jamesfchen.common.util.ThreadUtil
import com.jamesfchen.myhome.screen.photo.model.Photo
import okhttp3.ConnectionPool
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import java.io.InputStream
import java.util.concurrent.TimeUnit


@GlideModule(glideName = "HomeGlide")
class HomeModule : LibraryGlideModule() {
    /**
     *
     * ResourceDecoder, 用于对新的 Resources(Drawables, Bitmaps)或新的 Data 类型(InputStreams, FileDescriptors)进行解码。
     * ResourceEncoder，用于向 Glide 的磁盘缓存写 Resources(BitmapResource, DrawableResource)。
     * Encoder, 用于向 Glide 的磁盘缓存写 Data (InputStreams, FileDesciptors)。
     * ModelLoader, 用于加载自定义的 Model(Url, Uri,任意的 POJO )和 Data(InputStreams, FileDescriptors)。
     * ResourceTranscoder，用于在不同的资源类型之间做转换，例如，从 BitmapResource 转换为 DrawableResource 。
     */
    val okhttpClient = OkHttpClient.Builder()
        .dispatcher(Dispatcher(ThreadUtil.getIOPool()))//默认任务分发池，最多并发请求为64个，每个host最多5个，线程池最大为无线个，对于低端手机能不能根据cpu来控制线程核心数，优化图片加载任务分发池最大线程数为2*cpu+1
        .connectTimeout(15_000, TimeUnit.MILLISECONDS)//15s
        .readTimeout(15_000, TimeUnit.MILLISECONDS)//
        .writeTimeout(15_000, TimeUnit.MILLISECONDS)//
        .connectionPool(ConnectionPool(5, 10_000, TimeUnit.MILLISECONDS))//空闲5个，保活10s
        .addInterceptor { chain ->//应用层的拦截器
            return@addInterceptor chain.proceed(chain.request())
        }
        .build()
    override fun registerComponents(
        @NonNull context: Context,
        @NonNull glide: Glide,
        @NonNull registry: Registry
    ) {
        //替换默认的HttpGlideUrlLoader，使用Okhttp网络库并且优化任务分发池与连接池
        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(okhttpClient))
        //处理Glide.load(Photo对象)
        registry.append(Photo::class.java, InputStream::class.java, HomeGlideUrlModuleLoader.Factory())
    }
}