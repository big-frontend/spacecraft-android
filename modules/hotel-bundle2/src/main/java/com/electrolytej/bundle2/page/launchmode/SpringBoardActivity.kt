package com.electrolytej.bundle2.page.launchmode

import android.app.Activity
import android.os.Bundle
import android.os.Debug
import com.facebook.device.yearclass.YearClass
import com.electrolytej.bundle2.databinding.ActivitySpringBoardBinding

class SpringBoardActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySpringBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btSingleTaskIntent.setOnClickListener {
            Jump2SingleTaskInIntentActivity.startActivity(this@SpringBoardActivity)
        }
        binding.btSingleTaskManifest.setOnClickListener {
            Jump2SingleTaskManifestActivity.startActivity(this@SpringBoardActivity)
        }
        binding.btNewTaskIntent.setOnClickListener {
            NewTaskActivity.startActivity(this@SpringBoardActivity)
        }
        binding.btSingleInstanceManifest.setOnClickListener {
            Jump2SingleInstanceManifestActivity.startActivity(this@SpringBoardActivity)

        }
        val year = YearClass.get(applicationContext)
        if (year >= 2013) {
            // Do advanced animation
        } else if (year > 2010) {
            // Do simple animation
        } else {
            // Phone too slow, don't do any animations
        }
        Debug.startAllocCounting()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
    }

}