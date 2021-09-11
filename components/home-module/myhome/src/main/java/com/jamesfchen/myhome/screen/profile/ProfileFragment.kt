package com.jamesfchen.myhome.screen.profile

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.jamesfchen.myhome.R
import com.jamesfchen.myhome.databinding.FragmentProfileBinding
import com.jamesfchen.myhome.databinding.ViewstubTagSampleBinding

class ProfileFragment : Fragment() {
    lateinit var binding:FragmentProfileBinding
    override fun onAttach(context: Context) {
        super.onAttach(context)
        lifecycleScope.launchWhenCreated {
            Log.d("cjf","launchWhenCreated")
        }
        lifecycleScope.launchWhenStarted {
            Log.d("cjf","launchWhenStarted")

        }
        lifecycleScope.launchWhenResumed {
            Log.d("cjf","launchWhenResumed")
            binding.btSettings.setOnClickListener {
//                findNavController().navigate(directions)
                findNavController().navigate(R.id.action_settings)
            }
            binding.somethingInclude.bt123.text = "123_ad"
            binding.btInflate.setOnClickListener{
                val viewStubBinding = ViewstubTagSampleBinding.bind(binding.somethingViewstub.inflate())
//                viewStubBinding.btViewstub.text
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("cjf","onCreate")

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Log.d("cjf","onCreateView")
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("cjf","onViewCreated")
        super.onViewCreated(view, savedInstanceState)
    }
    override fun onStart() {
        super.onStart()
        Log.d("cjf","onStart")
    }
    override fun onResume() {
        super.onResume()
        Log.d("cjf","onResume")
    }




}