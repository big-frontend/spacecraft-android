package com.jamesfchen.uicomponent

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity

class FragmentNotAttachActivity : AppCompatActivity() {
    companion object {
        @JvmStatic
        fun startActivity(context: Context) {
            val intent = Intent(context, FragmentNotAttachActivity::class.java)
            context.startActivity(intent)
        }

        const val TAG = "FragmentNotAttach"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val frameLayout = FrameLayout(this)
        frameLayout.setBackgroundColor(Color.DKGRAY)
        val bt = Button(this)
        bt.text = "fragment has not been attached"
        bt.isAllCaps = false
        bt.setOnClickListener {

        }
        frameLayout.addView(bt, ViewGroup.LayoutParams.MATCH_PARENT, 200)
        val lp = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        setContentView(frameLayout, lp)
        if (isFirst) {
            isFirst = false
            var outerFragment = supportFragmentManager.findFragmentByTag("OuterFragment") as? OuterFragment
            if (outerFragment == null) {
                outerFragment = OuterFragment.newInstance()
            }
            supportFragmentManager.beginTransaction()
                    .replace(android.R.id.content, outerFragment!!, "OuterFragment")
                    .commit()
        }
    }

    var isFirst = true
    override fun onResume() {
        super.onResume()
        supportFragmentManager.fragments.forEach {
            Log.d(TAG, "onResume:${it} / ${it.parentFragmentManager} / ${it.childFragmentManager}")

        }
    }
}