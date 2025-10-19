package com.example.levelupprueba.ui.components.inputs

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
/**
 * Boton de icono personalizado LevelUp
 *
 * @param onClick Accion a realizar al hacer click en el boton
 * @param imageVector Icono a mostrar
 * @param contentDescription Descripcion del icono
 * @param modifier Modificadores para personalizar el boton
 * @param tint Color del icono
 * @param iconSize Tamaño del icono
 * @param buttonSize Tamaño del boton
 *
 * @author Christian Mesa
 * */
@Composable
fun LevelUpIconButton(
    onClick: () -> Unit,
    imageVector: ImageVector,
    contentDescription: String,
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colorScheme.onBackground,
    iconSize: Dp? = null, // Si es null, usa el default
    buttonSize: Dp? = null  // Si es null, usa el default
) {
    IconButton(
        onClick = onClick,
        modifier = if (buttonSize != null) modifier
            .size(buttonSize)
            else modifier
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = tint,
            modifier = if (iconSize != null) Modifier.size(iconSize) else Modifier
        )
    }
}
