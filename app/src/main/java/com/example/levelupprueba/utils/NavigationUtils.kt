package com.example.levelupprueba.utils

import com.example.levelupprueba.navigation.Screen

fun isProfileScreen(route: String?) = route == Screen.Perfil.route

fun isCarritoScreen(route: String?) = route == Screen.Carrito.route

fun isProductoDetalleScreen(route: String?) = route == Screen.ProductoDetalle.route

fun shouldShowBottomBar(route: String?): Boolean {
    return route in listOf(
        Screen.Home.route,
        Screen.Productos.route,
        Screen.Blog.route,
        Screen.Eventos.route
    ).map { it }
}

fun shouldShowMenu(route: String?): Boolean {
    return !isProfileScreen(route) && !isProductoDetalleScreen(route) && !isCarritoScreen(route)
}

fun shouldShowBackArrow(route: String?): Boolean {
    return isProfileScreen(route) || isProductoDetalleScreen(route) || isCarritoScreen(route)
}

fun shouldShowCart(route: String?): Boolean = !isProfileScreen(route)

fun shouldShowProfile(route: String?): Boolean = !isProfileScreen(route)

fun shouldShowSearch(route: String?): Boolean = !isProfileScreen(route)

fun isGestureEnabled(route: String?): Boolean = !isProfileScreen(route)