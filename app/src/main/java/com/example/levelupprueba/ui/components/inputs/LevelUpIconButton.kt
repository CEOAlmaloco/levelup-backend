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
