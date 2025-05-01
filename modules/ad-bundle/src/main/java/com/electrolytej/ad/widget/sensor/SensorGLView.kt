package com.electrolytej.ad.widget.sensor

import android.content.Context
import android.graphics.PixelFormat
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.util.AttributeSet
import android.util.Log
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class SensorGLView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : GLSurfaceView(context, attrs), SensorEventListener {

    // OpenGL 渲染组件
     val renderer = SensorGLRenderer()

    // 传感器管理
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

    // 旋转矩阵数据
    private val rotationMatrix = FloatArray(16).apply { Matrix.setIdentityM(this, 0) }
    private val displayRotationMatrix = FloatArray(16)

    init {
        setupGLEnvironment()
    }

    private fun setupGLEnvironment() {
        setEGLContextClientVersion(3)
        setEGLConfigChooser(8, 8, 8, 8, 16, 0)
        holder.setFormat(PixelFormat.TRANSLUCENT)
        setRenderer(renderer)
        renderMode = RENDERMODE_CONTINUOUSLY
        preserveEGLContextOnPause = true
    }

    override fun onSensorChanged(event: SensorEvent) {
        when (event.sensor.type) {
            Sensor.TYPE_ROTATION_VECTOR -> {
                SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)
                adjustCoordinateSystem()
                renderer.updateModelMatrix(displayRotationMatrix)
            }
        }
    }

    private fun adjustCoordinateSystem() {
        // 适配屏幕方向（示例为竖屏模式）
        SensorManager.remapCoordinateSystem(
            rotationMatrix,
            SensorManager.AXIS_X,
            SensorManager.AXIS_Z,
            displayRotationMatrix
        )
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onResume() {
        super.onResume()
        rotationSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    override fun onPause() {
        sensorManager.unregisterListener(this)
        super.onPause()
    }

    inner class SensorGLRenderer : Renderer {
        private lateinit var cube: Cube
         val projectionMatrix = FloatArray(16)
        private val viewMatrix = FloatArray(16).apply {
            Matrix.setLookAtM(
                this, 0,
                0f, 0f, 3f,   // 相机位置
                0f, 0f, 0f,   // 观察点
                0f, 1f, 0f    // 上方向
            )
        }
        private val mvpMatrix = FloatArray(16)

        override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
            GLES30.glEnable(GLES30.GL_DEPTH_TEST)
            GLES30.glEnable(GLES30.GL_BLEND)
            GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA, GLES30.GL_ONE_MINUS_SRC_ALPHA)
            cube = Cube().apply { init() }
        }

        override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
            GLES30.glViewport(0, 0, width, height)
            val aspectRatio = width.toFloat() / height
            Matrix.perspectiveM(projectionMatrix, 0, 45f, aspectRatio, 0.1f, 100f)
        }

        override fun onDrawFrame(gl: GL10?) {
            GLES30.glClearColor(0.1f, 0.1f, 0.1f, 1f)
            GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT or GLES30.GL_DEPTH_BUFFER_BIT)

            Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, displayRotationMatrix, 0)
            Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0)

            cube.draw(mvpMatrix)
        }

        fun updateModelMatrix(matrix: FloatArray) {
            System.arraycopy(matrix, 0, displayRotationMatrix, 0, 16)
        }
    }

    companion object {
        private const val TAG = "SensorGLView"

        // 矩阵操作扩展函数
        fun FloatArray.logMatrix(tag: String) {
            val sb = StringBuilder("\n")
            for (i in 0 until 4) {
                sb.append("[")
                for (j in 0 until 4) {
                    sb.append("%.2f".format(this[i * 4 + j])).append("\t")
                }
                sb.append("]\n")
            }
            Log.d(tag, sb.toString())
        }
    }
}