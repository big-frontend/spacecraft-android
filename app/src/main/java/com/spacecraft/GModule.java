package com.spacecraft;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ThreadUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.engine.executor.GlideExecutor;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.electrolytej.base.BuildConfig;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Mar/28/2020  Sat
 * <p>
 * Generated API 目前仅可以在 Application 模块内使用。这一限制可以让我们仅持有一份 Generated API，
 * 而不是各个 Library 和 Application 中均有自己定义出来的 Generated GAPI。这一做法会让 Generated API 的调用更简单，
 * 并确保 Application 模块中 Generated API 调用的选项在各处行为一致。这一限制在接下来的版本中也许会被取消（以实验性或其他的方式给出）。
 * <p>
 * 缓存
 * - 内存缓存
 * - Bitmap 池
 * - 磁盘缓存
 */
@com.bumptech.glide.annotation.GlideModule(glideName = "G")
public class GModule extends AppGlideModule {
    final GlideExecutor.UncaughtThrowableStrategy myUncaughtThrowableStrategy = t -> {
        Log.e("GlideModule",Log.getStackTraceString(t));
    };
    /**
     *
     * ResourceDecoder, 用于对新的 Resources(Drawables, Bitmaps)或新的 Data 类型(InputStreams, FileDescriptors)进行解码。
     * ResourceEncoder，用于向 Glide 的磁盘缓存写 Resources(BitmapResource, DrawableResource)。
     * Encoder, 用于向 Glide 的磁盘缓存写 Data (InputStreams, FileDesciptors)。
     * ModelLoader, 用于加载自定义的 Model(Url, Uri,任意的 POJO )和 Data(InputStreams, FileDescriptors)。
     * ResourceTranscoder，用于在不同的资源类型之间做转换，例如，从 BitmapResource 转换为 DrawableResource 。
     */
    OkHttpClient okhttpClient = new OkHttpClient.Builder()
                .dispatcher(new Dispatcher(ThreadUtils.getIoPool()))//默认任务分发池，最多并发请求为64个，每个host最多5个，线程池容量为整形最大值，为缓存池，对于低端手机能不能根据cpu来控制线程核心数，优化图片加载任务分发池最大线程数为2*cpu+1
                .connectTimeout(15_000, TimeUnit.MILLISECONDS)//15s
                .readTimeout(15_000, TimeUnit.MILLISECONDS)//
                .writeTimeout(15_000, TimeUnit.MILLISECONDS)//
                .connectionPool(new ConnectionPool(5, 10_000, TimeUnit.MILLISECONDS))//空闲5个，保活10s
                .addInterceptor((chain) -> {//应用层的拦截器
                    return chain.proceed(chain.request());
                }).build();

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        //    int memoryCacheSizeBytes = 1024 * 1024 * 20; // 20mb
        //    builder.setMemoryCache(new LruResourceCache(memoryCacheSizeBytes));
        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context)
                .setMemoryCacheScreens(2)
                .setBitmapPoolScreens(3)
                .build();
        builder.setMemoryCache(new LruResourceCache(calculator.getMemoryCacheSize()))
//                .setMemoryCache(new YourAppMemoryCacheImpl());
                .setBitmapPool(new LruBitmapPool(calculator.getBitmapPoolSize()))
                //disk cache默认250m缓存
                .setDiskCache(new ExternalPreferredCacheDiskCacheFactory(context))
//                .setDiskCache(new InternalCacheDiskCacheFactory(context))
                .setDiskCacheExecutor(GlideExecutor.newDiskCacheBuilder().setUncaughtThrowableStrategy(myUncaughtThrowableStrategy).build())
                .setSourceExecutor(GlideExecutor.newSourceBuilder().setUncaughtThrowableStrategy(myUncaughtThrowableStrategy).build())
//                .setDefaultRequestOptions(new RequestOptions().format(DecodeFormat.PREFER_ARGB_8888))
                .setLogLevel(BuildConfig.DEBUG ? Log.DEBUG : Log.ERROR);
    }

    @Override
    public void registerComponents(
            @NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        //替换默认的HttpGlideUrlLoader，使用Okhttp网络库并且优化任务分发池与连接池
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory((Call.Factory) okhttpClient));
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
