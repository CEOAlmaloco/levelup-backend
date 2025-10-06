package com.example.levelupprueba.ui.components.overlays

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun LevelUpLoadingOverlay(
    modifier: Modifier = Modifier,
    scrimColor: Color = Color.Black.copy(alpha = 0.4f),
    contentAlignment: Alignment = Alignment.Center,
    indicator: @Composable () -> Unit = {CircularProgressIndicator()}
){
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(scrimColor),
        contentAlignment = contentAlignment
    ){
        indicator()
    }
}