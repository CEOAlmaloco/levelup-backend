package com.example.levelupprueba.data.remote

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.*

// ProductoDto está en el mismo paquete (ProductosApiService.kt)
// Accesible directamente sin import explícito, pero se puede importar para claridad

/**
 * Interfaz de API para carrito de compras
 */
interface CarritoApiService {
    
    /**
     * Obtiene el carrito activo del usuario
     * Requiere header X-User-Id (se agrega automáticamente en ApiConfig)
     */
    @GET("carrito/activo")
    suspend fun getCarritoActivo(): Response<CarritoDto>
    
    /**
     * Agrega un item al carrito
     */
    @POST("carrito/items")
    suspend fun agregarItem(@Body request: AgregarItemRequest): Response<CarritoDto>
    
    /**
     * Actualiza la cantidad de un item
     */
    @PUT("carrito/items/{itemId}")
    suspend fun actualizarCantidad(
        @Path("itemId") itemId: String,
        @Body request: ActualizarCantidadRequest
    ): Response<CarritoDto>
    
    /**
     * Elimina un item del carrito
     */
    @DELETE("carrito/items/{itemId}")
    suspend fun eliminarItem(@Path("itemId") itemId: String): Response<CarritoDto>
    
    /**
     * Vacía el carrito
     */
    @DELETE("carrito/vaciar")
    suspend fun vaciarCarrito(): Response<Unit>
}

/**
 * Request para agregar item
 */
data class AgregarItemRequest(
    @SerializedName("idCarrito")
    val idCarrito: Long,
    @SerializedName("idProducto")
    val idProducto: Long,
    val cantidad: Int
)

/**
 * Request para actualizar cantidad
 */
data class ActualizarCantidadRequest(
    val cantidad: Int
)

/**
 * DTO de Carrito
 */
data class CarritoDto(
    val id: Long?,
    val usuarioId: Long?,
    val items: List<ItemCarritoDto>,
    val total: Double?,
    val estado: String?,
    val createdAt: String?,
    val updatedAt: String?
)

/**
 * DTO de Item del Carrito
 */
data class ItemCarritoDto(
    val id: String,
    val productoId: Long?,
    val producto: ProductoDto,
    val cantidad: Int,
    val precioUnitario: Double,
    val subtotal: Double
)
