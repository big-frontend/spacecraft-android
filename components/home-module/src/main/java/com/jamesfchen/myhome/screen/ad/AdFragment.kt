package com.jamesfchen.myhome.screen.ad

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jamesfchen.util.BarUtil
import com.jamesfchen.myhome.R
import com.jamesfchen.myhome.databinding.FragmentAdBinding

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
        binding.bt.setOnClickListener {
            BarUtil.setBarsFullscreen(requireActivity(), BarUtil.IMMERSIVE_STICKY)
            findNavController().navigate(R.id.action_screen)
        }
    }

}