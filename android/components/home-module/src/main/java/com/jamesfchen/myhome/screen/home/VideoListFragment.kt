package com.jamesfchen.myhome.screen.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jamesfchen.myhome.databinding.FragmentVideoListBinding

class VideoListFragment : Fragment()  {
    lateinit var binding: FragmentVideoListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoListBinding.inflate(inflater, container, false)
        return binding.root
    }
}