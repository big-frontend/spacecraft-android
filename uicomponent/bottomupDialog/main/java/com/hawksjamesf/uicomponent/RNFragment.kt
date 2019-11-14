package com.hawksjamesf.uicomponent

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_rn.*

class RNFragment : Fragment() {
    companion object {

        @JvmStatic
        fun newInstance(): RNFragment {
            val args = Bundle()
            val fragment = RNFragment()
            fragment.arguments = args
            return fragment
        }
        const val TAG = "RNFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView")
        return inflater.inflate(R.layout.fragment_rn, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")
        repeat(20) {
            val textView = TextView(view.context)
            textView.text = "asdfasfsafaf"
            val divider=View(view.context)
            divider.setBackgroundColor(Color.RED)
            ll_container.addView(divider,ViewGroup.LayoutParams.MATCH_PARENT,2)
            ll_container.addView(textView, ViewGroup.LayoutParams.MATCH_PARENT, 400)
        }
    }
}