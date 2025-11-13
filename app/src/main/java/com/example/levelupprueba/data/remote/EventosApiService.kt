package com.example.levelupprueba.data.remote

import com.google.gson.annotations.SerializedName
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
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("idEvento")
    val idEvento: Long? = null,
    @SerializedName("titulo")
    val titulo: String? = null,
    @SerializedName("nombreEvento")
    val nombreEvento: String? = null,
    @SerializedName("descripcion")
    val descripcion: String? = null,
    @SerializedName("descripcionEvento")
    val descripcionEvento: String? = null,
    @SerializedName("fechaInicioString")
    val fechaInicioString: String? = null,
    @SerializedName("fechaInicio")
    val fechaInicio: String? = null,
    @SerializedName("fechaFinString")
    val fechaFinString: String? = null,
    @SerializedName("fechaFin")
    val fechaFin: String? = null,
    @SerializedName("ubicacion")
    val ubicacion: String? = null,
    @SerializedName("ubicacionEvento")
    val ubicacionEvento: String? = null,
    @SerializedName("ciudad")
    val ciudad: String? = null,
    @SerializedName("latitud")
    val latitud: Double? = null,
    @SerializedName("coordenadasLatitud")
    val coordenadasLatitud: Double? = null,
    @SerializedName("longitud")
    val longitud: Double? = null,
    @SerializedName("coordenadasLongitud")
    val coordenadasLongitud: Double? = null,
    @SerializedName("imagenUrl")
    val imagenUrl: String? = null,
    @SerializedName("imagenStorageUrl")
    val imagenStorageUrl: String? = null,
    @SerializedName("bannerUrl")
    val bannerUrl: String? = null,
    @SerializedName("imagen")
    val imagen: String? = null,
    @SerializedName("imagenes")
    val imagenes: String? = null,
    @SerializedName("imagenesUrls")
    val imagenesUrls: List<String>? = null,
    @SerializedName("categoria")
    val categoria: String? = null,
    @SerializedName("tipoEvento")
    val tipoEvento: String? = null,
    @SerializedName("capacidadMaxima")
    val capacidadMaxima: Int? = null,
    @SerializedName("cuposMaximos")
    val cuposMaximos: Int? = null,
    @SerializedName("participantesActuales")
    val participantesActuales: Int? = null,
    @SerializedName("cuposDisponibles")
    val cuposDisponibles: Int? = null,
    @SerializedName("puntosRecompensa")
    val puntosRecompensa: Int? = null,
    @SerializedName("puntosLevelUp")
    val puntosLevelUp: Int? = null,
    @SerializedName("edadMinima")
    val edadMinima: Int? = null,
    @SerializedName("requisitosEdad")
    val requisitosEdad: Int? = null,
    @SerializedName("precio")
    val precio: Double? = null,
    @SerializedName("costoEntrada")
    val costoEntrada: Double? = null,
    @SerializedName("estado")
    val estado: String? = null,
    @SerializedName("activo")
    val activo: Boolean? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("equiposRequeridos")
    val equiposRequeridos: String? = null
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

