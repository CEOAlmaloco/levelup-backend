package com.example.levelupprueba.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.levelupprueba.model.producto.Producto

/**
 * Entity del carrito en Room.
 * Guardamos un snapshot del Producto (completo) y la cantidad.
 */
@Entity(tableName = "carrito_items")
data class CarritoItemEntity(
    @PrimaryKey val id: String,         // id del ítem en el carrito
    val producto: Producto,             // snapshot (se serializa vía TypeConverter)
    val cantidad: Int
)
