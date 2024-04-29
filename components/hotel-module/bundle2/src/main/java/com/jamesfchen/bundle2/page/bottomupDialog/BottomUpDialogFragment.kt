package com.jamesfchen.bundle2.page.bottomupDialog

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
        fun newInstance(): com.jamesfchen.map.page.bottomupDialog.BottomUpDialogFragment {
            val args = Bundle()

            val fragment = com.jamesfchen.map.page.bottomupDialog.BottomUpDialogFragment()
            fragment.arguments = args
            return fragment
        }

        const val TAG = "BottomUpDialogFragment"
        const val fragmentTag = "${com.jamesfchen.map.page.bottomupDialog.BottomUpDialogFragment.Companion.TAG}_RNFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(com.jamesfchen.map.page.bottomupDialog.BottomUpDialogFragment.Companion.TAG, "onCreate")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Log.d(com.jamesfchen.map.page.bottomupDialog.BottomUpDialogFragment.Companion.TAG, "onCreateDialog")
        return context?.let { com.jamesfchen.map.page.bottomupDialog.BottomUpDialog(it, theme) }
                ?: super.onCreateDialog(savedInstanceState)
    }
    lateinit var binding: DialogBottomUpBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(com.jamesfchen.map.page.bottomupDialog.BottomUpDialogFragment.Companion.TAG, "onCreateView")
        binding = DialogBottomUpBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(com.jamesfchen.map.page.bottomupDialog.BottomUpDialogFragment.Companion.TAG, "onViewCreated:$view")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(com.jamesfchen.map.page.bottomupDialog.BottomUpDialogFragment.Companion.TAG, "onActivityCreated:${view}")
        var findFragmentByTag = childFragmentManager.findFragmentByTag(com.jamesfchen.map.page.bottomupDialog.BottomUpDialogFragment.Companion.fragmentTag) as com.jamesfchen.map.page.bottomupDialog.RNFragment
        if (findFragmentByTag == null) {
            findFragmentByTag =
                com.jamesfchen.map.page.bottomupDialog.RNFragment.Companion.newInstance()
        }
        childFragmentManager.beginTransaction()
                .replace(R.id.fl_container,findFragmentByTag,
                    com.jamesfchen.map.page.bottomupDialog.BottomUpDialogFragment.Companion.fragmentTag
                )
                .commitAllowingStateLoss()
    }
}