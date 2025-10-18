package com.example.levelupprueba.ui.components

sealed class GlobalSnackbarState {
    data class Error(val message: String) : GlobalSnackbarState()
    data class Info(val message: String) : GlobalSnackbarState()
    data class Success(val message: String) : GlobalSnackbarState()
    object Idle : GlobalSnackbarState()
}