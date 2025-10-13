package com.example.levelupprueba.navigation

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.levelupprueba.AuthActivity
import com.example.levelupprueba.data.local.getUserSessionFlow
import com.example.levelupprueba.ui.components.LevelUpMainTopBar
import com.example.levelupprueba.ui.screens.auth.LoginScreen
import com.example.levelupprueba.ui.screens.auth.RegisterScreen
import com.example.levelupprueba.ui.screens.blog.BlogListScreen
import com.example.levelupprueba.ui.screens.home.HomeScreenProductos
import com.example.levelupprueba.ui.screens.productos.ProductosScreen
import com.example.levelupprueba.viewmodel.*
import kotlinx.coroutines.launch

// Definición de rutas de navegación
sealed class Screen(val route: String, val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    object Home : Screen("home", "Inicio", Icons.Filled.Home)
    object Productos : Screen("productos", "Productos", Icons.Filled.ShoppingCart)
    object Blog : Screen("blogs", "Blog", Icons.Filled.Article)
    object Perfil : Screen("perfil", "Perfil", Icons.Filled.Person)
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(mainViewModel: MainViewModel, navController: NavHostController) {

    // ViewModels compartidos
    val usuarioViewModel: UsuarioViewModel = viewModel()
    val blogViewModel: BlogViewModel = viewModel()
    val productoViewModel: ProductoViewModel = viewModel()
    val loginViewModel: LoginViewModel = viewModel()

    // Estado de login
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val userSessionFlow = getUserSessionFlow(context)
    val userSession by userSessionFlow.collectAsState(initial = null)
    val isLoggedIn = userSession != null && userSession?.userId.isNullOrBlank()
    // Items del bottom navigation
    val bottomNavItems = listOf(
        Screen.Home,
        Screen.Productos,
        Screen.Blog,
        Screen.Perfil
    )

    Scaffold(
        topBar = {
            LevelUpMainTopBar(
                isLoggedIn = isLoggedIn,
                nombre = userSession?.displayName,
                apellidos = null,
                onMenuClick = {
                    // TODO: Abrir menú
                },
                onCartClick = {
                    // TODO: Ir al carrito
                },
                onProfileClick = {
                    // TODO: Ir al perfil
                },
                onSearchClick = {

                }
            )
        },
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                bottomNavItems.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            // Si intenta ir a perfil sin login, redirige a login
                            if (screen.route == "perfil" && !isLoggedIn) {
                                val intent = Intent(context, AuthActivity::class.java)
                                intent.putExtra("startDestination", "login")
                                context.startActivity(intent)
                                (context as? Activity)?.finish()
                            } else {
                                coroutineScope.launch {
                                    mainViewModel.navigateTo(screen.route)
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Home
            composable(Screen.Home.route) {
                HomeScreenProductos(
                    viewModel = productoViewModel,
                    onVerMasClick = {
                        navController.navigate(Screen.Productos.route)
                    }
                )
            }

            // Productos
            composable(Screen.Productos.route) {
                ProductosScreen(viewModel = productoViewModel)
            }

            // Blog
            composable(Screen.Blog.route) {
                BlogListScreen(blogViewModel)
            }

            // Login
            composable("login") {
                LoginScreen(navController = navController, viewModel = loginViewModel)
            }

            // Registro
            composable("registro") {
                RegisterScreen(
                    mainViewModel = mainViewModel,
                    viewModel = usuarioViewModel
                )
            }

            // Perfil (requiere login)
            composable(Screen.Perfil.route) {
                if (isLoggedIn) {
                    // TODO: Pantalla de perfil
                    Text("Perfil de usuario")
                } else {
                    // Redirige a login
                    LaunchedEffect(Unit) {
                        navController.navigate("login")
                    }
                }
            }
        }
    }
}

