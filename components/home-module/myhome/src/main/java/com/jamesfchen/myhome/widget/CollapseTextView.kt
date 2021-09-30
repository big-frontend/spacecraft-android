package com.jamesfchen.myhome.widget

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.IntDef
import androidx.core.content.ContextCompat
import androidx.emoji.widget.EmojiTextView
import com.blankj.utilcode.util.ConvertUtils.dp2px
import com.jamesfchen.loader.R
import android.text.StaticLayout
import android.util.Log


/**
 * 代码块初始化执行顺序
 * 1. init{} 代码块(当存在主构造器+init{} 那么初始化的代码就是在构造器中； 当不存在主构造器 那么初始化的代码就可能直接字段)
 * 2. 次级构造器
 */
open class CollapseTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1,
    defStyleRes: Int = -1
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {
    companion object {
        const val DURATION_MILLIS = 350L
        const val STATE_COLLAPSED: Int = 1
        const val STATE_COLLAPSING: Int = 2
        const val STATE_EXPANDED: Int = 3
        const val STATE_EXPANDING: Int = 4
        const val RESET = 5
        const val TEXT_COLLAPSE: String = "收起"
        const val TEXT_EXPAND: String = "展开"
    }

    @IntDef(value = [STATE_COLLAPSED, STATE_EXPANDED])
    annotation class State

    var totalLines = 0
    var isCollapsed: Boolean = true
    var contentMaxLine: Int = 6
    val collapseColor = ContextCompat.getColor(context, R.color.collapse_blue)

    var state: Int = STATE_COLLAPSED
    private val etvContent = EmojiTextView(context)
    private val tvCollapse = TextView(context)

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.CollapseTextView)
        contentMaxLine = ta.getInteger(R.styleable.CollapseTextView_contentMaxLine, 6)
        val textSize = ta.getFloat(R.styleable.CollapseTextView_collapseTextSize, 15f)
        val textColor = ta.getColor(
            R.styleable.CollapseTextView_collapseTextColor,
            ContextCompat.getColor(context, R.color.b44)
        )
        ta.recycle()
        orientation = VERTICAL
//        android:lineSpacingExtra="1dp"
//        android:lineSpacingMultiplier="1.2"
        etvContent.setLineSpacing(dp2px(1f).toFloat(), 1.2f)
        etvContent.textSize = textSize
        etvContent.setTextColor(textColor)
        val etvContentLp = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        etvContentLp.bottomMargin = dp2px(4f)
        addView(etvContent, etvContentLp)
//        attachViewToParent(etvContent, -1, etvContentLp)
        val tvCollapseLp = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        tvCollapse.textSize = 14f//COMPLEX_UNIT_SP
        tvCollapse.setTypeface(tvCollapse.typeface, Typeface.BOLD)
        tvCollapse.setTextColor(Color.parseColor("#5A86FF"))
        tvCollapse.setOnClickListener {
            onCollapse(it)
        }
//        addView(tvCollapse,tvCollapseLp)
        attachViewToParent(tvCollapse, -1, tvCollapseLp)
    }

    private fun onCollapse(view: View) {
        etvContent.clearAnimation()
        val deltaValue: Int
        val startValue = etvContent.height
        isCollapsed = !isCollapsed

        if (isCollapsed) {
            deltaValue = etvContent.lineHeight * contentMaxLine - startValue
            tvCollapse.text = TEXT_EXPAND
            etvContent.height = etvContent.lineHeight * contentMaxLine
            state = STATE_COLLAPSED
        } else {
            deltaValue = etvContent.lineHeight * etvContent.lineCount - startValue
            tvCollapse.text = TEXT_COLLAPSE

            val contentAnimation = object : Animation() {
                override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                    etvContent.height =
                        (startValue + deltaValue * interpolatedTime).toInt()
                }
            }
            contentAnimation.duration = DURATION_MILLIS
            etvContent.startAnimation(contentAnimation)
            state = STATE_EXPANDED
        }
        listener?.invoke(etvContent, state)

    }

    fun setText(text: String?) {
        if (text.isNullOrBlank()) {
            visibility = View.GONE
            etvContent.text = null
            return
        }
        visibility = View.VISIBLE
        totalLines = getTotalLines(text)
        if (totalLines > contentMaxLine) {
            tvCollapse.setTextColor(collapseColor)
            tvCollapse.visibility = View.VISIBLE
        } else {
            tvCollapse.visibility = View.GONE
        }
        etvContent.text = text.trim()
        //View#post方法一定会在完成测绘被执行，因为测绘阶段处理的都是优先异步消息
//        etvContent.post {
//            totalLines = etvContent.lineCount
//           if (totalLines > contentMaxLine) {
//                tvCollapse.setTextColor(collapseColor)
//                tvCollapse.visibility = View.VISIBLE
//            } else {
//                tvCollapse.visibility = View.GONE
//            }
//        }
    }

    /**
     * https://stackoverflow.com/questions/3654321/measuring-text-height-to-be-drawn-on-canvas-android
     * 在测绘之前获取布局宽高信息的方式
     * - StaticLayout
     * - Paint.FontMetrics
     *      Paint.FontMetrics fm = mTextPaint.getFontMetrics();
            float height = fm.descent - fm.ascent;
     *
     * - getTextBounds
     *      Rect bounds = new Rect();
            mTextPaint.getTextBounds(mText, 0, mText.length(), bounds);
            int height = bounds.height();
     */
    fun getTotalLines(text: String): Int {
        val myStaticLayout =
            StaticLayout.Builder.obtain(text, 0, text.length, etvContent.paint, etvContent.width)
                .setLineSpacing(etvContent.lineSpacingExtra, etvContent.lineSpacingMultiplier)
                .build()
        return myStaticLayout.lineCount
    }

    fun recoverState(@State state: Int) {
        if (state == STATE_COLLAPSED) {
            tvCollapse.text = TEXT_EXPAND
            etvContent.height = etvContent.lineHeight * contentMaxLine
        } else if (state == STATE_EXPANDED) {
            tvCollapse.text = TEXT_COLLAPSE
            etvContent.height = etvContent.lineHeight * totalLines
        }
    }

    private var listener: ((view: View, state: Int) -> Unit)? = null
    fun setOnCollapseTextListener(listener: (view: View, state: Int) -> Unit) {
        this.listener = listener
    }
}
