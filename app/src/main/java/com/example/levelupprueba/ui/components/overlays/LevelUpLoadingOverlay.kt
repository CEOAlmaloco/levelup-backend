package com.example.levelupprueba.ui.components.overlays

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun LevelUpLoadingOverlay(
    visible: Boolean,
    modifier: Modifier = Modifier,
    scrimColor: Color = MaterialTheme.colorScheme.scrim,
    contentAlignment: Alignment = Alignment.Center,
    indicator: @Composable () -> Unit = {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.onSurface
        )
    }
){
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(scrimColor),
            contentAlignment = contentAlignment
        ) {
            indicator()
        }
    }
}