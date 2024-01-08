package com.jamesfchen.bundle2.bottomupDialog

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.jamesfchen.bundle2.R
import com.jamesfchen.bundle2.databinding.DialogBottomUpBinding

/**
 *DialogFragment的设计是为了将业务代码放到fragment，通过fragment的生命周期管理。让dialog更纯粹。
 */
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
        const val fragmentTag = "${TAG}_RNFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Log.d(TAG, "onCreateDialog")
        return context?.let { BottomUpDialog(it, theme) }
                ?: super.onCreateDialog(savedInstanceState)
    }
    lateinit var binding: DialogBottomUpBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView")
        binding = DialogBottomUpBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated:$view")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG, "onActivityCreated:${view}")
        var findFragmentByTag = childFragmentManager.findFragmentByTag(fragmentTag) as RNFragment
        if (findFragmentByTag == null) {
            findFragmentByTag = RNFragment.newInstance()
        }
        childFragmentManager.beginTransaction()
                .replace(R.id.fl_container,findFragmentByTag, fragmentTag)
                .commitAllowingStateLoss()
    }
}