package com.example.levelupprueba.data.remote

import retrofit2.Response
import retrofit2.http.*

/**
 * Interfaz de API para reseñas y calificaciones
 */
interface ReseniaApiService {
    
    /**
     * Obtiene reseñas de un producto
     */
    @GET("resenia/producto/{productoId}")
    suspend fun getReseniasPorProducto(
        @Path("productoId") productoId: String,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 10
    ): Response<ReseniaResponsePage>
    
    /**
     * Crea una nueva reseña
     */
    @POST("resenia")
    suspend fun crearResenia(@Body resenia: ReseniaRequest): Response<ReseniaDto>
    
    /**
     * Actualiza una reseña
     */
    @PUT("resenia/{id}")
    suspend fun actualizarResenia(
        @Path("id") id: String,
        @Body resenia: ReseniaRequest
    ): Response<ReseniaDto>
    
    /**
     * Elimina una reseña
     */
    @DELETE("resenia/{id}")
    suspend fun eliminarResenia(@Path("id") id: String): Response<Unit>
    
    /**
     * Obtiene las reseñas del usuario
     */
    @GET("resenia/mis-resenias")
    suspend fun getMisResenias(): Response<List<ReseniaDto>>
}

/**
 * Request de reseña
 */
data class ReseniaRequest(
    val productoId: String,
    val calificacion: Int,
    val comentario: String? = null
)

/**
 * DTO de Reseña
 */
data class ReseniaDto(
    val id: String,
    val productoId: String,
    val usuarioId: String,
    val nombreUsuario: String,
    val calificacion: Int,
    val comentario: String? = null,
    val fechaCreacion: String,
    val fechaActualizacion: String? = null
)

/**
 * Respuesta paginada de reseñas
 */
data class ReseniaResponsePage(
    val content: List<ReseniaDto>,
    val totalElements: Int,
    val totalPages: Int,
    val number: Int,
    val size: Int
)

