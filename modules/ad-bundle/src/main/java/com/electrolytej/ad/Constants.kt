package com.electrolytej.ad

object Constants {
    /**
     * {
     * 		"sk_by_angle_acceleration": 1000,
     * 		"sk_by_angle_acceleration_y": 1000,
     * 		"sk_by_angle_acceleration_z": 10,
     * 		"sk_by_angle": 1000,
     * 		"sk_by_angle_y": 1000,
     * 		"sk_by_angle_z": 40,
     *
     *
     * 		"sk_by_angle_acceleration2": 1000,
     * 		"sk_by_angle_acceleration2_y": 1000,
     * 		"sk_by_angle_acceleration2_z": 10,
     * 		"sk_by_angle2": 1000,
     * 		"sk_by_angle2_y": 1000,
     * 		"sk_by_angle2_z": 40,
     *
     * 		"sk_by_gap_duration": 180,
     * 		"module_id": 4
     *
     * 	}
     */
    const val sk_by_angle_acceleration = 3f
    const val sk_by_angle_acceleration_y = 3f
    const val sk_by_angle_acceleration_z = 5f
    const val sk_by_angle = 40f
    const val sk_by_angle_y = 65f
    const val sk_by_angle_z = 16f
    const val sk_by_angle_acceleration2 = 3f
    const val sk_by_angle_acceleration2_y = 3f
    const val sk_by_angle_acceleration2_z = 5f
    const val sk_by_angle2 = 40f
    const val sk_by_angle2_y = 65f
    const val sk_by_angle2_z = 16f
    const val sk_by_gap_duration = 180

    var accelerationX1 = sk_by_angle_acceleration
    var accelerationY1 = sk_by_angle_acceleration_y
    var accelerationZ1 = sk_by_angle_acceleration_z
    var degreesX1 = sk_by_angle
    var degreesY1 = sk_by_angle_y
    var degreesZ1 = sk_by_angle_z

    var accelerationX2 = sk_by_angle_acceleration2
    var accelerationY2 = sk_by_angle_acceleration2_y
    var accelerationZ2 = sk_by_angle_acceleration2_z
    var degreesX2 = sk_by_angle2
    var degreesY2 = sk_by_angle2_y
    var degreesZ2 = sk_by_angle2_z
    var duration = sk_by_gap_duration
}