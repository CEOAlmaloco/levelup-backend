package com.example.levelupprueba.data.remote

import retrofit2.Response
import retrofit2.http.*

/**
 * Interfaz de API para notificaciones
 * Endpoints: /api/v1/notificaciones/
 */
interface NotificacionesApiService {
    
    /**
     * Obtener todas las notificaciones
     * GET /api/v1/notificaciones
     */
    @GET("notificaciones")
    suspend fun getAllNotificaciones(): Response<List<NotificacionResponse>>
    
    /**
     * Obtener notificaciones por usuario
     * GET /api/v1/notificaciones/usuario/{idUsuario}
     */
    @GET("notificaciones/usuario/{idUsuario}")
    suspend fun getNotificacionesPorUsuario(@Path("idUsuario") idUsuario: Long): Response<List<NotificacionResponse>>
    
    /**
     * Obtener notificaciones por estado
     * GET /api/v1/notificaciones/estado/{estado}
     */
    @GET("notificaciones/estado/{estado}")
    suspend fun getNotificacionesPorEstado(@Path("estado") estado: String): Response<List<NotificacionResponse>>
    
    /**
     * Obtener notificaciones por tipo
     * GET /api/v1/notificaciones/tipo/{tipo}
     */
    @GET("notificaciones/tipo/{tipo}")
    suspend fun getNotificacionesPorTipo(@Path("tipo") tipo: String): Response<List<NotificacionResponse>>
    
    /**
     * Obtener notificación por ID
     * GET /api/v1/notificaciones/{id}
     */
    @GET("notificaciones/{id}")
    suspend fun getNotificacionById(@Path("id") id: Long): Response<NotificacionResponse>
    
    /**
     * Crear notificación
     * POST /api/v1/notificaciones
     */
    @POST("notificaciones")
    suspend fun crearNotificacion(@Body request: NotificacionCreationRequest): Response<NotificacionResponse>
    
    /**
     * Marcar como entregada
     * PUT /api/v1/notificaciones/{id}/entregada
     */
    @PUT("notificaciones/{id}/entregada")
    suspend fun marcarComoEntregada(@Path("id") id: Long): Response<NotificacionResponse>
    
    /**
     * Marcar como abierta
     * PUT /api/v1/notificaciones/{id}/abierta
     */
    @PUT("notificaciones/{id}/abierta")
    suspend fun marcarComoAbierta(@Path("id") id: Long): Response<NotificacionResponse>
}

/**
 * Request para crear notificación
 */
data class NotificacionCreationRequest(
    val idUsuario: Long,
    val tipoNotificacion: String,
    val canalNotificacion: String,
    val asuntoNotificacion: String,
    val contenidoNotificacion: String,
    val destinatarioNotificacion: String? = null,
    val fechaProgramada: String? = null
)

/**
 * Response de notificación
 */
data class NotificacionResponse(
    val idNotificacion: Long,
    val idUsuario: Long,
    val tipoNotificacion: String,
    val canalNotificacion: String,
    val asuntoNotificacion: String,
    val contenidoNotificacion: String,
    val plantillaNotificacion: String?,
    val destinatarioNotificacion: String?,
    val fechaCreacion: String,
    val fechaProgramada: String?,
    val fechaEnvio: String?,
    val fechaEntrega: String?,
    val fechaApertura: String?,
    val estadoNotificacion: String,
    val intentosEnvio: Int?,
    val maxIntentos: Int?,
    val errorMensaje: String?,
    val metadataNotificacion: String?,
    val prioridadNotificacion: String?,
    val activo: Boolean?
)