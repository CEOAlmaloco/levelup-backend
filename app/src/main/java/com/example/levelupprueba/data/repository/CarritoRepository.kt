package com.example.levelupprueba.data.repository

import com.example.levelupprueba.model.carrito.Carrito
import com.example.levelupprueba.model.producto.Producto

/**
 * Contrato del repositorio del carrito.
 * La UI/ViewModel solo usa estas funciones, sin importar si la fuente es Room o red.
 */
interface CarritoRepository {

    /** Obtiene el carrito actual. */
    suspend fun getCarrito(): Carrito

    /** Agrega un producto (si existe, suma cantidad). Devuelve carrito actualizado. */
    suspend fun agregarProducto(producto: Producto, cantidad: Int = 1): Carrito

    /** Cambia cantidad (+/-). Si queda en 0, elimina el ítem. Devuelve carrito actualizado. */
    suspend fun actualizarCantidad(itemId: String, delta: Int): Carrito

    /** Elimina un ítem por id. Devuelve carrito actualizado. */
    suspend fun eliminarItem(itemId: String): Carrito

    /** Checkout: en local limpia; en real llamará al backend. Devuelve carrito final. */
    suspend fun checkout(): Carrito
}
