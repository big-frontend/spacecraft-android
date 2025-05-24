package com.electrolytej.ad.page.shake

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.electrolytej.ad.R
import com.electrolytej.ad.page.shake.sensor.ShakeSensorHandler
import com.electrolytej.ad.widget.SensorGLView

class Shake3Activity : AppCompatActivity() {
    // 在Activity中
    lateinit var sensorView: SensorGLView
    lateinit var btnReset: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shake3)
        sensorView = findViewById<SensorGLView>(R.id.sgl_gl)
        btnReset = findViewById<Button>(R.id.btn_clear)
        // 重置参考系
        btnReset.setOnClickListener {
            sensorView.resetReference()
        }
        // 获取角度差数据
        sensorView.setOnShakeListener(object :
            ShakeSensorHandler.OnShakeListener {
            override fun onTrace(resultValues: DoubleArray) {
//                Log.d(
//                    "Shake3Activity",
//                    "X: ${"%.1f".format(resultValues[0])}° Y: ${"%.1f".format(resultValues[1])}° Z: ${
//                        "%.1f".format(resultValues[2])
//                    }°"
//                )
                Log.d("Shake3Activity", "onTrace: ${resultValues.joinToString(",")}")
            }

        })


    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

}

