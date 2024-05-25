package rma.lv1.view


import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import rma.lv1.viewmodel.StepCounterViewModel

@Composable
fun StepCounterScreen(navController: NavController, viewModel: StepCounterViewModel) {
    val context = LocalContext.current
    val sensorManager = (LocalContext.current.getSystemService(Context.SENSOR_SERVICE) as SensorManager)
    val sensor = remember {sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)}
    viewModel.setSensorManager(sensorManager)
    val steps by viewModel.stepCount.collectAsState()

    DisposableEffect(Unit) {
        viewModel.startListeningForSensorEvents(context)
        onDispose {
            viewModel.stopListeningForSensorEvents()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = "Step Count: $steps",
            fontSize = 20.sp
        )
        Button(
            onClick = {
                viewModel.updateDatabase()
                navController.popBackStack()
            },
            modifier = Modifier
                .align(Alignment.End)
                .padding(16.dp)
        ) {
            Text("User Info")
        }

    }
}
