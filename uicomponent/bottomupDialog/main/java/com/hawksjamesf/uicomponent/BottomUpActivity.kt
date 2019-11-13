package com.hawksjamesf.uicomponent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_bottom_up_dialog.*

class BottomUpActivity : AppCompatActivity() {
    companion object {
        const val isDialog = true
        const val isFragment = true
        const val isPopupWindow = true
        const val fragmentTag = "BottomUpDialogFragment"
    }
    val dialogFragment  = BottomUpDialogFragment.newInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_up_dialog)
        button2.setOnClickListener {
//            var findFragmentByTag = supportFragmentManager.findFragmentByTag(fragmentTag)
//            if (findFragmentByTag == null) {
//                findFragmentByTag = BottomUpDialogFragment.newInstance()
//            }
//            supportFragmentManager.beginTransaction()
//                    .add(findFragmentByTag, fragmentTag)
//                    .commitAllowingStateLoss()
            dialogFragment.show(supportFragmentManager,fragmentTag)
            if (isDialog) {

            } else if (isPopupWindow) {

            }
        }
    }
}