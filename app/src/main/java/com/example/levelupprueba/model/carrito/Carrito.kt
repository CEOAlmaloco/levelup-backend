package com.example.levelupprueba.model.carrito

data class Carrito (
    val items: List<CarritoItem> = emptyList()
){
    val subtotal: Double get() = items.sumOf { it.totalLinea }
    val total: Double get() = subtotal
    val unidadesTotales: Int get() = items.sumOf { it.cantidad }
}
