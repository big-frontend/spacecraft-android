package com.jamesfchen.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.jamesfchen.main.databinding.ActivityMainBinding

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        binding.wxPay.setOnClickListener{
            startActivity(Intent(this@MainActivity,net.sourceforge.simcpux.MainActivity::class.java))
        }
    }
}