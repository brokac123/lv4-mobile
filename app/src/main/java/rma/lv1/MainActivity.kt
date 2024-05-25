package rma.lv1

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import rma.lv1.view.BMICalculatorScreen
import rma.lv1.view.BackgroundImage
import rma.lv1.view.StepCounterScreen
import rma.lv1.viewmodel.BMIViewModel
import rma.lv1.viewmodel.StepCounterViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            BackgroundImage(modifier = Modifier)

            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "main_screen") {
                composable("main_screen") {
                    BMICalculatorScreen(navController = navController, viewModel = BMIViewModel())
                }
                composable("step_counter") {
                    StepCounterScreen(navController = navController, viewModel = StepCounterViewModel())
                }
            }

        }
    }
}
