package com.example.levelupprueba.ui.components.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import com.example.levelupprueba.ui.theme.SemanticColors

/**
 * Colores de la barra de navegación
 *
 * @author Christian Mesa
 * */
@Composable
fun levelUpNavigationBarItemsColors(): NavigationBarItemColors =  NavigationBarItemDefaults.colors(

    selectedTextColor = MaterialTheme.colorScheme.onBackground,
    selectedIconColor = MaterialTheme.colorScheme.onBackground,

    unselectedTextColor = MaterialTheme.colorScheme.onSurface,
    unselectedIconColor = MaterialTheme.colorScheme.onSurface,

    indicatorColor = SemanticColors.AccentBlue
)