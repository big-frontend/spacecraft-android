package com.electrolytej.main.page.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.electrolytej.main.R
import com.electrolytej.main.databinding.FragmentBrowserBinding

class BrowserFragment : Fragment() {
    lateinit var binding: FragmentBrowserBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBrowserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bt.setOnClickListener {
            findNavController().navigate(R.id.action_search)
        }
    }
}