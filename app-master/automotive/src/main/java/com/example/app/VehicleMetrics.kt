package com.eb.obd2

data class VehicleMetrics(
    val speed: Float = 0f,          // km/h
    val rpm: Int = 0,              // RPM
    val engineTemp: Float = 0f,    // °C
    val gear: Int = 1,             // 1-6
    val gearPosition: String = "N"  // P, R, N, D
) 