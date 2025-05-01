package com.eb.obd2

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.Socket
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmulatorService @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val TAG = "EmulatorService"
    private val _metricsFlow = MutableSharedFlow<VehicleMetrics>()
    val metricsFlow: SharedFlow<VehicleMetrics> = _metricsFlow

    private var socket: Socket? = null
    private var isRunning = false

    suspend fun connect(host: String, port: Int) {
        withContext(Dispatchers.IO) {
            try {
                socket = Socket(host, port)
                isRunning = true
                startReading()
            } catch (e: Exception) {
                Log.e(TAG, "Failed to connect to emulator", e)
            }
        }
    }

    private fun startReading() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val reader = BufferedReader(InputStreamReader(socket?.getInputStream()))
                while (isRunning) {
                    val line = reader.readLine()
                    if (line != null) {
                        parseAndEmitMetrics(line)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error reading from socket", e)
                isRunning = false
            }
        }
    }

    private suspend fun parseAndEmitMetrics(data: String) {
        try {
            // Parse the data from the emulator
            // This is a simple example - you'll need to adjust the parsing based on the actual data format
            val metrics = VehicleMetrics(
                speed = data.split(",")[0].toFloatOrNull() ?: 0f,
                rpm = data.split(",")[1].toIntOrNull() ?: 0,
                engineTemp = data.split(",")[2].toFloatOrNull() ?: 0f,
                gear = data.split(",")[3].toIntOrNull() ?: 1,
                gearPosition = data.split(",")[4]
            )
            _metricsFlow.emit(metrics)
        } catch (e: Exception) {
            Log.e(TAG, "Error parsing metrics", e)
        }
    }

    fun disconnect() {
        isRunning = false
        try {
            socket?.close()
        } catch (e: Exception) {
            Log.e(TAG, "Error closing socket", e)
        }
    }
} 