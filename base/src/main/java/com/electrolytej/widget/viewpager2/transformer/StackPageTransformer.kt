package com.electrolytej.widget.viewpager2.transformer

import android.content.res.Resources
import android.view.View
import androidx.viewpager2.widget.ViewPager2

class StackPageTransformer(private val offscreenPageLimit: Int) : ViewPager2.PageTransformer {

    enum class SpacingMode {
        /** provider 提供的是“每层的绝对 offset”（第二张、第三张…各自的总偏移量） */
        ABSOLUTE,
        /** provider 提供的是“每层的增量”(delta)，最终 offset 为 1..layer 的累加 */
        CUMULATIVE,
    }

    companion object {
        private const val DEFAULT_SCALE_STEP = 0.08f
        private const val DEFAULT_ALPHA_STEP = 0.15f
        private const val DEFAULT_SPACING_DP = 12f
    }

    /**
     * 每一层卡片“间距/偏移”的提供器（返回 px）。
     * layer 从 1 开始：1=第二张卡，2=第三张...
     *
     * 注意：它返回的含义由 [spacingMode] 决定：
     * - ABSOLUTE: 返回该层的总 offset
     * - CUMULATIVE: 返回该层的增量 delta
     */
    private var itemSpacingProviderPx: (layer: Int) -> Float = { _ -> dpToPx(DEFAULT_SPACING_DP) }

    private var spacingMode: SpacingMode = SpacingMode.ABSOLUTE

    /** 每层缩放递减（例如 0.08f 表示每层缩小 8%）。 */
    private var scaleStep: Float = DEFAULT_SCALE_STEP

    /** 每层透明度递减（例如 0.15f 表示每层 alpha 减少 0.15）。 */
    private var alphaStep: Float = DEFAULT_ALPHA_STEP

    /** RecyclerView 复用/布局抖动时宽度可能短暂为 0，用上一帧宽度兜底，避免出现“跳一帧/顿一下”。 */
    private var lastWidthPx: Float = 0f

    /** 是否对每段内的 frac 使用 smoothStep（更柔和但非严格线性）。 */
    private var useSmoothStep: Boolean = false

    fun setSpacingMode(mode: SpacingMode) {
        spacingMode = mode
    }

    /**
     * 设置所有层使用相同间距（dp）。
     * - ABSOLUTE：每一层的总 offset 都是 spacingDp（看起来“所有层挤在同一条线”）
     * - CUMULATIVE：每一层增量都是 spacingDp（等差叠开：12、24、36...）
     */
    fun setItemSpacingDp(spacingDp: Float) {
        val px = dpToPx(spacingDp)
        itemSpacingProviderPx = { _ -> px }
    }

    /**
     * 设置每一层不同的间距（dp）。
     *
     * 例：setItemSpacingPerLayerDp(18f, 12f, 8f)
     * - ABSOLUTE 模式：layer=1 使用 18dp；layer=2 使用 12dp（注意：这是“总 offset”，可能出现第三张比第二张更靠左）
     * - CUMULATIVE 模式：layer=1 增量=18dp；layer=2 增量=12dp（最终总 offset=18+12=30dp）
     *
     * layer 超出数组长度时使用最后一个值兜底。
     */
    fun setItemSpacingPerLayerDp(vararg spacingDpPerLayer: Float) {
        if (spacingDpPerLayer.isEmpty()) return
        val pxList = spacingDpPerLayer.map { dpToPx(it) }
        itemSpacingProviderPx = { layer ->
            val idx = (layer - 1).coerceAtLeast(0)
            pxList.getOrElse(idx) { pxList.last() }
        }
    }

    /** 更高级的用法：直接按层返回 dp（含义由 spacingMode 决定）。 */
    fun setItemSpacingProviderDp(provider: (layer: Int) -> Float) {
        itemSpacingProviderPx = { layer -> dpToPx(provider(layer)) }
    }

    fun setScaleStep(step: Float) {
        scaleStep = step
    }

