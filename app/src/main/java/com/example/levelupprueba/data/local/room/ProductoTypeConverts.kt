package com.example.levelupprueba.data.local.room

import androidx.room.TypeConverter
import com.example.levelupprueba.model.producto.Producto
import com.google.gson.Gson

// Converters para guardar Producto completo como JSON en Room (snapshot).
object ProductoTypeConverters {
    private val gson = Gson()
    @TypeConverter
    @JvmStatic
    fun fromProducto(p: Producto): String = gson.toJson(p)
    @TypeConverter
    @JvmStatic
    fun toProducto(json: String): Producto = gson.fromJson(json, Producto::class.java)
}
