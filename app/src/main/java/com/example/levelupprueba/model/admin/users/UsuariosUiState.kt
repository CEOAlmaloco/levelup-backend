package com.example.levelupprueba.model.admin.users

import com.example.levelupprueba.model.usuario.Usuario

data class UsuariosUiState(
    val state: UsuariosState = UsuariosState.Loading,
    val selected: Usuario? = null
)