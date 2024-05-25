package rma.lv1.viewmodel

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import rma.lv1.model.StepCountModel
import kotlin.math.sqrt

class StepCounterViewModel() : ViewModel() {
    private val stepCounterModel = StepCountModel(stepCount = 0)
    private val _stepCount = MutableStateFlow(0)
    val stepCount = _stepCount.asStateFlow()

    private lateinit var sensorManager: SensorManager
    private var sensor : Sensor? = null

    val sensorEventListener = object : SensorEventListener {
        val values = FloatArray(3)
        var MagnitudePrevious : Float = 0F
        var MagnitudeDelta : Double = 0.0
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}

        override fun onSensorChanged(event: SensorEvent) {
            if (event?.sensor == sensor) {
                event.values.copyInto(values)
                val x_acceleration = event.values[0]
                val y_acceleration = event.values[1]
                val z_acceleration = event.values[2]
                val Magnitude = sqrt(x_acceleration * x_acceleration + y_acceleration * y_acceleration + z_acceleration * z_acceleration)
                MagnitudeDelta = (Magnitude - MagnitudePrevious).toDouble()
                MagnitudePrevious = Magnitude
                if (MagnitudeDelta > 6){
                    _stepCount.value++
                }

            }
        }

    }

    fun setSensorManager(sensorManager: SensorManager){
        this.sensorManager = sensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    fun updateDatabase() {
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("BMI").document("hPe8eLDZwWj5Aw6SJDNn") //Your Document ID
        docRef.update("Koraci", _stepCount.value)
            .addOnSuccessListener {}
            .addOnFailureListener { e ->
                // Update failed (handle error, e.g., show an error message)
                Log.e("MainActivity", "Error updating Koraci: $e")
            }
    }

    fun startListeningForSensorEvents(context: Context) {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensor?.let {
            sensorManager.registerListener(sensorEventListener, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    fun stopListeningForSensorEvents() {
        sensorManager.unregisterListener(sensorEventListener)
    }

}