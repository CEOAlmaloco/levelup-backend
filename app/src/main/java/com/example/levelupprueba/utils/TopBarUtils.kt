package com.example.levelupprueba.utils

import androidx.navigation.NavBackStackEntry
import com.example.levelupprueba.navigation.Screen

fun getTopBarTitle(currentRoute: String?, navBackStackEntry: NavBackStackEntry?): String {
    return when (currentRoute) {
        Screen.Perfil.route -> "Perfil"
        Screen.ProductoDetalle.route -> {
            // Obtener el productoId del NavBackStackEntry
            val productoId = navBackStackEntry?.arguments?.getString("productoId")
            if (!productoId.isNullOrBlank()) productoId else "Detalle producto"
        }
        // Otros casos dinámicos...
        else -> Screen.Screens.all.find { it.route == currentRoute }?.title ?: "LevelUp"
    }
}