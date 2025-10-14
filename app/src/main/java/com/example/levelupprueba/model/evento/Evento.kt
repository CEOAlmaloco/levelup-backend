package com.example.levelupprueba.model.evento
//model evento luego IMPLEMENTAR CON SQL LITE
data class Evento(
    val id: String = "",
    val titulo: String = "",
    val lugar: String = "",
    val direccion: String = "",
    val ciudad: String = "",
    val fecha: String = "",
    val hora: String = "",
    val puntos: Int = 0,
    val latitud: Double = 0.0,
    val longitud: Double = 0.0,
    val descripcion: String = ""
)

