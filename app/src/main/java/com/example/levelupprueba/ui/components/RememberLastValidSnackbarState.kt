package com.example.levelupprueba.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun RememberLastValidSnackbarState(
    current: GlobalSnackbarState
): GlobalSnackbarState {
    var lastValidState by remember { mutableStateOf<GlobalSnackbarState>(GlobalSnackbarState.Idle) }
    if (current !is GlobalSnackbarState.Idle) {
        lastValidState = current
    }
    return lastValidState
}