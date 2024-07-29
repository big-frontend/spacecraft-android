package com.jamesfchen.bundle2.page.bottomupDialog

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.electrolytej.bundle2.R
import com.electrolytej.bundle2.databinding.DialogBottomUpBinding

class BottomUpDialogFragmentv2 : Fragment() ,DialogInterface.OnCancelListener,DialogInterface.OnDismissListener,DialogInterface.OnShowListener{
    companion object {
        @JvmStatic
        fun newInstance(): BottomUpDialogFragmentv2 {
            val args = Bundle()
            val fragment = BottomUpDialogFragmentv2()
            fragment.arguments = args
            return fragment
        }

        const val TAG = "BottomUpFragment"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "onAttach"+context)

    }
    lateinit var binding: DialogBottomUpBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogBottomUpBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")
        binding.flContainer.setBackgroundColor(Color.CYAN)
        binding.flContainer.layoutParams.height = 400
        val dialog = AlertDialog.Builder(context)
                .setTitle("asdfsaf")
                .setIcon(com.electrolytej.base.R.drawable.tmp)
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

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
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