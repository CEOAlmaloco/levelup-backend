package com.example.levelupprueba.ui.components.navigation

import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.material3.DrawerState
import com.example.levelupprueba.navigation.Screen

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
                else -> Unit
            }
        },
        sections = drawerSections
    )
}
