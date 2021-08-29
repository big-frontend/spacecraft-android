package com.jamesfchen.myhome.screen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.jamesfchen.myhome.R
import com.jamesfchen.myhome.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val navController by lazy {
//        binding.fragmentNavHost.findFragment<NavHostFragment>().navController
        (supportFragmentManager.findFragmentById(R.id.fragment_nav_host) as NavHostFragment).navController
    }
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        //        Fragment fragment = WebViewFragment.newInstance("https://i.meituan.com/c/ZDg0Y2FhNjMt");
//        FragmentUtils.add(getSupportFragmentManager(),fragment,android.R.id.content);
//        FragmentUtils.show(fragment);
        WebViewActivity.startActivity(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
//        Uri uri = Uri.parse("https://i.spacecraft.com/c/ZDg0Y2FhNjMt");
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        Intent intent =  new Intent();
//        intent.addCategory(Intent.CATEGORY_BROWSABLE);
//        intent.setData(uri);
//        Intent intent = new Intent(Intent.CATEGORY_APP_BROWSER,uri);
//        startActivity(intent);
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bnv.setupWithNavController(navController)
//        binding.bnv.setOnItemSelectedListener { item->
//            when (item.itemId) {
//                R.id.dest_home -> {,
//        //                navController.navigate(R.id.action_step_two)
//        //                navController.navigate(R.id.fragment_flow_step_two_dest)
//
//                    return@setOnItemSelectedListener true
//                }
//                R.id.dest_dashboard -> {
//                    return@setOnItemSelectedListener true
//                }
//                R.id.dest_notifications -> {
//                    return@setOnItemSelectedListener true
//                }
//                R.id.dest_profile -> {
//                    navController.navigate(R.id.dest_profile)
//                    return@setOnItemSelectedListener true
//                }
//                else -> false
//            }
//        }
    }
}