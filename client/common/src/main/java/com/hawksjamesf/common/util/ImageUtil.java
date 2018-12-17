package com.hawksjamesf.common.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;

import com.hawksjamesf.common.constants.MemoryUnit;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.core.content.ContextCompat;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @since: Nov/07/2018  Wed
 */
public class ImageUtil {
    public static byte[] bitmap2Bytes(final Bitmap bitmap, final Bitmap.CompressFormat format) {
        if (bitmap == null) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(format, 100, baos);
        return baos.toByteArray();
    }

    public static Bitmap bytes2Bitmap(final byte[] bytes) {
        return (bytes == null || bytes.length == 0)
                ? null
                : BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public static Bitmap drawable2Bitmap(final Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        Bitmap bitmap;
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            /*Opaque pixel format is basically associated with determining pixels transparency / opaqueness.
            Usually the alpha channel is used as an opacity channel.
            By Setting the Pixel format to OPAQUE Android will choose its own OPAQUE format rather than using the Alpha channel bits for transparency information.
             */
            bitmap = Bitmap.createBitmap(1, 1,
                    drawable.getOpacity() != PixelFormat.OPAQUE
                            ? Bitmap.Config.ARGB_8888
                            : Bitmap.Config.RGB_565);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                    drawable.getOpacity() != PixelFormat.OPAQUE
                            ? Bitmap.Config.ARGB_8888
                            : Bitmap.Config.RGB_565);
        }
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static Drawable bitmap2Drawable(final Bitmap bitmap) {
        return bitmap == null ? null : new BitmapDrawable(Util.getApp().getResources(), bitmap);
    }

    public static byte[] drawable2Bytes(final Drawable drawable, final Bitmap.CompressFormat format) {
        return drawable == null ? null : bitmap2Bytes(drawable2Bitmap(drawable), format);
    }

    public static Drawable bytes2Drawable(final byte[] bytes) {
        return bitmap2Drawable(bytes2Bitmap(bytes));
    }

    public static Bitmap view2Bitmap(final View view) {
        if (view == null) return null;
        Bitmap ret = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(ret);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return ret;
    }

    public static Bitmap getBitmap(final File file) {
        if (file == null) return null;
        return BitmapFactory.decodeFile(file.getAbsolutePath());
    }

    public static Bitmap getBitmap(final File file, final int maxWidth, final int maxHeight) {
        if (file == null) return null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        //只是计算边界不会加载图片---start
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        //只是计算边界不会加载图片---end
        return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
    }

    public static int calculateInSampleSize(final BitmapFactory.Options options,
                                            final int maxWidth,
                                            final int maxHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        while ((width >>= 1) >= maxWidth && (height >>= 1) >= maxHeight) {
            inSampleSize <<= 1;
        }
        return inSampleSize;
    }

    public static Bitmap getBitmap(final String filePath) {
        if (isSpace(filePath)) return null;
        return BitmapFactory.decodeFile(filePath);
    }

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static Bitmap getBitmap(final String filePath, final int maxWidth, final int maxHeight) {
        if (isSpace(filePath)) return null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static Bitmap getBitmap(final InputStream is) {
        if (is == null) return null;
        return BitmapFactory.decodeStream(is);
    }

    public static Bitmap getBitmap(final InputStream is, final int maxWidth, final int maxHeight) {
        if (is == null) return null;
        byte[] bytes = input2Bytes(is);
        return getBitmap(bytes, 0, maxWidth, maxHeight);
    }

    public static Bitmap getBitmap(final byte[] bytes,
                                   final int offset,
                                   final int maxWidth,
                                   final int maxHeight) {
        if (bytes.length == 0) return null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bytes, offset, bytes.length);
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(bytes, offset, bytes.length, options);

    }


    public static Bitmap getBitmap(final byte[] bytes, final int offset) {
        if (bytes.length == 0) return null;
        return BitmapFactory.decodeByteArray(bytes, offset, bytes.length);
    }

