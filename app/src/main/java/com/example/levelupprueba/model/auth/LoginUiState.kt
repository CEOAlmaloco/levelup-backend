package com.example.levelupprueba.model.auth

// Modelo que representa el estado actual del formulario de login en la UI
data class LoginUiState(
    //Campo para el email o nombre de usuario
    val emailOrName: String = "",
    // Campo para la contraseña
    val password: String = "",
    // Objeto que contiene posibles errores en el formulario
    val errores: LoginErrores = LoginErrores()
)
// Modelo que representa los posibles errores del formulario de login
data class LoginErrores(
    //Error especifico para el campo email/nombre
    val emailOrName: String? = null,
    // Error especifico para el campo contraseña
    val password: String? = null,
    // Error general de formulario
    val form: String? = null,
    // Error general para otros casos
    val general: String? = null
)