package com.electrolytej.main

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.blankj.utilcode.util.SPUtils
import com.bumptech.glide.Glide
import com.electrolytej.main.databinding.ActivityMainBinding
import com.electrolytej.main.util.BadgeUtils
import com.electrolytej.util.CryptoUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.wait
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    val navController by lazy {
//        binding.fragmentNavHost.findFragment<NavHostFragment>().navController
        (supportFragmentManager.findFragmentById(R.id.fragment_nav_host) as NavHostFragment).navController
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
//        navController.handleDeepLink(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //native  crash
//        Crypto.getClientKey("", 0L)
        val navGraph = navController.navInflater.inflate(R.navigation.nav_main)
        navController.graph = navGraph
        binding.bnv.setupWithNavController(navController)
        if (!SPUtils.getInstance().getBoolean(Constants.KEY_WELCOME_SPLASH)) {//欢迎页 --> 首页
            navGraph.setStartDestination(R.id.dest_welcome)
        } else {//闪屏页 --> 广告 --> 首页
            navGraph.setStartDestination(R.id.dest_splash)
            navController.navigate(R.id.dest_home)
            lifecycleScope.launch(Dispatchers.IO) {
                delay(1000)
                jump2Ad()
            }
            !SPUtils.getInstance().getBoolean(Constants.KEY_AD_SPLASH)
        }
        BadgeUtils.setCount(1,this)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            Log.d(
                TAG,
                "addOnDestinationChangedListener ${destination} ${destination.id} ${destination.route} ${destination.navigatorName} ${destination.displayName}"
            )
            if (destination.id == R.id.dest_home) binding.bnv.visibility = View.VISIBLE

        }
        binding.bnv.setOnItemSelectedListener { item ->
            return@setOnItemSelectedListener when (item.itemId) {
                R.id.dest_home -> {
                    navController.navigate(R.id.dest_home)
                    true
                }

                R.id.dest_feeds -> {
                    navController.navigate(R.id.dest_new_feeds)
                    true
                }

                R.id.dest_infos -> {
                    navController.navigate(R.id.dest_infos)
                    true
                }

                R.id.dest_profile -> {
                    navController.navigate(R.id.dest_profile)
                    true
                }

                R.id.dest_bundle2 -> {
                    navController.navigate(R.id.dest_bundle2)
                    true
                }

                else -> false
            }
        }
    }

    private suspend fun jump2Ad() {
        return withContext(Dispatchers.Main) {
            navController.navigate(R.id.dest_ad)
        }
    }
}