package com.jamesfchen.bundle2.page.bottomupDialog

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.electrolytej.bundle2.R
import com.electrolytej.bundle2.databinding.ActivityBottomUpDialogBinding

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

    val dialogFragment =
        BottomUpDialogFragment.Companion.newInstance()
    val dialogFragment2 =
        BottomUpDialogFragmentv2.Companion.newInstance()
    val rnFragment = RNFragment.Companion.newInstance()
    val bottomUpDialogFragmentForSheet =
        BottomUpDialogFragmentForSheet.Companion.newInstance()
    lateinit var binding : ActivityBottomUpDialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomUpDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
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