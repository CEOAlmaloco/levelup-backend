package com.example.levelupprueba.ui.components.fab

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Configuracion para el FAB.
 * @param icon Icono del FAB.
 * @param contentDescription Descripcion del icono.
 * @param onClickAction Accion a realizar al hacer click en el FAB.
 *
 * @author Christian Mesa
 * */
data class FabConfig(
    val icon: ImageVector,
    val contentDescription: String,
    val onClickAction: () -> Unit
)
