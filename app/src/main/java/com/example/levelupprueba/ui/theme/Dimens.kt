package com.example.levelupprueba.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

// Data class que agrupa todas las dimensiones que usarás en la UI.
// Así puedes tener sets de dimensiones para distintos tamaños de pantalla.
data class Dimens(
    val screenPadding: Dp,    // Margen general alrededor de la pantalla/formulario
    val sectionSpacing: Dp,   // Espacio vertical/entre secciones del formulario
    val fieldSpacing: Dp,     // Espacio vertical/entre campos del formulario
    val buttonHeight: Dp,     // Altura estándar de los botones
    val titleSpacing: Dp,      // Espacio debajo del título principal
    val iconSize: Dp,           // Tamaño del icono
    val smallSpacing: Dp,      // Espaciado pequeño (ej. entre icono y texto)
    val mediumSpacing: Dp,     // Espaciado mediano (ej. entre elementos relacionados)
    val largeSpacing: Dp       // Espaciado grande (ej. entre
)

// Set de dimensiones para pantallas compactas (móviles pequeños)
val compactDimens = Dimens(
    screenPadding = 16.dp,      // Margen lateral agradable y no apretado
    sectionSpacing = 16.dp,     // Aire entre secciones
    fieldSpacing = 8.dp,        // Aire entre campos (inputs)
    buttonHeight = 48.dp,       // Botón suficientemente alto para toque cómodo
    titleSpacing = 12.dp,       // Aire bajo el título principal/subtítulo
    iconSize = 20.dp,           // Iconos pequeños, pero legibles
    smallSpacing = 8.dp,   // Espaciado pequeño (ej. entre icono y texto)
    mediumSpacing = 16.dp,  // Espaciado mediano (ej. entre elementos relacionados)
    largeSpacing = 24.dp   // Espaciado grande (ej. entre secciones
    )

// Set de dimensiones para pantallas medianas (móviles normales, tablets pequeñas)
val mediumDimens = Dimens(
    screenPadding = 20.dp,      // Margen lateral mayor
    sectionSpacing = 24.dp,     // Aire entre secciones más generoso
    fieldSpacing = 12.dp,       // Aire entre campos más cómodo
    buttonHeight = 48.dp,       // Botón ideal para pantallas medianas
    titleSpacing = 16.dp,       // Separación visual bajo títulos
    iconSize = 24.dp,            // Iconos estándar Material Design
    smallSpacing = 8.dp,        // Espaciado pequeño (ej. entre icono y texto)
    mediumSpacing = 20.dp,      // Espaciado mediano (ej. entre elementos relacionados)
    largeSpacing = 32.dp,       // Espaciado grande (ej. entre secciones
)

// Set de dimensiones para pantallas expandidas (tablets grandes, escritorio)
val expandedDimens = Dimens(
    screenPadding = 32.dp,      // Margen lateral generoso en pantallas grandes
    sectionSpacing = 32.dp,     // Mucho aire entre secciones (puedes bajar a 24dp si ves demasiado espacio)
    fieldSpacing = 16.dp,       // Aire entre campos más amplio
    buttonHeight = 56.dp,       // Botón grande y cómodo para pantallas grandes
    titleSpacing = 24.dp,       // Aire bajo título para jerarquía visual
    iconSize = 32.dp,            // Iconos grandes y claros
    smallSpacing = 12.dp,       // Espaciado pequeño (ej. entre icono y texto)
    mediumSpacing = 24.dp,      // Espaciado mediano (ej. entre elementos relacionados)
    largeSpacing = 40.dp        // Espaciado grande (ej. entre secciones
)