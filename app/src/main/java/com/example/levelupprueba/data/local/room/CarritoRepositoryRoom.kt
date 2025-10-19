package com.example.levelupprueba.data.repository

import com.example.levelupprueba.data.local.room.CarritoDao
import com.example.levelupprueba.data.local.room.CarritoItemEntity
import com.example.levelupprueba.model.carrito.Carrito
import com.example.levelupprueba.model.carrito.CarritoItem
import com.example.levelupprueba.model.producto.Producto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

/**
 * Implementación real usando Room.
 * No hace red; persiste localmente con DAO.
 */
class CarritoRepositoryRoom(
    private val dao: CarritoDao
) : CarritoRepository {

    override suspend fun getCarrito(): Carrito = withContext(Dispatchers.IO) {
        val entities = dao.getAll()
        Carrito(
            items = entities.map { it.toModel() }
        )
    }

    override suspend fun agregarProducto(producto: Producto, cantidad: Int): Carrito = withContext(Dispatchers.IO) {
        // Si ya existe por producto.id -> sumar a esa fila; si no, crear nueva.
        val actual = dao.getAll().toMutableList()
        val existente = actual.firstOrNull { it.producto.id == producto.id }
        if (existente != null) {
            val nuevaCantidad = existente.cantidad + cantidad
            dao.updateCantidad(existente.id, nuevaCantidad)
        } else {
            dao.upsert(
                CarritoItemEntity(
                    id = UUID.randomUUID().toString(),
                    producto = producto,
                    cantidad = cantidad
                )
            )
        }
        getCarrito()
    }

    override suspend fun actualizarCantidad(itemId: String, delta: Int): Carrito = withContext(Dispatchers.IO) {
        val items = dao.getAll()
        val ent = items.firstOrNull { it.id == itemId } ?: return@withContext getCarrito()
        val nueva = ent.cantidad + delta
        if (nueva <= 0) dao.delete(itemId) else dao.updateCantidad(itemId, nueva)
        getCarrito()
    }

    override suspend fun eliminarItem(itemId: String): Carrito = withContext(Dispatchers.IO) {
        dao.delete(itemId)
        getCarrito()
    }

    override suspend fun checkout(): Carrito = withContext(Dispatchers.IO) {
        // En local: limpiar carrito
        dao.clear()
        Carrito(emptyList())
    }

    // --- mapeo Entity <-> Model ---
    private fun CarritoItemEntity.toModel() = CarritoItem(
        id = id,
        producto = producto,
        cantidad = cantidad
    )
}
