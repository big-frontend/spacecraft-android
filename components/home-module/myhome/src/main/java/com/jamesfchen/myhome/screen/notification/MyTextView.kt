package com.jamesfchen.myhome.screen.notification

import android.content.Context
import android.util.AttributeSet
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

class MyTextView : androidx.appcompat.widget.AppCompatTextView {
    companion object {
        //自定义双向绑定 start
        @JvmStatic
        @BindingAdapter("poem")
        fun setText(view: MyTextView, newValue: String?) {
            // Important to break potential infinite loops.
            if (view.text != newValue) {
                view.text = newValue
            }
        }

        @JvmStatic
        @InverseBindingAdapter(attribute = "poem")
        fun getText(view: MyTextView): String {
            return view.text as String
        }

        @JvmStatic
        @BindingAdapter("app:poemAttrChanged")
        fun setListeners(
            view: MyTextView,
            attrChange: InverseBindingListener
        ) {
            // Set a listener for click, focus, touch, etc.
        }
        //自定义双向绑定 end
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
}