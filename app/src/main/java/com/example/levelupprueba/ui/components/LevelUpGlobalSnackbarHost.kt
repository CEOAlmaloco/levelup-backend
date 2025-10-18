package com.example.levelupprueba.ui.components

import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@Composable
fun GlobalSnackbarHost(
    snackbarState: GlobalSnackbarState,
    snackbarHostState: SnackbarHostState,
    onDismiss: () -> Unit
){
    val scope = rememberCoroutineScope()

    LaunchedEffect(
        snackbarState
    ) {
        when (snackbarState){
            is GlobalSnackbarState.Error -> {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = snackbarState.message,
                        duration = SnackbarDuration.Long
                    )
                    onDismiss()
                }
            }
            is GlobalSnackbarState.Info -> {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = snackbarState.message,
                        duration = SnackbarDuration.Short
                    )
                    onDismiss()
                }
            }
            is GlobalSnackbarState.Success -> {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = snackbarState.message,
                        duration = SnackbarDuration.Short
                    )
                    onDismiss()
                }
            }
            GlobalSnackbarState.Idle -> {}
        }
    }
}