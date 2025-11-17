package com.example.levelupprueba.model.producto

data class Review(
    val id: String,
    val productoId: String,
    val usuarioNombre: String,
    val rating: Float,
    val comentario: String,
    val fecha: String
)
