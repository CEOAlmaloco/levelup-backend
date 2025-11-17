package com.example.levelupprueba.model.usuario

data class Usuario(
    val id: String,
    val nombre: String,
    val apellidos: String = "",
    val email: String,
    val telefono: String? = null,
    val fechaNacimiento: String? = null,
    val region: String? = null,
    val comuna: String? = null,
    val direccion: String? = null,
    val referralCode: String? = null,
    val points: Int = 0,
    val redeemedCodes: List<String> = emptyList(),
    val referredBy: String? = null,
    val role: String = "",
    val avatar: String? = null
)