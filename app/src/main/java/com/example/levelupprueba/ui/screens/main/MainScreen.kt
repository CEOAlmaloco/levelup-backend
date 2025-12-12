package com.example.levelupprueba.ui.screens.main

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
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
import com.example.levelupprueba.ui.components.RememberLastValidSnackbarState
import com.example.levelupprueba.ui.components.fab.LevelUpFloatingActionButton
import com.example.levelupprueba.ui.components.filtros.LevelUpFiltersOverlay
import com.example.levelupprueba.ui.components.navigation.DrawerSection
import com.example.levelupprueba.ui.components.navigation.LevelUpMainTopBar
import com.example.levelupprueba.ui.components.navigation.LevelUpNavigationBar
import com.example.levelupprueba.ui.screens.admin.users.AdminUsuariosStatusHandler
import com.example.levelupprueba.ui.screens.profile.PasswordStatusHandler
import com.example.levelupprueba.utils.getFabConfigForRoute
import com.example.levelupprueba.utils.getTopBarTitle
import com.example.levelupprueba.utils.isGestureEnabled
import com.example.levelupprueba.utils.shouldShowBackArrow
import com.example.levelupprueba.utils.shouldShowBottomBar
import com.example.levelupprueba.utils.shouldShowCart
import com.example.levelupprueba.utils.shouldShowMenu
import com.example.levelupprueba.utils.shouldShowProfile
import com.example.levelupprueba.utils.shouldShowSearch
import com.example.levelupprueba.viewmodel.*
import kotlinx.coroutines.launch

