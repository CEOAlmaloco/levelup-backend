package com.example.levelupprueba.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Inicio", Icons.Filled.Home)
    object Productos : Screen("productos", "Productos", Icons.Filled.ShoppingCart)
    object Blog : Screen("blogs", "Blog", Icons.Filled.Article)
    object Eventos : Screen("eventos", "Eventos", Icons.Filled.Event)
    object Perfil : Screen("perfil", "Perfil", Icons.Filled.Person)

    object ProductoDetalle : Screen("producto_detalle/{productoId}", "Detalle", Icons.Filled.Info) {
        fun createRoute(productId: String) = "producto_detalle/$productId"
    }

    object Carrito : Screen("carrito", "Carrito", Icons.Filled.ShoppingCart)

    object Screens {
        val all = listOf(Home, Productos, Blog, Eventos, Perfil, Carrito)
    }
}