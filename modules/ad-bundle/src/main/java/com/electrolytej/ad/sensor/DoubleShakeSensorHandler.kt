package com.electrolytej.ad.sensor

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.util.Half.EPSILON
import com.electrolytej.sensor.filter.A
import com.electrolytej.sensor.ISensorHandler
import getOrientation
import kotlin.math.asin
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.sqrt

class DoubleShakeSensorHandler : ISensorHandler {
    companion object {
        private const val TAG = "DoubleShakeSensorHandler"
    }

    private val accelerometerValues = FloatArray(3)
    private val magneticValues = FloatArray(3)
    private val gyroscopeValues = FloatArray(3)

    private val deltaRotationVector = FloatArray(4) { 0f }
    private val rotationCurrent = FloatArray(9) { 0f }
    private var timestamp: Float = 0f
    private val NS2S = 1.0f / 1000000000.0f
    override fun sensors(): Set<Int> {
        return setOf(
//            Sensor.TYPE_ACCELEROMETER,
            Sensor.TYPE_LINEAR_ACCELERATION,
            Sensor.TYPE_MAGNETIC_FIELD, Sensor.TYPE_GYROSCOPE
        )
    }

    var lastUpdateTime: Long = 0
    var mLastTime = 0L
    var mAngleX = 0f
    var mAngleY = 0f
    var mAngleZ = 0f

    var mLastTime1 = 0L
    val f139383h = FloatArray(3)
    val f139385j = DoubleArray(9)
    val f139391p = 100.0f

    /**
     * 手机水平放在桌上，xyz加速度：0,0,9.8 ; xyz角度：0,0，xx 度
     */
    override fun onSensorChanged(sensorEvent: SensorEvent) {
        when (sensorEvent.sensor?.type) {
            Sensor.TYPE_ACCELEROMETER,
            Sensor.TYPE_LINEAR_ACCELERATION -> {
                sensorEvent.values?.copyInto(accelerometerValues)
                val (ax, ay, az) = accelerometerValues
//            if (abs(ax) > 10 || abs(ay) > 10 || abs(az) > 10) {
//                Log.d(TAG, "xyz加速度: ${ax} ${ay} ${az}")
//            }
            }

            Sensor.TYPE_MAGNETIC_FIELD -> {
                sensorEvent.values?.copyInto(magneticValues)
            }

            Sensor.TYPE_GYROSCOPE -> {
                sensorEvent.values?.copyInto(gyroscopeValues)
//                a(sensorEvent)
//                var slideAngleX = 0f
//                var slideAngleY = 0f
//                var slideAngleZ = 0f
//                if (mLastTime != 0L) {
//                    val deltaTime = (sensorEvent.timestamp - mLastTime) / 1e9f // 纳秒转秒
//                    slideAngleX = gyroscopeValues[0] * deltaTime
//                    slideAngleY = gyroscopeValues[1] * deltaTime
//                    slideAngleZ = gyroscopeValues[2] * deltaTime
//                    mAngleX += slideAngleX
//                    mAngleY += slideAngleY
//                    mAngleZ += slideAngleZ
//                }
//                mLastTime = sensorEvent.timestamp
//                val slideDegreeX = NumberUtil.round(Math.toDegrees(slideAngleX.toDouble()), 3)
//                val slideDegreeY = NumberUtil.round(Math.toDegrees(slideAngleY.toDouble()), 3)
//                val slideDegreeZ = NumberUtil.round(Math.toDegrees(slideAngleZ.toDouble()), 3)
//
//                Log.d(TAG, "陀螺仪2计算出xyz角度片 ${sensorEvent.values.size}: ${slideDegreeX} ${slideDegreeY} ${slideDegreeZ}")
//                val degreeX = NumberUtil.round(Math.toDegrees(mAngleX.toDouble()), 3)
//                val degreeY = NumberUtil.round(Math.toDegrees(mAngleY.toDouble()), 3)
//                val degreeZ = NumberUtil.round(Math.toDegrees(mAngleZ.toDouble()), 3)
//                Log.d(TAG, "陀螺仪2计算出xyz角度: ${degreeX} ${degreeY} ${degreeZ}")

                if (this.mLastTime1 != 0L) {
                    A.smoothFilter(sensorEvent.values, this.f139383h);
                    f139385j[6] = ((f139383h[0] * f139391p).roundToInt() / f139391p).toDouble();
                    f139385j[7] = ((f139383h[1] * f139391p).roundToInt() / f139391p).toDouble();
                    f139385j[8] = ((f139383h[2] * f139391p).roundToInt() / f139391p).toDouble();
                }
                this.mLastTime1 = sensorEvent.timestamp;


            }
        }

//        //现在检测时间
//        val currentUpdateTime = System.currentTimeMillis()
//
//        //两次检测的时间间隔
//        val timeInterval = currentUpdateTime - lastUpdateTime
//
//        //判断是否达到了检测时间间隔
//        if (timeInterval < 50) return
//        //现在的时间变成last时间
//        lastUpdateTime = currentUpdateTime


        val (azimuth, pitch, roll) = getOrientation(accelerometerValues, magneticValues)
//            if (abs(pitch) > 35 || abs(roll) > 35 || abs(azimuth) > 35) {
//                Log.d(TAG, "xyz角度: ${pitch} ${roll} ${azimuth}")
//            }
//        Log.d(TAG, "加速计+罗盘计算出xyz角度: ${pitch} ${roll} ${azimuth}")
        //1.摇一摇
        //2.扭一扭
        //3.反转
    }


