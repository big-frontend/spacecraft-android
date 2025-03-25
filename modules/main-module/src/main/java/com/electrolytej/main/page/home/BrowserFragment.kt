package com.electrolytej.main.page.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
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
        val  options = RequestOptions()
        options.format(DecodeFormat.PREFER_RGB_565)
        Glide.with(this)
            .load("https://nb-ssp.oss-cn-beijing.aliyuncs.com/1648699553998828f6f50-79c1-11ec-92c0-01612323b7a1.png")
            .apply(options)
            .into(binding.ivP)
    }
}