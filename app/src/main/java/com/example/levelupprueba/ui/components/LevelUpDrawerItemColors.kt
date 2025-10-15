package com.example.levelupprueba.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItemColors
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.runtime.Composable

@Composable
fun levelUpDrawerItemColors(): NavigationDrawerItemColors = NavigationDrawerItemDefaults.colors(
    selectedIconColor = MaterialTheme.colorScheme.onBackground,
    selectedTextColor = MaterialTheme.colorScheme.onBackground,

    unselectedIconColor = MaterialTheme.colorScheme.onSurface,
    unselectedTextColor = MaterialTheme.colorScheme.onSurface,

    selectedContainerColor = MaterialTheme.colorScheme.secondary
)