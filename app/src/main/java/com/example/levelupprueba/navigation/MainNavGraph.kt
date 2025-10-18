package com.example.levelupprueba.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.rememberCoroutineScope
import com.example.levelupprueba.ui.screens.home.HomeScreenProductos
import com.example.levelupprueba.ui.screens.productos.ProductosScreen
import com.example.levelupprueba.ui.screens.productos.ProductoDetalleScreen
import com.example.levelupprueba.ui.screens.blog.BlogListScreen
import com.example.levelupprueba.ui.screens.eventos.EventoScreen
import com.example.levelupprueba.ui.screens.auth.LoginScreen
import com.example.levelupprueba.ui.screens.auth.RegisterScreen
import com.example.levelupprueba.ui.screens.profile.ProfileScreen
import com.example.levelupprueba.viewmodel.*
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainNavGraph(
    navController: NavHostController,
    startDestination: String,
    mainViewModel: MainViewModel,
    usuarioViewModel: UsuarioViewModel,
    loginViewModel: LoginViewModel,
    blogViewModel: BlogViewModel,
    productoViewModel: ProductoViewModel,
    eventoViewModel: EventoViewModel,
    productoDetalleViewModel: ProductoDetalleViewModel,
    profileViewModel: ProfileViewModel,
    changePasswordViewModel: ChangePasswordViewModel,
    innerPadding: PaddingValues,
    isLoading: Boolean,
    isLoggedIn: Boolean,
    userSessionId: String?,
    userDisplayName: String?
) {
    val coroutineScope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Home
        composable(Screen.Home.route) {
            HomeScreenProductos(
                viewModel = productoViewModel,
                onVerMasClick = {
                    // usa tu MainViewModel para navegar si así lo deseas
                    coroutineScope.launch {
                        mainViewModel.navigateTo(Screen.Productos.route)
                    }
                },
                contentPadding = innerPadding,
                onProductoClick = { productoId ->
                    coroutineScope.launch {
                        mainViewModel.navigateTo(Screen.ProductoDetalle.createRoute(productoId))
                    }
                }
            )
        }

        // Productos
        composable(Screen.Productos.route) {
            ProductosScreen(
                viewModel = productoViewModel,
                contentPadding = innerPadding,
                onProductoClick = { productoId ->
                    coroutineScope.launch{
                        mainViewModel.navigateTo(Screen.ProductoDetalle.createRoute(productoId))
                    }
                }
            )
        }

        // Detalle Producto (con argumento)
        composable(
            route = Screen.ProductoDetalle.route,
            arguments = listOf(navArgument("productoId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productoId = backStackEntry.arguments?.getString("productoId") ?: ""
            ProductoDetalleScreen(
                productoId = productoId,
                viewModel = productoDetalleViewModel,
                onProductoClick = { id ->
                    coroutineScope.launch {
                        mainViewModel.navigateTo(Screen.ProductoDetalle.createRoute(id))
                    }
                },
                onNavigateBack = {
                    coroutineScope.launch {
                        mainViewModel.navigateBack()
                    }
                }
            )
        }

        // Blog
        composable(Screen.Blog.route) {
            BlogListScreen(viewModel = blogViewModel, contentPadding = innerPadding)
        }

        // Eventos
        composable(Screen.Eventos.route) {
            EventoScreen(viewModel = eventoViewModel, contentPadding = innerPadding)
        }

        // Login / Registro
        composable("login") {
            LoginScreen(navController = navController, viewModel = loginViewModel, mainViewModel = mainViewModel)
        }
        composable("registro") {
            RegisterScreen(mainViewModel = mainViewModel, viewModel = usuarioViewModel)
        }

        // Perfil (requiere login)
        composable(Screen.Perfil.route) {
            when {
                isLoading -> {
                    // tu overlay interno ya es manejado en MainScreen/ProfileStatusHandler,
                    // aquí puedes mostrar un placeholder o simplemente la pantalla si no está cargando
                }
                isLoggedIn -> {
                    ProfileScreen(
                        viewModel = profileViewModel,
                        mainViewModel = mainViewModel,
                        changePasswordViewModel = changePasswordViewModel,
                        isLoggedIn = isLoggedIn,
                        userId = userSessionId ?: "",
                        displayName = userDisplayName ?: "",
                        contentPadding = innerPadding,

                    )
                }
            }
        }
    }
}

