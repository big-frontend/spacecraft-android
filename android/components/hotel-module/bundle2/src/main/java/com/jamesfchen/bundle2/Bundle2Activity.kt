package com.jamesfchen.bundle2

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AlertDialog
import com.jamesfchen.bundle2.kk.PagerViewActivity
import com.jamesfchen.bundle2.launchmode.SpringBoardActivity
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
//        startActivity(Intent(Bundle2Activity@this, CustomViewActivity::class.java))
        startActivity(Intent(Bundle2Activity@this, SpringBoardActivity::class.java))
//        startActivity(Intent(Bundle2Activity@this, OptimizationActivity::class.java))
//        startActivity(Intent(Bundle2Activity@this, PagerViewActivity::class.java))
//        startActivity(Intent(Bundle2Activity@this, NestedScrollActivity::class.java))
        //        startActivity(new Intent(this, Bundle1Activity.class));


    }
}

