package com.example.levelupprueba.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable

/**
 * Función de navegación principal de la app.
 * Ahora usa MainScreen que incluye:
 * - BottomNavigationBar para navegar entre Home, Productos, Blog, Perfil
 * - TopAppBar con botones de Login/Registro (opcionales)
 * - Login/Registro solo obligatorios para comprar
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(){
    MainScreen()
}
