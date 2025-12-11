package com.example.levelupprueba.data.repository

import android.util.Log
import com.example.levelupprueba.data.remote.ApiConfig
import com.example.levelupprueba.data.remote.NotificacionResponse
import com.example.levelupprueba.model.notificacion.Notificacion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NotificacionesRepositoryRemote {

    private val service = ApiConfig.notificacionesService

    suspend fun obtenerNotificacionesUsuario(idUsuario: Long): List<Notificacion> = withContext(Dispatchers.IO) {
        if (idUsuario <= 0) return@withContext emptyList()
        try {
            val response = service.getNotificacionesPorUsuario(idUsuario)
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.map { it.toModel() }
            } else {
                Log.w(
                    "NotificacionesRepo",
                    "Fallo al obtener notificaciones: code=${response.code()} body=${response.errorBody()?.string()}"
                )
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("NotificacionesRepo", "Excepción al obtener notificaciones", e)
            emptyList()
        }
    }

    suspend fun marcarComoAbierta(idNotificacion: Long): Boolean = withContext(Dispatchers.IO) {
        try {
            val response = service.marcarComoAbierta(idNotificacion)
            response.isSuccessful
        } catch (e: Exception) {
            Log.e("NotificacionesRepo", "Error al marcar como abierta", e)
            false
        }
    }

    suspend fun marcarComoEntregada(idNotificacion: Long): Boolean = withContext(Dispatchers.IO) {
        try {
            val response = service.marcarComoEntregada(idNotificacion)
            response.isSuccessful
        } catch (e: Exception) {
            Log.e("NotificacionesRepo", "Error al marcar como entregada", e)
            false
        }
    }

    private fun NotificacionResponse.toModel(): Notificacion {
        return Notificacion(
            id = idNotificacion,
            titulo = asuntoNotificacion,
            mensaje = contenidoNotificacion,
            tipo = tipoNotificacion,
            canal = canalNotificacion,
            estado = estadoNotificacion,
            prioridad = prioridadNotificacion,
            fechaProgramada = fechaProgramada,
            fechaEnvio = fechaEnvio,
            fechaEntrega = fechaEntrega,
            fechaApertura = fechaApertura
        )
    }
}


