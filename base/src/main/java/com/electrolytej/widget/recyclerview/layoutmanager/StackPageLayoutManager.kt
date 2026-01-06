package com.electrolytej.widget.recyclerview.layoutmanager

import android.content.Context
import androidx.annotation.Px
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max
import kotlin.math.min

/**
 * Horizontal stacked-card LayoutManager.
 *
 * Contract:
 * - Shows [maxVisibleCount] items at most.
 * - Item 0 (top card) is fully visible; items behind are scaled down and shifted.
 * - Spacing can be configured per-layer using absolute px offsets (cumulative by default).
 */
class StackPageLayoutManager(
    context: Context,
    reverseLayout: Boolean = false,
) : LinearLayoutManager(context, RecyclerView.HORIZONTAL, reverseLayout) {

    /** How many cards to layout (including the top one). */
    var maxVisibleCount: Int = 3
        set(value) {
            field = max(1, value)
            requestLayout()
        }

    /** Scale reduction per layer, e.g. 0.06 -> 2nd card scale 0.94, 3rd card 0.88. */
    var scaleStep: Float = 0.06f
        set(value) {
            field = value.coerceIn(0f, 0.5f)
            requestLayout()
        }

    /** Optional slight Y offset per layer to strengthen stack feel. */
    @Px
    var stackYOffsetPx: Int = 0
        set(value) {
            field = max(0, value)
            requestLayout()
        }

    /**
     * Per-layer absolute X offsets (in px).
     *
     * Example (cumulative): [0, 24, 52] -> layer1 shifts 24px, layer2 shifts 24+52=76px.
     * Example (non-cumulative): [0, 24, 52] -> layer2 shifts 52px.
     */
    private var itemSpacingPerLayerPx: IntArray = intArrayOf(0, 24, 52)

    enum class SpacingMode { CUMULATIVE, ABSOLUTE }

    var spacingMode: SpacingMode = SpacingMode.CUMULATIVE
        set(value) {
            field = value
            requestLayout()
        }

    /** Layout scroll offset in pixels. */
    private var scrollOffsetPx: Int = 0

    fun setItemSpacingPerLayerPx(@Px offsets: IntArray, mode: SpacingMode = spacingMode) {
        itemSpacingPerLayerPx = if (offsets.isNotEmpty()) offsets.copyOf() else intArrayOf(0)
        spacingMode = mode
        requestLayout()
    }

    override fun canScrollHorizontally(): Boolean = true
    override fun canScrollVertically(): Boolean = false

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        if (state.itemCount == 0) {
            detachAndScrapAttachedViews(recycler)
            scrollOffsetPx = 0
            return
        }
        if (state.isPreLayout) return

        detachAndScrapAttachedViews(recycler)
        layoutStack(recycler, state)
    }

    private fun layoutStack(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        val itemCount = state.itemCount
        if (itemCount <= 0) return

        val itemExtent = width.takeIf { it > 0 } ?: return

        val topPos = (scrollOffsetPx / itemExtent).coerceIn(0, max(0, itemCount - 1))
        val topOffsetWithinItem = scrollOffsetPx % itemExtent

        val layers = min(maxVisibleCount, itemCount - topPos)

        // Bottom -> top to keep drawing order: the last added view is on top.
        for (layer in layers - 1 downTo 0) {
            val position = topPos + layer
            val child = recycler.getViewForPosition(position)
            addView(child)

            // We lay out each child full size; transforms create the stack.
            measureChildWithMargins(child, 0, 0)
            layoutDecoratedWithMargins(child, 0, 0, width, height)

            val layerF = layer.toFloat()

            // Scale by layer.
            val scale = 1f - scaleStep * layerF
            child.scaleX = scale
            child.scaleY = scale

            // Compute stack offset for this layer.
            val stackOffset = computeLayerOffsetPx(layer)

            // Parallax during scroll: top card follows finger; behind cards move a bit towards their next position.
            // Use linear interpolation to avoid easing mismatch.
            val progress = topOffsetWithinItem.toFloat() / itemExtent.toFloat() // 0..1
            val nextOffset = computeLayerOffsetPx(layer + 1)
            val scrollingOffset = lerp(stackOffset.toFloat(), nextOffset.toFloat(), progress)

            child.translationX = scrollingOffset - topOffsetWithinItem.toFloat()
            child.translationY = (stackYOffsetPx * layerF)

            // Ensure z ordering is stable: top card should always be on top.
            // Use positive translationZ for top cards, decreasing by layer.
            child.translationZ = (layers - layer).toFloat()

            child.alpha = 1f
        }
    }

    private fun computeLayerOffsetPx(layer: Int): Int {
        if (layer <= 0) return 0
        if (itemSpacingPerLayerPx.isEmpty()) return 0

        return when (spacingMode) {
            SpacingMode.ABSOLUTE -> {
                val idx = min(layer, itemSpacingPerLayerPx.size - 1)
                itemSpacingPerLayerPx[idx]
            }

            SpacingMode.CUMULATIVE -> {
                val end = min(layer, itemSpacingPerLayerPx.size - 1)
                var sum = 0
                for (i in 1..end) sum += itemSpacingPerLayerPx[i]
                // If layer exceeds provided size, keep adding the last step.
                if (layer > itemSpacingPerLayerPx.size - 1) {
                    val last = itemSpacingPerLayerPx.last()
                    sum += (layer - (itemSpacingPerLayerPx.size - 1)) * last
                }
                sum
            }
        }
    }

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State,
    ): Int {
        if (state.itemCount == 0 || width <= 0) return 0

        val itemExtent = width
        val maxOffset = max(0, (state.itemCount - 1) * itemExtent)
        val newOffset = (scrollOffsetPx + dx).coerceIn(0, maxOffset)
        val consumed = newOffset - scrollOffsetPx
        if (consumed == 0) return 0

        scrollOffsetPx = newOffset
        detachAndScrapAttachedViews(recycler)
        layoutStack(recycler, state)
        return consumed
    }

    private fun lerp(a: Float, b: Float, t: Float): Float {
        return a + (b - a) * t.coerceIn(0f, 1f)
    }
}