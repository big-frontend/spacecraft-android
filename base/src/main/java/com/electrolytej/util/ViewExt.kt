package com.electrolytej.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.Nullable
import androidx.constraintlayout.widget.ConstraintLayout
import com.blankj.utilcode.util.ThreadUtils.runOnUiThread
import com.electrolytej.image.processor.Grey2Postprocessor
import com.facebook.common.executors.UiThreadImmediateExecutorService
import com.facebook.common.references.CloseableReference
import com.facebook.datasource.DataSource
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.interfaces.DraweeController
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber
import com.facebook.imagepipeline.image.CloseableImage
import com.facebook.imagepipeline.request.ImageRequestBuilder


var View.leftMargin
    get() = 0
    set(value) {
        (layoutParams as? ViewGroup.MarginLayoutParams)?.let {
            if (it.leftMargin != value || it.marginStart != value) {
                it.leftMargin = value
                it.marginStart = value
                layoutParams = it
            }
        }
    }

var View.rightMargin
    get() = 0
    set(value) {
        (layoutParams as? ViewGroup.MarginLayoutParams)?.let {
            if (it.rightMargin != value || it.marginEnd != value) {
                it.rightMargin = value
                it.marginEnd = value
                layoutParams = it
            }
        }
    }
var View.topMargin
    get() = 0
    set(value) {
        (layoutParams as? ViewGroup.MarginLayoutParams)?.let {
            if (it.topMargin != value) {
                it.topMargin = value
                layoutParams = it
            }
        }
    }

var View.bottomMargin
    get() = 0
    set(value) {
        (layoutParams as? ViewGroup.MarginLayoutParams)?.let {
            if (it.bottomMargin != value) {
                it.bottomMargin = value
                layoutParams = it
            }
        }
    }


var View.leftPadding
    get() = 0
    set(value) {
        (layoutParams as? ViewGroup.MarginLayoutParams)?.let {
            if (it.leftMargin != value || it.marginStart != value) {
                it.leftMargin = value
                it.marginStart = value
//                setPadding() = it
            }
        }
    }

var View.rightPadding
    get() = 0
    set(value) {
        (layoutParams as? ViewGroup.MarginLayoutParams)?.let {
            if (it.leftMargin != value || it.marginStart != value) {
                it.leftMargin = value
                it.marginStart = value
                layoutParams = it
            }
        }
    }


var View.leftToRightId
    get() = 0
    @IdRes
    set(value) {
        (layoutParams as? ConstraintLayout.LayoutParams)?.let {
            if (it.leftToRight != value) {
                it.leftToRight = value
                layoutParams = it
            }
        }
    }

var View.rightToLeftId
    get() = 0
    @IdRes
    set(value) {
        (layoutParams as? ConstraintLayout.LayoutParams)?.let {
            if (it.rightToLeft != value) {
                it.rightToLeft = value
                layoutParams = it
            }
        }
    }

fun SimpleDraweeView?.loadUrl(url: String) {
    if (url.isNullOrEmpty() || this == null) return
    val gifUri = Uri.parse(url)
    val request = ImageRequestBuilder.newBuilderWithSource(gifUri)
        .setPostprocessor(Grey2Postprocessor())
        .build()

    // 获取 GIF 数据源
    val dataSource: DataSource<CloseableReference<CloseableImage>> =
        Fresco.getImagePipeline().fetchDecodedImage(request, this)

    dataSource.subscribe(object : BaseBitmapDataSubscriber() {
        override fun onNewResultImpl(@Nullable bitmap: Bitmap?) {
            if (bitmap == null) return

            // 获取 AnimatedImageResult
            val imageReference = dataSource.result
            if (imageReference != null) {
                try {
                    val closeableImage = imageReference.get()
//                    if (closeableImage is CloseableImage) {
//                        val result: AnimatedImageResult =
//                            (closeableImage as CloseableAnimatedImage).getAnimatedImageResult()
//
//
//                        // 处理所有帧
//                        val image: AnimatedImage = result.getAnimatedImage()
//                        val frameCount: Int = image.getFrameCount()
//
//
//                        // 创建处理后的帧数组
//                        val processedFrames = arrayOfNulls<Bitmap>(frameCount)
//                        val durations = IntArray(frameCount)
//
//
//                        // 遍历每一帧并应用灰度效果
//                        for (i in 0..<frameCount) {
//                            val frame: AnimatedImageFrame = image.getFrame(i)
//                            durations[i] = frame.getDurationMs()
//
//
//                            // 创建新的 Bitmap 用于处理
//                            processedFrames[i] = Bitmap.createBitmap(
//                                image.getWidth(),
//                                image.getHeight(),
//                                Bitmap.Config.ARGB_8888
//                            )
//
//
//                            // 应用灰度滤镜
//                            applyGrayScaleFilter(processedFrames[i], frame)
//                            frame.dispose()
//                        }

                        // 使用处理后的帧创建动画（需要自定义 AnimatedDrawable）
//                        runOnUiThread {}
//                    }
                } finally {
                    CloseableReference.closeSafely(imageReference)
                }
            }
        }

        override fun onFailureImpl(dataSource: DataSource<CloseableReference<CloseableImage?>>) {
            // 处理失败
        }
    }, UiThreadImmediateExecutorService.getInstance())
    val controller: DraweeController = Fresco.newDraweeControllerBuilder()
        .setImageRequest(request)
        .setAutoPlayAnimations(true) // 自动播放GIF
        .build()
    setController(controller)
}

//private fun applyGrayScaleFilter(destBitmap: Bitmap, frame: AnimatedImageFrame) {
//    val canvas = Canvas(destBitmap)
//    val colorMatrix = ColorMatrix()
//    colorMatrix.setSaturation(0F)
//    val paint = Paint()
//    paint.setColorFilter(ColorMatrixColorFilter(colorMatrix))
//    // 绘制原始帧到目标 Bitmap 并应用滤镜
//    frame.renderFrame(frame.getWidth(), frame.getHeight(), destBitmap)
//    canvas.drawBitmap(destBitmap, 0f, 0f, paint)
//}