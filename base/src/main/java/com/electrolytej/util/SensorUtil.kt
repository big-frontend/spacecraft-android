import android.hardware.SensorManager
import kotlin.math.abs

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
fun checkGimbalLock(currentQuaternion: FloatArray): Boolean {
    val rotationMatrix = FloatArray(16)
    SensorManager.getRotationMatrixFromVector(rotationMatrix, currentQuaternion)
    val orientation = FloatArray(3)
    SensorManager.getOrientation(rotationMatrix, orientation)
    return abs(Math.toDegrees(orientation[1].toDouble())) > 89.0 // 俯仰角接近90°
}

// 扩展函数：弧度 → 角度
fun Float.toDegrees() = Math.toDegrees(this.toDouble())
fun Double.toDegrees() = Math.toDegrees(this)

/**

 * 方向传感器：orientation sensor，已经被废弃
 * 方向角解释
 * azimuth：方位角，表示设备绕 Z 轴旋转的角度，范围是 [-π, π]。Z轴
 * pitch：俯仰角，表示设备绕 X 轴旋转的角度，范围是 [-π/2, π/2]。X轴
 * roll：横滚角，表示设备绕 Y 轴旋转的角度，范围是 [-π, π]。Y轴
 * https://www.cnblogs.com/mengdd/archive/2013/05/19/3086781.html
 * SensorManager.getRotationMatrix + SensorManager.getOrientation 获取到的角为欧拉角，欧拉角存在万向节锁问题，
 * 使用四元数([w, x, y, z])，相比欧拉角，能避免万向节锁问题
 *
 *
 * 常见问题解答
 * 1. 为什么俯仰角范围是 [-90°, 90°]？
 * 这是欧拉角的 万向节锁（Gimbal Lock） 限制。当设备垂直（如竖起）时，俯仰角达到极限值，方位角和横滚角会失去唯一性。
 *
 * 2. 如何避免磁力计干扰？
 * 使用 TYPE_GAME_ROTATION_VECTOR（仅依赖加速度计和陀螺仪）：
 *
 * 3. 如何校准传感器？
 * 在代码中监听 SensorManager.SENSOR_STATUS_ACCURACY_HIGH
 *
 * 4. 传感器依赖
 * - 方位角的准确性依赖 磁力计 校准（TYPE_ROTATION_VECTOR）。
 * - 若使用 TYPE_GAME_ROTATION_VECTOR（不依赖磁力计），方位角可能随时间漂移。
 *
 * 解决万向节锁问题
 * 方法	优点	缺点	适用场景
 * 四元数	无万向节锁，计算高效	数学抽象，调试复杂	实时旋转叠加、插值
 * 旋转矩阵	直观，适合 GPU 计算	存储冗余（9个浮点数）	图形渲染、坐标变换
 * 调整旋转顺序	简单易实现	不能完全避免万向节锁	对精度要求不高的场景
 * 轴-角表示	物理意义明确	插值计算复杂	机械控制、物理引擎
 *
 *  angle: 角
 *  degree：角度 ，单位度
 *  radians: 弧度 ，单位
 *
 */
fun getOrientation(
    accelerometerValues: FloatArray,
    magneticValues: FloatArray
): Triple<Double, Double, Double> {
//    if (accelerometerValues == null || accelerometerValues.isEmpty() || magneticValues == null || magneticValues.isEmpty()) {
//        return Triple(null, null, null)
//    }
    val orientationAngles = FloatArray(3)
    val rotationMatrix = FloatArray(9)
    //获取世界坐标系
    SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerValues, magneticValues)
    SensorManager.getOrientation(rotationMatrix, orientationAngles);
    return Triple(
        Math.toDegrees(orientationAngles[0].toDouble()),
        Math.toDegrees(orientationAngles[1].toDouble()),
        Math.toDegrees(
            orientationAngles[2].toDouble()
        )
    )
}

/**
 * 旋转矢量传感器和方向传感器的坐标系都是地球坐标系
 *
 * TYPE_ROTATION_VECTOR
 * 输出：设备姿态（四元数/旋转向量，可转旋转矩阵、欧拉角）。
 * 依赖：陀螺仪 + 加速度计 + 磁力计（磁力计用于把 yaw/azimuth 锁定到地磁北，减少漂移）。
 * 特点：方位角比较“绝对”（相对地磁北），但容易被磁干扰。
 * 典型用途：指南针增强、AR、方向相关交互。
 * TYPE_GAME_ROTATION_VECTOR
 * 输出：姿态（不含磁北约束）。
 * 依赖：陀螺仪 + 加速度计（通常不使用磁力计）。
 * 特点：抗磁干扰、短期更稳，但 yaw 会随时间漂移（没有地磁北做绝对约束）。
 * 典型用途：游戏、手势/体感控制（更平滑）。
 * TYPE_GEOMAGNETIC_ROTATION_VECTOR
 * 输出：姿态（用地磁 + 重力定姿）。
 * 依赖：加速度计 + 磁力计（不需要陀螺仪）。
 * 特点：功耗低，但动态响应一般，噪声/抖动会更明显。
 * 典型用途：低功耗方向估计、简化版指南针。
 */
fun getOrientation(
    rotationValues: FloatArray
): Triple<Double, Double, Double> {
//    if (rotationValues == null || rotationValues.isEmpty()) {
//        return Triple(null, null, null)
//    }
    val rotationMatrix = FloatArray(9)
    val orientationAngles = FloatArray(3)
    //获取世界坐标系
    SensorManager.getRotationMatrixFromVector(rotationMatrix, rotationValues)
//     val displayRotationMatrix = FloatArray(9)
//    SensorManager.remapCoordinateSystem(
//        rotationMatrix,
//        SensorManager.AXIS_X,
//        SensorManager.AXIS_Z,
//        displayRotationMatrix
    SensorManager.getOrientation(rotationMatrix, orientationAngles)
    // orientationAngles包含:
    // [0]: 方位角(绕Z轴)
    // [1]: 俯仰角(绕X轴)
    // [2]: 横滚角(绕Y轴)
    return Triple(
        Math.toDegrees(orientationAngles[0].toDouble()),
        Math.toDegrees(orientationAngles[1].toDouble()),
        Math.toDegrees(
            orientationAngles[2].toDouble()
        )
    )
}