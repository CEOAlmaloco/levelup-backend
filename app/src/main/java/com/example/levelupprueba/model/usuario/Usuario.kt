package com.example.levelupprueba.model.usuario

import kotlinx.serialization.Serializable

@Serializable
data class Usuario(
    val id: String,
    val nombre: String,
    val apellidos: String,
    val email: String,
    val password: String,
    val telefono: String?,
    val fechaNacimiento: String?,
    val region: String?,
    val comuna: String?,
    val direccion: String?,
    val referralCode: String,
    val points: Int = 0,
    val redeemedCodes: List<String> = emptyList(),//kitian te agrege esto adicional para el canje de puntos
    val role: String
)