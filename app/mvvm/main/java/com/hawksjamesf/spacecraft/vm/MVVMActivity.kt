package com.hawksjamesf.spacecraft.vm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.hawksjamesf.spacecraft.R
import com.hawksjamesf.spacecraft.databinding.ActivityMvvmBinding

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Oct/09/2019  Wed
 */
class MVVMActivity : AppCompatActivity() {
    lateinit var binding: ActivityMvvmBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm)
        val viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        binding.viewModel  = viewModel
    }
}