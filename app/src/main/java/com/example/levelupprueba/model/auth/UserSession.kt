package com.example.levelupprueba.model.auth

/**
 * Sesión de usuario con tokens JWT
 */
data class UserSession(
    val displayName: String,
    val loginAt: Long,
    val userId: Long,
    val role: String,
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long,
    val email: String,
    val nombre: String,
    val apellidos: String,
    val tipoUsuario: String,
    val descuentoDuoc: Boolean? = null,
    val region: String? = null,
    val comuna: String? = null
)