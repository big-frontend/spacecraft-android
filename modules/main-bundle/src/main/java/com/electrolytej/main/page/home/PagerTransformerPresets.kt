package com.electrolytej.main.page.home

import androidx.viewpager2.widget.CompositePageTransformer
import com.electrolytej.widget.viewpager2.transformer.BeamSlicePageTransformer
import com.electrolytej.widget.viewpager2.transformer.DepthFlipPageTransformer
import com.electrolytej.widget.viewpager2.transformer.FluidCardPageTransformer
import com.electrolytej.widget.viewpager2.transformer.GlitchNeonPageTransformer

/**
 * 一些现成的 ViewPager2 动效预设，方便快速切换对比。
 */
object PagerTransformerPresets {

    enum class Preset {
        /** 稳重、轻量，不太花 */
        CALM,

        /** 炸裂但不晕（推荐默认） */
        COOL,

        /** 特效大片，可能会有点花 */
        INSANE,
    }

    fun create(preset: Preset): CompositePageTransformer {
        return CompositePageTransformer().apply {
            when (preset) {
                Preset.CALM -> {
                    addTransformer(
                        DepthFlipPageTransformer(
                            maxRotationY = 18f,
                            minScale = 0.95f,
                            minAlpha = 0.7f,
                        )
                    )
                }

                Preset.COOL -> {
                    // 你当前选的：炸裂但不晕
                    addTransformer(
                        BeamSlicePageTransformer(
                            maxRotationX = 8f,
                            minScale = 0.94f,
                            minAlpha = 0.6f,
                            maxBlurRadius = 16f,
                        )
                    )
                    addTransformer(
                        DepthFlipPageTransformer(
                            maxRotationY = 28f,
                            minScale = 0.92f,
                            minAlpha = 0.55f,
                        )
                    )
                }

                Preset.INSANE -> {
                    addTransformer(
                        BeamSlicePageTransformer(
                            maxRotationX = 12f,
                            minScale = 0.92f,
                            minAlpha = 0.5f,
                            maxBlurRadius = 24f,
                        )
                    )
                    addTransformer(
                        DepthFlipPageTransformer(
                            maxRotationY = 40f,
                            minScale = 0.9f,
                            minAlpha = 0.45f,
                        )
                    )
                    addTransformer(
                        FluidCardPageTransformer(
                            maxRotationZ = 10f,
                            maxRotationY = 20f,
                            parallaxFactor = 0.35f,
                            maxTranslationY = 28f,
                            overshootFactor = 0.08f,
                            minScale = 0.9f,
                            minAlpha = 0.45f,
                        )
                    )
                    addTransformer(GlitchNeonPageTransformer())
                }
            }
        }
    }
}