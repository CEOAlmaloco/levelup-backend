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
import com.example.levelupprueba.ui.screens.admin.users.AdminUsuariosScreen
import com.example.levelupprueba.ui.screens.home.HomeScreenProductos
import com.example.levelupprueba.ui.screens.productos.ProductosScreen
import com.example.levelupprueba.ui.screens.productos.ProductoDetalleScreen
import com.example.levelupprueba.ui.screens.blog.BlogListScreen
import com.example.levelupprueba.ui.screens.eventos.EventoScreen
import com.example.levelupprueba.ui.screens.auth.LoginScreen
import com.example.levelupprueba.ui.screens.auth.RegisterScreen
import com.example.levelupprueba.ui.screens.carrito.CarritoScreen
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
    usuariosViewModel: UsuariosViewModel,
    loginViewModel: LoginViewModel,
    blogViewModel: BlogViewModel,
    productoViewModel: ProductoViewModel,
    carritoViewModel: CarritoViewModel,
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
                },
                carritoViewModel = carritoViewModel
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
                },
                carritoViewModel = carritoViewModel
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
                },
                contentPadding = innerPadding,
                userDisplayName = userDisplayName ?: "Invitado",
                isLoggedIn = isLoggedIn,
                carritoViewModel = carritoViewModel,
                userId = userSessionId?.toLongOrNull()
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

        // Carrito
        composable(Screen.Carrito.route) {
            CarritoScreen(
                viewModel = carritoViewModel,
                contentPadding = innerPadding,
            )
        } // Fin carritoScreen

        composable(Screen.GestionUsuarios.route){
            AdminUsuariosScreen(
                viewModel = usuariosViewModel,
                contentPadding = innerPadding
            )
        }
    }
}

