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
import com.example.levelupprueba.data.local.AppDatabase
import com.example.levelupprueba.data.local.room.CarritoDatabase
import com.example.levelupprueba.data.repository.CarritoRepositoryRoom
import com.example.levelupprueba.data.repository.NotificacionesRepositoryRemote
import com.example.levelupprueba.data.repository.UsuarioRepository
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
import com.example.levelupprueba.viewmodel.ProductoDetalleViewModelFactory
import com.example.levelupprueba.viewmodel.ProfileViewModel
import com.example.levelupprueba.viewmodel.ProfileViewModelFactory
import com.example.levelupprueba.viewmodel.UsuarioViewModel
import com.example.levelupprueba.viewmodel.UsuarioViewModelFactory


//Todo lo que pongamos dentro de setcontent sera la interfaz
class   MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //carlosc
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
            val database = AppDatabase.getInstance(context) //traemos la instancia de la bd completa
            val usuarioDao = database.usuarioDao() //obtenemos el dao de usuarios
            val reviewDao = database.reviewDao() //obtenemos el dao de reviews para los productos
            val carritoDao = CarritoDatabase.get(context).carritoDao()
            val carritoRepo = CarritoRepositoryRoom(carritoDao)
            val usuarioRepository = UsuarioRepository(usuarioDao)
            val notificacionesRepository = NotificacionesRepositoryRemote()

            // Factories - las fabricas para crear viewmodels con parametros personalizados
            val mainViewModelFactory = MainViewModelFactory(context, usuarioRepository)
            val eventoViewModelFactory = EventoViewModelFactory(usuarioRepository)
            val loginViewModelFactory = LoginViewModelFactory(usuarioRepository)
            val productoDetalleViewModelFactory = ProductoDetalleViewModelFactory(reviewDao) //factory con el reviewDao
            val carritoViewModelFactory = CarritoViewModelFactory(carritoRepo)
            val profileViewModelFactory = ProfileViewModelFactory(usuarioRepository, notificacionesRepository)
            val changePasswordViewModelFactory = ChangePasswordViewModelFactory(usuarioRepository)

            // ViewModels - aca creamos los viewmodels, algunos con factory y otros sin factory
            val eventoViewModel: EventoViewModel = viewModel(factory = eventoViewModelFactory)
            val usuarioViewModelFactory = UsuarioViewModelFactory(usuarioRepository, eventoViewModel)
            val usuarioViewModel: UsuarioViewModel = viewModel(factory = usuarioViewModelFactory)
            val mainViewModel: MainViewModel = viewModel(factory = mainViewModelFactory)
            val loginViewModel: LoginViewModel = viewModel(factory = loginViewModelFactory)
            val blogViewModel: BlogViewModel = viewModel()
            val productoViewModel: ProductoViewModel = viewModel()
            val carritoViewModel: CarritoViewModel = viewModel(factory = carritoViewModelFactory)
            val productoDetalleViewModel: ProductoDetalleViewModel = viewModel(factory = productoDetalleViewModelFactory) //ahora usa SQLite
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
