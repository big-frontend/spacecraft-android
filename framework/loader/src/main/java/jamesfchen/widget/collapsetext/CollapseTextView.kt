package jamesfchen.widget.collapsetext

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.jamesfchen.loader.R
import com.jamesfchen.loader.databinding.ViewCollapseTextBinding

class CollapseTextView : FrameLayout {
    private lateinit var mBinding: ViewCollapseTextBinding
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

    companion object {
        const val DURATION_MILLIS = 350L
        const val COLLPASED: Int = 1
        const val COLLPASING: Int = 2
        const val EXPANDED: Int = 3
        const val EXPANDING: Int = 4
        const val RESET = 5
        var i: Int = 0

        @JvmStatic
        @BindingAdapter(value = ["collapseText"])
        fun setCollapseText(ctv: CollapseTextView?, text: String?) {
            ctv?.setCollapseText(text)
        }
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initComponent(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initComponent(attrs)
    }


    private fun initComponent(attrs: AttributeSet?) {
        mBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.view_collapse_text,
            this,
            true
        )
        mBinding.presenter = Presenter()
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
        mBinding.etvContent.textSize = textSize
        mBinding.etvContent.setTextColor(textColor)
        mBinding.etvContent.text = text?.trim()

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mBinding.etvContent.post {
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

        val lineCount = mBinding.etvContent.lineCount
        val contentHeight = mBinding.etvContent.height
        val contentLineHeight = mBinding.etvContent.lineHeight

        mBinding.tvCollapse.visibility = if (isAvailableCollapse && lineCount > contentMaxLine) {
            mBinding.etvContent.height = mBinding.etvContent.lineHeight * contentMaxLine
            mBinding.tvCollapse.text = if (isCollapsed) expand else collapse
            mBinding.tvCollapse.setTextColor(collapseColor)
            View.VISIBLE

        } else if (isAvailableCollapse && lineCount <= contentMaxLine) {
            mBinding.etvContent.height = mBinding.etvContent.lineHeight * lineCount
            View.GONE

        } else if (!isAvailableCollapse) {
            mBinding.etvContent.height = mBinding.etvContent.lineHeight * lineCount
            View.GONE
        } else {
            View.GONE
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }


    inner class Presenter {
        fun onCollapse(view: View) {
            isCollapsingAnim = true
            mBinding.etvContent.clearAnimation()
            val deltaValue: Int
            val startValue = mBinding.etvContent.height
            isCollapsed = !isCollapsed
            if (isCollapsed) {
                deltaValue = mBinding.etvContent.lineHeight * contentMaxLine - startValue
                mBinding.tvCollapse.text = expand
                mBinding.etvContent.height = mBinding.etvContent.lineHeight * contentMaxLine
                if (listener != null) {
                    listener?.onCollapseText(mBinding.etvContent)
                }
                state = COLLPASED
            } else {
                deltaValue =
                    mBinding.etvContent.lineHeight * mBinding.etvContent.lineCount - startValue
                mBinding.tvCollapse.text = collapse


                val contentAnimation = object : Animation() {
                    override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                        mBinding.etvContent.height =
                            (startValue + deltaValue * interpolatedTime).toInt()
                    }
                }
                contentAnimation.duration = DURATION_MILLIS
                mBinding.etvContent.startAnimation(contentAnimation)
                state = EXPANDED
            }

        }
    }

    fun collapse() {
        mBinding.tvCollapse.text = expand
        mBinding.etvContent.height = mBinding.etvContent.lineHeight * contentMaxLine
    }

    interface OnCollapseTextListener {
        fun onCollapseText(view: View)
    }

    private var listener: OnCollapseTextListener? = null
    fun setOnCollapseTextListener(listener: OnCollapseTextListener) {
        this.listener = listener

    }

}