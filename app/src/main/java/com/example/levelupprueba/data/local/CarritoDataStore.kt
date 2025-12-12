package com.example.levelupprueba.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

<<<<<<< HEAD
val Context.carritoDataStore by preferencesDataStore(name = "carrito")
=======
val Context.carritoDataStore by preferencesDataStore(name = "carrito_cache")
>>>>>>> main

object CarritoKeys {
    val CARRITO_ID = longPreferencesKey("carrito_id")
}

<<<<<<< HEAD
suspend fun saveCarritoId(context: Context, carritoId: Long) {
    context.carritoDataStore.edit { prefs ->
        prefs[CarritoKeys.CARRITO_ID] = carritoId
    }
}

suspend fun getCarritoId(context: Context): Long? {
    val prefs = context.carritoDataStore.data.first()
    return prefs[CarritoKeys.CARRITO_ID]
}

suspend fun clearCarritoId(context: Context) {
    context.carritoDataStore.edit { prefs ->
        prefs.remove(CarritoKeys.CARRITO_ID)
=======
/**
 * Guarda el ID del carrito en caché local
 */
suspend fun saveCarritoId(context: Context, carritoId: Long) {
    context.carritoDataStore.edit { prefs ->
        prefs[CarritoKeys.CARRITO_ID] = carritoId
        android.util.Log.d("CarritoDataStore", "ID de carrito guardado: $carritoId")
    }
}

/**
 * Obtiene el ID del carrito desde caché local
 */
suspend fun getCarritoId(context: Context): Long? {
    return try {
        val prefs = context.carritoDataStore.data.first()
        val carritoId = prefs[CarritoKeys.CARRITO_ID]
        android.util.Log.d("CarritoDataStore", "ID de carrito obtenido desde caché: $carritoId")
        carritoId
    } catch (e: Exception) {
        android.util.Log.e("CarritoDataStore", "Error al obtener ID de carrito desde caché: ${e.message}", e)
        null
    }
}

/**
 * Limpia el ID del carrito del caché local
 */
suspend fun clearCarritoId(context: Context) {
    context.carritoDataStore.edit { prefs ->
        prefs.remove(CarritoKeys.CARRITO_ID)
        android.util.Log.d("CarritoDataStore", "ID de carrito limpiado del caché")
>>>>>>> main
    }
}

