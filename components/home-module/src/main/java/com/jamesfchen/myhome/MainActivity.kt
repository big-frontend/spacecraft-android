package com.jamesfchen.myhome

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.jamesfchen.myhome.databinding.ActivityMainBinding
import com.jamesfchen.myhome.network.Crypto

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val navController by lazy {
//        binding.fragmentNavHost.findFragment<NavHostFragment>().navController
        (supportFragmentManager.findFragmentById(R.id.fragment_nav_host) as NavHostFragment).navController
    }
//    override fun onNewIntent(intent: Intent?) {
//        super.onNewIntent(intent)
//        //        Fragment fragment = WebViewFragment.newInstance("https://i.meituan.com/c/ZDg0Y2FhNjMt");
////        FragmentUtils.add(getSupportFragmentManager(),fragment,android.R.id.content);
////        FragmentUtils.show(fragment);
//        WebViewActivity.startActivity(this)
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //native  crash
        Crypto.getClientKey("",0L)
    }
}