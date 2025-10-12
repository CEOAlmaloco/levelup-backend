package com.example.levelupprueba.ui.components.buttons

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.levelupprueba.ui.theme.Dimens


@Composable
fun LevelUpTextButton(
    text: String,
    onClick: () -> Unit,
    icon: ImageVector?,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    modifier: Modifier = Modifier,
    dimens: Dimens
) {
    TextButton(
        onClick = onClick,
        modifier = modifier
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = "Icono de Botón",
            )
        }
        Text(
            text = text,
            color = textColor,
            style = MaterialTheme.typography.labelLarge,
            fontSize = dimens.buttonTextSize
        )
    }
}