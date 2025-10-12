package com.example.levelupprueba.ui.components.buttons

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.levelupprueba.ui.components.animatedTapScale
import com.example.levelupprueba.ui.theme.ButtonColors
import com.example.levelupprueba.ui.theme.Dimens
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
 * @param iconTint Color del icono, por defecto el color onPrimary del tema
 * @param enabled Si está en false, el botón aparece deshabilitado y no responde al tap
 */
@Composable
fun LevelUpButton(
    text: String, // Texto visible dentro del botón.
    textTint: Color = MaterialTheme.colorScheme.onPrimary, // Color del texto del botón
    modifier: Modifier = Modifier, // Permite modificar el botón desde afuera
    onClick: () -> Unit, // Función que se ejecuta al hacer clic en el botón
    dimens: Dimens, // Dimensiones personalizadas para el botón
    icon: ImageVector? = null, // Opcional: icono a mostrar junto al texto (si es null no se muestra)
    iconTint: Color = MaterialTheme.colorScheme.onPrimary, // Color del icono, por defecto el color del tema
    enabled: Boolean = true, // Si está en false, el botón aparece deshabilitado y no responde al tap
) {

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

    //Animacion del scale al tap
    val interactionSource = remember { MutableInteractionSource() }

    // Construye el botón como un Box con fondo gradiente y bordes redondeados
    Box(
        modifier = modifier
            .animatedTapScale(interactionSource)
            .clip(RoundedCornerShape(dimens.buttonCornerRadius)) // Aplica bordes redondeados
            .background(
                Brush.horizontalGradient(
                    colors = listOf(animatedStartColor, animatedEndColor)
                )
            ) // Aplica el fondo gradiente
            .height(dimens.buttonHeight) // Altura estándar del botón
            .fillMaxWidth() // Ocupa todo el ancho disponible
            // Si el botón está habilitado, permite el click; si no, no responde
            .clickable(
                enabled = enabled,
                onClick = onClick,
                indication = ripple(color = Color.White.copy(alpha = 0.3f)),
                interactionSource = interactionSource
            ),
        contentAlignment = Alignment.Center // Centra el contenido vertical/horizontalmente
    ) {
        // El contenido del botón es una Row centrada: icono + espacio + texto
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = dimens.buttonHorizontalPadding) // Padding horizontal interno
        ) {
            if (icon != null) {
                Icon(
                    icon, // Vector de imagen del icono
                    contentDescription = "Icono de Botón", // Descripción para accesibilidad (puedes poner texto si quieres)
                    modifier = Modifier.size(dimens.buttonIconSize), // Tamaño del icono
                    tint = animatedIconColor // Aplica el color según el estado
                )
                Spacer(Modifier.width(dimens.smallSpacing)) // Espacio horizontal entre el icono y el texto
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