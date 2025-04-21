package com.electrolytej.bundle2.page

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.electrolytej.bundle2.R
import com.electrolytej.bundle2.databinding.ActivityBundle2Binding
import java.io.File

class Bundle2Activity : AppCompatActivity() {
    companion object {
        private const val TAG = "Bundle2Activity"
        private const val REQUEST_CODE_PERMISSIONS = 101
    }

    val navCtrl: NavController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return@lazy navHostFragment.navController
    }
    val binding: ActivityBundle2Binding by lazy {
        return@lazy ActivityBundle2Binding.inflate(layoutInflater)
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
//        startActivity(Intent(Bundle2Activity@ this, PagerViewActivity::class.java))
//        startActivity(Intent(Bundle2Activity@this, NestedScrollActivity::class.java))
        //        startActivity(new Intent(this, Bundle1Activity.class));
//        finish()
        setContentView(binding.root)
        binding.bt.setOnClickListener {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS), REQUEST_CODE_PERMISSIONS
            )
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onSupportNavigateUp(): Boolean {
        return navCtrl.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (REQUEST_CODE_PERMISSIONS == requestCode) {
            if ((ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED)
            ) {
                Log.d(TAG, "granted")
            }
        }
    }
}