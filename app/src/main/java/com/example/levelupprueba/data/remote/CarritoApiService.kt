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
     * Crea un nuevo carrito para el usuario
     * Requiere header X-User-Id (se agrega automáticamente en ApiConfig)
     */
    @POST("carrito")
    suspend fun crearCarrito(@Body request: CarritoCreationRequest): Response<CarritoDto>
    
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
 * Request para crear carrito
 */
data class CarritoCreationRequest(
    @SerializedName("idUsuario")
    val idUsuario: Long,
    @SerializedName("codigoPromocional")
    val codigoPromocional: String? = null,
    @SerializedName("notasCarrito")
    val notasCarrito: String? = null
)

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
 * El backend espera un Map con la clave "cantidad"
 */
data class ActualizarCantidadRequest(
    val cantidad: Int
) {
    fun toMap(): Map<String, Int> = mapOf("cantidad" to cantidad)
}

/**
 * DTO de Carrito
 */
data class CarritoDto(
<<<<<<< HEAD
    @SerializedName("idCarrito")
    val idCarrito: Long? = null,
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("idUsuario")
    val idUsuario: Long? = null,
    @SerializedName("usuarioId")
    val usuarioId: Long? = null,
    @SerializedName("items")
    val items: List<ItemCarritoDto>? = null,
    @SerializedName("total")
    val total: Double? = null,
    @SerializedName("totalCarrito")
    val totalCarrito: Double? = null,
    @SerializedName("totalFinal")
    val totalFinal: Double? = null,
    @SerializedName("estado")
    val estado: String? = null,
    @SerializedName("estadoCarrito")
    val estadoCarrito: String? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
    @SerializedName("fechaCreacion")
    val fechaCreacion: String? = null,
    @SerializedName("fechaActualizacion")
    val fechaActualizacion: String? = null
=======
    val idCarrito: Long?,
    val id: Long?,
    val idUsuario: Long?,
    val usuarioId: Long?,
    val items: List<ItemCarritoDto>?,
    val total: Double?,
    val totalCarrito: Double?,
    val totalFinal: Double?,
    val estado: String?,
    val estadoCarrito: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val fechaCreacion: String?,
    val fechaActualizacion: String?
>>>>>>> main
)

/**
 * DTO de Item del Carrito
 */
data class ItemCarritoDto(
<<<<<<< HEAD
    @SerializedName("idItem")
    val idItem: Long? = null,
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("idCarrito")
    val idCarrito: Long? = null,
    @SerializedName("idProducto")
    val idProducto: Long? = null,
    @SerializedName("productoId")
    val productoId: Long? = null,
    @SerializedName("nombreProducto")
    val nombreProducto: String? = null,
    @SerializedName("descripcionProducto")
    val descripcionProducto: String? = null,
    @SerializedName("producto")
    val producto: ProductoDto? = null,
    @SerializedName("cantidad")
    val cantidad: Int = 0,
    @SerializedName("precioUnitario")
    val precioUnitario: Double? = null,
    @SerializedName("subtotal")
    val subtotal: Double? = null,
    @SerializedName("totalItem")
    val totalItem: Double? = null,
    @SerializedName("descuentoAplicado")
    val descuentoAplicado: Double? = null,
    @SerializedName("impuestoAplicado")
    val impuestoAplicado: Double? = null,
    @SerializedName("estadoItem")
    val estadoItem: String? = null,
    @SerializedName("activo")
    val activo: Boolean? = null
=======
    val idItem: Long?,
    val id: Long?,
    val idCarrito: Long?,
    val idProducto: Long?,
    val productoId: Long?,
    val nombreProducto: String?,
    val descripcionProducto: String?,
    val producto: ProductoDto?,
    val cantidad: Int,
    val precioUnitario: Double?,
    val subtotal: Double?,
    val totalItem: Double?,
    val descuentoAplicado: Double?,
    val impuestoAplicado: Double?,
    val estadoItem: String?,
    val activo: Boolean?
>>>>>>> main
)
