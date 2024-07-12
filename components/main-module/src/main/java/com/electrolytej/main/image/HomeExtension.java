package com.electrolytej.main.image;

import androidx.annotation.NonNull;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.request.BaseRequestOptions;
import com.electrolytej.main.R;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * <p>
 * GlideOption注解: 会在 RequestOptions 的子类中生成一个方法
 * GlideType注解：会在 RequesetManager 的子类中生成一个方法
 * <p>
 * 该类必须放在和AppGlideModule一样的模块中
 */
@SuppressWarnings({"WeakerAccess", "unused"})
@GlideExtension
public final class HomeExtension {
    private static final int MINI_THUMB_SIZE = 100;
    private HomeExtension() {
    } // utility class

    /**
     * 提供默认的占位符 、 错误符 、 后备回调符
     *
     * @param options
     * @return
     */
    @NonNull
    @GlideOption
    public static BaseRequestOptions<?> logo(BaseRequestOptions<?> options) {
        if (options instanceof RequestBuilder){
            return ((RequestBuilder) options)
                    .transition(BitmapTransitionOptions.withCrossFade(3000))
                    .placeholder(R.drawable.ic_stat_name)
                    .error(R.drawable.ic_stat_name)
                    .fallback(R.drawable.ic_stat_name);
//        }else if (options instanceof GlideOptions){
        } else {
            return options
                    .placeholder(R.drawable.ic_stat_name)
                    .error(R.drawable.ic_stat_name)
                    .fallback(R.drawable.ic_stat_name);
        }

    }

    @NonNull
    @GlideOption
    public static BaseRequestOptions<?> miniThumb(BaseRequestOptions<?> options) {
        return options
                .fitCenter()
                .override(MINI_THUMB_SIZE);
    }

    /**
     * 扩展流式调用的操作符miniThumb
     * GlideApp.with(fragment)
     * .load(url)
     * .miniThumb(thumbnailSize)
     * .into(imageView);
     *
     * @param options
     * @return
     */
    @GlideOption
    public static BaseRequestOptions<?> miniThumb(BaseRequestOptions<?> options, int size) {
        return options
                .fitCenter()
                .override(size);
    }
}
