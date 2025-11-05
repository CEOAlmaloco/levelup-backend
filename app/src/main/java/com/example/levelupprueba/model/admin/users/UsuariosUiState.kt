package com.example.levelupprueba.model.admin.users

import com.example.levelupprueba.model.usuario.Usuario

/**
 * UiState para la lista de usuarios.
 * @param state El estado actual de la lista de usuarios.
 * @param selected El usuario seleccionado en la lista.
 * @param lastAction La última acción realizada en la lista de usuarios.
 *
 * @author Christian Mesa
 * */
data class UsuariosUiState(
    val state: UsuariosStatus = UsuariosStatus.Idle,
    val selected: Usuario? = null,
    val lastAction: String? = null
)