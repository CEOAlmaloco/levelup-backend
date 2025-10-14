package com.example.levelupprueba.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

/**
 * Helper para animar el tap del boton
 */
@Composable
fun Modifier.animatedTapScale(
    interactionSource: MutableInteractionSource,
): Modifier{
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(if (isPressed) 0.95f else 1f, label = "TapScale")
    return this.graphicsLayer {
        scaleX = scale
        scaleY = scale
    }
}