package com.example.levelupprueba.model.producto

data class ImagenCarrusel(
    val id: Int,
    val imagenUrl: String,
    val titulo: String,
    val descripcion: String,
    val enlace: String = ""
)
//el mismo copia y pega de blogs donde la imagen tiene sus metadatos y atributos 
//lo separe para q no se mezclen los datos siguiendo buenas practicas :D
