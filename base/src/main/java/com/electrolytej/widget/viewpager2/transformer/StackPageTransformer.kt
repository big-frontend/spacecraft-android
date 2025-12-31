package com.electrolytej.widget.viewpager2.transformer

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class StackPageTransformer(private val offscreenPageLimit: Int) : ViewPager2.PageTransformer {

    companion object {
        private const val DEFAULT_SCALE = 0.8f
        private const val DEFAULT_ALPHA = 0.5f
    }

    override fun transformPage(page: View, position: Float) {
        page.apply {
            val scaleFactor = DEFAULT_SCALE.coerceAtLeast(1 - abs(position) * 0.1f)
            
            when {
                position <= 0 -> { // 当前页向左滑动或处于中心
                    translationX = 0f
                    scaleX = 1f
                    scaleY = 1f
                    alpha = 1f
                    // 保证当前页在最上层
                    translationZ = 0f
                }
                position > 0 && position < offscreenPageLimit -> { // 右侧的页面（堆叠在后面）
                    // 调整缩放
                    scaleX = scaleFactor
                    scaleY = scaleFactor

                    // 调整透明度
                    alpha = DEFAULT_ALPHA + (1 - DEFAULT_ALPHA) * (1 - position)

                    // 核心：负的 translationX 让后面的页面向左偏移，产生重叠效果
                    // 这里的值需要根据实际 View 的宽度和期望的重叠程度调整
                    translationX = -width * position * 0.9f

                    // 调整层级，越靠后的 position 层级越低
                    translationZ = -position
                }
                else -> { // 屏幕外的页面
                    alpha = 0f
                }
            }
        }
    }
}