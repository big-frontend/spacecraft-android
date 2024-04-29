package com.jamesfchen.bundle2.page

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.jamesfchen.bundle2.R
import com.jamesfchen.bundle2.page.kk.PagerViewActivity
import java.io.File

class Bundle2Activity : AppCompatActivity() {
    val navCtrl: NavController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return@lazy navHostFragment.navController
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val file = File("/data/local/tmp/uidump.xml")
//        if (file.exists()) {
//            file.inputStream().use { fis ->
//                val content = fis.reader().readText()
//                Log.d("cjf", "content:${content}")
//            }
//        }
//        startActivity(Intent(Bundle2Activity@this, ConstraintPerformanceActivity::class.java))
//        startActivity(Intent(Bundle2Activity@this, CustomViewActivity::class.java))
//        startActivity(Intent(Bundle2Activity@this, SpringBoardActivity::class.java))
//        startActivity(Intent(Bundle2Activity@this, OptimizationActivity::class.java))
        startActivity(Intent(Bundle2Activity@ this, PagerViewActivity::class.java))
//        startActivity(Intent(Bundle2Activity@this, NestedScrollActivity::class.java))
        //        startActivity(new Intent(this, Bundle1Activity.class));
        finish()

    }
    override fun onSupportNavigateUp(): Boolean {
        return navCtrl.navigateUp() || super.onSupportNavigateUp()
    }
}