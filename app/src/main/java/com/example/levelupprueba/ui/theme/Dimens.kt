package com.example.levelupprueba.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

// Data class que agrupa todas las dimensiones que usarás en la UI.
// Así puedes tener sets de dimensiones para distintos tamaños de pantalla.
data class Dimens(
    val screenPadding: Dp,    // Margen general alrededor de la pantalla/formulario
    val fieldSpacing: Dp,     // Espacio vertical/entre campos del formulario
    val buttonHeight: Dp,     // Altura estándar de los botones
    val titleSpacing: Dp,      // Espacio debajo del título principal
    val iconSize: Dp           // Tamaño del icono
)

// Set de dimensiones para pantallas compactas (móviles pequeños)
val compactDimens = Dimens(
    screenPadding = 8.dp,     // Menor margen externo
    fieldSpacing = 8.dp,      // Menor espacio entre campos
    buttonHeight = 40.dp,     // Botón más pequeño
    titleSpacing = 12.dp,     // Menor espacio bajo el título
    iconSize = 20.dp          // Icono más pequeño
)

// Set de dimensiones para pantallas medianas (móviles normales, tablets pequeñas)
val mediumDimens = Dimens(
    screenPadding = 16.dp,    // Margen estándar
    fieldSpacing = 12.dp,      // Espacio estándar entre campos
    buttonHeight = 48.dp,     // Botón estándar
    titleSpacing = 16.dp,      // Espacio estándar bajo el título
    iconSize = 24.dp          //Tamaño estandar para icono
)

// Set de dimensiones para pantallas expandidas (tablets grandes, escritorio)
val expandedDimens = Dimens(
    screenPadding = 24.dp,    // Mayor margen externo
    fieldSpacing = 16.dp,     // Mayor espacio entre campos
    buttonHeight = 56.dp,     // Botón más alto
    titleSpacing = 24.dp,      // Más espacio bajo el título
    iconSize = 32.dp          // Mayor tamaño para icono
)