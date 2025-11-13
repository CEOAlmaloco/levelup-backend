package com.example.levelupprueba.data.remote

import retrofit2.Response
import retrofit2.http.*

/**
 * Interfaz de API para promociones
 * Endpoints: /api/v1/promociones/
 */
interface PromocionesApiService {
    
    /**
     * Obtener todas las promociones
     * GET /api/v1/promociones
     */
    @GET("promociones")
    suspend fun getAllPromociones(): Response<List<PromocionResponse>>
    
    /**
     * Obtener promociones activas
     * GET /api/v1/promociones/activas
     */
    @GET("promociones/activas")
    suspend fun getPromocionesActivas(): Response<List<PromocionResponse>>
    
    /**
     * Obtener promociones Duoc
     * GET /api/v1/promociones/duoc
     */
    @GET("promociones/duoc")
    suspend fun getPromocionesDuoc(): Response<List<PromocionResponse>>
    
    /**
     * Obtener promoción por ID
     * GET /api/v1/promociones/{id}
     */
    @GET("promociones/{id}")
    suspend fun getPromocionById(@Path("id") id: Long): Response<PromocionResponse>
    
    /**
     * Obtener promoción por código
     * GET /api/v1/promociones/codigo/{codigo}
     */
    @GET("promociones/codigo/{codigo}")
    suspend fun getPromocionByCodigo(@Path("codigo") codigo: String): Response<PromocionResponse>
    
    /**
     * Validar promoción
     * GET /api/v1/promociones/validar/{codigo}
     */
    @GET("promociones/validar/{codigo}")
    suspend fun validarPromocion(@Path("codigo") codigo: String): Response<Boolean>
    
    /**
     * Validar promoción con parámetros
     * POST /api/v1/promociones/validar
     */
    @POST("promociones/validar")
    suspend fun validarPromocionConParametros(@Body request: ValidarPromocionRequest): Response<Boolean>
    
    /**
     * Aplicar promoción
     * POST /api/v1/promociones/aplicar
     */
    @POST("promociones/aplicar")
    suspend fun aplicarPromocion(@Body request: AplicarPromocionRequest): Response<PromocionResponse>
    
    /**
     * Obtener promociones por categoría
     * GET /api/v1/promociones/categoria/{categoria}
     */
    @GET("promociones/categoria/{categoria}")
    suspend fun getPromocionesPorCategoria(@Path("categoria") categoria: String): Response<List<PromocionResponse>>
    
    /**
     * Obtener promociones de usuario
     * GET /api/v1/promociones/usuario?correoUsuario={correo}
     */
    @GET("promociones/usuario")
    suspend fun getPromocionesUsuario(@Query("correoUsuario") correoUsuario: String?): Response<List<PromocionResponse>>
}

/**
 * Request para validar promoción
 */
data class ValidarPromocionRequest(
    val codigo: String,
    val montoTotal: Double,
    val correoUsuario: String
)

/**
 * Request para aplicar promoción
 */
data class AplicarPromocionRequest(
    val codigo: String,
    val montoTotal: Double,
    val correoUsuario: String
)

/**
 * Response de promoción
 */
data class PromocionResponse(
    val idPromocion: Long,
    val codigoPromocion: String,
    val nombrePromocion: String,
    val descripcionPromocion: String,
    val tipoDescuento: String,
    val valorDescuento: Double,
    val montoMinimo: Double?,
    val montoMaximoDescuento: Double?,
    val fechaInicio: String,
    val fechaFin: String,
    val usosMaximos: Int?,
    val usosActuales: Int?,
    val usosPorUsuario: Int?,
    val activo: Boolean,
    val aplicableDuoc: Boolean,
    val categoriaAplicable: String?,
    val puntosRequeridos: Int?,
    val tipoPromocion: String?
)