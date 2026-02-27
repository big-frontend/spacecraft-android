package com.electrolytej.ad.page.gesture

import android.graphics.PixelFormat
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.electrolytej.ad.R
import com.electrolytej.ad.util.MatrixHelper
import com.electrolytej.ad.widget.CubeView
import com.electrolytej.sensor.ISensorHandler
import com.electrolytej.sensor.SensorDetector
import com.electrolytej.widget.LineChartView
import getOrientation

class SensorActivity : AppCompatActivity(), ISensorHandler {
    private lateinit var sensorGLView: CubeView
    lateinit var rationChart: LineChartView
    lateinit var angleSpeedChart: LineChartView
    override fun sensors() = setOf(
        Sensor.TYPE_GYROSCOPE,
        Sensor.TYPE_ROTATION_VECTOR,
//        Sensor.TYPE_GAME_ROTATION_VECTOR,
//        Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR,
    )

    private var startTime = 0L

    private val rotationValues = FloatArray(9)

    // 缓存 rotation-vector 角度，给其他分支/展示复用（避免未定义变量）
    private var lastAzimuth: Double = 0.0
    private var lastPitch: Double = 0.0
    private var lastRoll: Double = 0.0
    private val mDetector: SensorDetector by lazy {
        val d = SensorDetector(this)
        d.samplingPeriodUs = 20_000
        return@lazy d
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor)
        sensorGLView = findViewById(R.id.sgl_gl)
        rationChart = findViewById(R.id.rationChart)
        angleSpeedChart = findViewById(R.id.angleSpeedChart)
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
        startTime = System.currentTimeMillis()
        mDetector.addHandler(this)
    }


    override fun onSensorChanged(sensorEvent: SensorEvent) {
        val endTime = System.currentTimeMillis() - startTime
        when (sensorEvent.sensor.type) {
            Sensor.TYPE_ROTATION_VECTOR, Sensor.TYPE_GAME_ROTATION_VECTOR -> {
                sensorEvent.values?.copyInto(rotationValues)
                val (azimuth, pitch, roll) = getOrientation(rotationValues)
                lastAzimuth = azimuth
                lastPitch = pitch
                lastRoll = roll

                rationChart.addDataPoint(LineChartView.DataPoint(endTime, pitch, roll, azimuth))
                sensorGLView.updateModelMatrix(sensorEvent.values)
            }

            Sensor.TYPE_GYROSCOPE -> {
                // 陀螺仪：单位 rad/s（角速度）
                val values = sensorEvent.values ?: return
                val gx = values.getOrNull(0) ?: return
                val gy = values.getOrNull(1) ?: return
                val gz = values.getOrNull(2) ?: return

                // 额外：角速度幅值（更稳定、更有意义）
                val magnitude = kotlin.math.sqrt(gx * gx + gy * gy + gz * gz)
                angleSpeedChart.addDataPoint(LineChartView.DataPoint(endTime, magnitude.toDouble()))
            }
        }
    }


    override fun onResume() {
        super.onResume()
        mDetector.start()
    }

    override fun onPause() {
        super.onPause()
        mDetector.stop()
    }

}
