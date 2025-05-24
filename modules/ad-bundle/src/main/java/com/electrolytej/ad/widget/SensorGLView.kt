package com.electrolytej.ad.widget

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.util.AttributeSet
import com.electrolytej.ad.page.shake.sensor.ShakeSensorHandler.OnShakeListener
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import kotlin.math.acos
import kotlin.math.sin
import kotlin.math.sqrt

class SensorGLView(context: Context, attrs: AttributeSet?) : GLSurfaceView(context, attrs),
    SensorEventListener {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    // 四元数数据
    private var referenceQuaternion = floatArrayOf(1f, 0f, 0f, 0f)
    private var currentQuaternion = floatArrayOf(1f, 0f, 0f, 0f)

    // 角度差存储
    var deltaAngles = doubleArrayOf(0.0, 0.0, 0.0)
        private set
    private val modelMatrix = FloatArray(16)
    init {
        setupGLEnvironment()
        Matrix.setIdentityM(modelMatrix, 0)
    }
    private inner class Renderer : GLSurfaceView.Renderer {
        private lateinit var cube: Cube
        private val projectionMatrix = FloatArray(16)
        private val viewMatrix = FloatArray(16).apply {
            Matrix.setLookAtM(this, 0,
                0f, 0f, 3f,   // 相机位置
                0f, 0f, 0f,   // 观察点
                0f, 1f, 0f    // 上方向
            )
        }

        private val mvpMatrix = FloatArray(16)
        init {

        }
        override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
            GLES30.glEnable(GLES30.GL_DEPTH_TEST)
            cube = Cube().apply { init() }
        }

        override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
            GLES30.glViewport(0, 0, width, height)
            Matrix.perspectiveM(projectionMatrix, 0, 45f, width.toFloat()/height, 0.1f, 100f)
        }

        override fun onDrawFrame(gl: GL10?) {
            GLES30.glClearColor(0.1f, 0.1f, 0.1f, 1f)
            GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT or GLES30.GL_DEPTH_BUFFER_BIT)

            // 计算模型矩阵
            calculateModelMatrix()

            // 矩阵计算
            Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, modelMatrix, 0)
            Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0)

            // 绘制立方体
            cube.draw(mvpMatrix)
            onShakeListener?.onTrace(deltaAngles)
        }

        private fun calculateModelMatrix() {
            // 获取相对旋转的四元数
            val relativeQ = getRelativeQuaternion(referenceQuaternion, currentQuaternion)

            // 转换为旋转矩阵
            Matrix.setIdentityM(modelMatrix, 0)
            SensorManager.getRotationMatrixFromVector(modelMatrix, quaternionToVector(relativeQ))

            // 计算各轴角度差
            calculateAxisAngles(relativeQ)
        }
    }

    private fun setupGLEnvironment() {
        setEGLContextClientVersion(3)
        setRenderer(Renderer())
        renderMode = RENDERMODE_CONTINUOUSLY
    }

    // region 传感器处理
    override fun onSensorChanged(event: SensorEvent) {
        when (event.sensor.type) {
            Sensor.TYPE_ROTATION_VECTOR -> {
                // 获取当前四元数
                SensorManager.getQuaternionFromVector(currentQuaternion, event.values)
                normalizeQuaternion(currentQuaternion)
            }
        }
    }

    fun resetReference() {
        referenceQuaternion = currentQuaternion.copyOf()
    }
    // endregion

    // region 四元数运算
    private fun getRelativeQuaternion(q1: FloatArray, q2: FloatArray): FloatArray {
        // q_rel = q2 * q1⁻¹
        val q1Conj = floatArrayOf(q1[0], -q1[1], -q1[2], -q1[3])
        return floatArrayOf(
            q1Conj[0]*q2[0] - q1Conj[1]*q2[1] - q1Conj[2]*q2[2] - q1Conj[3]*q2[3],
            q1Conj[0]*q2[1] + q1Conj[1]*q2[0] + q1Conj[2]*q2[3] - q1Conj[3]*q2[2],
            q1Conj[0]*q2[2] - q1Conj[1]*q2[3] + q1Conj[2]*q2[0] + q1Conj[3]*q2[1],
            q1Conj[0]*q2[3] + q1Conj[1]*q2[2] - q1Conj[2]*q2[1] + q1Conj[3]*q2[0]
        ).apply { normalizeQuaternion(this) }
    }

    private fun calculateAxisAngles(q: FloatArray) {
        // 方法1：直接从小角度近似计算
        deltaAngles[0] = 2 * Math.toDegrees(q[1].toDouble()) // X轴
        deltaAngles[1] = 2 * Math.toDegrees(q[2].toDouble()) // Y轴
        deltaAngles[2] = 2 * Math.toDegrees(q[3].toDouble()) // Z轴

        // 方法2：通过轴角计算（更准确）
        val angle = 2 * acos(q[0])
        if (angle > 1e-6) {
            val axis = floatArrayOf(
                q[1] / sin(angle/2),
                q[2] / sin(angle/2),
                q[3] / sin(angle/2)
            )
            val totalAngle = Math.toDegrees(angle.toDouble())
            deltaAngles[0] = axis[0] * totalAngle
            deltaAngles[1] = axis[1] * totalAngle
            deltaAngles[2] = axis[2] * totalAngle

        }
    }

    private fun quaternionToVector(q: FloatArray): FloatArray {
        return floatArrayOf(q[1], q[2], q[3], q[0])
    }

    private fun normalizeQuaternion(q: FloatArray) {
        val norm = sqrt(q[0]*q[0] + q[1]*q[1] + q[2]*q[2] + q[3]*q[3])
        if (norm != 0f) {
            q[0] /= norm
            q[1] /= norm
            q[2] /= norm
            q[3] /= norm
        }
    }
    // endregion

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR),
            SensorManager.SENSOR_DELAY_GAME
        )
    }

    override fun onPause() {
        sensorManager.unregisterListener(this)
        super.onPause()
    }

    // 立方体类（需实现顶点缓冲和绘制逻辑）
    private inner class Cube {
        // 实现顶点缓冲对象(VBO)和着色器程序
        fun init() { /* 初始化OpenGL资源 */ }
        fun draw(mvpMatrix: FloatArray) { /* 绘制立方体 */ }
    }
    private var onShakeListener: OnShakeListener? = null
    fun setOnShakeListener(onShakeListener: OnShakeListener) {
        this.onShakeListener = onShakeListener
    }
}