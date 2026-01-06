package com.electrolytej.widget.viewpager2.transformer

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

/**
 * 一个偏 3D 的炫酷效果：翻转 + 景深 + 轻微缩放。
 *
 * - position = 0: 当前页
 * - position = 1: 下一页
 */
class DepthFlipPageTransformer(
    private val maxRotationY: Float = 45f,
    private val minScale: Float = 0.88f,
    private val minAlpha: Float = 0.35f,
) : ViewPager2.PageTransformer {

    override fun transformPage(page: View, position: Float) {
        page.apply {
            if (position < -1f || position > 1f) {
                alpha = 0f
                return
            }

            // 让 3D 透视更明显一点（值越小透视越强）
            cameraDistance = 12000f

            val p = position.coerceIn(-1f, 1f)
            val absP = abs(p)

            // Y 轴翻转：左滑时当前页往右翻，右滑时往左翻
            pivotX = if (p < 0) width.toFloat() else 0f
            pivotY = height * 0.5f
            rotationY = -maxRotationY * p

            val scale = (1f - (1f - minScale) * absP)
            scaleX = scale
            scaleY = scale

            alpha = (1f - (1f - minAlpha) * absP)

            // 防止在某些机型上出现闪烁
            translationZ = -absP
        }
    }
}

