package com.example.levelupprueba.model.notificacion

data class Notificacion(
    val id: Long,
    val titulo: String,
    val mensaje: String,
    val tipo: String,
    val canal: String,
    val estado: String,
    val prioridad: String? = null,
    val fechaProgramada: String? = null,
    val fechaEnvio: String? = null,
    val fechaEntrega: String? = null,
    val fechaApertura: String? = null
)


