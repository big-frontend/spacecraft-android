package com.jamesfchen.myhome.page.welcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jamesfchen.util.BarUtil
import com.jamesfchen.myhome.R
import com.jamesfchen.myhome.databinding.FragmentWelcomeBinding

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
        binding.bt.setOnClickListener {
            BarUtil.setBarsFullscreen(requireActivity(), BarUtil.IMMERSIVE_STICKY)
            findNavController().navigate(R.id.action_screen)
        }
    }
}