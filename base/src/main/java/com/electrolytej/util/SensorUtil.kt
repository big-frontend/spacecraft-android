import android.hardware.SensorManager

/**
 *
 * 三大类型 sensor
 * a.Motion sensors
 *
 * b.Environmental sensors
 *
 * c.Position sensors
 *
 * sensor在移动设备的坐标轴
   传感器坐标系定义
    Android 传感器坐标系是一个 右手坐标系，基于设备的默认方向（竖屏）定义：

    X 轴：水平方向，向右为正方向。

    Y 轴：垂直方向，向上为正方向。

    Z 轴：垂直于屏幕，向外（指向用户）为正方向。
 * https://juejin.cn/post/6995538111111381028#heading-8
 * https://source.android.com/docs/core/interaction/sensors/sensor-types?hl=zh-cn
 *
 *
 */

/**
 * TYPE_ACCELEROMETER
 *
 * x,y,z三个方向的加速度， m/s2
 * fun onSensorChanged(event: SensorEvent) {
 *     // alpha is calculated as t / (t + dT)
 *     // with t, the low-pass filter's time-constant
 *     // and dT, the event delivery rate
 *
 *     val alpha: Float = 0.8.toFloat()
 *
 *     gravity.get(0) = alpha * gravity.get(0) + (1 - alpha) * event.values.get(0)
 *     gravity.get(1) = alpha * gravity.get(1) + (1 - alpha) * event.values.get(1)
 *     gravity.get(2) = alpha * gravity.get(2) + (1 - alpha) * event.values.get(2)
 *
 *     linear_acceleration.get(0) = event.values.get(0) - gravity.get(0)
 *     linear_acceleration.get(1) = event.values.get(1) - gravity.get(1)
 *     linear_acceleration.get(2) = event.values.get(2) - gravity.get(2)
 * }
 * TYPE_LINEAR_ACCELERATION
 * acceleration = gravity + linear_acceleration
 *
 * 加速度传感器测量的是设备在 X、Y、Z 三个方向上的加速度，包括重力加速度。
 *
 * X 轴：
 *
 * 正值：设备向右倾斜。
 *
 * 负值：设备向左倾斜。
 *
 * Y 轴：
 *
 * 正值：设备向上倾斜。
 *
 * 负值：设备向下倾斜。
 *
 * Z 轴：
 *
 * 正值：设备屏幕朝上。
 *
 * 负值：设备屏幕朝下。
 *
 * 注意：当设备静止时，加速度传感器的 Z 轴值约为 9.8 m/s²（重力加速度）。
 */


/**
 * TYPE_MAGNETIC_FIELD
 *
 * x，y，z三个方向的环境地磁场(罗盘）， μT
 * 角速度Angular Velocity
 *
 * 磁力计传感器测量的是设备周围的地磁场强度。
 *
 * X 轴：
 *
 * 正值：磁场方向向右。
 *
 * 负值：磁场方向向左。
 *
 * Y 轴：
 *
 * 正值：磁场方向向上。
 *
 * 负值：磁场方向向下。
 *
 * Z 轴：
 *
 * 正值：磁场方向向外（指向用户）。
 *
 * 负值：磁场方向向内（指向设备背面）。
 */


/**
 * TYPE_GYROSCOPE
 * x，y，z 三个方向的旋转速率 陀螺仪，RotationalSpeed rad/s
 * 陀螺仪传感器测量的是设备绕 X、Y、Z 三个轴的旋转角速度。
 *
 * X 轴：
 *
 * 正值：设备绕 X 轴顺时针旋转。
 *
 * 负值：设备绕 X 轴逆时针旋转。
 *
 * Y 轴：
 *
 * 正值：设备绕 Y 轴顺时针旋转。
 *
 * 负值：设备绕 Y 轴逆时针旋转。
 *
 * Z 轴：
 *
 * 正值：设备绕 Z 轴顺时针旋转。
 *
 * 负值：设备绕 Z 轴逆时针旋转。
 *
 * 注意：陀螺仪数据的单位是 弧度/秒（rad/s）。
 */
//TYPE_ROTATION_VECTOR

/**
 * 方向传感器：orientation sensor，已经被废弃
 * 方向角解释
 * azimuth：方位角，表示设备绕 Z 轴旋转的角度，范围是 [-π, π]。Z轴
 * pitch：俯仰角，表示设备绕 X 轴旋转的角度，范围是 [-π/2, π/2]。X轴
 * roll：横滚角，表示设备绕 Y 轴旋转的角度，范围是 [-π, π]。Y轴
 * https://www.cnblogs.com/mengdd/archive/2013/05/19/3086781.html
 *
 */
fun getOrientation(
    accelerometerValues: FloatArray,
    magneticValues: FloatArray
): Triple<Double, Double, Double> {
    val orientationAngles = FloatArray(3)
    val rotationMatrix = FloatArray(9)
    //获取世界坐标系
    SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerValues, magneticValues)
    SensorManager.getOrientation(rotationMatrix, orientationAngles);
    // Convert from radians to degrees
    return Triple(
        Math.toDegrees(orientationAngles[0].toDouble()),
        Math.toDegrees(orientationAngles[1].toDouble()),
        Math.toDegrees(
            orientationAngles[2].toDouble()
        )
    )
}