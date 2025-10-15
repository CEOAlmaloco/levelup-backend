package com.example.levelupprueba.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Data class que agrupa todas las dimensiones que usarás en la UI.
// Así puedes tener sets de dimensiones para distintos tamaños de pantalla.
data class Dimens(
    // Espaciados generales
    val screenPadding: Dp,
    val sectionSpacing: Dp,
    val fieldSpacing: Dp,
    val rowSpacing: Dp,
    val smallSpacing: Dp,
    val mediumSpacing: Dp,
    val largeSpacing: Dp,

    // Tamaños de texto
    val titleSize: TextUnit,
    val subtitleSize: TextUnit,
    val bodySize: TextUnit,
    val captionSize: TextUnit,

    // Botones
    val buttonHeight: Dp,
    val buttonCornerRadius: Dp,
    val buttonIconSize: Dp,
    val buttonHorizontalPadding: Dp,
    val buttonTextSize: TextUnit,

    // Cards y diálogos
    val cardCornerRadius: Dp,
    val cardElevation: Dp,
    val dialogCornerRadius: Dp,
    val overlayCornerRadius: Dp,

    // Imágenes y avatares
    val imageCornerRadius: Dp,
    val avatarSize: Dp,
    val imageHeight: Dp,

    // Iconos
    val iconSize: Dp,
    val largeIconSize: Dp,
    val smallIconSize: Dp,

    // Inputs/campos de texto
    val inputHeight: Dp,
    val inputCornerRadius: Dp,
    val inputPadding: Dp,

    // Otros
    val dividerThickness: Dp,
    val topBarHeight: Dp,
    val bottomBarHeight: Dp,
    val progressBarHeight: Dp,
    val chipHeight: Dp,
    val chipCornerRadius: Dp,
    val dropdownMenuMaxHeight: Dp,

    // Badges

    // BADGE
    val badgeHeight: Dp,
    val badgeCornerRadius: Dp,
    val badgeHorizontalPadding: Dp,
    val badgeVerticalPadding: Dp,
    val badgeTextSize: TextUnit
)

// Set de dimensiones para pantallas compactas (móviles pequeños)
val compactDimens = Dimens(
    screenPadding = 16.dp,
    sectionSpacing = 16.dp,
    fieldSpacing = 8.dp,
    rowSpacing = 8.dp,
    smallSpacing = 8.dp,
    mediumSpacing = 16.dp,
    largeSpacing = 24.dp,
    titleSize = 20.sp,
    subtitleSize = 18.sp,
    bodySize = 16.sp,
    captionSize = 12.sp,
    buttonHeight = 40.dp,
    buttonCornerRadius = 24.dp,
    buttonIconSize = 24.dp,
    buttonHorizontalPadding = 16.dp,
    buttonTextSize = 16.sp,
    cardCornerRadius = 16.dp,
    cardElevation = 4.dp,
    dialogCornerRadius = 24.dp,
    overlayCornerRadius = 24.dp,
    imageCornerRadius = 24.dp,
    avatarSize = 40.dp,
    imageHeight = 200.dp,
    iconSize = 24.dp,
    largeIconSize = 32.dp,
    smallIconSize = 16.dp,
    inputHeight = 56.dp,
    inputCornerRadius = 16.dp,
    inputPadding = 12.dp,
    dividerThickness = 1.dp,
    topBarHeight = 64.dp,
    bottomBarHeight = 56.dp,
    progressBarHeight = 8.dp,
    chipHeight = 32.dp,
    chipCornerRadius = 16.dp,
    dropdownMenuMaxHeight = 240.dp,
    badgeHeight = 24.dp,
    badgeCornerRadius = 12.dp,
    badgeHorizontalPadding = 12.dp,
    badgeVerticalPadding = 4.dp,
    badgeTextSize = 12.sp
)

// Set de dimensiones para pantallas medianas (móviles normales, tablets pequeñas)
val mediumDimens = Dimens(
    screenPadding = 20.dp,
    sectionSpacing = 24.dp,
    fieldSpacing = 12.dp,
    rowSpacing = 12.dp,
    smallSpacing = 12.dp,
    mediumSpacing = 20.dp,
    largeSpacing = 32.dp,
    titleSize = 28.sp,
    subtitleSize = 22.sp,
    bodySize = 18.sp,
    captionSize = 14.sp,
    buttonHeight = 48.dp,
    buttonCornerRadius = 28.dp,
    buttonIconSize = 28.dp,
    buttonHorizontalPadding = 20.dp,
    buttonTextSize = 18.sp,
    cardCornerRadius = 24.dp,
    cardElevation = 6.dp,
    dialogCornerRadius = 28.dp,
    overlayCornerRadius = 28.dp,
    imageCornerRadius = 32.dp,
    avatarSize = 56.dp,
    imageHeight = 220.dp,
    iconSize = 24.dp,
    largeIconSize = 36.dp,
    smallIconSize = 20.dp,
    inputHeight = 60.dp,
    inputCornerRadius = 20.dp,
    inputPadding = 16.dp,
    dividerThickness = 1.5.dp,
    topBarHeight = 64.dp,
    bottomBarHeight = 64.dp,
    progressBarHeight = 10.dp,
    chipHeight = 36.dp,
    chipCornerRadius = 18.dp,
    dropdownMenuMaxHeight = 285.dp,
    badgeHeight = 28.dp,
    badgeCornerRadius = 14.dp,
    badgeHorizontalPadding = 16.dp,
    badgeVerticalPadding = 6.dp,
    badgeTextSize = 14.sp
)

// Set de dimensiones para pantallas expandidas (tablets grandes, escritorio)
val expandedDimens = Dimens(
    screenPadding = 32.dp,
    sectionSpacing = 32.dp,
    fieldSpacing = 16.dp,
    rowSpacing = 16.dp,
    smallSpacing = 16.dp,
    mediumSpacing = 24.dp,
    largeSpacing = 40.dp,
    titleSize = 32.sp,
    subtitleSize = 26.sp,
    bodySize = 20.sp,
    captionSize = 16.sp,
    buttonHeight = 56.dp,
    buttonCornerRadius = 32.dp,
    buttonIconSize = 32.dp,
    buttonHorizontalPadding = 24.dp,
    buttonTextSize = 20.sp,
    cardCornerRadius = 32.dp,
    cardElevation = 8.dp,
    dialogCornerRadius = 32.dp,
    overlayCornerRadius = 32.dp,
    imageCornerRadius = 40.dp,
    avatarSize = 72.dp,
    imageHeight = 280.dp,
    iconSize = 32.dp,
    largeIconSize = 48.dp,
    smallIconSize = 24.dp,
    inputHeight = 64.dp,
    inputCornerRadius = 24.dp,
    inputPadding = 20.dp,
    dividerThickness = 2.dp,
    topBarHeight = 72.dp,
    bottomBarHeight = 72.dp,
    progressBarHeight = 12.dp,
    chipHeight = 44.dp,
    chipCornerRadius = 22.dp,
    dropdownMenuMaxHeight = 340.dp,
    badgeHeight = 32.dp,
    badgeCornerRadius = 16.dp,
    badgeHorizontalPadding = 20.dp,
    badgeVerticalPadding = 8.dp,
    badgeTextSize = 16.sp
)