package com.electrolytej.image.processor;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;

import com.facebook.cache.common.CacheKey;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.imagepipeline.request.BasePostprocessor;
public class GreyPostprocessor extends BasePostprocessor{

    @Override
    public void process(Bitmap bitmap) {
        final int w = bitmap.getWidth();
        final int h = bitmap.getHeight();
        final int[] pixels = new int[w * h];
        bitmap.getPixels(pixels, 0, w, 0, 0, w, h);
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                final int offset = y * w + x;
                // 设置为灰度值
                pixels[offset] = getGreyColor(pixels[offset]);
            }
        }
        bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
    }

    static int getGreyColor(int color) {
        final int alpha = color & 0xFF000000;
        final int r = (color >> 16) & 0xFF;
        final int g = (color >> 8) & 0xFF;
        final int b = color & 0xFF;
        final int luminance = (int) (0.2126 * r + 0.7152 * g + 0.0722 * b);
        return alpha | luminance << 16 | luminance << 8 | luminance;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public CacheKey getPostprocessorCacheKey() {
        return new SimpleCacheKey("hashCode=" + hashCode());
    }
}