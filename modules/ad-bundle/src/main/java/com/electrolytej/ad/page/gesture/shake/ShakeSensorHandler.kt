package com.electrolytej.ad.page.gesture.shake

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.opengl.Matrix
import android.util.Log
import androidx.annotation.WorkerThread
import com.electrolytej.sensor.ISensorHandler
import getOrientation
import kotlin.math.acos
import kotlin.math.sin
import kotlin.math.sqrt

class ShakeSensorHandler : ISensorHandler {
    companion object {
        private const val TAG = "ShakeSensorHandler"
    }

    private var baseLineQuaternion: FloatArray? = null
    private val rotationValues = FloatArray(9)
    private val accelerometerValues = FloatArray(3)
    private val magneticValues = FloatArray(3)
    private val resultValues = DoubleArray(9)
    private var onShakeListener: OnShakeListener? = null

    // 用于存储初始和当前的 4x4 旋转矩阵
    private val rotMatStart = FloatArray(16)
    private val rotMatCurrent = FloatArray(16)

    // 标记是否已记录“起始”姿态
    private var hasStartPose = false

    private var referenceQuaternion = floatArrayOf(1f, 0f, 0f, 0f)
    private var currentQuaternion = floatArrayOf(1f, 0f, 0f, 0f)

    // 存储 “起始” 和 “当前” 的 4×4 旋转矩阵
    private val angleChange = FloatArray(3)
    private val currentRationMatrix = FloatArray(9)
    private var lastRotationMatrix: FloatArray? = null
    private var hasStart = false

    // 存储“上一次”与“当前”4×4 旋转矩阵
    private val prevMat = FloatArray(16)
    private val curMat = FloatArray(16)
    private var hasPrev = false
    override fun sensors(): Set<Int> {
        return setOf(
            Sensor.TYPE_LINEAR_ACCELERATION,
//            Sensor.TYPE_ACCELEROMETER,
            Sensor.TYPE_MAGNETIC_FIELD,
            Sensor.TYPE_ROTATION_VECTOR,
//            Sensor.TYPE_GAME_ROTATION_VECTOR,
//        Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR,
        )
    }

    fun setOnShakeListener(onShakeListener: OnShakeListener) {
        this.onShakeListener = onShakeListener
    }

    interface OnShakeListener {
        @WorkerThread
        fun onTrace(resultValues: DoubleArray)

        @WorkerThread
        fun onShake(p: String?) {
        }
//            void onShake(float ax1, float ay1, float az1,
//                             float degreeDx1, float degreeDy1, float degreeDz1, float ax2, float ay2, float az2,
//                             float degreeDx2, float degreeDy2, float degreeDz2,long delta);
    }


    override fun onSensorChanged(sensorEvent: SensorEvent) {
        when (sensorEvent.sensor?.type) {
            Sensor.TYPE_ACCELEROMETER, Sensor.TYPE_LINEAR_ACCELERATION -> {
              sensorEvent.values.copyInto(accelerometerValues)
                val (ax, ay, az) = accelerometerValues
                resultValues[0] = ax.toDouble()
                resultValues[1] = ay.toDouble()
                resultValues[2] = az.toDouble()
//            if (abs(ax) > 10 || abs(ay) > 10 || abs(az) > 10) {
//                Log.d(TAG, "xyz加速度: ${ax} ${ay} ${az}")
//            }
            }

            Sensor.TYPE_MAGNETIC_FIELD -> {
                sensorEvent.values.copyInto(magneticValues)
            }


            Sensor.TYPE_ROTATION_VECTOR -> {
                sensorEvent.values?.copyInto(rotationValues)
                val (azimuth, pitch, roll) = getOrientation(rotationValues)
                resultValues[3] = pitch
                resultValues[4] = roll
                resultValues[5] = azimuth

//                val quaternion = FloatArray(4)
//                SensorManager.getQuaternionFromVector(quaternion, rotationValues)
//                if (baseLineQuaternion == null) {
//                    baseLineQuaternion = quaternion.copyOf()
//                }
//                baseLineQuaternion?.let {
//                    val (deltaX, deltaY, deltaZ) = quaternion.minus(it)
//                    resultValues[3] = deltaX
//                    resultValues[4] = deltaY
//                    resultValues[5] = deltaZ
//                Log.d(TAG, "旋转传感器计算出xyz角度: ${degreeX}/${degreeY}/${degreeZ}")


//                SensorManager.getRotationMatrixFromVector(currentRationMatrix, sensorEvent.values)
//                if (lastRotationMatrix == null) {
//                    lastRotationMatrix = FloatArray(9)
//                    currentRationMatrix.copyInto(lastRotationMatrix!!)
//                } else {
//                    SensorManager.getAngleChange(
//                        angleChange, currentRationMatrix, lastRotationMatrix
//                    )
////                    angleChangeSum[0] += angleChange[0].toDegrees()
////                    angleChangeSum[1] += angleChange[1].toDegrees()
////                    angleChangeSum[2] += angleChange[2].toDegrees()
//                    resultValues[3] = angleChange[1].toDegrees()
//                    resultValues[4] = angleChange[2].toDegrees()
//                    resultValues[5] = angleChange[0].toDegrees()
//                }


//                SensorManager.getRotationMatrixFromVector(currentRationMatrix, sensorEvent.values)
//                val (axis, theta) = rotationMatrixToAxisAngle(currentRationMatrix)
//                resultValues[3] = angleChange[1].toDegrees()
//                resultValues[4] = angleChange[2].toDegrees()
//                resultValues[5] = angleChange[0].toDegrees()
            }

        }

        val (azimuth, pitch, roll) = getOrientation(accelerometerValues, magneticValues)
        resultValues[6] = pitch
        resultValues[7] = roll
        resultValues[8] = azimuth
        onShakeListener?.onTrace(resultValues)
    }

