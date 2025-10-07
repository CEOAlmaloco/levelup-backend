package com.example.levelupprueba.model.blog

data class Blog(
    val id: String = "",
    val titulo: String = "",
    val contenido: String = "",
    val resumen: String = "",
    val categoria: String = "",
    val imagenUrl: String = "",
    val autor: String = "",
    val fechaPublicacion: String = "",
    val tags: List<String> = emptyList(),
    val destacado: Boolean = false
)
