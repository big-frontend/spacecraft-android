package com.jamesfchen.myhome.screen.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.jamesfchen.myhome.R
import com.jamesfchen.myhome.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    lateinit var binding:FragmentProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenResumed {
            binding.btSettings.setOnClickListener {
//                findNavController().navigate(directions)
                findNavController().navigate(R.id.action_settings)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }


}