    fun a(event: SensorEvent) {
        // This timestep's delta rotation to be multiplied by the current rotation
        // after computing it from the gyro sample data.
        if (timestamp != 0f && event != null) {
            val dT = (event.timestamp - timestamp) * NS2S
            // Axis of the rotation sample, not normalized yet.
            var axisX: Float = event.values[0]
            var axisY: Float = event.values[1]
            var axisZ: Float = event.values[2]

            // Calculate the angular speed of the sample
            val omegaMagnitude: Float = sqrt(axisX * axisX + axisY * axisY + axisZ * axisZ)

            // Normalize the rotation vector if it's big enough to get the axis
            // (that is, EPSILON should represent your maximum allowable margin of error)
            if (omegaMagnitude > EPSILON) {
                axisX /= omegaMagnitude
                axisY /= omegaMagnitude
                axisZ /= omegaMagnitude
            }

            // Integrate around this axis with the angular speed by the timestep
            // in order to get a delta rotation from this sample over the timestep
            // We will convert this axis-angle representation of the delta rotation
            // into a quaternion before turning it into the rotation matrix.
            val thetaOverTwo: Float = omegaMagnitude * dT / 2.0f
            val sinThetaOverTwo: Float = sin(thetaOverTwo)
            val cosThetaOverTwo: Float = cos(thetaOverTwo)
            deltaRotationVector[0] = sinThetaOverTwo * axisX
            deltaRotationVector[1] = sinThetaOverTwo * axisY
            deltaRotationVector[2] = sinThetaOverTwo * axisZ
            deltaRotationVector[3] = cosThetaOverTwo
        }
        timestamp = event?.timestamp?.toFloat() ?: 0f
        val deltaRotationMatrix = FloatArray(9) { 0f }
        SensorManager.getRotationMatrixFromVector(deltaRotationMatrix, deltaRotationVector);
        // User code should concatenate the delta rotation we computed with the current rotation
        // in order to get the updated rotation.
        // rotationCurrent = rotationCurrent * deltaRotationMatrix;
        // 合并当前旋转矩阵和增量旋转矩阵
        val tempRotation = FloatArray(9) { 0f }
        matrixMultiply(rotationCurrent, deltaRotationMatrix, tempRotation)
        System.arraycopy(tempRotation, 0, rotationCurrent, 0, 9)

        // 将旋转矩阵转换为欧拉角
        val eulerAngles = rotationMatrixToEulerAngles(rotationCurrent)
        // 打印欧拉角
//        Log.d(TAG,"Pitch: ${eulerAngles[0]}, Yaw: ${eulerAngles[1]}, Roll: ${eulerAngles[2]}")
//        val degreeX1  = NumberUtil.round(Math.toDegrees(pitch.toDouble()),3)
//        val degreeY1  = NumberUtil.round(Math.toDegrees(roll.toDouble()),3)
//        val degreeZ1  = NumberUtil.round(Math.toDegrees(azimuth.toDouble()),3)
//        //                Log.d(TAG, "陀螺仪1计算出xyz弧度: ${x} ${y}} ${z}")
//        Log.d(TAG, "陀螺仪1计算出xyz角度: ${degreeX1} ${degreeY1} ${degreeZ1}")
    }

    private fun matrixMultiply(a: FloatArray, b: FloatArray, result: FloatArray) {
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                var sum = 0f
                for (k in 0 until 3) {
                    sum += a[i * 3 + k] * b[k * 3 + j]
                }
                result[i * 3 + j] = sum
            }
        }
    }

    private fun rotationMatrixToEulerAngles(matrix: FloatArray): FloatArray {
        val pitch = -asin(matrix[6])
        val yaw = atan2(matrix[7], matrix[8])
        val roll = atan2(matrix[3], matrix[0])

        return floatArrayOf(pitch, yaw, roll)
    }

}