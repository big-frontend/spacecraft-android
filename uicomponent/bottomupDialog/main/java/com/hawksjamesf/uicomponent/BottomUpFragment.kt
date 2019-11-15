package com.hawksjamesf.uicomponent

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.dialog_bottom_up.*

class BottomUpFragment : Fragment() ,DialogInterface.OnCancelListener,DialogInterface.OnDismissListener,DialogInterface.OnShowListener{
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
        val dialog = AlertDialog.Builder(context)
                .setTitle("asdfsaf")
                .setIcon(R.drawable.tmp)
                .setMessage("sadfsaf")
                .create()
        dialog.setOnCancelListener(this)
        dialog.setOnDismissListener(this)
        dialog.setOnShowListener(this)
        dialog.show()

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

    override fun onCancel(dialog: DialogInterface?) {
        Log.d(TAG, "onCancel")
    }

    override fun onShow(dialog: DialogInterface?) {
        Log.d(TAG, "onShow")
    }

    override fun onDismiss(dialog: DialogInterface?) {
        Log.d(TAG, "onDismiss")
        parentFragmentManager.beginTransaction()
                .remove(this)
                .commitAllowingStateLoss()
    }
}