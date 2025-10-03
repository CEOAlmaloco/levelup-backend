package com.example.levelupprueba.ui.theme

/**
 * Colores personalizados para OutlinedTextField siguiendo la paleta LevelUp.
 *
 * Este helper centraliza los colores de los campos de texto para mantener consistencia visual
 * en la app. Modifica colores de texto, borde, etiqueta y cursor para todos los estados (enfocado, desenfocado, error, deshabilitado).
 *
 * Como se usa: Llamar a `levelUpTextFieldColors()` en el parámetro `colors` de cada OutlinedTextField.
 * Ejemplo:
 * ```
 * OutlinedTextField(
 *     value = ...,
 *     onValueChange = ...,
 *     label = { Text("Nombre") },
 *     colors = levelUpTextFieldColors(),
 *     modifier = Modifier.fillMaxWidth()
 * )
 * ```
 *
 * Si necesitas que se adapte automáticamente al modo claro/oscuro, puedes usar colores
 * de `MaterialTheme.colorScheme` en vez de los de la paleta fija.
 */
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.*
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun levelUpTextFieldColors(): TextFieldColors = OutlinedTextFieldDefaults.colors(
    focusedTextColor = TextPrimary,        // Color de texto cuando el campo está enfocado
    unfocusedTextColor = TextSecondary,    // Color de texto cuando el campo está desenfocado
    disabledTextColor = TextDisabled,      // Color de texto cuando el campo está deshabilitado
    errorTextColor = AccentRed,            // Color de texto cuando hay error

    focusedBorderColor = AccentBlue,       // Color del borde cuando está enfocado
    unfocusedBorderColor = Divider,        // Color del borde cuando está desenfocado
    errorBorderColor = AccentRed,          // Color del borde en error

    focusedLabelColor = AccentBlue,        // Color de la etiqueta cuando está enfocado
    unfocusedLabelColor = TextSecondary,   // Color de la etiqueta desenfocada
    errorLabelColor = AccentRed,           // Color de la etiqueta en error

    cursorColor = AccentBlue,              // Color del cursor
    errorCursorColor = AccentRed           // Color del cursor en error
)

/**
 * Colores personalizados para Button siguiendo la paleta LevelUp
 * Este helper centraliza los colores de los botones para mantener consistencia visual
 * en la app.
 */
@Composable
fun MenuButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = ButtonMenuBackground,
            contentColor = ButtonMenuContent
        )
    ){
        Text(text)
    }
}


