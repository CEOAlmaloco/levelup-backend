package com.example.levelupprueba.ui.screens.profile

data class PerfilEditable(
    val nombre: String = "",
    val apellidos: String = "",
    val email: String = "",
    val telefono: String = "",
    val fechaNacimiento: String = "",
    val region: String = "",
    val comuna: String = "",
    val direccion: String = "",
    val avatar: String? = null
)
