package com.electrolytej.main.page

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.blankj.utilcode.util.ActivityUtils
import com.electrolytej.main.R
import com.google.jetstream.TvMainActivity
import kotlin.random.Random

/**
 * Loads [MainFragment].
 */
class MainActivity2 : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val nextInt = Random(10).nextInt()
//        if (nextInt % 2 == 0) {
//        setContentView(R.layout.activity_main2)
//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.main_browse_fragment, MainFragment()).commitNow()
//        }
//        } else {
            ActivityUtils.startActivity(this, TvMainActivity::class.java)
//        }
        finish()
    }
}