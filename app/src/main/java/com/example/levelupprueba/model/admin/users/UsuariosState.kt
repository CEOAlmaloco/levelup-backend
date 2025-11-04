package com.example.levelupprueba.model.admin.users

import com.example.levelupprueba.model.usuario.Usuario

sealed class UsuariosState {
    object Loading : UsuariosState()
    object Empty : UsuariosState()
    data class Success(val usuarios: List<Usuario>) : UsuariosState()
    data class Error(val errorMessage: String) : UsuariosState()
}
