package com.example.levelupprueba.ui.screens.profile

import androidx.compose.runtime.Composable
import com.example.levelupprueba.model.password.PasswordStatus
import com.example.levelupprueba.ui.components.overlays.LevelUpLoadingOverlay
import com.example.levelupprueba.viewmodel.MainViewModel

@Composable
fun PasswordStatusHandler(
    status: PasswordStatus,
    mainViewModel: MainViewModel
){
    when (status){
        is PasswordStatus.Saving -> {
            LevelUpLoadingOverlay()
        }
        is PasswordStatus.Success -> {
            mainViewModel.showSuccessSnackbar("Contraseña guardada correctamente")
        }
        is PasswordStatus.Error -> {
            mainViewModel.showErrorSnackbar(status.mensaje)
        }
        is PasswordStatus.ValidationError -> {
            mainViewModel.showErrorSnackbar(status.errorMessage)
        }
        is PasswordStatus.Idle -> {}
    }
}