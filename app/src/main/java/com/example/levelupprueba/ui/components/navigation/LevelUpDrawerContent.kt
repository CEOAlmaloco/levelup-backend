package com.example.levelupprueba.ui.components.navigation

import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.material3.DrawerState
import com.example.levelupprueba.navigation.Screen

/**
 * Contenido del Drawer de navegación para la app LevelUp
 * @param drawerSections Lista de secciones del drawer
 * @param isLoggedIn Indica si el usuario está logueado
 * @param updatedDisplayName Nombre de usuario actualizado
 * @param updatedAvatar Avatar actualizado
 * @param drawerState Estado del drawer
 * @param coroutineScope Alcance de la corrutina
 * @param onNavigate Función para navegar a otra pantalla
 * @param onLogout Función para cerrar sesión
 * @param onProfileClick Función para navegar a la pantalla de perfil
 *
 * @author Christian Mesa
 * */
@Composable
fun LevelUpDrawerContent(
    drawerSections: List<DrawerSection>,
    isLoggedIn: Boolean,
    updatedDisplayName: String?,
    updatedAvatar: String?,
    drawerState: DrawerState,
    coroutineScope: CoroutineScope,
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit,
    onProfileClick: () -> Unit
) {
    LevelUpDrawer(
        isLoggedIn = isLoggedIn,
        userName = updatedDisplayName,
        avatar = updatedAvatar,
        onBackClick = { coroutineScope.launch { drawerState.close() } },
        onProfileClick = {
            coroutineScope.launch { drawerState.close() }
            onProfileClick()
        },
        onLogoutClick = {
            coroutineScope.launch {
                onLogout()
                drawerState.close()
            }
        },
        onSectionClick = { section ->
            coroutineScope.launch { drawerState.close() }
            when (section.label) {
                "Inicio" -> onNavigate(Screen.Home.route)
                "Productos" -> onNavigate(Screen.Productos.route)
                "Blog" -> onNavigate(Screen.Blog.route)
                "Eventos" -> onNavigate(Screen.Eventos.route)
                "Usuarios" -> onNavigate(Screen.GestionUsuarios.route)
                else -> Unit
            }
        },
        sections = drawerSections
    )
}
