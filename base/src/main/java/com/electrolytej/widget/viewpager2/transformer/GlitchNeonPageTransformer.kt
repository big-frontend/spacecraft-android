package com.electrolytej.widget.viewpager2.transformer

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs
import kotlin.math.sin

/**
 * 更“炸裂”的故障霓虹风：抖动/错位 + 轻微倾斜 + 闪烁。
 *
 * 说明：
 * - 不依赖任何额外库。
 * - 通过 position 生成确定性 noise（不是随机），滑动过程稳定不抖帧。
 * - 适合和其它 transformer 叠加使用（CompositePageTransformer）。
 */
class GlitchNeonPageTransformer(
    private val maxJitterPx: Float = 14f,
    private val maxRotation: Float = 4f,
    private val minAlpha: Float = 0.35f,
    private val flickerStrength: Float = 0.25f,
) : ViewPager2.PageTransformer {

    override fun transformPage(page: View, position: Float) {
        page.apply {
            if (position < -1f || position > 1f) {
                alpha = 0f
                translationX = 0f
                translationY = 0f
                rotation = 0f
                scaleX = 1f
                scaleY = 1f
                translationZ = 0f
                return
            }

            val p = position.coerceIn(-1f, 1f)
            val absP = abs(p)

            // 生成确定性“噪声”：使用 sin 的组合，保证同一个 position 对应同一个 jitter
            // 频率越高越“故障”，但太高会视觉疲劳
            val n1 = sin((p * 12.7f).toDouble()).toFloat()
            val n2 = sin((p * 29.3f + 1.7f).toDouble()).toFloat()
            val noise = (n1 * 0.6f + n2 * 0.4f)

            // jitter 在中间更明显，边缘收敛
            val jitter = maxJitterPx * (1f - absP) * noise

            // 故障错位：X/Y 小幅抖动
            translationX += jitter
            translationY += jitter * 0.35f

            // 倾斜：带一点“舞台灯”味道
            pivotX = width * 0.5f
            pivotY = height * 0.5f
            rotation = maxRotation * p

            // 闪烁：让 alpha 产生轻微呼吸（同样是确定性的）
            val flicker = 1f - flickerStrength * abs(sin((p * 18f).toDouble()).toFloat())
            alpha = ((1f - (1f - minAlpha) * absP) * flicker).coerceIn(0f, 1f)

            // 稍微推到后面，避免抢过“主卡片”的 z
            translationZ = -absP
        }
    }
}

