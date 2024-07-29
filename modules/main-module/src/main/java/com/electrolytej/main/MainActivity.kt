package com.electrolytej.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.blankj.utilcode.util.SPUtils
import com.electrolytej.network.Crypto
import com.electrolytej.main.databinding.ActivityMainBinding

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
        if (!SPUtils.getInstance().getBoolean(Constants.KEY_WELCOMNE_SPLASH)) {
            navGraph.setStartDestination(R.id.dest_welcome_splash)
        } else if (!SPUtils.getInstance().getBoolean(Constants.KEY_AD_SPLASH)) {
            navGraph.setStartDestination(R.id.dest_ad_splash)
        } else {
            navGraph.setStartDestination(R.id.dest_blank_splash)
        }
        navController.graph = navGraph
        binding.bnv.setupWithNavController(navController)
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

}