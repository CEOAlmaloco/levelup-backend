package com.example.levelupprueba.model.usuario

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
    val role: String
)