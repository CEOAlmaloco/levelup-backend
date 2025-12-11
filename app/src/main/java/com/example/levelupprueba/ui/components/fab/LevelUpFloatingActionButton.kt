package com.example.levelupprueba.ui.components.fab

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.levelupprueba.ui.theme.Dimens
import com.example.levelupprueba.ui.theme.LocalDimens
import com.example.levelupprueba.ui.theme.SemanticColors

/**
 * FAB para distintas acciones.
 * @param onClick acción a realizar.
 * @param backgroundColor color de fondo.
 * @param icon icono a mostrar.
 * @param iconTint color del icono.
 * @param contentDescription descripción del icono.
 * @param modifier modificador.
 * @param dimens dimensiones.
 *
 * @author Christian Mesa
 * */
@Composable
fun LevelUpFloatingActionButton(
    onClick: () -> Unit,
    backgroundColor: Color = SemanticColors.AccentBlue,
    icon: ImageVector? = Icons.Default.Add,
    iconTint: Color = MaterialTheme.colorScheme.onBackground,
    contentDescription: String,
    modifier: Modifier = Modifier,
    dimens: Dimens = LocalDimens.current
){

    // FAB con icono
    FloatingActionButton(
        onClick = onClick,
        containerColor = backgroundColor,
        contentColor = iconTint,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = dimens.cardElevation
        ),
        modifier = modifier
            .clip(CircleShape)
    ){
        // Si hay icono se renderiza en el FAB
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
            )
        }
    }
}