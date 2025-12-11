package com.example.levelupprueba.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

val Context.carritoDataStore by preferencesDataStore(name = "carrito")

object CarritoKeys {
    val CARRITO_ID = longPreferencesKey("carrito_id")
}

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
    }
}

