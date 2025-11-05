package com.example.levelupprueba.data.remote

import retrofit2.Response
import retrofit2.http.*

/**
 * Interfaz de API para inventario
 */
interface InventarioApiService {
    
    /**
     * Obtiene todo el inventario
     */
    @GET("inventario")
    suspend fun getInventario(): Response<List<InventarioDto>>
    
    /**
     * Obtiene inventario por ID
     */
    @GET("inventario/{id}")
    suspend fun getInventarioById(@Path("id") id: Long): Response<InventarioDto>
    
    /**
     * Obtiene inventario por producto ID
     */
    @GET("inventario/producto/{productoId}")
    suspend fun getInventarioPorProducto(@Path("productoId") productoId: Long): Response<InventarioDto>
    
    /**
     * Obtiene productos con stock crítico
     */
    @GET("inventario/stock-critico")
    suspend fun getStockCritico(): Response<List<InventarioDto>>
    
    /**
     * Obtiene productos agotados
     */
    @GET("inventario/agotados")
    suspend fun getAgotados(): Response<List<InventarioDto>>
}

/**
 * DTO de Inventario
 */
data class InventarioDto(
    val id: Long? = null,
    val productoId: Long? = null,
    val codigoProducto: String? = null,
    val cantidadDisponible: Int? = null,
    val stockCritico: Int? = null,
    val stockMaximo: Int? = null,
    val ubicacion: String? = null,
    val estado: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

/**
 * Respuesta paginada de inventario
 */
data class InventarioResponsePage(
    val content: List<InventarioDto>,
    val totalElements: Int,
    val totalPages: Int,
    val number: Int,
    val size: Int
)