/**
 * Pantalla principal de la aplicacion Level-Up.
 * @param userSession sesion del usuario.
 * @param isLoading indica si se esta cargando.
 * @param avatar avatar del usuario.
 * @param mainViewModel viewmodel principal.
 * @param navController controlador de navegacion.
 * @param carritoViewModel viewmodel del carrito.
 * @param usuarioViewModel viewmodel del usuario.
 * @param usuariosViewModel viewmodel de los usuarios.
 * @param loginViewModel viewmodel de login.
 * @param blogViewModel viewmodel del blog.
 * @param productoViewModel viewmodel de los productos.
 * @param eventoViewModel viewmodel de los eventos.
 * @param productoDetalleViewModel viewmodel del detalle de producto.
 * @param profileViewModel viewmodel del perfil.
 * @param changePasswordViewModel viewmodel de cambio de contraseña.
 * @param startOnEventosForTesting indica si se debe iniciar en la pantalla de eventos.
 *
 * @author Level-Up Team
 * */
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    userSession: UserSession?,
    isLoading: Boolean,
    avatar: String?,
    mainViewModel: MainViewModel,
    navController: NavHostController,
    carritoViewModel: CarritoViewModel,
    usuarioViewModel: UsuarioViewModel,
    usuariosViewModel: UsuariosViewModel,
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
    // -- Esencial para Compose y Context --
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val snackbarHostState = remember { SnackbarHostState() }

    // -- Relacionado al usuario --
    val isLoggedIn = userSession?.userId != null && userSession.userId > 0 && userSession.accessToken.isNotBlank()

    // -- Navegacion --
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val topBarTitle = getTopBarTitle(currentRoute, navBackStackEntry)
    val bottomNavItems = listOf(Screen.Home, Screen.Productos, Screen.Blog, Screen.Eventos)
    val showBottomBar = shouldShowBottomBar(currentRoute)

    // -- Drawer y secciones --
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val isAdmin = userSession?.tipoUsuario == "ADMINISTRADOR"
    val drawerSections = remember(isAdmin) {
        buildList {
            add(DrawerSection(icon = Icons.Default.Home, label = "Inicio"))
            add(DrawerSection(icon = Icons.Default.ShoppingCart, label = "Productos"))
            add(DrawerSection(icon = Icons.Default.Article, label = "Blog"))
            add(DrawerSection(icon = Icons.Default.Event, label = "Eventos"))
            if (isAdmin) {
                add(DrawerSection(icon = Icons.Default.Group, label = "Usuarios"))
            }
        }
    }

    // -- Profile UI State --

    val profileUiState by profileViewModel.estado.collectAsState()
    var showOverlayAfterDelete by remember { mutableStateOf(false) }

    // -- DisplayName y Avatar

    val updatedDisplayName = remember(profileUiState.nombre.valor, userSession?.displayName) {
        profileUiState.nombre.valor.takeIf { it.isNotBlank() } ?: userSession?.displayName
    }
    val updatedAvatar = remember(profileUiState.avatar, avatar) {
        profileUiState.avatar.takeIf { it?.isNotBlank() == true } ?: avatar
    }

    // -- Snackbar/Status
    val globalSnackbarState by mainViewModel.globalSnackbarState.collectAsState()

    val passwordStatus by changePasswordViewModel.status.collectAsState()

    val usuariosState by usuariosViewModel.estado.collectAsState()
    // -- Carrito --

    val cantidadCarrito by carritoViewModel.cantidadCarrito.collectAsState()

    // -- Efects --

    LaunchedEffect(isLoggedIn, userSession?.userId) {
        userSession?.let { session ->
            if (isLoggedIn && session.userId > 0) {
                profileViewModel.cargarDatosUsuario(session.userId.toString())
            }
        }
    }

    // -- Navegacion de Perfil

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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }
    ) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            gesturesEnabled = isGestureEnabled(currentRoute),
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
                    LevelUpMainTopBar(
                        title = topBarTitle,
                        isLoggedIn = isLoggedIn,
                        nombre = updatedDisplayName,
                        avatar = updatedAvatar,
                        showMenu = shouldShowMenu(currentRoute),
                        showCart = shouldShowCart(currentRoute),
                        showProfile = shouldShowProfile(currentRoute),
                        showSearch = shouldShowSearch(currentRoute),
                        showBackArrow = shouldShowBackArrow(currentRoute),
                        onBackClick = { coroutineScope.launch { mainViewModel.navigateBack() } },
                        onMenuClick = { coroutineScope.launch { drawerState.open() } },
                        onCartClick = {
                            if (currentRoute != Screen.Carrito.route) {
                                coroutineScope.launch { mainViewModel.navigateTo(Screen.Carrito.route) }
                            }
                        },
                        onProfileClick = { handleProfileNavigation() },
                        onSearchClick = { query ->
                            productoViewModel.actualizarTextoBusqueda(query)
                            if (currentRoute != Screen.Productos.route) {
                                coroutineScope.launch { mainViewModel.navigateTo(Screen.Productos.route) }
                            }
                        },
                        cantidadCarrito = cantidadCarrito
                    )
                },
                floatingActionButton = {
                    val fabConfig = getFabConfigForRoute(
                        route = currentRoute,
                        onAddUsuario = {/*TODO*/},
                        onAddProducto = {/*TODO*/},
                        onAddCategoria = {/*TODO*/}
                    )
                    AnimatedVisibility(visible = fabConfig != null) {
                        fabConfig?.let {
                            LevelUpFloatingActionButton(
                                onClick = it.onClickAction,
                                icon = it.icon,
                                contentDescription = it.contentDescription
                            )
                        }
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
                                snackbarState = RememberLastValidSnackbarState(globalSnackbarState)
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
                    usuariosViewModel = usuariosViewModel,
                    loginViewModel = loginViewModel,
                    blogViewModel = blogViewModel,
                    productoViewModel = productoViewModel,
                    carritoViewModel = carritoViewModel,
                    eventoViewModel = eventoViewModel,
                    productoDetalleViewModel = productoDetalleViewModel,
                    profileViewModel = profileViewModel,
                    changePasswordViewModel = changePasswordViewModel,
                    innerPadding = innerPadding,
                    isLoading = isLoading,
                    isLoggedIn = isLoggedIn,
                    userSessionId = userSession?.userId?.toString(),
                    userDisplayName = userSession?.displayName,
                    tipoUsuario = userSession?.tipoUsuario
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
        LevelUpFiltersOverlay(
            productoViewModel = productoViewModel,

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
        AdminUsuariosStatusHandler(
            usuariosUiState = usuariosState,
            mainViewModel = mainViewModel,
            usuariosViewModel = usuariosViewModel
        )
    }
}
