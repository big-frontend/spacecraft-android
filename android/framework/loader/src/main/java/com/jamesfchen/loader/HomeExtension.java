package com.jamesfchen.loader;

import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.jamesfchen.loader.GlideRequest;

import androidx.annotation.NonNull;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Mar/28/2020  Sat
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
    private static final RequestOptions DECODE_TYPE_GIF = RequestOptions.decodeTypeOf(GifDrawable.class).lock();

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
        if (options instanceof GlideRequest) {
            return ((GlideRequest) options)
//                    .transition(DrawableTransitionOptions.withCrossFade(3000))
                    .placeholder(com.jamesfchen.loader.R.mipmap.ic_launcher)
                    .error(com.jamesfchen.loader.R.mipmap.ic_launcher)
                    .fallback(com.jamesfchen.loader.R.mipmap.ic_launcher);
        }else if (options instanceof GlideRequest){
            return ((GlideRequest) options)
//                    .transition(BitmapTransitionOptions.withCrossFade(3000))
                    .placeholder(com.jamesfchen.loader.R.mipmap.ic_launcher)
                    .error(com.jamesfchen.loader.R.mipmap.ic_launcher)
                    .fallback(com.jamesfchen.loader.R.mipmap.ic_launcher);
//        }else if (options instanceof GlideOptions){
        } else {
            return options
                    .placeholder(com.jamesfchen.loader.R.mipmap.ic_launcher)
                    .error(com.jamesfchen.loader.R.mipmap.ic_launcher)
                    .fallback(com.jamesfchen.loader.R.mipmap.ic_launcher);
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

//    @NonNull
//    @GlideType(GifDrawable.class)
//    public static RequestBuilder<GifDrawable> asGif(RequestBuilder<GifDrawable> requestBuilder) {
//        return requestBuilder
//                .transition(new DrawableTransitionOptions())
//                .apply(DECODE_TYPE_GIF);
//    }
}
