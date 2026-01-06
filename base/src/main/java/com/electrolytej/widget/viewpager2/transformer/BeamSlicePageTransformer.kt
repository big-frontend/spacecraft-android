package com.electrolytej.widget.viewpager2.transformer

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs
import kotlin.math.max

/**
 * “光束切割”效果（赛博/舞台灯风格）：
 * - 滑动时页面会出现类似被光束扫过/切开的高亮与轻微拖影。
 * - Android 12+(API 31) 使用 RenderEffect 做模糊拖影（更像光束/扫描线）。
 * - 低版本自动降级为：轻微缩放 + 透视倾斜 + 高亮(提升 alpha/translationZ)。
 *
 * 说明：
 * - RenderEffect 是作用在整个 View 上的后处理，不会修改你的 item layout。
 * - 建议与 clipToPadding=false / clipChildren=false 配合。
 */
class BeamSlicePageTransformer(
    private val maxRotationX: Float = 10f,
    private val minScale: Float = 0.92f,
    private val minAlpha: Float = 0.55f,
    private val maxBlurRadius: Float = 18f,
) : ViewPager2.PageTransformer {

    override fun transformPage(page: View, position: Float) {
        page.apply {
            val p = position.coerceIn(-1f, 1f)
            val absP = abs(p)

            if (position < -1f || position > 1f) {
                alpha = 0f
                scaleX = 1f
                scaleY = 1f
                rotationX = 0f
                translationZ = 0f
                clearBeamEffect()
                return
            }

            // 基础“切割”姿态：像被一束光斜着扫过
            pivotX = width * 0.5f
            pivotY = height * 0.45f
            rotationX = maxRotationX * p
            cameraDistance = 16000f

            val scale = (1f - (1f - minScale) * absP)
            scaleX = scale
            scaleY = scale

            // “高亮扫过”感觉：中心更亮，边缘更暗
            alpha = (1f - (1f - minAlpha) * absP)

            // 给中心一点点“顶到前面”的感觉
            translationZ = (1f - absP)

            // Android 12+ 用 RenderEffect 做光束拖影（模糊半径随着滑动变化）
            val blur = max(0f, maxBlurRadius * (1f - absP))
            applyBeamEffect(blur)
        }
    }

    private fun View.applyBeamEffect(blurRadius: Float) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            setBeamEffectApi31(blurRadius)
        }
    }

    private fun View.clearBeamEffect() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            setRenderEffect(null)
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun View.setBeamEffectApi31(blurRadius: Float) {
        // blurRadius=0 时不要反复创建 effect
        if (blurRadius <= 0.1f) {
            setRenderEffect(null)
            return
        }

        // 轻微 blur 作为“光束拖影”
        val blur = RenderEffect.createBlurEffect(blurRadius, blurRadius, Shader.TileMode.CLAMP)

        // 组合：可继续叠加其它 RenderEffect（比如色差/偏移），这里先保持性能友好
        setRenderEffect(blur)
    }
}

