package com.jamesfchen.image.svg;

import android.graphics.drawable.PictureDrawable;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.annotation.GlideType;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;

@GlideExtension
public class SvgExtension {
    private SvgExtension() {
    } // utility class
    private static RequestOptions DECODE_TYPE_SVG = RequestOptions.decodeTypeOf(PictureDrawable.class).lock();

    @GlideType(PictureDrawable.class)
    public static RequestBuilder<PictureDrawable> asSvg(RequestBuilder<PictureDrawable> requestBuilder) {
        return requestBuilder.apply(DECODE_TYPE_SVG);
    }

    @GlideOption
    public static BaseRequestOptions<?> squareThumb(BaseRequestOptions<?> requestOptions) {
        return requestOptions.centerCrop();
    }
}