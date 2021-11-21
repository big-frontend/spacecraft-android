package com.jamesfchen.bundle2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.jamesfchen.bundle2.customview.CustomViewActivity
import java.io.File

class Bundle2Activity : ComponentActivity() {
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
        startActivity(Intent(Bundle2Activity@this, CustomViewActivity::class.java))
//        startActivity(Intent(Bundle2Activity@this, SpringBoardActivity::class.java))
//        startActivity(Intent(Bundle2Activity@this, OptimizationActivity::class.java))
//        startActivity(Intent(Bundle2Activity@this, PagerViewActivity::class.java))
//        startActivity(Intent(Bundle2Activity@this, NestedScrollActivity::class.java))
    }
}

