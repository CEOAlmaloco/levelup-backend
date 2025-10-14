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
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.levelupprueba.data.local.AppDatabase
import com.example.levelupprueba.data.repository.UsuarioRepository
import com.example.levelupprueba.navigation.MainScreen
import com.example.levelupprueba.ui.theme.LevelUpPruebaTheme
import com.example.levelupprueba.viewmodel.BlogViewModel
import com.example.levelupprueba.viewmodel.EventoViewModel
import com.example.levelupprueba.viewmodel.LoginViewModel
import com.example.levelupprueba.viewmodel.LoginViewModelFactory
import com.example.levelupprueba.viewmodel.MainViewModel
import com.example.levelupprueba.viewmodel.ProductoViewModel
import com.example.levelupprueba.viewmodel.UsuarioViewModel
import com.example.levelupprueba.viewmodel.UsuarioViewModelFactory


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

            val context = LocalContext.current
            val usuarioDao = AppDatabase.getInstance(context).usuarioDao()
            val usuarioRepository = UsuarioRepository(usuarioDao)

            // Factories
            val usuarioViewModelFactory = UsuarioViewModelFactory(usuarioRepository)
            val loginViewModelFactory = LoginViewModelFactory(usuarioRepository)

            // ViewModels
            val usuarioViewModel: UsuarioViewModel = viewModel(factory = usuarioViewModelFactory)
            val loginViewModel: LoginViewModel = viewModel(factory = loginViewModelFactory)
            val blogViewModel: BlogViewModel = viewModel() // si no requiere parámetros
            val productoViewModel: ProductoViewModel = viewModel() // si no requiere parámetros
            val eventoViewModel: EventoViewModel = viewModel()

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
                    navController = navController,
                    usuarioViewModel = usuarioViewModel,
                    loginViewModel = loginViewModel,
                    blogViewModel = blogViewModel,
                    productoViewModel = productoViewModel,
                    eventoViewModel = eventoViewModel
                )
            }
        }
    }
}
