package com.example.levelupprueba.data.remote

import retrofit2.Response
import retrofit2.http.*

/**
 * Interfaz de API para pedidos
 * Endpoints: /api/v1/pedidos/
 */
interface PedidosApiService {
    
    /**
     * Crear nuevo pedido
     * POST /api/v1/pedidos
     */
    @POST("pedidos")
    suspend fun crearPedido(@Body request: PedidoCreationRequest): Response<PedidoResponse>
    
    /**
     * Obtener pedido por ID
     * GET /api/v1/pedidos/{id}
     */
    @GET("pedidos/{id}")
    suspend fun getPedidoById(@Path("id") id: Long): Response<PedidoResponse>
    
    /**
     * Obtener pedido por código
     * GET /api/v1/pedidos/codigo/{codigo}
     */
    @GET("pedidos/codigo/{codigo}")
    suspend fun getPedidoByCodigo(@Path("codigo") codigo: String): Response<PedidoResponse>
    
    /**
     * Obtener pedidos por usuario
     * GET /api/v1/pedidos/usuario/{idUsuario}
     */
    @GET("pedidos/usuario/{idUsuario}")
    suspend fun getPedidosPorUsuario(@Path("idUsuario") idUsuario: Long): Response<List<PedidoResponse>>
    
    /**
     * Obtener todos los pedidos
     * GET /api/v1/pedidos
     */
    @GET("pedidos")
    suspend fun getAllPedidos(): Response<List<PedidoResponse>>
    
    /**
     * Actualizar estado de pedido
     * PUT /api/v1/pedidos/{id}/estado
     */
    @PUT("pedidos/{id}/estado")
    suspend fun actualizarEstadoPedido(
        @Path("id") id: Long,
        @Body estado: PedidoEstadoRequest
    ): Response<PedidoResponse>
    
    /**
     * Eliminar pedido
     * DELETE /api/v1/pedidos/{id}
     */
    @DELETE("pedidos/{id}")
    suspend fun eliminarPedido(@Path("id") id: Long): Response<Unit>
}

/**
 * Request para crear pedido
 */
data class PedidoCreationRequest(
    val idUsuario: Long,
    val idCarrito: Long?,
    val nombreEnvio: String,
    val apellidoEnvio: String,
    val emailEnvio: String,
    val telefonoEnvio: String,
    val direccionEnvio: String,
    val departamentoEnvio: String? = null,
    val regionEnvio: String,
    val comunaEnvio: String,
    val indicadoresEntrega: String? = null
)

/**
 * Request para actualizar estado
 */
data class PedidoEstadoRequest(
    val estado: String
)

/**
 * Response de pedido
 */
data class PedidoResponse(
    val id: Long,
    val codigo: String,
    val idUsuario: Long,
    val nombreEnvio: String,
    val apellidoEnvio: String,
    val emailEnvio: String,
    val telefonoEnvio: String,
    val direccionEnvio: String,
    val departamentoEnvio: String?,
    val regionEnvio: String,
    val comunaEnvio: String,
    val indicadoresEntrega: String?,
    val subtotal: Double,
    val descuento: Double,
    val iva: Double,
    val total: Double,
    val estado: String,
    val fechaCreacion: String,
    val fechaActualizacion: String,
    val idCarrito: Long?,
    val idPago: Long?,
    val items: List<PedidoItemResponse>
)

/**
 * Item de pedido
 */
data class PedidoItemResponse(
    val id: Long,
    val idPedido: Long,
    val idProducto: Long,
    val nombreProducto: String,
    val precio: Double,
    val cantidad: Int,
    val subtotal: Double,
    val imagenUrl: String?
)