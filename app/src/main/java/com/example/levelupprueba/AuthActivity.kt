package com.example.levelupprueba

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.levelupprueba.navigation.AuthNavigation
import com.example.levelupprueba.ui.theme.LevelUpPruebaTheme
import com.example.levelupprueba.viewmodel.LoginViewModel
import com.example.levelupprueba.viewmodel.LoginViewModelFactory
import com.example.levelupprueba.viewmodel.MainViewModel
import com.example.levelupprueba.viewmodel.MainViewModelFactory
import com.example.levelupprueba.viewmodel.UsuarioViewModel
import kotlinx.coroutines.launch


class AuthActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Detecta si es tablet usando smallestScreenWidthDp
        val isTablet = resources.configuration.smallestScreenWidthDp >= 600

        // Si NO es tablet, fuerza portrait
        if (!isTablet) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        } else {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }

        enableEdgeToEdge()
        setContent {
            // Calcula el tamaño de la ventana
            val windowSizeClass = calculateWindowSizeClass(this)

            val navController =  rememberNavController()

            val startDestination = intent.getStringExtra("startDestination") ?: "welcome"

            val context = LocalContext.current

            // Instanciar Factories
            val mainViewModelFactory = MainViewModelFactory(context)
            val loginViewModelFactory = LoginViewModelFactory()

            // Instanciar ViewModels
            val mainViewModel: MainViewModel = viewModel(factory = mainViewModelFactory)
            val usuarioViewModel: UsuarioViewModel = viewModel()
            val loginViewModel: LoginViewModel = viewModel(factory = loginViewModelFactory)

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
                AuthNavigation(
                    mainViewModel = mainViewModel,
                    usuarioViewModel = usuarioViewModel,
                    loginViewModel = loginViewModel,
                    navController = navController,
                    startDestination = startDestination
                )
            }
        }
    }
}