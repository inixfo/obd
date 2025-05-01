package com.eb.obd2.models

data class VehicleMetrics(
    val rpm: Int = 0,
    val speed: Float = 0f,
    val engineTemp: Int = 0,
    val currentGear: Int = 0,
    val gearPosition: GearPosition = GearPosition.NEUTRAL
)

enum class GearPosition {
    PARK,
    REVERSE,
    NEUTRAL,
    DRIVE
}

fun calculateGear(rpm: Int, speed: Float): Int {
    if (speed < 5f) return 1
    if (rpm < 1000) return 0 // Neutral
    
    // Simple gear calculation based on speed and RPM
    return when {
        speed < 20f -> 1
        speed < 40f -> 2
        speed < 60f -> 3
        speed < 80f -> 4
        speed < 100f -> 5
        else -> 6
    }
}

fun calculateGearPosition(speed: Float, rpm: Int): GearPosition {
    return when {
        speed == 0f && rpm < 1000 -> GearPosition.NEUTRAL
        speed == 0f && rpm > 1000 -> GearPosition.PARK
        speed < 0f -> GearPosition.REVERSE
        else -> GearPosition.DRIVE
    }
} 