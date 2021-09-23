package com.jamesfchen.image;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.LibraryGlideModule;
import com.caverock.androidsvg.SVG;
import com.jamesfchen.image.svg.SvgDecoder;
import com.jamesfchen.image.svg.SvgDrawableTranscoder;

import java.io.InputStream;

import androidx.annotation.NonNull;

/**
 * Module for the SVG sample app.
 */
@GlideModule
public class ImageModule extends LibraryGlideModule {
    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        registry.register(SVG.class, PictureDrawable.class, new SvgDrawableTranscoder())
                .append(InputStream.class, SVG.class, new SvgDecoder());
    }
}
