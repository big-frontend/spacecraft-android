package com.electrolytej.main.widget

import android.app.Activity
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.Display
import android.view.MotionEvent
import androidx.annotation.RawRes
import com.almeros.android.multitouch.MoveGestureDetector
import com.blankj.utilcode.util.ImageUtils


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
 *
    功能点：
    - 手势操作：拖动/拉伸
    - 互动操作：保存/分享
    - 优化：先加载模糊图，在加载大图
    - 转场动画
 */
class PhotoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1,
): androidx.appcompat.widget.AppCompatImageView (context, attrs, defStyleAttr) {
    var moveGestureDetector:MoveGestureDetector
    private var mFocusX = 0f
    private var mFocusY = 0f
    private val mMatrix: Matrix = Matrix()
    init {
        val maxMemory = Runtime.getRuntime().maxMemory()
        val freeMemory = Runtime.getRuntime().freeMemory()
//        val viewOffsetHelper: ViewOffsetHelper = this.getViewOffsetHelper()
        moveGestureDetector= MoveGestureDetector(context,object: MoveGestureDetector.SimpleOnMoveGestureListener(){
            override fun onMove(detector: MoveGestureDetector): Boolean {
                val d = detector.focusDelta
                mFocusX += d.x
                mFocusY += d.y
                mMatrix.reset()
//                mMatrix.postScale(mScaleFactor, mScaleFactor)
//                mMatrix.postRotate(mRotationDegrees, scaledImageCenterX, scaledImageCenterY)
//                mMatrix.postTranslate(mFocusX - scaledImageCenterX, mFocusY - scaledImageCenterY)
                mMatrix.postTranslate(mFocusX , mFocusY)
                Log.d(TAG, "onMove: "+mFocusX+"/"+mFocusY)

//                this@PhotoView.imageMatrix = mMatrix
//                this@PhotoView.alpha = 0.4f
//                this@PhotoView.setAlpha(mAlpha)
                // mFocusX = detector.getFocusX();
                // mFocusY = detector.getFocusY();
//                viewOffsetHelper.setTopAndBottomOffset(mFocusY.toInt())
                return true
            }
        })
        val display: Display = (context as Activity).getWindowManager().getDefaultDisplay()
        mFocusX = display.width / 2f
        mFocusY = display.height / 2f
    }

    companion object {
        const val FULL_LOADER = 0
        const val PART_LOADER = 1
        const val TAG="photo_view"
    }

    fun setRawRes(@RawRes rawResId: Int, loader: Int = PART_LOADER) {
        if (rawResId == -1) throw  IllegalArgumentException("rawResId must not null")
        when (loader) {
            FULL_LOADER -> {
                loadFull(rawResId)
            }
            PART_LOADER -> {
                loadPart(rawResId)
            }
        }
    }

    private fun loadFull(@RawRes rawResId: Int) {
        val openRawResource = resources.openRawResource(rawResId)
        val bitmap = ImageUtils.getBitmap(openRawResource)
        Log.d(TAG, "loadFull: "+bitmap.byteCount/(1024f*1024f)+"m")
//        setImageBitmap(bitmap)
    }

    private fun loadPart(@RawRes rawResId: Int) {
        val openRawResource = resources.openRawResource(rawResId)
        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.RGB_565
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources,rawResId, options);
        val rawWidth = options.outWidth
        val rawHeight = options.outHeight

        try {
            val decoder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                BitmapRegionDecoder.newInstance(openRawResource)
            } else {
                BitmapRegionDecoder.newInstance(openRawResource,false)
            }
            val rect = Rect(0, 0, rawWidth, rawHeight/4)
            val regionOptions = BitmapFactory.Options()
            regionOptions.inPreferredConfig = Bitmap.Config.RGB_565
            val regionBitmap = decoder?.decodeRegion(rect, regionOptions)
            Log.d(TAG, "loadFull: "+(regionBitmap?.byteCount?:0)/(1024f*1024f)+"m")
            setImageBitmap(regionBitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return moveGestureDetector.onTouchEvent(event)
    }


}