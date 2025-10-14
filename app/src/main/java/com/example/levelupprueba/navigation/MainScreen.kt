package com.example.levelupprueba.navigation

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
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
import com.example.levelupprueba.data.local.clearUserSession
import com.example.levelupprueba.data.local.getUserSessionFlow
import com.example.levelupprueba.ui.components.DrawerSection
import com.example.levelupprueba.ui.components.LevelUpDrawer
import com.example.levelupprueba.ui.components.LevelUpMainTopBar
import com.example.levelupprueba.ui.screens.auth.LoginScreen
import com.example.levelupprueba.ui.screens.auth.RegisterScreen
import com.example.levelupprueba.ui.screens.blog.BlogListScreen
import com.example.levelupprueba.ui.screens.eventos.EventoScreen
import com.example.levelupprueba.ui.screens.home.HomeScreenProductos
import com.example.levelupprueba.ui.screens.productos.ProductosScreen
import com.example.levelupprueba.viewmodel.*
import kotlinx.coroutines.launch

// Definición de rutas de navegación
sealed class Screen(val route: String, val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    object Home : Screen("home", "Inicio", Icons.Filled.Home)
    object Productos : Screen("productos", "Productos", Icons.Filled.ShoppingCart)
    object Blog : Screen("blogs", "Blog", Icons.Filled.Article)
    object Eventos : Screen("eventos", "Eventos", Icons.Filled.Event) //Agregado para eventos gaming
    object Perfil : Screen("perfil", "Perfil", Icons.Filled.Person)
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    navController: NavHostController,
    usuarioViewModel: UsuarioViewModel,
    loginViewModel: LoginViewModel,
    blogViewModel: BlogViewModel,
    productoViewModel: ProductoViewModel,
    eventoViewModel: EventoViewModel
) {

    // Estado de login
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val userSessionFlow = getUserSessionFlow(context)
    val userSession by userSessionFlow.collectAsState(initial = null)
    val isLoggedIn = userSession != null && !userSession?.userId.isNullOrBlank()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    // Items del bottom navigation
    val bottomNavItems = listOf(
        Screen.Home,
        Screen.Productos,
        Screen.Blog,
        Screen.Eventos, // Agregado para eventos gaming
        Screen.Perfil
    )

    val drawerSections = listOf(
        DrawerSection(
            icon = Icons.Default.Home,
            label = "Inicio"
        ),
        DrawerSection(
            icon = Icons.Default.ShoppingCart,
            label = "Productos"
        ),
        DrawerSection(
            icon = Icons.Default.Article,
            label = "Blog"
        ),
        DrawerSection(
            icon = Icons.Default.Person,
            label = "Perfil"
        )
    )


    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }
    ) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            gesturesEnabled = true,
            drawerContent = {
                LevelUpDrawer(
                    isLoggedIn = isLoggedIn,
                    userName = userSession?.displayName,
                    avatar = null,
                    onBackClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                    },
                    onProfileClick = {
                        if (!isLoggedIn) {
                            val intent = Intent(context, AuthActivity::class.java)
                            intent.putExtra("startDestination", "login")
                            context.startActivity(intent)
                            (context as? Activity)?.finish()
                        } else {
                            coroutineScope.launch {
                                drawerState.close()
                            }
                            navController.navigate(Screen.Perfil.route)
                        }
                    },
                    onLogoutClick = {
                        coroutineScope.launch {
                            clearUserSession(context)
                            drawerState.close()

                            val intent = Intent(context, AuthActivity::class.java)
                            intent.putExtra("startDestination", "login")
                            context.startActivity(intent)
                            (context as? Activity)?.finish()
                        }
                    },
                    onSectionClick = { section ->
                        coroutineScope.launch { drawerState.close() }
                        when (section.label) {
                            "Inicio" -> navController.navigate(Screen.Home.route)
                            "Productos" -> navController.navigate(Screen.Productos.route)
                            "Blog" -> navController.navigate(Screen.Blog.route)
                            "Perfil" -> {
                                if (!isLoggedIn) {
                                    val intent = Intent(context, AuthActivity::class.java)
                                    intent.putExtra("startDestination", "login")
                                    context.startActivity(intent)
                                    (context as? Activity)?.finish()
                                } else {
                                    navController.navigate(Screen.Perfil.route)
                                }
                            }
                        }
                    },
                    sections = drawerSections
                )
            }
        ) {
            Scaffold(
                topBar = {
                    LevelUpMainTopBar(
                        isLoggedIn = isLoggedIn,
                        nombre = userSession?.displayName,
                        onMenuClick = {
                            coroutineScope.launch {
                                drawerState.open()
                            }
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
                    startDestination = Screen.Eventos.route, // ⛔⛔⛔⛔⛔⛔⛔ CAMBIADO TEMPORALMENTE A EVENTOS PARA TESTING⛔⛔⛔⛔⛔
                    // startDestination = Screen.Home.route, // ← Descomentar esto para volver al inicio normal
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

                    // Eventos - Pantalla de eventos gaming y sistema LevelUp
                    composable(Screen.Eventos.route) {
                        EventoScreen(viewModel = eventoViewModel)
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
    }
}

