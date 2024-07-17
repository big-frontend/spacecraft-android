package com.electrolytej.main.page.ad

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.PathInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.blankj.utilcode.util.SPUtils
import com.electrolytej.main.Constants
import com.electrolytej.main.R
import com.electrolytej.main.databinding.FragmentAdBinding
import com.electrolytej.util.BarUtil


class AdFragment : Fragment() {

    private val viewModel by viewModels<AdViewModel>()
    lateinit var binding: FragmentAdBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.svShiny.setOnShinyListener {
            BarUtil.setBarsFullscreen(requireActivity(), BarUtil.IMMERSIVE_STICKY)
            findNavController().navigate(R.id.dest_home)
//            SPUtils.getInstance().put(Constants.KEY_AD_SPLASH, true)
        }
    }
}