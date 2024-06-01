package com.electrolytej.main.page.welcome

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class WelcomeViewModel(
    //允许VM访问相关的Activity or Fragment已经保存状态和参数，数据来自Intent.getExtras() 或者 getArguments()方法
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val uid: String = savedStateHandle["uid"] ?: throw IllegalArgumentException("missing user id")

}