package rma.lv1.viewmodel

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.firestore.FirebaseFirestore
import rma.lv1.model.BMIModel
import kotlin.math.sqrt


class BMIViewModel() : ViewModel() {
    val bmiModel = BMIModel(weight = null, height = null, bmiResult = null)
    fun calculateBMI(weight: Float?, height: Float?): Float? {
        return if (weight != null && height != null && height > 0) {
            val bmi = weight / ((height / 100) * (height / 100))
            bmiModel.bmiResult = bmi
            bmi // Return calculated BMI
        } else {
            bmiModel.bmiResult = null
            null // Return null if weight or height is null or height is less than or equal to 0
        }
    }

}