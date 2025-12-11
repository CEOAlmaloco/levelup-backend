package com.example.levelupprueba.ui.screens.admin.users

import androidx.compose.runtime.Composable
import com.example.levelupprueba.model.admin.users.UsuariosStatus
import com.example.levelupprueba.model.admin.users.UsuariosUiState
import com.example.levelupprueba.ui.components.dialogs.LevelUpAlertDialog
import com.example.levelupprueba.ui.components.overlays.LevelUpLoadingOverlay
import com.example.levelupprueba.viewmodel.MainViewModel
import com.example.levelupprueba.viewmodel.UsuariosViewModel

/**
 * Maneja el estado de la pantalla de usuarios.
 *
 * @param usuariosUiState El estado de la pantalla de usuarios.
 * @param mainViewModel El ViewModel de la pantalla principal.
 * @param usuariosViewModel El ViewModel de la pantalla de usuarios.
 *
 * @author Christian Mesa
 * */
@Composable
fun AdminUsuariosStatusHandler (
    usuariosUiState: UsuariosUiState,
    mainViewModel: MainViewModel,
    usuariosViewModel: UsuariosViewModel
){
    // Maneja el estado de la pantalla de usuarios
    when (val state = usuariosUiState.state){

        // Si el estado es UsuariosStatus.Loading, muestra un overlay de carga
        is UsuariosStatus.Loading -> {
            LevelUpLoadingOverlay()
        }

        // Si el estado es UsuariosStatus.Deleting, muestra un overlay de carga
        is UsuariosStatus.Deleting -> {
            LevelUpLoadingOverlay()
        }

        // Si el estado es UsuariosStatus.Error, muestra un diálogo de error
        is UsuariosStatus.Error -> {
            LevelUpAlertDialog(
                title = "Error",
                text = state.errorMessage,
                onConfirm = {usuariosViewModel.cargarUsuarios()}
            )
        }

        // Si el estado es UsuariosStatus.Success, no hace nada
        is UsuariosStatus.Success -> Unit

        else -> {}
    }

    // Maneja la acción de la pantalla de usuarios para mostrar un snackbar
    when (usuariosUiState.lastAction) {
        "delete" -> {
            mainViewModel.showSuccessSnackbar("Usuario eliminado")
            usuariosViewModel.clearLastAction()
        }
        "create" -> {
            mainViewModel.showSuccessSnackbar("Usuario creado con éxito")
            usuariosViewModel.clearLastAction()
        }
        "update" -> {
            mainViewModel.showSuccessSnackbar("Usuario actualizado")
            usuariosViewModel.clearLastAction()
        }
        "delete_all" -> {
            mainViewModel.showSuccessSnackbar("Todos los usuarios fueron eliminados")
            usuariosViewModel.clearLastAction()
        }
    }
}