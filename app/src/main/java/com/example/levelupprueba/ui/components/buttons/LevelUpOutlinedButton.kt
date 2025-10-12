package com.example.levelupprueba.ui.components.buttons

import androidx.compose.runtime.getValue
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.levelupprueba.ui.components.animatedTapScale
import com.example.levelupprueba.ui.theme.ButtonColors
import com.example.levelupprueba.ui.theme.Dimens
import com.example.levelupprueba.ui.theme.TextDisabled

/**
 * Botón outlined con borde degradado y fondo transparente al estilo LevelUp
 */
@Composable
fun LevelUpOutlinedButton(
    text: String,
    textTint: Color = MaterialTheme.colorScheme.onSurface,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    dimens: Dimens,
    icon: ImageVector? = null,
    iconTint: Color = MaterialTheme.colorScheme.onSurface,
    enabled: Boolean = true,
    shape: Shape = MaterialTheme.shapes.extraLarge,
) {
    val shape = shape

    // Animaciones de colores (degradado dinámico)
    val animatedStartColor by animateColorAsState(
        targetValue = if (enabled) ButtonColors.LevelUpGradientStart else ButtonColors.Disabled
    )
    val animatedEndColor by animateColorAsState(
        targetValue = if (enabled) ButtonColors.LevelUpGradientEnd else ButtonColors.Disabled
    )
    val animatedTextColor by animateColorAsState(
        targetValue = if (enabled) textTint else TextDisabled.copy(alpha = 0.9f)
    )
    val animatedIconColor by animateColorAsState(
        targetValue = if (enabled) iconTint else TextDisabled.copy(alpha = 0.9f)
    )

    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .animatedTapScale(interactionSource)
            .clip(RoundedCornerShape(dimens.buttonCornerRadius))
            // Borde degradado
            .border(
                width = dimens.dividerThickness,
                brush = Brush.horizontalGradient(
                    colors = listOf(animatedStartColor, animatedEndColor)
                ),
                shape = shape
            )
            // Fondo semitransparente para contraste
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
            .height(dimens.buttonHeight)
            .fillMaxWidth()
            .clickable(
                enabled = enabled,
                onClick = onClick,
                indication = ripple(color = Color.White.copy(alpha = 0.3f)),
                interactionSource = interactionSource
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = dimens.buttonHorizontalPadding)
        ) {
            if (icon != null) {
                Icon(
                    icon,
                    contentDescription = "Icono de Botón",
                    modifier = Modifier.size(dimens.buttonIconSize),
                    tint = animatedIconColor
                )
                Spacer(Modifier.width(dimens.smallSpacing))
            }
            Text(
                text =  text, // Texto principal del botón
                color = animatedTextColor, // Color del texto según el estado
                style = MaterialTheme.typography.labelLarge, // Estilo de texto del botón
                fontSize = dimens.buttonTextSize // Tamaño de fuente del texto
            )
        }
    }
}
