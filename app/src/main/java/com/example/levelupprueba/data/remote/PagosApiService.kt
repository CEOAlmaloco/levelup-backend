package com.example.levelupprueba.data.remote

import retrofit2.Response
import retrofit2.http.*

/**
 * Interfaz de API para pagos
 * Endpoints: /api/v1/pagos/
 */
interface PagosApiService {
    
    /**
     * Crear nuevo pago
     * POST /api/v1/pagos
     */
    @POST("pagos")
    suspend fun crearPago(@Body request: PagoCreationRequest): Response<PagoResponse>
    
    /**
     * Procesar pago desde checkout
     * POST /api/v1/pagos/procesar
     */
    @POST("pagos/procesar")
    suspend fun procesarPagoCheckout(@Body request: PagoCreationRequest): Response<PagoResponse>
    
    /**
     * Procesar pago por ID
     * POST /api/v1/pagos/{id}/procesar
     */
    @POST("pagos/{id}/procesar")
    suspend fun procesarPago(@Path("id") id: Long): Response<PagoResponse>
    
    /**
     * Obtener pago por ID
     * GET /api/v1/pagos/{id}
     */
    @GET("pagos/{id}")
    suspend fun getPagoById(@Path("id") id: Long): Response<PagoResponse>
    
    /**
     * Obtener pagos por usuario
     * GET /api/v1/pagos/usuario/{idUsuario}
     */
    @GET("pagos/usuario/{idUsuario}")
    suspend fun getPagosPorUsuario(@Path("idUsuario") idUsuario: Long): Response<List<PagoResponse>>
    
    /**
     * Obtener pagos por pedido
     * GET /api/v1/pagos/pedido/{idPedido}
     */
    @GET("pagos/pedido/{idPedido}")
    suspend fun getPagosPorPedido(@Path("idPedido") idPedido: Long): Response<List<PagoResponse>>
    
    /**
     * Obtener todos los pagos
     * GET /api/v1/pagos
     */
    @GET("pagos")
    suspend fun getAllPagos(): Response<List<PagoResponse>>
    
    /**
     * Verificar estado de pago
     * GET /api/v1/pagos/{id}/verificar
     */
    @GET("pagos/{id}/verificar")
    suspend fun verificarPago(@Path("id") id: Long): Response<PagoResponse>
    
    /**
     * Cancelar pago
     * POST /api/v1/pagos/{id}/cancelar
     */
    @POST("pagos/{id}/cancelar")
    suspend fun cancelarPago(@Path("id") id: Long): Response<PagoResponse>
    
    /**
     * Reembolsar pago
     * POST /api/v1/pagos/{id}/reembolsar
     */
    @POST("pagos/{id}/reembolsar")
    suspend fun reembolsarPago(
        @Path("id") id: Long,
        @Query("montoReembolso") montoReembolso: Double
    ): Response<PagoResponse>
}

/**
 * Request para crear pago
 */
data class PagoCreationRequest(
    val idPedido: Long? = null,
    val idUsuario: Long,
    val montoPago: Double,
    val metodoPago: String,
    val numeroTarjetaEnmascarado: String? = null,
    val tipoTarjeta: String? = null,
    val bancoEmisor: String? = null
)

/**
 * Response de pago
 */
data class PagoResponse(
    val idPago: Long,
    val idPedido: Long?,
    val idUsuario: Long,
    val montoPago: Double,
    val monedaPago: String,
    val metodoPago: String,
    val numeroTarjetaEnmascarado: String?,
    val tipoTarjeta: String?,
    val bancoEmisor: String?,
    val numeroTransaccion: String?,
    val codigoAutorizacion: String?,
    val fechaPago: String?,
    val fechaProcesamiento: String?,
    val estadoPago: String,
    val codigoRespuesta: String?,
    val mensajeRespuesta: String?,
    val comisionPago: Double?,
    val montoNeto: Double?,
    val intentosPago: Int?,
    val maxIntentos: Int?,
    val fechaVencimiento: String?,
    val activo: Boolean?
)