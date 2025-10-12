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
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.levelupprueba.data.local.UserDataStore
import com.example.levelupprueba.model.usuario.Usuario
import com.example.levelupprueba.navigation.AuthNavigation
import com.example.levelupprueba.ui.theme.LevelUpPruebaTheme
import com.example.levelupprueba.viewmodel.MainViewModel
import kotlinx.coroutines.launch


class AuthActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Detecta si es tablet usando smallestScreenWidthDp
        val isTablet = resources.configuration.smallestScreenWidthDp >= 600

        // Define el usuarioDemo
        val usuarioDemo = Usuario(
            id = "123",
            nombre = "LevelUp User",
            apellidos = "Prueba",
            email = "demo@duoc.cl",
            password = "1234",
            telefono = "123456789",
            fechaNacimiento = "2000-01-01",
            region = "Región Metropolitana",
            comuna = "Santiago",
            direccion = "Av. Siempre Viva 123",
            referralCode = "LEVELUP1234",
            points = 0,
            role = "cliente"
        )

        val userDataStore = UserDataStore(this)
        lifecycleScope.launch {
            // Solo lo agrega si no existe ya
            val usuarios = userDataStore.getUsuarios()
            if (usuarios.none { it.email == usuarioDemo.email }) {
                userDataStore.addUsuario(usuarioDemo)
            }
        }

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

            val mainViewModel: MainViewModel = viewModel()

            val navController =  rememberNavController()

            val startDestination = intent.getStringExtra("startDestination") ?: "welcome"

            // LaunchedEffect SOLO AQUÍ, no en cada pantalla
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
                    navController = navController,
                    startDestination = startDestination
                )
            }
        }
    }
}