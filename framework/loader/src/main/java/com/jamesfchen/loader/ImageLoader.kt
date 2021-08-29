package com.jamesfchen.loader

import android.graphics.Bitmap
import com.facebook.cache.disk.DiskCacheConfig
import com.facebook.common.memory.MemoryTrimType
import com.facebook.common.memory.NoOpMemoryTrimmableRegistry
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.backends.okhttp3.OkHttpNetworkFetcher
import com.facebook.imagepipeline.cache.MemoryCacheParams
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.jamesfchen.common.util.ThreadUtil
import okhttp3.*
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Aug/29/2021  Sun
 */
class ImageLoader {
    companion object {
        val lock = Any()

        @Volatile
        private var sINSTANCE: ImageLoader? = null
        fun getInstance(): ImageLoader {
            if (sINSTANCE == null) {
                synchronized(lock) {
                    if (sINSTANCE == null) {
                        sINSTANCE = ImageLoader()
                    }
                }
            }
            return sINSTANCE!!
        }
        fun init(){
            getInstance()
        }

    }

    init {
         ThreadPoolExecutor.CallerRunsPolicy()
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
        val diskCacheSize = 80 * 1024 * 1024L
        //大致占用最大堆15%
        val memoryCacheSize = Runtime.getRuntime().maxMemory().toInt() / 7
        val memoryCacheParams =
            MemoryCacheParams(memoryCacheSize, 125, memoryCacheSize, Int.MAX_VALUE, Int.MAX_VALUE)

        val memTrimRegistry = NoOpMemoryTrimmableRegistry.getInstance()
        memTrimRegistry.registerMemoryTrimmable{type->
            val suggestedTrimRatio = type.suggestedTrimRatio
            if (MemoryTrimType.OnCloseToDalvikHeapLimit.suggestedTrimRatio == suggestedTrimRatio
                || MemoryTrimType.OnSystemLowMemoryWhileAppInBackground.suggestedTrimRatio == suggestedTrimRatio
                || MemoryTrimType.OnSystemLowMemoryWhileAppInForeground.suggestedTrimRatio == suggestedTrimRatio){
                //回收内存缓存
                Fresco.getImagePipeline().clearMemoryCaches()
            }

        }
        val diskCacheConfig = DiskCacheConfig.newBuilder(App.getInstance())
            .setBaseDirectoryPath(App.getInstance().cacheDir)
            .setBaseDirectoryName("fresco_cache")
            .setMaxCacheSize(diskCacheSize)
            .build()
        val config = ImagePipelineConfig.newBuilder(App.getInstance())
            .setDownsampleEnabled(true)
            .setNetworkFetcher(OkHttpNetworkFetcher(okhttpClient))
            .setBitmapsConfig(Bitmap.Config.RGB_565)
            .experiment()
            .setWebpSupportEnabled(true)
            .experiment()
            .setShouldDownscaleFrameToDrawableDimensions(true)//fix gif oom
            .setBitmapMemoryCacheParamsSupplier {
                return@setBitmapMemoryCacheParamsSupplier memoryCacheParams
            }
            .setMainDiskCacheConfig(diskCacheConfig)
            .setMemoryTrimmableRegistry(memTrimRegistry)
            .build()
        Fresco.initialize(App.getInstance(),config)
    }
}