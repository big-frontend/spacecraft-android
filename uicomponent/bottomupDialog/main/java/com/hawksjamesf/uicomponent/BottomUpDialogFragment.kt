package com.hawksjamesf.uicomponent

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

class BottomUpDialogFragment : DialogFragment() {
    companion object {
        @JvmStatic
        fun newInstance(): BottomUpDialogFragment {
            val args = Bundle()

            val fragment = BottomUpDialogFragment()
            fragment.arguments = args
            return fragment
        }

        const val TAG = "BottomUpDialogFragment"
        const val fragmentTag = "RNFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Log.d(TAG, "onCreateDialog")
        return context?.let { BottomUpDialog(it) } ?: super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView")
        return inflater.inflate(R.layout.dialog_bottom_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated:$view")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG, "onActivityCreated:${view}")
        var findFragmentByTag = childFragmentManager.findFragmentByTag(fragmentTag)
        if (findFragmentByTag == null) {
            findFragmentByTag = RNFragment.newInstance()
        }
        childFragmentManager.beginTransaction()
                .add(R.id.fl_container, findFragmentByTag, fragmentTag)
                .commitAllowingStateLoss()
    }
}