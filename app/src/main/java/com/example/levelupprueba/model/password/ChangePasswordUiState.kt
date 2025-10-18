package com.example.levelupprueba.model.password

import com.example.levelupprueba.model.usuario.UsuarioCampo

data class PasswordUiState(
    val actual: UsuarioCampo = UsuarioCampo(),
    val nueva: UsuarioCampo = UsuarioCampo(),
    val confirmar: UsuarioCampo = UsuarioCampo(),
    val status: PasswordStatus = PasswordStatus.Idle
)