package com.electrolytej.widget.viewpager2.transformer

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

/**
 * 偏“卡片/液体”质感的炫酷效果：摇摆 + 视差 + 轻微纵向漂移。
 *
 * 进阶版（更炸裂）：
 * - 加透视(cameraDistance)
 * - 复合旋转(rotation + rotationY)
 * - 更强的视差/位移，并带一点点 over-shoot 的感觉
 */
class FluidCardPageTransformer(
    private val maxRotationZ: Float = 10f,
    private val maxRotationY: Float = 18f,
    private val parallaxFactor: Float = 0.35f,
    private val maxTranslationY: Float = 28f,
    private val overshootFactor: Float = 0.08f,
    private val minScale: Float = 0.9f,
    private val minAlpha: Float = 0.45f,
) : ViewPager2.PageTransformer {

    override fun transformPage(page: View, position: Float) {
        page.apply {
            val p = position.coerceIn(-1f, 1f)
            val absP = abs(p)

            if (position < -1f || position > 1f) {
                // 离屏页一定要清理关键属性，避免 RecyclerView 复用造成“突然飞来/闪一下”
                alpha = 0f
                translationX = 0f
                translationY = 0f
                rotation = 0f
                rotationY = 0f
                scaleX = 1f
                scaleY = 1f
                translationZ = 0f
                return
            }

            // 3D 透视：值越大透视越弱；这里给一个偏强的透视
            cameraDistance = 14000f

            // pivot：偏下（更像卡片有重量）
            pivotX = width * 0.5f
            pivotY = height * 0.82f

            // 复合旋转：Z 轴摇摆 + Y 轴倾斜
            rotation = maxRotationZ * p
            rotationY = -maxRotationY * p

            // 视差：整体错位，让“液体”感更强
            val baseParallax = -width * p * parallaxFactor

            // over-shoot：在靠近边缘时略微多走一点点（松手吸附会更有弹性）
            val overshoot = -width * p * (overshootFactor * absP)
            translationX = baseParallax + overshoot

            // 越接近中心越“抬起”，边缘下沉：形成景深层级感
            translationY = maxTranslationY * (1f - (1f - absP) * (1f - absP))

            val scale = (1f - (1f - minScale) * absP)
            scaleX = scale
            scaleY = scale

            alpha = (1f - (1f - minAlpha) * absP)

            // 保证绘制顺序稳定
            translationZ = -absP
        }
    }
}
