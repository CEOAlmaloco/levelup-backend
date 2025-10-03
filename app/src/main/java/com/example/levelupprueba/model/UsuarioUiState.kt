package com.example.levelupprueba.model

// Datos del formulario de registro
data class UsuarioUiState(
    val nombre: String = "",
    val apellidos: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val telefono: String = "",
    val fechaNacimiento: String = "",
    val region: String = "",
    val comuna: String = "",
    val direccion: String = "",
    val terminos: Boolean = false,
    val errores: UsuarioErrores = UsuarioErrores()
)

// Posibles errores de validación por campo
data class UsuarioErrores(
    val nombre: String? = null,
    val apellidos: String? = null,
    val email: String? = null,
    val password: String? = null,
    val confirmPassword: String? = null,
    val telefono: String? = null,
    val fechaNacimiento: String? = null,
    val region: String? = null,
    val comuna: String? = null,
    val direccion: String? = null,
    val terminos: String? = null
)
