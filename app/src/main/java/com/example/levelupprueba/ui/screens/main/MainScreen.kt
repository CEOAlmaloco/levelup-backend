package com.example.levelupprueba.ui.screens.main

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.levelupprueba.AuthActivity
import com.example.levelupprueba.model.auth.UserSession
import com.example.levelupprueba.ui.components.navigation.LevelUpDrawerContent
import com.example.levelupprueba.navigation.MainNavGraph
import com.example.levelupprueba.ui.screens.profile.ProfileStatusHandler
import com.example.levelupprueba.navigation.Screen
import com.example.levelupprueba.ui.components.GlobalSnackbarHost
import com.example.levelupprueba.ui.components.LevelUpCustomSnackbar
import com.example.levelupprueba.ui.components.navigation.DrawerSection
import com.example.levelupprueba.ui.components.navigation.LevelUpMainTopBar
import com.example.levelupprueba.ui.components.navigation.LevelUpNavigationBar
import com.example.levelupprueba.ui.components.rememberLastValidSnackbarState
import com.example.levelupprueba.ui.screens.profile.PasswordStatusHandler
import com.example.levelupprueba.viewmodel.*
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    userSession: UserSession?,
    isLoading: Boolean,
    avatar: String?,
    mainViewModel: MainViewModel,
    navController: NavHostController,
    usuarioViewModel: UsuarioViewModel,
    loginViewModel: LoginViewModel,
    blogViewModel: BlogViewModel,
    productoViewModel: ProductoViewModel,
    eventoViewModel: EventoViewModel,
    productoDetalleViewModel: ProductoDetalleViewModel,
    profileViewModel: ProfileViewModel,
    changePasswordViewModel: ChangePasswordViewModel,
    // opcional: permite cambiar startDestination sin tocar el código
    startOnEventosForTesting: Boolean = false
) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val isLoggedIn = userSession?.userId?.isNotBlank() == true
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val currentTitle = Screen.Screens.all.find { it.route == currentRoute }?.title ?: "LevelUp"

    val bottomNavItems = listOf(Screen.Home, Screen.Productos, Screen.Blog, Screen.Eventos)
    val showBottomBar = currentRoute in bottomNavItems.map { it.route }

    val drawerSections = listOf(
        DrawerSection(icon = Icons.Default.Home, label = "Inicio"),
        DrawerSection(icon = Icons.Default.ShoppingCart, label = "Productos"),
        DrawerSection(icon = Icons.Default.Article, label = "Blog"),
        DrawerSection(icon = Icons.Default.Event, label = "Eventos")
    )

    fun handleProfileNavigation() {
        if (!isLoggedIn) {
            Intent(context, AuthActivity::class.java).apply {
                putExtra("startDestination", "login")
                context.startActivity(this)
                (context as? Activity)?.finish()
            }
        } else {
            coroutineScope.launch {
                mainViewModel.navigateTo(Screen.Perfil.route)
            }
        }
    }

    val profileUiState by profileViewModel.estado.collectAsState()
    var showOverlayAfterDelete by remember { mutableStateOf(false) }

    LaunchedEffect(isLoggedIn, userSession?.userId) {
        if (isLoggedIn && userSession?.userId != null) {
            profileViewModel.cargarDatosUsuario(userSession.userId)
        }
    }

    val updatedDisplayName = remember(profileUiState.nombre.valor, userSession?.displayName) {
        profileUiState.nombre.valor.takeIf { it.isNotBlank() } ?: userSession?.displayName
    }
    val updatedAvatar = remember(profileUiState.avatar, avatar) {
        profileUiState.avatar.takeIf { it?.isNotBlank() == true } ?: avatar
    }

    val snackbarHostState = remember { SnackbarHostState() }

    val globalSnackbarState by mainViewModel.globalSnackbarState.collectAsState()

    val passwordStatus by changePasswordViewModel.status.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }
    ) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            gesturesEnabled = currentRoute != Screen.Perfil.route,
            drawerContent = {
                LevelUpDrawerContent(
                    drawerSections = drawerSections,
                    isLoggedIn = isLoggedIn,
                    updatedDisplayName = updatedDisplayName,
                    updatedAvatar = updatedAvatar,
                    drawerState = drawerState,
                    coroutineScope = coroutineScope,
                    onNavigate = { route ->
                        coroutineScope.launch {
                            mainViewModel.navigateTo(route)
                        }
                    },
                    onLogout = { loginViewModel.logout(context, mainViewModel) },
                    onProfileClick = { handleProfileNavigation() }
                )
            }
        ) {
            Scaffold(
                topBar = {
                    if (currentRoute == Screen.Perfil.route) {
                        LevelUpMainTopBar(
                            title = "Perfil",
                            isLoggedIn = isLoggedIn,
                            nombre = updatedDisplayName,
                            avatar = updatedAvatar,
                            showMenu = false,
                            showCart = false,
                            showProfile = false,
                            showSearch = false,
                            showBackArrow = true,
                            onBackClick = {
                                coroutineScope.launch {
                                    mainViewModel.navigateBack()
                                }
                            },
                            onMenuClick = {},
                            onCartClick = {},
                            onProfileClick = {},
                            onSearchClick = {}
                        )
                    } else {
                        LevelUpMainTopBar(
                            title = currentTitle,
                            isLoggedIn = isLoggedIn,
                            nombre = updatedDisplayName,
                            avatar = updatedAvatar,
                            onMenuClick = { coroutineScope.launch { drawerState.open() } },
                            onCartClick = { /* TODO: ir al carrito */ },
                            onProfileClick = { handleProfileNavigation() },
                            onSearchClick = { /* TODO */ }
                        )
                    }
                },
                bottomBar = {
                    if (showBottomBar) {
                        LevelUpNavigationBar(
                            navController = navController,
                            bottomNavItems = bottomNavItems,
                            coroutineScope = coroutineScope,
                            mainViewModel = mainViewModel
                        )
                    }
                },
                snackbarHost = {
                    SnackbarHost(
                        hostState = snackbarHostState,
                        snackbar = { snackbarData ->
                            LevelUpCustomSnackbar(
                                snackbarData = snackbarData,
                                snackbarState = rememberLastValidSnackbarState(globalSnackbarState)
                            )
                        }
                    )
                }
            ) { innerPadding ->
                // startDestination configurable para testing
                val startDestination = if (startOnEventosForTesting) Screen.Eventos.route else Screen.Home.route

                MainNavGraph(
                    navController = navController,
                    startDestination = startDestination,
                    mainViewModel = mainViewModel,
                    usuarioViewModel = usuarioViewModel,
                    loginViewModel = loginViewModel,
                    blogViewModel = blogViewModel,
                    productoViewModel = productoViewModel,
                    eventoViewModel = eventoViewModel,
                    productoDetalleViewModel = productoDetalleViewModel,
                    profileViewModel = profileViewModel,
                    changePasswordViewModel = changePasswordViewModel,
                    innerPadding = innerPadding,
                    isLoading = isLoading,
                    isLoggedIn = isLoggedIn,
                    userSessionId = userSession?.userId,
                    userDisplayName = userSession?.displayName
                )
            }
        }

        GlobalSnackbarHost(
            snackbarState = globalSnackbarState,
            snackbarHostState = snackbarHostState,
            onDismiss = {
                mainViewModel.clearSnackbar()
                changePasswordViewModel.resetPasswordStatus()
            }
        )

        // Overlay global / status dialogs
        ProfileStatusHandler(
            profileUiState = profileUiState,
            showOverlayAfterDelete = showOverlayAfterDelete,
            onOverlayAfterDeleteChange = { showOverlayAfterDelete = it },
            loginViewModel = loginViewModel,
            mainViewModel = mainViewModel,
            context = context,
            profileViewModel = profileViewModel
        )
        PasswordStatusHandler(
            status = passwordStatus,
            mainViewModel = mainViewModel
        )
    }
}
