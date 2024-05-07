package com.jamesfchen.myhome.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.jamesfchen.myhome.R
import com.jamesfchen.myhome.databinding.FragmentScreenBinding


class MainScreenFragment : Fragment() {
    lateinit var binding: FragmentScreenBinding
    val navController by lazy {
//        binding.fragmentNavHost.findFragment<NavHostFragment>().navController
        (childFragmentManager.findFragmentById(R.id.fragment_nav_host) as NavHostFragment).navController
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
//        binding.bnv.setupWithNavController(navController)

        binding.bnv.setOnItemSelectedListener { item ->
            return@setOnItemSelectedListener when (item.itemId) {
                R.id.dest_home -> {
                    //                navController.navigate(R.id.action_step_two)
                    //                navController.navigate(R.id.fragment_flow_step_two_dest)
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