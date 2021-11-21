package com.jamesfchen.main

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

/**
 * 一个纯正的控件是不应该有数据逻辑相关的代码，控件包含的逻辑只会是动画、绘制、滚动
 */
class PoemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) : androidx.appcompat.widget.AppCompatEditText(
    context,
    attrs,
    defStyleAttr
)
//自定义双向绑定 start
@BindingAdapter("poem")
fun setText(view: PoemView, newValue: String) {
    //接受来自网络数据的update,数据流由下至上
    Log.d("cjf", "setText ${newValue}")
    if (view.text.toString() != newValue) {
        view.setText(newValue)
    }
}

@InverseBindingAdapter(attribute = "poem")
fun getText(view: PoemView): String {
    //发送控件变化,数据流由上至下 or 由上至其他控件
    return view.text.toString()
}

@BindingAdapter("poemAttrChanged")
fun setListeners(view: PoemView, attrChange: InverseBindingListener) {
    //发送控件变化,数据流由上至下 or 由上至其他控件
    Log.d("cjf", "poemAttrChanged")
    view.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(
            s: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            attrChange.onChange()
        }
    })
}
//自定义双向绑定 end

