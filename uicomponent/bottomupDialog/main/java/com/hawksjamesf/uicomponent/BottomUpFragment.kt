package com.hawksjamesf.uicomponent

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.dialog_bottom_up.*

class BottomUpFragment : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance(): BottomUpFragment {
            val args = Bundle()
            val fragment = BottomUpFragment()
            fragment.arguments = args
            return fragment
        }

        const val TAG = "BottomUpFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_bottom_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")
        fl_container.setBackgroundColor(Color.CYAN)
        fl_container.layoutParams.height = 400
        AlertDialog.Builder(context)
                .setTitle("asdfsaf")
                .setIcon(R.drawable.tmp)
                .setMessage("sadfsaf")
                .create()
                .show()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    fun show(fragmentManager: FragmentManager, tag: String) {
        fragmentManager.beginTransaction()
                .add(this, tag)
                .commitAllowingStateLoss()
    }
}