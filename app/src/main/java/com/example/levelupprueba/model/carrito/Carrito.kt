package com.example.levelupprueba.model.carrito

data class Carrito(
    val id: Long? = null,
    val usuarioId: Long? = null,
    val estado: String? = null,
    val items: List<CarritoItem> = emptyList(),
    val totalServidor: Double? = null
) {
    val subtotal: Double get() = items.sumOf { it.totalLinea }
    val total: Double get() = totalServidor ?: subtotal
    val unidadesTotales: Int get() = items.sumOf { it.cantidad }
}
