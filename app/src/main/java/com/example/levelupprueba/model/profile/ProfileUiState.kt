package com.example.levelupprueba.model.profile

import com.example.levelupprueba.model.notificacion.Notificacion
import com.example.levelupprueba.model.usuario.UsuarioCampo

data class ProfileUiState(
    val nombre: UsuarioCampo = UsuarioCampo(),
    val apellidos: UsuarioCampo = UsuarioCampo(),
    val email: UsuarioCampo = UsuarioCampo(),
    val telefono: UsuarioCampo = UsuarioCampo(),
    val fechaNacimiento: UsuarioCampo = UsuarioCampo(),
    val region: UsuarioCampo = UsuarioCampo(),
    val comuna: UsuarioCampo = UsuarioCampo(),
    val direccion: UsuarioCampo = UsuarioCampo(),
    val avatar: String? = null,
    val referralCode: String = "", // Codigo de referido del usuario
    val points: Int = 0, // Puntos del usuario
    val notificaciones: List<Notificacion> = emptyList(),
    val isEditing: Boolean = false,
    val isLoading: Boolean = false,
    val errors: Map<String, String> = emptyMap(),
    val profileStatus: ProfileStatus = ProfileStatus.Idle
)

