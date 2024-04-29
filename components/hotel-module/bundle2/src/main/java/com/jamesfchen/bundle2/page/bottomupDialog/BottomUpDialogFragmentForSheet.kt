package com.jamesfchen.bundle2.page.bottomupDialog

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jamesfchen.bundle2.databinding.FragmentRnBinding

class BottomUpDialogFragmentForSheet : BottomSheetDialogFragment() {
    companion object {

        @JvmStatic
        fun newInstance(): BottomUpDialogFragmentForSheet {
            val args = Bundle()
            val fragment = BottomUpDialogFragmentForSheet()
            fragment.arguments = args
            return fragment
        }
        const val TAG = "RNFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
    }
    lateinit var  binding: FragmentRnBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Log.d(TAG, "onCreateView")
        binding = FragmentRnBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")
        view.setOnClickListener {
            parentFragmentManager.beginTransaction()
                    .remove(this)
                    .commitAllowingStateLoss()

        }
        repeat(20) {
            val textView = TextView(view.context)
            textView.text = "asdfasfsafaf"
            val divider=View(view.context)
            divider.setBackgroundColor(Color.RED)
            binding.llContainer.addView(divider,ViewGroup.LayoutParams.MATCH_PARENT,2)
            binding.llContainer.addView(textView, ViewGroup.LayoutParams.MATCH_PARENT, 400)
        }
    }
//    fun show(fragmentManager: FragmentManager, tag: String) {
//        fragmentManager.beginTransaction()
////                .add(android.R.id.content, tag)
//                .add(android.R.id.content,this)
//                .commitAllowingStateLoss()
//    }
}