package com.electrolytej.commerce

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.electrolytej.commerce.databinding.FragmentAdBinding
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
//            findNavController().navigate(R.id.dest_home)
            findNavController().navigateUp()
//            SPUtils.getInstance().put(Constants.KEY_AD_SPLASH, true)
        }
    }
}