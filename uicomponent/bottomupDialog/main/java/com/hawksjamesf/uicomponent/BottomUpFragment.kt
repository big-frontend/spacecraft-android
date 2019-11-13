package com.hawksjamesf.uicomponent

import android.os.Bundle
import androidx.fragment.app.Fragment

class BottomUpFragment : Fragment() {
    fun newInstance(): BottomUpFragment {
        val args = Bundle()
        val fragment = BottomUpFragment()
        fragment.arguments = args
        return fragment
    }
}