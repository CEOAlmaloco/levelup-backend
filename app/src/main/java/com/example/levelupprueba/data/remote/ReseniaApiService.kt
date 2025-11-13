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
    @GET("resenias/producto/{productoId}")
    suspend fun getReseniasPorProducto(
        @Path("productoId") productoId: Long
    ): Response<List<ReseniaDto>>
    
    /**
     * Crea una nueva reseña
     */
    @POST("resenias/producto/{productoId}")
    suspend fun crearResenia(
        @Path("productoId") productoId: Long,
        @Body resenia: ReseniaRequest
    ): Response<ReseniaDto>
    
    /**
     * Actualiza una reseña
     */
    @PUT("resenias/{id}")
    suspend fun actualizarResenia(
        @Path("id") id: Long,
        @Body resenia: ReseniaRequest
    ): Response<ReseniaDto>
    
    /**
     * Elimina una reseña
     */
    @DELETE("resenias/{id}")
    suspend fun eliminarResenia(@Path("id") id: Long): Response<Unit>
    
    /**
     * Obtiene las reseñas del usuario
     */
    @GET("resenias/usuario/{idUsuario}")
    suspend fun getReseniasPorUsuario(@Path("idUsuario") idUsuario: Long): Response<List<ReseniaDto>>
}

/**
 * Request de reseña
 */
data class ReseniaRequest(
    val idUsuario: Long,
    val usuarioNombre: String,
    val rating: Int,
    val comentario: String
)

/**
 * DTO de Reseña
 */
data class ReseniaDto(
    val id: Long?,
    val idProducto: Long,
    val idUsuario: Long,
    val usuarioNombre: String,
    val rating: Int,
    val comentario: String,
    val fechaCreacion: String?,
    val fechaActualizacion: String?,
    val activo: Boolean? = true
)

