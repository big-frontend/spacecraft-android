package com.jamesfchen.myhome.widget

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.jamesfchen.loader.R
import com.jamesfchen.myhome.databinding.ViewCollapseTextBinding

class CollapseTextView : FrameLayout {
    companion object {
        const val DURATION_MILLIS = 350L
        const val COLLPASED: Int = 1
        const val COLLPASING: Int = 2
        const val EXPANDED: Int = 3
        const val EXPANDING: Int = 4
        const val RESET = 5
        var i: Int = 0
    }

    private lateinit var binding: ViewCollapseTextBinding
    private var isCollapsed: Boolean = true
    var contentMaxLine: Int = 6
    val collapse: String = "收起"
    val expand: String = "展开"
    val collapseColor = ContextCompat.getColor(context, R.color.collapse_blue)
    var isAvailableCollapse: Boolean = true
        set(value) {
            field = value
            invalidate()
        }
    var content: String? = null
    var textSize = 15f
    var textColor = ContextCompat.getColor(context, R.color.b44)

    var isCollapsingAnim = false

    var state: Int = COLLPASED


    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, -1)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(attrs)
    }

    private fun initView(attrs: AttributeSet?) {
        binding = ViewCollapseTextBinding.inflate(LayoutInflater.from(context), this, true)
        val ta = context.obtainStyledAttributes(attrs, R.styleable.CollapseTextView)
        isCollapsed = ta.getBoolean(R.styleable.CollapseTextView_collapsed, true)
        isAvailableCollapse = ta.getBoolean(R.styleable.CollapseTextView_availableCollapse, true)
        contentMaxLine = ta.getInteger(R.styleable.CollapseTextView_contentMaxLine, 6)
        content = ta.getString(R.styleable.CollapseTextView_collapseText)
        textSize = ta.getFloat(R.styleable.CollapseTextView_collapseTextSize, 15f)
        textColor = ta.getColor(
            R.styleable.CollapseTextView_collapseTextColor,
            ContextCompat.getColor(context, R.color.b44)
        )
        ta.recycle()
        ++i
    }

    fun setCollapseText(text: String?) {

        isCollapsingAnim = false

        if (TextUtils.isEmpty(text?.trim())) {
            visibility = View.GONE
            return
        } else {
            visibility = View.VISIBLE
        }
        binding.etvContent.textSize = textSize
        binding.etvContent.setTextColor(textColor)
        binding.etvContent.text = text?.trim()
        binding.tvCollapse.setOnClickListener {
            onCollapse(it)
        }

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        binding.etvContent.post {
            if (!isCollapsingAnim) {
                requestLayout()
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (isCollapsingAnim) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            return
        }

        val lineCount = binding.etvContent.lineCount
        val contentHeight = binding.etvContent.height
        val contentLineHeight = binding.etvContent.lineHeight

        binding.tvCollapse.visibility = if (isAvailableCollapse && lineCount > contentMaxLine) {
            binding.etvContent.height = binding.etvContent.lineHeight * contentMaxLine
            binding.tvCollapse.text = if (isCollapsed) expand else collapse
            binding.tvCollapse.setTextColor(collapseColor)
            View.VISIBLE

        } else if (isAvailableCollapse && lineCount <= contentMaxLine) {
            binding.etvContent.height = binding.etvContent.lineHeight * lineCount
            View.GONE

        } else if (!isAvailableCollapse) {
            binding.etvContent.height = binding.etvContent.lineHeight * lineCount
            View.GONE
        } else {
            View.GONE
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }


    fun onCollapse(view: View) {
        isCollapsingAnim = true
        binding.etvContent.clearAnimation()
        val deltaValue: Int
        val startValue = binding.etvContent.height
        isCollapsed = !isCollapsed
        if (isCollapsed) {
            deltaValue = binding.etvContent.lineHeight * contentMaxLine - startValue
            binding.tvCollapse.text = expand
            binding.etvContent.height = binding.etvContent.lineHeight * contentMaxLine
            if (listener != null) {
                listener?.onCollapseText(binding.etvContent)
            }
            state = COLLPASED
        } else {
            deltaValue = binding.etvContent.lineHeight * binding.etvContent.lineCount - startValue
            binding.tvCollapse.text = collapse


            val contentAnimation = object : Animation() {
                override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                    binding.etvContent.height =
                        (startValue + deltaValue * interpolatedTime).toInt()
                }
            }
            contentAnimation.duration = DURATION_MILLIS
            binding.etvContent.startAnimation(contentAnimation)
            state = EXPANDED
        }

    }

    fun collapse() {
        binding.tvCollapse.text = expand
        binding.etvContent.height = binding.etvContent.lineHeight * contentMaxLine
    }

    interface OnCollapseTextListener {
        fun onCollapseText(view: View)
    }

    private var listener: OnCollapseTextListener? = null
    fun setOnCollapseTextListener(listener: OnCollapseTextListener) {
        this.listener = listener

    }

}