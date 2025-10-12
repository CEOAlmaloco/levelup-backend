package com.example.levelupprueba

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.levelupprueba.navigation.MainScreen
import com.example.levelupprueba.ui.theme.LevelUpPruebaTheme
import com.example.levelupprueba.viewmodel.MainViewModel


//Todo lo que pongamos dentro de setcontent sera la interfaz
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            // Calcula el tamaño de la ventana
            val windowSizeClass = calculateWindowSizeClass(this)
            val mainViewModel: MainViewModel = viewModel()
            val navController = rememberNavController()

            LaunchedEffect(Unit) {
                mainViewModel.navigationEvent.collect { event ->
                    when (event) {
                        is com.example.levelupprueba.navigation.NavigationEvents.NavigateTo -> {
                            navController.navigate(event.route)
                        }
                        is com.example.levelupprueba.navigation.NavigationEvents.NavigateBack -> {
                            navController.popBackStack()
                        }
                        is com.example.levelupprueba.navigation.NavigationEvents.NavigateUp -> {
                            navController.navigateUp()
                        }
                        null -> {}
                    }
                }
            }
            // Pasa la clase al tema
            LevelUpPruebaTheme (
                dynamicColor = false,
                windowSizeClass = windowSizeClass.widthSizeClass
            ) {
                MainScreen(
                    mainViewModel = mainViewModel,
                    navController = navController
                )
            }
        }
    }
}
