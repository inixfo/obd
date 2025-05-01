package com.eb.obd2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class VehicleMetricsViewModel @Inject constructor() : ViewModel() {
    private val _metrics = MutableStateFlow(VehicleMetrics())
    val metrics: StateFlow<VehicleMetrics> = _metrics.asStateFlow()

    fun updateMetrics(newMetrics: VehicleMetrics) {
        _metrics.value = newMetrics
    }

    fun updateSpeed(speed: Float) {
        _metrics.value = _metrics.value.copy(speed = speed)
    }

    fun updateRPM(rpm: Int) {
        _metrics.value = _metrics.value.copy(rpm = rpm)
    }

    fun updateEngineTemp(temp: Float) {
        _metrics.value = _metrics.value.copy(engineTemp = temp)
    }

    fun updateGear(gear: Int) {
        _metrics.value = _metrics.value.copy(gear = gear)
    }

    fun updateGearPosition(position: String) {
        _metrics.value = _metrics.value.copy(gearPosition = position)
    }
} 