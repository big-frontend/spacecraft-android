package com.hawksjamesf.common.mvvm

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.text.PrecomputedTextCompat
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Sep/19/2019  Thu
 */
class MyFragment: Fragment() {
    init { // Notice that we can safely launch in the constructor of the Fragment.
        viewLifecycleOwner.lifecycleScope.launch {
            whenStarted {
                // The block inside will run only when Lifecycle is at least STARTED.
                // It will start executing when fragment is started and
                // can call other suspend methods.
//                loadingView.visibility = View.VISIBLE
                val canAccess = withContext(Dispatchers.IO) {
//                    checkUserAccess()
                    false
                }

                // When checkUserAccess returns, the next line is automatically
                // suspended if the Lifecycle is not *at least* STARTED.
                // We could safely run fragment transactions because we know the
                // code won't run unless the lifecycle is at least STARTED.
//                loadingView.visibility = View.GONE
                if (canAccess == false) {
//                    findNavController().popBackStack()
                } else {
//                    showContent()
                }
            }

            // This line runs only after the whenStarted block above has completed.

        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            try {
                // Call some suspend functions.
            } finally {
                // This line might execute after Lifecycle is DESTROYED.
                if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                    // Here, since we've checked, it is safe to run any
                    // Fragment transactions.
                }
            }
        }
    }
    lateinit var textView :TextView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val longTextContent=""

        viewLifecycleOwner.lifecycleScope.launch {
            val params = TextViewCompat.getTextMetricsParams(textView)
            val precomputedText = withContext(Dispatchers.Default) {
                PrecomputedTextCompat.create(longTextContent, params)
            }
            TextViewCompat.setPrecomputedText(textView, precomputedText)
        }
    }
}