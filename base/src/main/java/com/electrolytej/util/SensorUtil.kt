
//x,y,z三个方向的加速度， m/s2
//TYPE_ACCELEROMETER
//fun onSensorChanged(event: SensorEvent) {
//    // alpha is calculated as t / (t + dT)
//    // with t, the low-pass filter's time-constant
//    // and dT, the event delivery rate
//
//    val alpha: Float = 0.8.toFloat()
//
//    gravity.get(0) = alpha * gravity.get(0) + (1 - alpha) * event.values.get(0)
//    gravity.get(1) = alpha * gravity.get(1) + (1 - alpha) * event.values.get(1)
//    gravity.get(2) = alpha * gravity.get(2) + (1 - alpha) * event.values.get(2)
//
//    linear_acceleration.get(0) = event.values.get(0) - gravity.get(0)
//    linear_acceleration.get(1) = event.values.get(1) - gravity.get(1)
//    linear_acceleration.get(2) = event.values.get(2) - gravity.get(2)
//}
//TYPE_LINEAR_ACCELERATION
//acceleration = gravity + linear_acceleration

//x，y，z三个方向的环境地磁场(罗盘）， μT
//TYPE_MAGNETIC_FIELD
//角速度Angular Velocity
//x，y，z 三个方向的旋转速率 陀螺仪，RotationalSpeed rad/s
//TYPE_GYROSCOPE
//
//TYPE_ROTATION_VECTOR