    private val modelMatrix = FloatArray(16)
    private fun calculateModelMatrix() {
        // 获取相对旋转的四元数
        val relativeQ = getRelativeQuaternion(referenceQuaternion, currentQuaternion)

        // 转换为旋转矩阵
        Matrix.setIdentityM(modelMatrix, 0)
        SensorManager.getRotationMatrixFromVector(modelMatrix, quaternionToVector(relativeQ))

        // 计算各轴角度差
        calculateAxisAngles(relativeQ)
    }

    private fun quaternionToVector(q: FloatArray): FloatArray {
        return floatArrayOf(q[1], q[2], q[3], q[0])
    }

    private fun normalizeQuaternion(q: FloatArray) {
        val norm = sqrt(q[0] * q[0] + q[1] * q[1] + q[2] * q[2] + q[3] * q[3])
        if (norm != 0f) {
            q[0] /= norm
            q[1] /= norm
            q[2] /= norm
            q[3] /= norm
        }
    }

    // region 四元数运算
    private fun getRelativeQuaternion(q1: FloatArray, q2: FloatArray): FloatArray {
        // q_rel = q2 * q1⁻¹
        val q1Conj = floatArrayOf(q1[0], -q1[1], -q1[2], -q1[3])
        return floatArrayOf(
            q1Conj[0] * q2[0] - q1Conj[1] * q2[1] - q1Conj[2] * q2[2] - q1Conj[3] * q2[3],
            q1Conj[0] * q2[1] + q1Conj[1] * q2[0] + q1Conj[2] * q2[3] - q1Conj[3] * q2[2],
            q1Conj[0] * q2[2] - q1Conj[1] * q2[3] + q1Conj[2] * q2[0] + q1Conj[3] * q2[1],
            q1Conj[0] * q2[3] + q1Conj[1] * q2[2] - q1Conj[2] * q2[1] + q1Conj[3] * q2[0]
        ).apply { normalizeQuaternion(this) }
    }

    var deltaAngles = floatArrayOf(0f, 0f, 0f)
    private fun calculateAxisAngles(q: FloatArray) {
        // 方法1：直接从小角度近似计算
        deltaAngles[0] = 2 * Math.toDegrees(q[1].toDouble()).toFloat() // X轴
        deltaAngles[1] = 2 * Math.toDegrees(q[2].toDouble()).toFloat() // Y轴
        deltaAngles[2] = 2 * Math.toDegrees(q[3].toDouble()).toFloat() // Z轴

        // 方法2：通过轴角计算（更准确）
        val angle = 2 * acos(q[0])
        if (angle > 1e-6) {
            val axis = floatArrayOf(
                q[1] / sin(angle / 2), q[2] / sin(angle / 2), q[3] / sin(angle / 2)
            )
            val totalAngle = Math.toDegrees(angle.toDouble()).toFloat()
            deltaAngles[0] = axis[0] * totalAngle
            deltaAngles[1] = axis[1] * totalAngle
            deltaAngles[2] = axis[2] * totalAngle

        }
    }


    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        Log.d(TAG, "传感器精度变化: ${sensor.name} ${accuracy}")
        if (accuracy == SensorManager.SENSOR_STATUS_ACCURACY_HIGH) {
            // 传感器已校准
        }
    }

}