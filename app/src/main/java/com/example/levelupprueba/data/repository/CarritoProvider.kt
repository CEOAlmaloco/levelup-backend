package com.example.levelupprueba.data.repository

import android.content.Context
import com.example.levelupprueba.data.local.room.CarritoDatabase

/**
 * Entrega un CarritoRepository (Room) como singleton.
 * Así podemos usarlo desde cualquier screen sin cambiar firmas.
 */
object CarritoProvider {
    @Volatile private var repo: CarritoRepository? = null

    fun get(context: Context): CarritoRepository {
        val cached = repo
        if (cached != null) return cached
        return synchronized(this) {
            val again = repo
            if (again != null) again
            else CarritoRepositoryRoom(CarritoDatabase.get(context).carritoDao())
                .also { repo = it }
        }
    }
}
