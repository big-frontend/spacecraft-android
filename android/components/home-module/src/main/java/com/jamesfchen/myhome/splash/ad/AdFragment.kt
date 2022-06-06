package com.jamesfchen.myhome.splash.ad

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jamesfchen.myhome.R

class AdFragment : Fragment() {

    companion object {
        fun newInstance() = AdFragment()
    }

    private lateinit var viewModel: AdViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.ad_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AdViewModel::class.java)
        // TODO: Use the ViewModel
    }

}