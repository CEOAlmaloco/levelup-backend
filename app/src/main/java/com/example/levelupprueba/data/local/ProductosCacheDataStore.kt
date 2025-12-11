package com.example.levelupprueba.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.levelupprueba.model.producto.Producto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.first

val Context.productosCacheDataStore by preferencesDataStore(name = "productos_cache")

object ProductosCacheKeys {
    val PRODUCTOS_JSON = stringPreferencesKey("productos_json")
    val CACHE_TIMESTAMP = longPreferencesKey("cache_timestamp")
    val PRODUCTOS_DESTACADOS_JSON = stringPreferencesKey("productos_destacados_json")
}

private val gson = Gson()

/**
 * Guarda productos en caché local
 */
suspend fun saveProductosCache(context: Context, productos: List<Producto>) {
    context.productosCacheDataStore.edit { prefs ->
        val productosJson = gson.toJson(productos)
        val timestamp = System.currentTimeMillis()
        
        prefs[ProductosCacheKeys.PRODUCTOS_JSON] = productosJson
        prefs[ProductosCacheKeys.CACHE_TIMESTAMP] = timestamp
        
        android.util.Log.d("ProductosCache", "Productos guardados en caché: ${productos.size}, timestamp: $timestamp")
    }
}

/**
 * Guarda productos destacados en caché local
 */
suspend fun saveProductosDestacadosCache(context: Context, productos: List<Producto>) {
    context.productosCacheDataStore.edit { prefs ->
        val productosJson = gson.toJson(productos)
        
        prefs[ProductosCacheKeys.PRODUCTOS_DESTACADOS_JSON] = productosJson
        
        android.util.Log.d("ProductosCache", "Productos destacados guardados en caché: ${productos.size}")
    }
}

/**
 * Obtiene productos desde caché si no han expirado
 * @param maxAgeMs Tiempo máximo de validez del caché en milisegundos (default: 5 minutos)
 */
suspend fun getProductosCache(context: Context, maxAgeMs: Long = 5 * 60 * 1000): List<Producto>? {
    return try {
        val prefs = context.productosCacheDataStore.data.first()
        val productosJson = prefs[ProductosCacheKeys.PRODUCTOS_JSON]
        val timestamp = prefs[ProductosCacheKeys.CACHE_TIMESTAMP] ?: 0L
        
        if (productosJson == null || timestamp == 0L) {
            android.util.Log.d("ProductosCache", "No hay caché de productos")
            return null
        }
        
        val cacheAge = System.currentTimeMillis() - timestamp
        if (cacheAge > maxAgeMs) {
            android.util.Log.d("ProductosCache", "Caché expirado (edad: ${cacheAge}ms, máximo: ${maxAgeMs}ms)")
            // Limpiar caché expirado
            context.productosCacheDataStore.edit { it.clear() }
            return null
        }
        
        val type = object : TypeToken<List<Producto>>() {}.type
        val productos = gson.fromJson<List<Producto>>(productosJson, type)
        
        android.util.Log.d("ProductosCache", "Productos cargados desde caché: ${productos.size}, edad: ${cacheAge}ms")
        productos
    } catch (e: Exception) {
        android.util.Log.e("ProductosCache", "Error al cargar productos desde caché: ${e.message}", e)
        null
    }
}

/**
 * Obtiene productos destacados desde caché
 */
suspend fun getProductosDestacadosCache(context: Context): List<Producto>? {
    return try {
        val prefs = context.productosCacheDataStore.data.first()
        val productosJson = prefs[ProductosCacheKeys.PRODUCTOS_DESTACADOS_JSON]
        
        if (productosJson == null) {
            android.util.Log.d("ProductosCache", "No hay caché de productos destacados")
            return null
        }
        
        val type = object : TypeToken<List<Producto>>() {}.type
        val productos = gson.fromJson<List<Producto>>(productosJson, type)
        
        android.util.Log.d("ProductosCache", "Productos destacados cargados desde caché: ${productos.size}")
        productos
    } catch (e: Exception) {
        android.util.Log.e("ProductosCache", "Error al cargar productos destacados desde caché: ${e.message}", e)
        null
    }
}

/**
 * Limpia el caché de productos
 */
suspend fun clearProductosCache(context: Context) {
    context.productosCacheDataStore.edit { prefs ->
        prefs.clear()
        android.util.Log.d("ProductosCache", "Caché de productos limpiado")
    }
}