    public static Bitmap getBitmap(@DrawableRes final int resId) {
        Drawable drawable = ContextCompat.getDrawable(Util.getApp(), resId);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap getBitmap(@DrawableRes final int resId, final int maxWidth, final int maxHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        final Resources resource = Util.getApp().getResources();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resource, resId, options);
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resource, resId, options);
    }

    public static Bitmap getBitmap(final FileDescriptor fd) {
        if (fd == null) return null;
        return BitmapFactory.decodeFileDescriptor(fd);
    }

    public static Bitmap getBitmap(final FileDescriptor fd, final int maxWidth, final int maxHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd, null, options);
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fd, null, options);
    }

    public static Bitmap scale(final Bitmap src, final int newWidth, final int newHeight) {
        return scale(src, newWidth, newHeight, false);
    }

    public static Bitmap scale(final Bitmap src,
                               final int newWidth,
                               final int newHeight,
                               final boolean recycle) {
        if (isEmpty(src)) return null;
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(src, newWidth, newHeight, true);
        if (recycle && !src.isRecycled()) src.recycle();
        return scaledBitmap;
    }

    public static Bitmap scale(final Bitmap src,
                               final float scaleWidth,
                               final float scaleHeight) {
        return scale(src, scaleWidth, scaleHeight, false);
    }

    public static Bitmap scale(final Bitmap src,
                               final float scaleWidth,
                               final float scaleHeight,
                               final boolean recycle) {
        if (isEmpty(src)) return null;
        Matrix matrix = new Matrix();
        matrix.setScale(scaleWidth, scaleHeight);
        Bitmap result = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        if (recycle && !src.isRecycled()) src.recycle();
        return result;
    }

    public static Bitmap clip(final Bitmap src,
                              final int x,
                              final int y,
                              final int width,
                              final int height) {
        return clip(src, x, y, width, height, false);
    }

    public static Bitmap clip(final Bitmap src,
                              final int x,
                              final int y,
                              final int width,
                              final int height,
                              final boolean recycle) {
        if (isEmpty(src)) return null;
        Bitmap bitmap = Bitmap.createBitmap(src, x, y, width, height);
        if (recycle && !src.isRecycled()) src.recycle();
        return bitmap;
    }

    public static Bitmap skew(final Bitmap src, final float kx, final float ky) {
        return skew(src, kx, ky, false);
    }

    public static Bitmap skew(final Bitmap src, final float kx, final float ky, final boolean recycle) {
        return skew(src, kx, ky, 0, 0, recycle);
    }

    public static Bitmap skew(final Bitmap src,
                              final float kx, final float ky,
                              final float px, final float py) {
        return skew(src, kx, ky, px, py, false);
    }

    public static Bitmap skew(final Bitmap src,
                              final float kx, final float ky,
                              final float px, final float py,
                              final boolean recycle) {
        if (isEmpty(src)) return null;
        Matrix matrix = new Matrix();
        matrix.setSkew(kx, ky, px, py);
        Bitmap result = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        if (recycle && !src.isRecycled()) src.recycle();
        return result;
    }

    public static Bitmap rotate(final Bitmap src,
                                final int degrees,
                                final float px,
                                final float py) {
        return rotate(src, degrees, px, py, false);
    }

    public static Bitmap rotate(final Bitmap src,
                                final int degrees,
                                final float px,
                                final float py,
                                final boolean recycle) {
        if (isEmpty(src)) return null;
        if (degrees == 0) return src;
        Matrix matrix = new Matrix();
        matrix.setRotate(degrees, px, py);
        Bitmap reslut = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        if (recycle && !src.isRecycled()) src.recycle();
        return reslut;
    }


    public static int getRotateDegree(final String filePath) {
        int degress = 0;
        ExifInterface exifInterface = null;
        try {
            exifInterface = new ExifInterface(filePath);

            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
            );
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degress = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degress = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degress = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return degress;
    }

    public static Bitmap toRound(final Bitmap src) {
        return toRound(src, 0, 0, false);
    }

    public static Bitmap toRound(final Bitmap src, final boolean recycle) {
        return toRound(src, 0, 0, recycle);
    }

    public static Bitmap toRound(final Bitmap src, @IntRange(from = 0) int borderSize, @ColorInt int borderColor) {
        return toRound(src, borderSize, borderColor, false);

    }

    public static Bitmap toRound(final Bitmap src, @IntRange(from = 0) int borderSize, @ColorInt int borderColor, final boolean recycle) {
        if (isEmpty(src)) return null;
        int width = src.getWidth();
        int height = src.getHeight();
        int size = Math.min(width, height);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Bitmap result = Bitmap.createBitmap(width, height, src.getConfig());
        float center = size / 2f;//整个即将绘制区域的中心点
        RectF rectF = new RectF(0, 0, width, height);
        //即将绘制的区域向内部缩小
        rectF.inset((width - size) / 2f, (height - size) / 2f);
        Matrix matrix = new Matrix();
        matrix.setTranslate(rectF.left, rectF.top);//将矩阵的原点坐标移动到即将绘制的区域的原点坐标，即，使用相同的坐标原点(pivot)
        matrix.preScale((float) size / width, (float) size / height);//通过缩放的比例，将图片缩放到可绘制的区域
        BitmapShader shader = new BitmapShader(src, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        shader.setLocalMatrix(matrix);
        paint.setShader(shader);
        Canvas canvas = new Canvas(result);
        /*
        使用drawRoundRect(rectF, center, center, paint)这种方式，最后的结果相当于使用center-crop，对src进行中间裁剪。
         */
//        canvas.drawCircle(cx,cy,radius,paint);
        canvas.drawRoundRect(rectF, center, center, paint);//rx:x-radius ，ry:y-radius

        if (borderSize > 0) {//做描边操作
            paint.setShader(null);
            paint.setColor(borderColor);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(borderSize);
            float radius = center - borderSize / 2f;
            canvas.drawCircle(width / 2f, height / 2f, radius, paint);
        }
        if (recycle && !src.isRecycled()) src.recycle();
        return result;
    }

    public static Bitmap toRoundCorner(final Bitmap src,
                                       final float radius) {
        return toRoundCorner(src, radius, 0, 0, false);
    }

    public static Bitmap toRoundCorner(final Bitmap src,
                                       final float radius,
                                       final boolean recycle) {
        return toRoundCorner(src,radius,0,0,recycle);

    }


    public static Bitmap toRoundCorner(final Bitmap src,
                                       final float radius,
                                       @IntRange(from = 0) int borderSize,
                                       @ColorInt int borderColor,
                                       final boolean recycle) {
        if (isEmpty(src)) return null;
        int width = src.getWidth();
        int height = src.getHeight();
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //先画出bitmap
        Bitmap result = Bitmap.createBitmap(width, height, src.getConfig());
        // CLAMP：边缘拉伸
        // REPEAT,重复模式
        // MIRROR：镜像模式
        BitmapShader shader = new BitmapShader(src, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        Canvas canvas = new Canvas(result);
        RectF rectF = new RectF(0, 0, width, height);
        float halfBorderSize = borderSize / 2f;
        //由于存在边框尺寸，所以整个边框需要向里面压缩bordersize
        rectF.inset(halfBorderSize, halfBorderSize);
        //与src的绘制区域相比，result的绘制区域变小，因为边界要加粗
        canvas.drawRoundRect(rectF, radius, radius, paint);

        //如果存在边框尺寸，则用粗笔描边
        if (borderSize > 0) {
            paint.setShader(null);
            paint.setColor(borderColor);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(borderSize);
            paint.setStrokeCap(Paint.Cap.ROUND);
            canvas.drawRoundRect(rectF, radius, radius, paint);
        }

        if (recycle && !src.isRecycled()) src.recycle();
        return result;
    }

    public static Bitmap addCornerBorder(final Bitmap src,
                                         @IntRange(from = 1) final int borderSize,
                                         @ColorInt final int color,
                                         @FloatRange(from = 0) final float cornerRadius) {
        return addBorder(src, borderSize, color, false, cornerRadius, false);
    }

    public static Bitmap addCornerBorder(final Bitmap src,
                                         @IntRange(from = 1) final int borderSize,
                                         @ColorInt final int color,
                                         @FloatRange(from = 0) final float connerRadius,
                                         final boolean recycle) {
        return addBorder(src, borderSize, color, false, connerRadius, recycle);
    }

    public static Bitmap addCircleBorder(final Bitmap scr,
                                         @IntRange(from = 1) final int borderSize,
                                         @ColorInt final int color) {
        return addBorder(scr, borderSize, color, true, 0, false);
    }

    public static Bitmap addCircleBorder(final Bitmap src,
                                         @IntRange(from = 1) final int borderSize,
                                         @ColorInt int color,
                                         final boolean recycle) {
        return addBorder(src, borderSize, color, true, 0, recycle);
    }

    public static Bitmap addBorder(final Bitmap src,
                                   @IntRange(from = 1) final int borderSize,
                                   @ColorInt final int color,
                                   final boolean isCircle,
                                   @FloatRange(from = 0) final float cornerRadius,
                                   final boolean recycle) {
        //由于src之前绘制的区域固定了，所以当边界尺寸变大，那么中间图片的size就要变小。
        if (isEmpty(src)) return null;
        Bitmap result = recycle ? src : src.copy(src.getConfig(), true);
        int width = result.getWidth();
        int height = result.getHeight();
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(borderSize);
        if (isCircle) {
            float radius = Math.min(width, height) / 2f - borderSize / 2f;
            canvas.drawCircle(width / 2f, height / 2f, radius, paint);
        } else {
            int halfBorderSize = borderSize >> 1;
            RectF rectF = new RectF(halfBorderSize, halfBorderSize, width - halfBorderSize, height - halfBorderSize);
            canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint);
        }

        return result;
    }

    public static Bitmap addReflection(final Bitmap src, final int reflectionHeight) {
        return addReflection(src, reflectionHeight, false);
    }

    public static Bitmap addReflection(final Bitmap src, final int reflectionHeight, final boolean recycle) {
        if (isEmpty(src)) return null;
        final int REFLECTION_GAP = 0;
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);//负值为拉伸，倒影的效果图，存在拉伸效果
        //不对原bitmap进行操作，如果为filter=true，会对原图进行操作。
        Bitmap reflectionBitmap = Bitmap.createBitmap(src, 0, srcHeight - reflectionHeight, srcWidth, reflectionHeight, matrix, false);
        //为倒影和src创建一块能绘制他两的区域
        Bitmap ret = Bitmap.createBitmap(srcWidth, srcHeight + reflectionHeight, src.getConfig());
        Canvas canvas = new Canvas(ret);
        canvas.drawBitmap(src, 0, 0, null);//绘制原图
        canvas.drawBitmap(reflectionBitmap, 0, srcHeight + REFLECTION_GAP, null);//绘制倒影图

        //给倒影添加效果
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        LinearGradient shader = new LinearGradient(
                0, srcHeight, 0, ret.getHeight() + REFLECTION_GAP,
                0x70FFFFFF,
                0x00FFFFFF,
                Shader.TileMode.MIRROR);
        paint.setShader(shader);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        //在倒影上面通过图片混合，制造模糊效果。dst为倒影，src为即将绘制的上去的图形，所以混合模式采用dst in，即两者绘制交集，显示dst
        canvas.drawRect(0, srcHeight + REFLECTION_GAP, srcWidth, ret.getHeight(), paint);
        if (!reflectionBitmap.isRecycled()) reflectionBitmap.recycle();
        if (recycle && !src.isRecycled()) src.recycle();
        return ret;
    }

    public static Bitmap addTextWatermark(final Bitmap src,
                                          final String content,
                                          final int textSize,
                                          @ColorInt final int color,
                                          final float x,
                                          final float y) {
        return addTextWatermark(src, content, textSize, color, x, y, false);
    }

    public static Bitmap addTextWatermark(final Bitmap src,
                                          final String content,
                                          final int textSize,
                                          @ColorInt final int color,
                                          final float x,
                                          final float y,
                                          final boolean recycle) {
        if (isEmpty(src)) return null;
        Bitmap ret = src.copy(src.getConfig(), true);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Canvas canvas = new Canvas(ret);
        paint.setColor(color);
        paint.setTextSize(textSize);
        Rect rect = new Rect();
        paint.getTextBounds(content, 0, content.length(), rect);
        canvas.drawText(content, x, y + textSize, paint);
        if (recycle && !src.isRecycled()) src.recycle();
        return ret;
    }

    public static Bitmap addImageWatchermark(final Bitmap src,
                                             final Bitmap watermark,
                                             final int x, final int y,
                                             final int alpal) {
        return addImageWatchermark(src, watermark, x, y, alpal, false);
    }

    public static Bitmap addImageWatchermark(final Bitmap src,
                                             final Bitmap watermark,
                                             final int x, final int y,
                                             final int alpal,
                                             final boolean recycle) {
        if (isEmpty(src)) return null;
        Bitmap copy = src.copy(src.getConfig(), true);
        if (isEmpty(watermark)) {
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setAlpha(alpal);
            Canvas canvas = new Canvas(copy);
            canvas.drawBitmap(watermark, x, y, paint);
        }
        if (recycle && !src.isRecycled()) src.recycle();
        return copy;
    }

    public static Bitmap toAlpha(final Bitmap src) {
        return toAlpha(src, false);
    }

    public static Bitmap toAlpha(final Bitmap src, final boolean recycle) {
        if (isEmpty(src)) return null;
        Bitmap ret = src.extractAlpha();
        if (recycle && !src.isRecycled()) src.recycle();
        return ret;
    }

    public static Bitmap toGray(final Bitmap src) {
        return toGray(src, false);
    }

    public static Bitmap toGray(final Bitmap src, final boolean recycle) {
        if (isEmpty(src)) return null;
        Bitmap ret = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        Canvas canvas = new Canvas(ret);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);//修改饱和度，取值为0表示完全无色彩
        ColorMatrixColorFilter colorMatrixColorFilter = new ColorMatrixColorFilter(colorMatrix);
        paint.setColorFilter(colorMatrixColorFilter);
        canvas.drawBitmap(src, 0, 0, paint);
        if (recycle && !src.isRecycled()) src.recycle();
        return ret;
    }

    public static Bitmap fastBlur(final Bitmap src,
                                  @FloatRange(from = 0, to = 1, fromInclusive = false) final float scale,
                                  @FloatRange(from = 0, to = 25, fromInclusive = false) final float radius) {
        return fastBlur(src, scale, radius, false);

    }

    public static Bitmap fastBlur(final Bitmap src,
                                  @FloatRange(from = 0, to = 1, fromInclusive = false) final float scale,
                                  @FloatRange(from = 0, to = 25, fromInclusive = false) final float radius,
                                  final boolean recycle) {
        if (isEmpty(src)) return null;
        int width = src.getWidth();
        int height = src.getHeight();
        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);
        Bitmap scaleBitmap = Bitmap.createBitmap(src, 0, 0, width, height, matrix, true);
        //Paint.ANTI_ALIAS_FLAG ：抗锯齿标志
        //Paint.FILTER_BITMAP_FLAG : 使位图过滤的位掩码标志
        //Paint.DITHER_FLAG : 使位图进行有利的抖动的位掩码标志
        //Paint.UNDERLINE_TEXT_FLAG : 下划线
        //Paint.STRIKE_THRU_TEXT_FLAG : 中划线
        //Paint.FAKE_BOLD_TEXT_FLAG : 加粗
        //Paint.LINEAR_TEXT_FLAG : 使文本平滑线性扩展的油漆标志
        //Paint.SUBPIXEL_TEXT_FLAG : 使文本的亚像素定位的绘图标志
        //Paint.EMBEDDED_BITMAP_TEXT_FLAG : 绘制文本时允许使用位图字体的绘图标志
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG | Paint.ANTI_ALIAS_FLAG);
        Canvas canvas = new Canvas();
        //在dst上面添加一成层透明度
        PorterDuffColorFilter filter = new PorterDuffColorFilter(Color.TRANSPARENT, PorterDuff.Mode.SRC_ATOP);
        paint.setColorFilter(filter);
        canvas.scale(scale, scale);
        canvas.drawBitmap(scaleBitmap, 0, 0, paint);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            scaleBitmap = renderScriptBlur(scaleBitmap, radius, recycle);
        } else {
            scaleBitmap = stackBlur(scaleBitmap, (int) radius, recycle);
        }

        if (scale == 1) {
            if (recycle && !src.isRecycled()) src.recycle();
            return scaleBitmap;
        }
        Bitmap ret = Bitmap.createScaledBitmap(scaleBitmap, width, height, true);
        if (!scaleBitmap.isRecycled()) scaleBitmap.recycle();
        if (recycle && !src.isRecycled()) src.recycle();
        return ret;

    }

    public static Bitmap renderScriptBlur(final Bitmap src, @FloatRange(from = 0, to = 25, fromInclusive = false) final float radius) {
        return renderScriptBlur(src, radius, false);
    }

    public static Bitmap renderScriptBlur(final Bitmap src, @FloatRange(from = 0, to = 25, fromInclusive = false) final float radius,
                                          final boolean recycle) {
        RenderScript rs = null;
        //如果src被回收就使用src如果不被回收就尽量不破src，copy来做修改
        Bitmap ret = recycle ? src : src.copy(src.getConfig(), true);
        try {

            rs = RenderScript.create(Util.getApp());
            rs.setMessageHandler(new RenderScript.RSMessageHandler());
            Allocation input = Allocation.createFromBitmap(rs, ret, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
            Allocation output = Allocation.createTyped(rs, input.getType());
            ScriptIntrinsicBlur buildScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            buildScript.setInput(input);
            buildScript.setRadius(radius);
            buildScript.forEach(output);
            output.copyTo(ret);
        } finally {
            if (rs != null) {
                rs.destroy();
            }
        }
        return ret;

    }

    public static Bitmap stackBlur(final Bitmap src, final int radius
    ) {
        return stackBlur(src, radius, false);

    }

    public static Bitmap stackBlur(final Bitmap src, int radius, final boolean recycle) {
        Bitmap ret = recycle ? src : src.copy(src.getConfig(), true);
        if (radius < 1) {
            radius = 1;
        }
        int w = ret.getWidth();
        int h = ret.getHeight();

        int[] pix = new int[w * h];
        ret.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }
        ret.setPixels(pix, 0, w, 0, 0, w, h);
        return ret;
    }

    public static boolean save(final Bitmap src,
                               final String filePath,
                               final Bitmap.CompressFormat format) {
        return save(src, getFileByPath(filePath), format, false);
    }

    public static boolean save(final Bitmap src,
                               final String filePath,
                               final Bitmap.CompressFormat format,
                               final boolean recycle) {
        return save(src, getFileByPath(filePath), format, recycle);
    }

    public static boolean save(final Bitmap src,
                               final File file,
                               final Bitmap.CompressFormat format) {

        return save(src, file, format, false);
    }


    public static boolean save(final Bitmap src,
                               final File file,
                               final Bitmap.CompressFormat format,
                               final boolean recycle) {
        if (isEmpty(src) || !createFileByDeleteOldFile(file)) return false;
        OutputStream os = null;
        boolean ret = false;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file));
            ret = src.compress(format, 100, os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            CloseUtil.closeIO(os);
        }
        return ret;

    }

    public static boolean isImage(final File file) {
        return file != null && isImage(file.getPath());
    }

    public static boolean isImage(final String filePath) {
        String path = filePath.toUpperCase();
        return path.endsWith(".PNG") || path.endsWith(".JPG") ||
                path.endsWith(".JPEG") ||
                path.endsWith(".BMP") ||
                path.endsWith(".GIF");
    }

    public static String getImageType(final String filePath) {
        return getImageType(getFileByPath(filePath));
    }

    public static String getImageType(final File file) {
        if (file == null) return null;
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            return getImageType(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } finally {
            CloseUtil.closeIO(is);
        }
    }

    public static String getImageType(final InputStream is) {
        if (is == null) return null;
        byte[] bytes = new byte[8];
        try {
            return is.read(bytes, 0, 8) != -1 ? getImageType(bytes) : null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getImageType(final byte[] bytes) {
        if (isJPEG(bytes)) return "JPEG";
        if (isGIF(bytes)) return "GIF";
        if (isPNG(bytes)) return "PNG";
        if (isBMP(bytes)) return "BMP";
        return null;
    }

    public static boolean isJPEG(final byte[] bytes) {
        return bytes.length >= 2 && (bytes[0] == (byte) 0xFF) && (bytes[1] == (byte) 0xD8);
    }

    public static boolean isGIF(final byte[] bytes) {
        return bytes.length >= 6
                && bytes[0] == 'G' && bytes[1] == 'I'
                && bytes[2] == 'F' && bytes[3] == '8'
                && bytes[4] == '7' || bytes[4] == '9' && bytes[5] == 'a';
    }

    public static boolean isPNG(final byte[] bytes) {
        return bytes.length >= 8
                && (bytes[0] == (byte) 137 && bytes[1] == (byte) 80
                && bytes[2] == (byte) 78 && bytes[3] == (byte) 71
                && bytes[4] == (byte) 13 && bytes[5] == (byte) 10
                && bytes[6] == (byte) 26 && bytes[7] == (byte) 10);
    }

    public static boolean isBMP(final byte[] bytes) {
        return bytes.length >= 2
                && bytes[0] == 0x42 && bytes[1] == 0x4d;
    }

    public static boolean createFileByDeleteOldFile(final File file) {
        if (file == null) return false;
        if (file.exists() && !file.delete()) return false;
        if (!createOrExitsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    public static boolean createOrExitsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    public static Bitmap compressByScale(final Bitmap src,
                                         final int newWidth,
                                         final int newHeight) {
        return scale(src, newWidth, newHeight, false);
    }


    public static Bitmap compressByScale(final Bitmap src,
                                         final int newWidth,
                                         final int newHeight,
                                         final boolean recyle) {
        return scale(src, newWidth, newHeight, recyle);
    }

    public static Bitmap compressByScale(final Bitmap src,
                                         final float scaleWidth,
                                         final float scaleHeight) {
        return scale(src, scaleWidth, scaleHeight, false);
    }

    public static Bitmap compressByScale(final Bitmap src,
                                         final float scaleWidth,
                                         final float scaleHeight,
                                         final boolean recyle) {
        return scale(src, scaleWidth, scaleHeight, recyle);
    }

    public static Bitmap compressByQuality(final Bitmap src,
                                           @IntRange(from = 0, to = 100) final int quality) {
        return compressByQuality(src, quality, false);
    }

    public static Bitmap compressByQuality(final Bitmap src,
                                           @IntRange(from = 0, to = 100) final int quality,
                                           final boolean recycle) {
        if (isEmpty(src)) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        byte[] bytes = baos.toByteArray();
        if (recycle && !src.isRecycled()) src.recycle();
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

    }

    public static Bitmap compressByQulity(final Bitmap src, final long maxByteSize) {
        return compressByQuality(src, maxByteSize, false);
    }

    public static Bitmap compressByQuality(final Bitmap src, final long maxByteSize, final boolean recycle) {
        if (isEmpty(src)) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //最好质量
        src.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes;
        if (baos.size() <= maxByteSize) {
            bytes = baos.toByteArray();
        } else {
            //压缩完之后还是大于maxByteSize，所以需要在进一步压缩
            baos.reset();
            //最差质量
            src.compress(Bitmap.CompressFormat.JPEG, 0, baos);
            if (baos.size() >= maxByteSize) {//当quality在100和0时，最好的压缩结果都是大于maxByteSize，那么选择quality=0压缩之后的结果
                bytes = baos.toByteArray();
            } else {
                //二分法寻找最佳质量
                int st = 0;
                int end = 100;
                int mid = 0;
                while (st < end) {
                    mid = (st + end) / 2;
                    baos.reset();
                    src.compress(Bitmap.CompressFormat.JPEG, mid, baos);
                    int len = baos.size();
                    if (len == maxByteSize) {
                        break;//使用maxByteSize为最佳
                    } else if (len > maxByteSize) {
                        end = mid - 1;
                    } else {
                        st = mid + 1;
                    }
                }

                if (end==mid-1){
                    baos.reset();
                    src.compress(Bitmap.CompressFormat.JPEG,st,baos);
                }
                bytes=baos.toByteArray();

            }

        }

        if (recycle && !src.isRecycled()) src.recycle();
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public static Bitmap compressBySampleSize(final Bitmap src,final int sampleSize){
        return compressBySampleSize(src,sampleSize,false);
    }

    public static Bitmap compressBySampleSize(final Bitmap src,final int sampleSize,final boolean recycle){
        if (isEmpty(src)) return null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize=sampleSize;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] bytes = baos.toByteArray();
        if (recycle&&!src.isRecycled()) src.recycle();
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);
    }

    public static Bitmap compressBySampleSize(final Bitmap src,final int maxWidth,final int maxHeight){
        return compressBySampleSize(src,maxWidth,maxHeight,false);
    }

    public static Bitmap compressBySampleSize(final Bitmap src,final int maxWidth,final int maxHeight,final boolean recycle){
        if (isEmpty(src)) return null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] bytes = baos.toByteArray();
        BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);

        options.inSampleSize=calculateInSampleSize(options,maxWidth,maxHeight);
        options.inJustDecodeBounds=false;

        if (recycle&&!src.isRecycled()) src.recycle();
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);

    }

    public static boolean isEmpty(final Bitmap src) {
        return src == null || src.getWidth() == 0 || src.getHeight() == 0;
    }

    public static byte[] input2Bytes(final InputStream is) {
        if (is == null) return null;
        try {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] bytes = new byte[MemoryUnit.KB];
            int len;
            while ((len = is.read(bytes, 0, MemoryUnit.KB)) != -1) {
                baos.write(bytes, 0, len);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            CloseUtil.closeIO(is);
        }
    }

}
