package com.example.levelupprueba.data.remote

import retrofit2.Response
import retrofit2.http.*

/**
 * Interfaz de API para eventos gaming
 */
interface EventosApiService {
    
    /**
     * Obtiene todos los eventos con paginación
     */
    @GET("eventos")
    suspend fun getEventos(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 10
    ): Response<EventoResponsePage>
    
    /**
     * Obtiene eventos próximos
     */
    @GET("eventos/proximos")
    suspend fun getEventosProximos(@Query("limit") limit: Int = 6): Response<List<EventoDto>>
    
    /**
     * Obtiene un evento por ID
     */
    @GET("eventos/{id}")
    suspend fun getEventoById(@Path("id") id: String): Response<EventoDto>
    
    /**
     * Inscribe al usuario en un evento
     */
    @POST("eventos/{id}/inscribir")
    suspend fun inscribirseEvento(@Path("id") eventoId: String): Response<InscripcionDto>
    
    /**
     * Cancela inscripción a un evento
     */
    @DELETE("eventos/{id}/cancelar")
    suspend fun cancelarInscripcion(@Path("id") eventoId: String): Response<Unit>
    
    /**
     * Obtiene las inscripciones del usuario
     */
    @GET("eventos/mis-inscripciones")
    suspend fun getMisInscripciones(): Response<List<InscripcionDto>>
    
    /**
     * Crea un nuevo evento (solo admin)
     */
    @POST("eventos")
    suspend fun crearEvento(@Body evento: EventoDto): Response<EventoDto>
    
    /**
     * Actualiza un evento (solo admin)
     */
    @PUT("eventos/{id}")
    suspend fun actualizarEvento(
        @Path("id") id: String,
        @Body evento: EventoDto
    ): Response<EventoDto>
    
    /**
     * Elimina un evento (solo admin)
     */
    @DELETE("eventos/{id}")
    suspend fun eliminarEvento(@Path("id") id: String): Response<Unit>
}

/**
 * DTO de Evento
 */
data class EventoDto(
    val id: String? = null,
    val titulo: String,
    val descripcion: String,
    val fechaInicio: String,
    val fechaFin: String,
    val ubicacion: String,
    val latitud: Double? = null,
    val longitud: Double? = null,
    val imagen: String,
    val categoria: String,
    val capacidadMaxima: Int,
    val participantesActuales: Int = 0,
    val puntosRecompensa: Int,
    val edadMinima: Int,
    val precio: Double,
    val estado: String,
    val createdAt: String? = null
)

/**
 * DTO de Inscripción
 */
data class InscripcionDto(
    val id: String,
    val eventoId: String,
    val usuarioId: String,
    val fechaInscripcion: String,
    val estado: String,
    val puntosGanados: Int? = null
)

/**
 * Respuesta paginada de eventos
 */
data class EventoResponsePage(
    val content: List<EventoDto>,
    val totalElements: Int,
    val totalPages: Int,
    val number: Int,
    val size: Int
)