    fun setAlphaStep(step: Float) {
        alphaStep = step
    }

    /**
     * 设置是否使用平滑步骤
     * @param enabled true 表示在每个段落内使用平滑步骤，false 表示严格线性
     */
    fun setUseSmoothStep(enabled: Boolean) {
        useSmoothStep = enabled
    }

    override fun transformPage(page: View, position: Float) {
        page.apply {
            when {
                position <= 0f -> {
                    translationX = 0f
                    scaleX = 1f
                    scaleY = 1f
                    alpha = 1f
                    translationZ = 0f
                }

                position < offscreenPageLimit -> {
                    val p = position.coerceAtLeast(0f)

                    val parentWidth = (parent as? View)?.width ?: 0
                    val wCandidate = when {
                        width > 0 -> width.toFloat()
                        measuredWidth > 0 -> measuredWidth.toFloat()
                        parentWidth > 0 -> parentWidth.toFloat()
                        else -> 0f
                    }

                    // 始终给本帧一个确定的 w：优先用最新的可用宽度，否则用缓存 lastWidthPx。
                    // 如果两者都不可用（极少数首次布局场景），w=0，此时会退化为“只做缩放/alpha/z，不做位移”。
                    val w = if (wCandidate > 0f) {
                        lastWidthPx = wCandidate
                        wCandidate
                    } else {
                        lastWidthPx
                    }


                    // 抵消 ViewPager2 默认布局偏移（线性）
                    val cancelOffset = -w * position

                    val layerCeil = kotlin.math.ceil(p).toInt().coerceAtLeast(1)
                    val frac = (p - (layerCeil - 1)).coerceIn(0f, 1f)
                    val fracToCeil = if (useSmoothStep) smoothStep(frac) else frac

                    val stackOffset = when (spacingMode) {
                        SpacingMode.ABSOLUTE -> {
                            itemSpacingProviderPx(layerCeil) * fracToCeil
                        }

                        SpacingMode.CUMULATIVE -> {
                            var sum = 0f
                            for (i in 1 until layerCeil) sum += itemSpacingProviderPx(i)
                            sum + itemSpacingProviderPx(layerCeil) * fracToCeil
                        }
                    }

                    translationX = cancelOffset + stackOffset

                    val scale = (1f - scaleStep * p).coerceAtLeast(0.5f)
                    scaleX = scale
                    scaleY = scale

                    // 给 alpha 一个下限，避免“以为消失”(尤其在滑动/回弹尾段)
                    alpha = (1f - alphaStep * p).coerceIn(0.2f, 1f)

                    val eps = 0.001f
                    translationZ = -(position + eps)
                }

                else -> {
                    translationX = 0f
                    scaleX = 1f
                    scaleY = 1f
                    alpha = 0f
                    translationZ = -100f
                }
            }
        }
    }

    private fun smoothStep(t: Float): Float {
        // classic smoothstep: 3t^2 - 2t^3, C1 连续
        val x = t.coerceIn(0f, 1f)
        return x * x * (3f - 2f * x)
    }


    /**
     * 返回当前 spacing 配置下，“最深层(=offscreenPageLimit-1)”需要的最大堆叠偏移（px）。
     * 可用于外部自动设置 ViewPager2 的 paddingEnd/paddingStart，避免第三张及以后被挤出可视区域。
     */
    fun getMaxStackOffsetPx(): Float {
        val maxLayer = (offscreenPageLimit - 1).coerceAtLeast(0)
        if (maxLayer <= 0) return 0f

        return when (spacingMode) {
            SpacingMode.ABSOLUTE -> itemSpacingProviderPx(maxLayer)
            SpacingMode.CUMULATIVE -> {
                var sum = 0f
                for (i in 1..maxLayer) sum += itemSpacingProviderPx(i)
                sum
            }
        }
    }

    private fun dpToPx(dp: Float): Float {
        return dp * Resources.getSystem().displayMetrics.density
    }
}