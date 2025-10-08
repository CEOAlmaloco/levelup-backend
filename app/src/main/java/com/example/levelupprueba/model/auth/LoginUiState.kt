package com.example.levelupprueba.model.auth

// Modelo que representa el estado actual del formulario de login en la UI
data class LoginUiState(
    //Campo para el email o nombre de usuario
    val emailOrName: LoginCampo = LoginCampo(),
    // Campo para la contraseña
    val password: LoginCampo = LoginCampo(),
)
// Modelo que representa los posibles errores del formulario de login
