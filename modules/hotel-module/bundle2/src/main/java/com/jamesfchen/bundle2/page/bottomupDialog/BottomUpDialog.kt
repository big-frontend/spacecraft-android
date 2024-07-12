package com.jamesfchen.bundle2.page.bottomupDialog

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomsheet.BottomSheetDialog

/**
 *

val dialog =  BottomSheetDialog(this@BottomUpActivity)
dialog.setContentView(R.layout.dialog_bottom_up)
dialog.show()

它们的初始化顺序依次是（静态变量、静态初始化块）>（变量、初始化块）>构造器。
 */
class BottomUpDialog : BottomSheetDialog {
    companion object {
        const val TAG= "BottomUpDialog"
    }
    constructor(context: Context) : super(context){
        Log.d(BottomUpDialog.Companion.TAG,"secondary constructor")
    }
    constructor(context: Context, theme: Int) : super(context, theme)
    constructor(context: Context, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener?) : super(context, cancelable, cancelListener)

    init {
        Log.d(BottomUpDialog.Companion.TAG,"primate constructor")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

}