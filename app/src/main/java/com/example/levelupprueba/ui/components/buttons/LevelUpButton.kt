package com.example.levelupprueba.ui.components.buttons

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.levelupprueba.ui.theme.ButtonColors
import com.example.levelupprueba.ui.theme.TextDisabled

/**
 * Botón principal personalizado con gradiente horizontal, bordes redondeados y estilo LevelUp
 *
 * Este botón NO usa el componente Button de Material3, sino que se construye manualmente
 * usando Box + Row para tener control total sobre el fondo gradiente y los bordes
 *
 * @param text Texto que se va a mostrar dentro del botón
 * @param modifier Permite modificar el estilo y tamaño del botón desde afuera.
 * @param onClick Acción que se ejecuta al presionar el botón
 * @param icon (Opcional) Icono a mostrar antes del texto
 * @param iconSize Tamaño del icono, por defecto 24.dp
 * @param iconTint Color del icono, por defecto el color onPrimary del tema
 * @param enabled Si está en false, el botón aparece deshabilitado y no responde al tap
 */
@Composable
fun LevelUpButton(
    text: String, // Texto visible dentro del botón.
    textTint: Color = MaterialTheme.colorScheme.onPrimary, // Color del texto del botón
    modifier: Modifier = Modifier, // Permite modificar el botón desde afuera
    onClick: () -> Unit, // Función que se ejecuta al hacer clic en el botón
    icon: ImageVector? = null, // Opcional: icono a mostrar junto al texto (si es null no se muestra)
    iconSize: Dp = 24.dp, // Tamaño del icono, por defecto 24.dp
    iconTint: Color = MaterialTheme.colorScheme.onPrimary, // Color del icono, por defecto el color del tema
    enabled: Boolean = true // Si está en false, el botón aparece deshabilitado y no responde al tap
) {
    // Define la forma del botón: bordes redondeados extra grandes
    val shape = MaterialTheme.shapes.extraLarge

    // Animación de colores del gradiente
    val animatedStartColor by animateColorAsState(
        targetValue = if (enabled) ButtonColors.LevelUpGradientStart else ButtonColors.Disabled
    )
    val animatedEndColor by animateColorAsState(
        targetValue = if (enabled) ButtonColors.LevelUpGradientEnd else ButtonColors.Disabled
    )
    // Animación de color de texto
    val animatedTextColor by animateColorAsState(
        targetValue = if (enabled) textTint else TextDisabled.copy(alpha = 0.9f)
    )
    // Animación de color de icono
    val animatedIconColor by animateColorAsState(
        targetValue = if (enabled) iconTint else TextDisabled.copy(alpha = 0.9f)
    )

    // Construye el botón como un Box con fondo gradiente y bordes redondeados
    Box(
        modifier = modifier
            .clip(shape) // Aplica bordes redondeados
            .background(
                Brush.horizontalGradient(
                    colors = listOf(animatedStartColor, animatedEndColor)
                )
            ) // Aplica el fondo gradiente
            .height(56.dp) // Altura estándar del botón
            .fillMaxWidth() // Ocupa todo el ancho disponible
            // Si el botón está habilitado, permite el click; si no, no responde
            .then(
                other = if (enabled) {
                    Modifier
                        .clickable(
                            onClick = onClick,
                            indication = ripple(color = Color.White.copy(alpha = 0.3f)),
                            interactionSource = remember { MutableInteractionSource() }
                        )
                } else {
                    Modifier
                }
            ),
        contentAlignment = Alignment.Center // Centra el contenido vertical/horizontalmente
    ) {
        // El contenido del botón es una Row centrada: icono + espacio + texto
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 24.dp) // Padding horizontal interno
        ) {
            if (icon != null) {
                Icon(
                    icon, // Vector de imagen del icono
                    contentDescription = null, // Descripción para accesibilidad (puedes poner texto si quieres)
                    modifier = Modifier.size(iconSize), // Tamaño del icono
                    tint = animatedIconColor // Aplica el color según el estado
                )
                Spacer(Modifier.width(8.dp)) // Espacio horizontal entre el icono y el texto
            }
            Text(
                text =  text, // Texto principal del botón
                color = animatedTextColor, // Color del texto según el estado
                style = MaterialTheme.typography.labelLarge // Estilo de texto del botón
            )
        }
    }
}