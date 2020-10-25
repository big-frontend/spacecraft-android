package com.hawksjamesf.uicomponent

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

/**
 * bottom up的控件实现由如下几种：
 *
 */
class BottomUpActivity : AppCompatActivity() {
    companion object {
        const val isDialog = true
        const val isFragment = true
        const val isPopupWindow = true
        const val fragmentTag = "BottomUpDialogFragment"
    }

    val dialogFragment = BottomUpDialogFragment.newInstance()
    val dialogFragment2 = BottomUpDialogFragmentv2.newInstance()
    val rnFragment = RNFragment.newInstance()
    val bottomUpDialogFragmentForSheet = BottomUpDialogFragmentForSheet.newInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_up_dialog)
    }

    fun onClick(view: View) {
        val button = view as Button
        when (button.id) {
            R.id.button0 -> {
                dialogFragment.show(supportFragmentManager, button.text.toString())
            }
            R.id.button1 -> {
                dialogFragment2.show(supportFragmentManager, button.text.toString())
            }
            R.id.button2 -> {
                rnFragment.show(supportFragmentManager, button.text.toString())
            }
            R.id.button3 -> bottomUpDialogFragmentForSheet.show(supportFragmentManager, button.text.toString())

        }
    }
}