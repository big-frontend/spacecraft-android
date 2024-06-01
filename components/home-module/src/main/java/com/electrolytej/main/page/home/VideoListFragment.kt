package com.electrolytej.main.page.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.electrolytej.main.R
import com.electrolytej.main.databinding.FragmentVideoListBinding

class VideoListFragment : Fragment() {
    lateinit var binding: FragmentVideoListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bt.setOnClickListener {
            val bundle = Bundle();
            bundle.putString("url", "https://www.baidu.com");
            findNavController().navigate(R.id.action_webview)
        }
    }
}