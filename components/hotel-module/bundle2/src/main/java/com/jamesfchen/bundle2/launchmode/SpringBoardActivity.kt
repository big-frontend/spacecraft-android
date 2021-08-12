package com.jamesfchen.bundle2.launchmode

import android.app.Activity
import android.os.Bundle
import com.jamesfchen.bundle2.databinding.ActivitySpringBoardBinding

class SpringBoardActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySpringBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btSingleTaskIntent.setOnClickListener{
            Jump2SingleTaskInIntentActivity.startActivity(this@SpringBoardActivity)
        }
        binding.btSingleTaskManifest.setOnClickListener{
            Jump2SingleTaskManifestActivity.startActivity(this@SpringBoardActivity)
        }
        binding.btNewTaskIntent.setOnClickListener{
            NewTaskActivity.startActivity(this@SpringBoardActivity)
        }
        binding.btSingleInstanceManifest.setOnClickListener{
            Jump2SingleInstanceManifestActivity.startActivity(this@SpringBoardActivity)

        }
    }
}