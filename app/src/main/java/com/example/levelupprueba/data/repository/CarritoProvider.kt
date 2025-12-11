package com.example.levelupprueba.data.repository

import android.content.Context
import com.example.levelupprueba.data.repository.CarritoRepositoryRemote

/**
 * Entrega un `CarritoRepository` singleton.
 * Actualmente siempre sincroniza con el backend.
 */
object CarritoProvider {
    @Volatile private var repo: CarritoRepository? = null

    fun get(context: Context): CarritoRepository {
        val cached = repo
        if (cached != null) return cached
        return synchronized(this) {
            repo ?: createRepository(context).also { repo = it }
        }
    }

    private fun createRepository(context: Context): CarritoRepository {
        // El carrito se sincroniza siempre con el backend; para usuarios no autenticados
        // el backend responderá con carrito vacío o validará sesión según corresponda.
        return CarritoRepositoryRemote(context.applicationContext)
    }
}
