package com.example.levelupprueba.model.carrito

import com.example.levelupprueba.model.producto.Producto

data class CarritoItem(
    val id: String, // (temporal) Se puede usar el producto id
    val producto: Producto,
    val cantidad: Int
){
    // Para ver el total por producto
    val totalLinea: Double get() = producto.precio * cantidad // Para ver el total
}

