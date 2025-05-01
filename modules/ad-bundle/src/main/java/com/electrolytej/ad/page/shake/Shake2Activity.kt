package com.electrolytej.ad.page.shake

import android.graphics.PixelFormat
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.electrolytej.ad.util.MatrixHelper
import com.electrolytej.ad.widget.sensor.SensorGLView

class Shake2Activity : AppCompatActivity() {
    private lateinit var sensorGLView: SensorGLView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sensorGLView = SensorGLView(this)
        // 设置背景透明度
        sensorGLView.setZOrderOnTop(true)
        sensorGLView.holder.setFormat(PixelFormat.TRANSLUCENT)
        // 动态调整视野
        sensorGLView.post {
            MatrixHelper.perspectiveM(
                sensorGLView.renderer.projectionMatrix,
                60f,
                sensorGLView.width.toFloat() / sensorGLView.height,
                0.1f,
                100f
            )
        }
        setContentView(
            sensorGLView, ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            )
        )

    }

    override fun onResume() {
        super.onResume()
        sensorGLView.onResume()
    }

    override fun onPause() {
        super.onPause()
        sensorGLView.onPause()
    }

}

