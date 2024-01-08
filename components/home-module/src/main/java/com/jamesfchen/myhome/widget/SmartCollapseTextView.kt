package com.jamesfchen.myhome.widget

import android.content.Context
import android.util.AttributeSet
import android.util.SparseIntArray
import androidx.core.util.getOrDefault

/**
 * Copyright ® $ 2021
 * All right reserved.

 * @author jamesfchen
 * @email hawksjamesf@gmail.com
 * @since 9月/30/2021  周四
 *
 * 使用与Recyclerview场景下的collapse text
 */
class SmartCollapseTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1,
    defStyleRes: Int = -1
) : CollapseTextView(context, attrs, defStyleAttr, defStyleRes) {
    companion object {
        //由于Recyclerview会复用ViewHolder所以我们需要记录每个CollapseTextView的状态
        private val stateArray = SparseIntArray()
    }

    fun recoverStateByUniqueKey(key: Int) {
        recoverState(stateArray.getOrDefault(key, STATE_COLLAPSED))
    }

    fun autoRecoverStateByUniqueKey(key: Int) {
        recoverStateByUniqueKey(key)
        setOnCollapseTextListener { _, state ->
            stateArray.append(key, state)
        }
    }
}