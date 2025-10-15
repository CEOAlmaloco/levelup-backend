package com.example.levelupprueba.ui.screens.profile

import androidx.compose.ui.graphics.vector.ImageVector

data class Option(
    val label: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)