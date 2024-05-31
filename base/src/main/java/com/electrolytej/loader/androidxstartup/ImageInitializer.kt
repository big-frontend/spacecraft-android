package com.electrolytej.loader.androidxstartup

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.startup.Initializer
import com.facebook.cache.disk.DiskCacheConfig
import com.facebook.common.memory.MemoryTrimType
import com.facebook.common.memory.NoOpMemoryTrimmableRegistry
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.backends.okhttp3.OkHttpNetworkFetcher
import com.facebook.imagepipeline.cache.MemoryCacheParams
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.google.firebase.perf.metrics.AddTrace
import com.electrolytej.util.ThreadUtil
import com.electrolytej.loader.SApp
import okhttp3.ConnectionPool
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class ImageInitializer : Initializer<Unit> {
    @AddTrace(name = "ImageInitializer#create", enabled = true)
    override fun create(context: Context) {
        Log.d("cjf", "ImageInitializer#create")
        val okhttpClient = OkHttpClient.Builder()
            .dispatcher(Dispatcher(ThreadUtil.getIOPool()))//默认任务分发池，最多并发请求为64个，每个host最多5个，线程池最大为无线个，对于低端手机能不能根据cpu来控制线程核心数，优化图片加载任务分发池最大线程数为2*cpu+1
            .connectTimeout(15_000, TimeUnit.MILLISECONDS)//15s
            .readTimeout(15_000, TimeUnit.MILLISECONDS)//
            .writeTimeout(15_000, TimeUnit.MILLISECONDS)//
            .connectionPool(
                ConnectionPool(
                    5, 10_000, TimeUnit.MILLISECONDS
                )
            )//默认最多空闲5个，每个连接最多保活5分钟，将保活时间修改为10s
            .addInterceptor { chain ->//应用层的拦截器
                return@addInterceptor chain.proceed(chain.request())
            }.build()
        val diskCacheSize = 80 * 1024 * 1024L
        //大致占用最大堆15%
        val memoryCacheSize = Runtime.getRuntime().maxMemory().toInt() / 7
        val memoryCacheParams =
            MemoryCacheParams(memoryCacheSize, 125, memoryCacheSize, Int.MAX_VALUE, Int.MAX_VALUE)

        val memTrimRegistry = NoOpMemoryTrimmableRegistry.getInstance()
        memTrimRegistry.registerMemoryTrimmable { type ->
            val suggestedTrimRatio = type.suggestedTrimRatio
            if (MemoryTrimType.OnCloseToDalvikHeapLimit.suggestedTrimRatio == suggestedTrimRatio || MemoryTrimType.OnSystemLowMemoryWhileAppInBackground.suggestedTrimRatio == suggestedTrimRatio || MemoryTrimType.OnSystemLowMemoryWhileAppInForeground.suggestedTrimRatio == suggestedTrimRatio) {
                //回收内存缓存
                Fresco.getImagePipeline().clearMemoryCaches()
            }

        }
        val diskCacheConfig = DiskCacheConfig.newBuilder(SApp.getInstance())
            .setBaseDirectoryPath(SApp.getInstance().cacheDir).setBaseDirectoryName("fresco_cache")
            .setMaxCacheSize(diskCacheSize).build()
        val config = ImagePipelineConfig.newBuilder(SApp.getInstance()).setDownsampleEnabled(true)
            .setNetworkFetcher(OkHttpNetworkFetcher(okhttpClient))
            .setBitmapsConfig(Bitmap.Config.RGB_565).experiment().setWebpSupportEnabled(true)
            .experiment().setShouldDownscaleFrameToDrawableDimensions(true)//fix gif oom
            .setBitmapMemoryCacheParamsSupplier {
                return@setBitmapMemoryCacheParamsSupplier memoryCacheParams
            }.setMainDiskCacheConfig(diskCacheConfig).setMemoryTrimmableRegistry(memTrimRegistry)
            .build()
//        Fresco.initialize(SApp.getInstance(),config)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}