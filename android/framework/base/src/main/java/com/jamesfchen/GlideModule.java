package com.jamesfchen;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.engine.executor.GlideExecutor;
import com.bumptech.glide.module.AppGlideModule;
import com.jamesfchen.base.BuildConfig;

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
public class GlideModule extends AppGlideModule {
    final GlideExecutor.UncaughtThrowableStrategy myUncaughtThrowableStrategy = t -> {
        Log.e("cjf",Log.getStackTraceString(t));
    };

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

    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
