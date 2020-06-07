package com.hawksjamesf.uicomponent

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapRegionDecoder
import android.graphics.Rect
import android.util.AttributeSet
import androidx.annotation.RawRes
import androidx.appcompat.widget.AppCompatImageView
import com.blankj.utilcode.util.ImageUtils
import java.io.InputStream


/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Dec/03/2019  Tue
 *
 * 图片分类有这么几种
 * 1.按照图片的存储bytes：低清图、高清图、超清图
 * 2.按照图片的长度：普通图、长图
 *
 * 对于高清以上的图片处理就是根据进程的freeMemory来尽力压缩
 * 对于长图应该做到，支持滚动
 */
class PhotoView : AppCompatImageView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        val maxMemory = Runtime.getRuntime().maxMemory()
        val freeMemory = Runtime.getRuntime().freeMemory()
    }

    var openRawResource: InputStream? = null
    fun setRaw(@RawRes rawResId: Int) {
        openRawResource = resources.openRawResource(rawResId)
        val bitmap = ImageUtils.getBitmap(openRawResource)

    }

    //    var rawResId: Int = -1
    var rawResId: Int = R.raw.wechatimg211

    fun showRegionBitmap() {
        if (rawResId == -1) throw  IllegalArgumentException("rawResId must not null")
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.raw.wechatimg211, options);
        val rawWidth = options.outWidth
        val rawHeight = options.outHeight

        try {
            val decoder = BitmapRegionDecoder.newInstance(resources.openRawResource(rawResId), false);

            // 指定需要显示的矩形区域，这里要显示的原图的左上 1/4 区域。
            val rect = Rect(0, 0, rawWidth / 2, rawHeight / 2);

            // 创建位图配置，这里使用 RGB_565，每个像素占 2 字节。
            val regionOptions = BitmapFactory.Options()
            regionOptions.inPreferredConfig = Bitmap.Config.RGB_565;

            // 创建得到指定区域的 Bitmap 对象并进行显示。
            val regionBitmap = decoder.decodeRegion(rect, regionOptions);
        } catch (e: Exception) {
            e.printStackTrace();
        }
    }


}