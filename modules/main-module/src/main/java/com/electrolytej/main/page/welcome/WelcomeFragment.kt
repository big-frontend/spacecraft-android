package com.electrolytej.main.page.welcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.blankj.utilcode.util.SPUtils
import com.electrolytej.main.Constants
import com.electrolytej.util.BarUtil
import com.electrolytej.main.R
import com.electrolytej.main.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {
    private val viewModel by viewModels<WelcomeViewModel>()
    lateinit var binding: FragmentWelcomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            BarUtil.setBarsFullscreen(requireActivity(), BarUtil.IMMERSIVE_STICKY)
            findNavController().navigate(R.id.dest_home)
            SPUtils.getInstance().put(Constants.KEY_WELCOMNE_SPLASH, true)
        }
    }
}