package com.example.levelupprueba.data.remote

import retrofit2.Response
import retrofit2.http.*

/**
 * Interfaz de API para referidos
 */
interface ReferidosApiService {
    
    /**
     * Obtiene todos los referidos
     */
    @GET("referidos")
    suspend fun getReferidos(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): Response<ReferidosResponsePage>
    
    /**
     * Obtiene referido por ID
     */
    @GET("referidos/{id}")
    suspend fun getReferidoById(@Path("id") id: Long): Response<ReferidoDto>
    
    /**
     * Obtiene código de referido por usuario ID
     * Nota: El endpoint devuelve un Map con "codigoReferido"
     */
    @GET("referidos/codigo")
    suspend fun getCodigoReferido(@Query("idUsuario") idUsuario: Long): Response<Map<String, String>>
    
    /**
     * Obtiene referido por código
     */
    @GET("referidos/codigo/{codigo}")
    suspend fun getReferidoPorCodigo(@Path("codigo") codigo: String): Response<ReferidoDto>
    
    /**
     * Obtiene lista de referidos por usuario ID
     */
    @GET("referidos/lista")
    suspend fun getListaReferidos(@Query("idUsuario") idUsuario: Long): Response<List<ReferidoDto>>
    
    /**
     * Obtiene referidos de un usuario
     */
    @GET("referidos/usuario/{usuarioId}/referidos")
    suspend fun getReferidosDeUsuario(@Path("usuarioId") usuarioId: Long): Response<List<ReferidoDto>>
    
    /**
     * Obtiene puntos de un referido
     */
    @GET("referidos/{id}/puntos")
    suspend fun getPuntosReferido(@Path("id") id: Long): Response<PuntosReferidoDto>
    
    /**
     * Obtiene productos canjeables por puntos
     */
    @GET("referidos/productos/canjeables")
    suspend fun getProductosCanjeables(@Query("puntos") puntos: Int): Response<List<ProductoDto>>
    
    /**
     * Obtiene recompensas por usuario
     */
    @GET("referidos/recompensas")
    suspend fun getRecompensas(@Query("idUsuario") idUsuario: Long): Response<List<RecompensaDto>>
    
    /**
     * Obtiene descuentos de un referido
     */
    @GET("referidos/{id}/descuentos")
    suspend fun getDescuentos(@Path("id") id: Long): Response<List<DescuentoDto>>

    /**
     * Otorga puntos diarios por inicio de sesión
     */
    @POST("puntos/usuario/{idUsuario}/inicio-sesion")
    suspend fun otorgarPuntosInicioSesion(
        @Path("idUsuario") idUsuario: Long
    ): Response<TransaccionPuntosResponseDto>
}

/**
 * DTO de Referido
 */
data class ReferidoDto(
    val id: Long? = null,
    val nombreReferido: String? = null,
    val apellidosReferido: String? = null,
    val emailReferido: String? = null,
    val runReferido: String? = null,
    val codigoReferido: String? = null,
    val puntosLevelup: Int? = null,
    val nivelUsuario: String? = null,
    val idReferidor: Long? = null,
    val activo: Boolean? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

/**
 * DTO de Puntos de Referido
 */
data class PuntosReferidoDto(
    val puntos: Int,
    val puntosUsados: Int,
    val puntosDisponibles: Int
)

/**
 * DTO de Recompensa
 */
data class RecompensaDto(
    val id: Long,
    val tipo: String,
    val descripcion: String,
    val puntos: Int,
    val fecha: String
)

/**
 * DTO de Descuento
 */
data class DescuentoDto(
    val id: Long,
    val codigo: String,
    val porcentaje: Double,
    val descripcion: String,
    val fechaExpiracion: String
)

/**
 * DTO de Transacción de puntos
 */
data class TransaccionPuntosResponseDto(
    val id: Long? = null,
    val idUsuario: Long? = null,
    val idEvento: Long? = null,
    val tipoTransaccion: String? = null,
    val puntos: Int? = null,
    val puntosAnteriores: Int? = null,
    val puntosNuevos: Int? = null,
    val descripcion: String? = null,
    val codigoReferencia: String? = null,
    val fechaTransaccion: String? = null,
    val evento: EventoPuntosResponseDto? = null
)

data class EventoPuntosResponseDto(
    val id: Long? = null,
    val tipoEvento: String? = null,
    val nombreEvento: String? = null,
    val descripcionEvento: String? = null,
    val puntosOtorgados: Int? = null,
    val codigoEvento: String? = null,
    val fechaEvento: String? = null,
    val lugarEvento: String? = null,
    val direccionEvento: String? = null,
    val idUsuario: Long? = null,
    val idProducto: Long? = null,
    val idPedido: Long? = null,
    val fechaCreacion: String? = null,
    val activo: Boolean? = null
)

/**
 * Respuesta paginada de referidos
 */
data class ReferidosResponsePage(
    val content: List<ReferidoDto>,
    val totalElements: Int,
    val totalPages: Int,
    val number: Int,
    val size: Int
)

