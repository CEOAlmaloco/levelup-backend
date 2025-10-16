package com.example.levelupprueba.ui.components.buttons

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.levelupprueba.ui.components.animations.animatedTapScale
import com.example.levelupprueba.ui.theme.ButtonColors
import com.example.levelupprueba.ui.theme.Dimens
import com.example.levelupprueba.ui.theme.LocalDimens

/**
 * Colores personalizados para Button siguiendo la paleta de la app LevelUp
 *
 * Este helper centraliza los colores de los botones para mantener consistencia visual
 * en la app.
 */

/**
 * Botón de menú personalizado para la app LevelUp.
 *
 * Este botón aplica colores y estilos de manera consistente con la paleta de la app para el menú.
 *
 * @param text Texto visible dentro del botón.
 * @param modifier Modificar para personalizar el estilo del botón.
 * @param onClick Accion que se ejecuta al presionar el botón.
 * @param icon (Opcional) Icono a mostrar antes del texto.
 * @param iconTint Color del icono. Por defecto tiene el color onPrimary.
 * @param enabled Esto indica si el boton esta deshabilitado o no, dependiendo si es true o false.
 */
@Composable
fun MenuButton(
    text: String, // Texto que se va a mostrar dentro del botón
    modifier: Modifier = Modifier, // Permite modificar el botón desde afuera
    onClick: () -> Unit, // Función que se ejecuta al hacer clic en el botón
    dimens: Dimens = LocalDimens.current,
    containerColor: Color = ButtonColors.MenuBackground, // Color de fondo del botón
    contentColor: Color = ButtonColors.MenuContent, // Color del texto del botón
    icon: ImageVector? = null, // Opcional: icono a mostrar junto al texto (si es null no se muestra)
    iconTint: Color = MaterialTheme.colorScheme.onPrimary, // Color del icono, por defecto uno del tema
    enabled: Boolean = true, // Si está en false, el botón aparece deshabilitado y no responde al tap
    shape: Shape = RoundedCornerShape(dimens.buttonCornerRadius) // Refuerza el borde redondeado del botón
)  {

    //Animacion del scale al tap
    val interactionSource = remember { MutableInteractionSource() }

    Button(

        onClick = onClick,  // Acción a ejecutar al presionar el botón
        enabled = enabled, // Si está en false, el botón se muestra deshabilitado
        modifier = modifier
            .animatedTapScale(interactionSource = interactionSource)
            .height(dimens.buttonHeight),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = shape // Refuerza el borde redondeado del botón

    ){
        // Si el parámetro icon no es null, muestra el icono antes del texto
        if (icon != null){
            Icon(
                icon, // Vector de imagen del icono a mostrar
                contentDescription = null, // Descripción para accesibilidad (puedes poner texto si quieres)
                modifier = Modifier.size(dimens.buttonIconSize), // Aplica el tamaño especificado al icono
                tint = iconTint // Aplica el color especificado al icono
            )
            Spacer(Modifier.width(dimens.smallSpacing)) // Espacio horizontal entre el icono y el texto
        }
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            fontSize = dimens.buttonTextSize
        ) // Muestra el texto del botón)
    }
}