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
import kotlin.math.sqrt

class SensorActivity : AppCompatActivity(), ISensorHandler {
    private lateinit var sensorGLView: CubeView
    lateinit var rationChart: LineChartView
    lateinit var angleSpeedChart: LineChartView
    lateinit var accelerometerChart: LineChartView

    override fun sensors() = setOf(
        Sensor.TYPE_ROTATION_VECTOR,
//        Sensor.TYPE_GAME_ROTATION_VECTOR,
//        Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR,
        Sensor.TYPE_GYROSCOPE,
        Sensor.TYPE_LINEAR_ACCELERATION,
    )

    private var startTime = 0L
    private var baseAngle = 0.0

    private val rotationValues = FloatArray(9)

    private val mDetector: SensorDetector by lazy {
        val d = SensorDetector(this)
        d.samplingPeriodUs = 20_000
        return@lazy d
    }
    private var phase: Phase = Phase.IDLE
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor)
        sensorGLView = findViewById(R.id.sgl_gl)
        rationChart = findViewById(R.id.rationChart)
        angleSpeedChart = findViewById(R.id.angleSpeedChart)
        accelerometerChart = findViewById(R.id.accelerometerChart)
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
        if (phase == Phase.STOPPED) return
        val endTime = System.currentTimeMillis() - startTime
        var angleSpeed = 0.0
        var a = 0.0
        var angle = 0.0
        when (sensorEvent.sensor.type) {
            Sensor.TYPE_ROTATION_VECTOR, Sensor.TYPE_GAME_ROTATION_VECTOR -> {
                sensorEvent.values?.copyInto(rotationValues)
                val (azimuth, pitch, roll) = getOrientation(rotationValues)
                angle = sqrt(pitch * pitch + roll * roll + azimuth * azimuth).toDouble()
                rationChart.addDataPoint(
                    LineChartView.DataPoint(
                        endTime,
                        pitch,
                        roll,
                        azimuth,
                        angle
                    )
                )
                sensorGLView.updateModelMatrix(sensorEvent.values)
            }

            Sensor.TYPE_GYROSCOPE -> {
                // 陀螺仪：单位 rad/s（角速度）
                val values = sensorEvent.values ?: return
                val gx = values.getOrNull(0) ?: return
                val gy = values.getOrNull(1) ?: return
                val gz = values.getOrNull(2) ?: return
                angleSpeed = sqrt(gx * gx + gy * gy + gz * gz).toDouble()
                angleSpeedChart.addDataPoint(
                    LineChartView.DataPoint(
                        endTime,
                        gx.toDouble(),
                        gy.toDouble(),
                        gz.toDouble(),
                        angleSpeed
                    )
                )
            }

            Sensor.TYPE_LINEAR_ACCELERATION -> {
                // 线性加速度：单位 m/s²（去重力加速度后的加速度）
                val values = sensorEvent.values ?: return
                val ax = values.getOrNull(0) ?: return
                val ay = values.getOrNull(1) ?: return
                val az = values.getOrNull(2) ?: return
                a = sqrt(ax * ax + ay * ay + az * az).toDouble()
                accelerometerChart.addDataPoint(
                    LineChartView.DataPoint(
                        endTime,
                        ax.toDouble(), ay.toDouble(), az.toDouble(),
                        a
                    )
                )
            }
        }


        when (phase) {
            Phase.IDLE -> {
                baseAngle = angle
                phase = Phase.ROTATING
            }

            Phase.ROTATING -> {
                val delta = angle - baseAngle
            }

            Phase.PEAK_RECORDED -> {

            }

            Phase.STOPPED -> Unit
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
