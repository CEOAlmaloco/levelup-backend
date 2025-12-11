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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.levelupprueba.data.repository.CarritoProvider
import com.example.levelupprueba.data.repository.NotificacionesRepositoryRemote
import com.example.levelupprueba.navigation.NavigationEvents
import com.example.levelupprueba.ui.screens.main.MainScreen
import com.example.levelupprueba.ui.theme.LevelUpPruebaTheme
import com.example.levelupprueba.viewmodel.BlogViewModel
import com.example.levelupprueba.viewmodel.CarritoViewModel
import com.example.levelupprueba.viewmodel.CarritoViewModelFactory
import com.example.levelupprueba.viewmodel.EventoViewModel
import com.example.levelupprueba.viewmodel.EventoViewModelFactory
import com.example.levelupprueba.viewmodel.LoginViewModel
import com.example.levelupprueba.viewmodel.LoginViewModelFactory
import com.example.levelupprueba.viewmodel.MainViewModel
import com.example.levelupprueba.viewmodel.MainViewModelFactory
import com.example.levelupprueba.viewmodel.ChangePasswordViewModel
import com.example.levelupprueba.viewmodel.ChangePasswordViewModelFactory
import com.example.levelupprueba.viewmodel.ProductoViewModel
import com.example.levelupprueba.viewmodel.ProductoDetalleViewModel
import com.example.levelupprueba.viewmodel.ProfileViewModel
import com.example.levelupprueba.viewmodel.ProfileViewModelFactory
import com.example.levelupprueba.viewmodel.UsuarioViewModel
import com.example.levelupprueba.viewmodel.UsuariosViewModel
import com.example.levelupprueba.viewmodel.UsuariosViewModelFactory



class   MainActivity : ComponentActivity() {
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
            val navController = rememberNavController()

            val context = LocalContext.current
            val carritoRepo = CarritoProvider.get(context)
            val notificacionesRepository = NotificacionesRepositoryRemote()

            // Factories - las fabricas para crear viewmodels con parametros personalizados
            val mainViewModelFactory = MainViewModelFactory(context)
            val usuariosViewModelFactory = UsuariosViewModelFactory()
            val eventoViewModelFactory = EventoViewModelFactory()
            val loginViewModelFactory = LoginViewModelFactory()
            val carritoViewModelFactory = CarritoViewModelFactory(carritoRepo)
            val profileViewModelFactory = ProfileViewModelFactory(notificacionesRepository)
            val changePasswordViewModelFactory = ChangePasswordViewModelFactory()

            // ViewModels - aca creamos los viewmodels, algunos con factory y otros sin factory
            val eventoViewModel: EventoViewModel = viewModel(factory = eventoViewModelFactory)
            val usuarioViewModel: UsuarioViewModel = viewModel()
            val usuariosViewModel: UsuariosViewModel = viewModel(factory = usuariosViewModelFactory)
            val mainViewModel: MainViewModel = viewModel(factory = mainViewModelFactory)
            val loginViewModel: LoginViewModel = viewModel(factory = loginViewModelFactory)
            val blogViewModel: BlogViewModel = viewModel()
            val productoViewModel: ProductoViewModel = viewModel()
            val carritoViewModel: CarritoViewModel = viewModel(factory = carritoViewModelFactory)
            val productoDetalleViewModel: ProductoDetalleViewModel = viewModel()
            val profileViewModel: ProfileViewModel = viewModel(factory = profileViewModelFactory)
            val changePasswordViewModel: ChangePasswordViewModel = viewModel(factory = changePasswordViewModelFactory)


            val userSession by mainViewModel.userSessionFlow.collectAsState()
            val isLoading by mainViewModel.isLoading.collectAsState()
            val avatar by mainViewModel.avatar.collectAsState()

            LaunchedEffect(Unit) {
                // Inicializar ViewModels que necesitan contexto
                eventoViewModel.inicializar(context)

                mainViewModel.navigationEvent.collect { event ->
                    when (event) {
                        is NavigationEvents.NavigateTo -> navController.navigate(event.route)
                        is NavigationEvents.NavigateBack -> navController.popBackStack()
                        else -> navController.navigateUp()
                    }
                }
            }

            // Pasa la clase al tema
            LevelUpPruebaTheme (
                dynamicColor = false,
                windowSizeClass = windowSizeClass.widthSizeClass
            ) {
                MainScreen(
                    userSession = userSession,
                    isLoading = isLoading,
                    avatar = avatar,
                    mainViewModel = mainViewModel,
                    navController = navController,
                    usuarioViewModel = usuarioViewModel,
                    usuariosViewModel = usuariosViewModel,
                    loginViewModel = loginViewModel,
                    blogViewModel = blogViewModel,
                    productoViewModel = productoViewModel,
                    eventoViewModel = eventoViewModel,
                    productoDetalleViewModel = productoDetalleViewModel,
                    profileViewModel = profileViewModel,
                    changePasswordViewModel = changePasswordViewModel,
                    carritoViewModel = carritoViewModel
                )
            }
        }
    }
}
