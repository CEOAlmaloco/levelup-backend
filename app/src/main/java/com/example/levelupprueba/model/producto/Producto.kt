package com.example.levelupprueba.model.producto
//atributos que tiene un producto
data class Producto(
    val id: String,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val imagenUrl: String,
    val categoria: Categoria,
    val subcategoria: Subcategoria?,
    val rating: Float = 0f,
    val disponible: Boolean = true,
    val destacado: Boolean = false,
    val stock: Int = 0
)

