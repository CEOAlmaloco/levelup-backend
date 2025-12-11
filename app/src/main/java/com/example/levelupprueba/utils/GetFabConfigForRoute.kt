package com.example.levelupprueba.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import com.example.levelupprueba.navigation.Screen
import com.example.levelupprueba.ui.components.fab.FabConfig

/**
 * Configuración del FAB según la ruta actual.
 * @param route Ruta actual.
 * @param onAddUsuario Acción a realizar al hacer clic en el FAB de agregar usuario.
 * @param onAddProducto Acción a realizar al hacer clic en el FAB de agregar producto.
 * @param onAddCategoria Acción a realizar al hacer clic en el FAB de agregar categoría.
 *
 * @author Christian Mesa
 * */
fun getFabConfigForRoute(
    route: String?,
    onAddUsuario: () -> Unit,
    onAddProducto: () -> Unit,
    onAddCategoria: () -> Unit,
): FabConfig? {

    // Retorna la configuración del FAB según la ruta actual.
    return when (route) {

        // Ruta de agregar usuario.
        Screen.GestionUsuarios.route -> FabConfig(
            icon = Icons.Default.PersonAdd,
            contentDescription = "Agregar Usuario",
            onClickAction = { onAddUsuario }
        )

        /*TODO: Agregar las demas pantallas*/

        else -> null
    }
}