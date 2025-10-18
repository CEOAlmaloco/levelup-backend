package com.example.levelupprueba.ui.screens.profile

import android.content.Context
import androidx.compose.runtime.Composable
import com.example.levelupprueba.model.profile.ProfileStatus
import com.example.levelupprueba.model.profile.ProfileUiState
import com.example.levelupprueba.ui.components.dialogs.LevelUpAlertDialog
import com.example.levelupprueba.ui.components.overlays.LevelUpLoadingOverlay
import com.example.levelupprueba.viewmodel.LoginViewModel
import com.example.levelupprueba.viewmodel.MainViewModel
import com.example.levelupprueba.viewmodel.ProfileViewModel

@Composable
fun ProfileStatusHandler(
    profileUiState: ProfileUiState,
    showOverlayAfterDelete: Boolean,
    onOverlayAfterDeleteChange: (Boolean) -> Unit,
    loginViewModel: LoginViewModel,
    mainViewModel: MainViewModel,
    context: Context,
    profileViewModel: ProfileViewModel
) {
    LevelUpLoadingOverlay(
        visible = showOverlayAfterDelete
    )

    when (val status = profileUiState.profileStatus) {
        is ProfileStatus.Loading ->{
            LevelUpLoadingOverlay()
        }
        is ProfileStatus.Saving -> {
            LevelUpLoadingOverlay()
        }
        is ProfileStatus.Deleting -> {
            LevelUpLoadingOverlay()
        }
        is ProfileStatus.Saved -> {
            mainViewModel.showSuccessSnackbar("Perfil guardado correctamente")
        }
        is ProfileStatus.Deleted -> {
            LevelUpAlertDialog(
                title = "Cuenta eliminada",
                text = "Tu cuenta ha sido eliminada exitosamente.",
                onConfirm = {
                    onOverlayAfterDeleteChange(true)
                    profileViewModel.resetProfileStatus()
                    loginViewModel.logout(context, mainViewModel)
                }
            )
        }
        is ProfileStatus.DeleteError -> {
            LevelUpAlertDialog(
                title = "Error",
                text = status.errorMessage,
                onConfirm = { profileViewModel.resetProfileStatus() },
                onDismiss = { profileViewModel.resetProfileStatus() }
            )
        }
        is ProfileStatus.Error -> {
            mainViewModel.showErrorSnackbar(status.errorMessage)
        }

        is ProfileStatus.ValidationError -> {
            mainViewModel.showErrorSnackbar(status.errorMessage)
        }
        else -> Unit
    }
}
