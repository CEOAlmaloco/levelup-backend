package com.example.levelupprueba.model.usuario

// Datos del formulario de registro
data class UsuarioUiState(
    val nombre: UsuarioCampo = UsuarioCampo(),
    val apellidos: UsuarioCampo = UsuarioCampo(),
    val email: UsuarioCampo = UsuarioCampo(),
    val password: UsuarioCampo = UsuarioCampo(),
    val confirmPassword: UsuarioCampo = UsuarioCampo(),
    val telefono: UsuarioCampo = UsuarioCampo(),
    val fechaNacimiento: UsuarioCampo = UsuarioCampo(),
    val region: UsuarioCampo = UsuarioCampo(),
    val comuna: UsuarioCampo = UsuarioCampo(),
    val direccion: UsuarioCampo = UsuarioCampo(),
    val terminos: UsuarioCampo = UsuarioCampo()
)
