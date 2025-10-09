package com.example.levelupprueba.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.levelupprueba.ui.screens.blog.BlogListScreen
import com.example.levelupprueba.ui.screens.home.HomeScreenProductos
import com.example.levelupprueba.ui.screens.home.LoginScreen
import com.example.levelupprueba.ui.screens.home.RegisterScreen
import com.example.levelupprueba.ui.screens.productos.ProductosScreen
import com.example.levelupprueba.viewmodel.*

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
fun MainScreen() {
    val navController = rememberNavController()
    
    // ViewModels compartidos
    val usuarioViewModel: UsuarioViewModel = viewModel()
    val blogViewModel: BlogViewModel = viewModel()
    val productoViewModel: ProductoViewModel = viewModel()
    val loginViewModel: LoginViewModel = viewModel()

    // Estado de sesión (simplificado por ahora)
    var isLoggedIn by remember { mutableStateOf(false) }

    // Items del bottom navigation
    val bottomNavItems = listOf(
        Screen.Home,
        Screen.Productos,
        Screen.Blog,
        Screen.Perfil
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Level-Up",
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    // Botones de Login/Registro solo si no está logueado
                    if (!isLoggedIn) {
                        TextButton(onClick = {
                            navController.navigate("login")
                        }) {
                            Text("Login")
                        }
                        TextButton(onClick = {
                            navController.navigate("registro")
                        }) {
                            Text("Registro")
                        }
                    } else {
                        IconButton(onClick = { /* TODO: Menú de perfil */ }) {
                            Icon(Icons.Filled.AccountCircle, contentDescription = "Perfil")
                        }
                    }
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
                                navController.navigate("login")
                            } else {
                                navController.navigate(screen.route) {
                                    // Evita múltiples copias de la misma pantalla
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
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
                LoginScreen(viewModel = loginViewModel)
            }

            // Registro
            composable("registro") {
                RegisterScreen(
                    navController = navController,
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

