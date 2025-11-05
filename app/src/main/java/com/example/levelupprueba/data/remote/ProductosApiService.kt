package com.example.levelupprueba.data.remote

import retrofit2.Response
import retrofit2.http.*

/**
 * Interfaz de API para productos - Conecta con el backend Spring Boot
 */
interface ProductosApiService {
    
    /**
     * Obtiene todos los productos (sin paginación)
     */
    @GET("productos")
    suspend fun getProductos(): Response<List<ProductoDto>>
    
    /**
     * Obtiene un producto por ID
     */
    @GET("productos/{id}")
    suspend fun getProductoById(@Path("id") id: Long): Response<ProductoDto>
    
    /**
     * Busca productos por nombre
     */
    @GET("productos/buscar")
    suspend fun buscarProductos(@Query("nombre") nombre: String?): Response<List<ProductoDto>>
    
    /**
     * Obtiene productos por categoría
     */
    @GET("productos/categoria/{categoria}")
    suspend fun getProductosPorCategoria(@Path("categoria") categoria: String): Response<List<ProductoDto>>
    
    /**
     * Obtiene productos disponibles
     */
    @GET("productos/disponibles")
    suspend fun getProductosDisponibles(): Response<List<ProductoDto>>
    
    /**
     * Obtiene categorías y subcategorías
     */
    @GET("productos/categorias")
    suspend fun getCategorias(): Response<CategoriasResponse>
    
    /**
     * Filtrar productos con paginación (POST)
     */
    @POST("productos/filtrar")
    suspend fun filtrarProductos(@Body filtros: ProductoFiltroDto): Response<ProductoPaginadoResponseDto>
    
    /**
     * Filtrar productos con paginación (GET)
     */
    @GET("productos/filtrar")
    suspend fun filtrarProductosGet(
        @Query("categoria") categoria: String? = null,
        @Query("subcategorias") subcategorias: List<String>? = null,
        @Query("texto") texto: String? = null,
        @Query("precioMin") precioMin: Double? = null,
        @Query("precioMax") precioMax: Double? = null,
        @Query("disponible") disponible: Boolean? = null,
        @Query("rating") rating: Double? = null,
        @Query("orden") orden: String? = null,
        @Query("pagina") pagina: Int = 0,
        @Query("tamano") tamano: Int = 10
    ): Response<ProductoPaginadoResponseDto>
    
    /**
     * Crea un producto (solo admin)
     */
    @POST("productos")
    suspend fun crearProducto(@Body producto: ProductoDto): Response<ProductoDto>
    
    /**
     * Actualiza un producto (solo admin)
     */
    @PUT("productos/{id}")
    suspend fun actualizarProducto(
        @Path("id") id: Long,
        @Body producto: ProductoDto
    ): Response<ProductoDto>
    
    /**
     * Elimina un producto (solo admin)
     */
    @DELETE("productos/{id}")
    suspend fun eliminarProducto(@Path("id") id: Long): Response<Unit>
}

/**
 * DTO de Producto del backend
 */
data class ProductoDto(
    val id: Long? = null,
    val idProducto: Long? = null, // Alias del backend
    val titulo: String? = null,
    val nombre: String? = null, // Compatibilidad con frontend
    val nombreProducto: String? = null, // Alias del backend
    val categoriaId: String? = null,
    val subcategoriaId: String? = null,
    val imagen: String? = null,
    val imagenUrl: String? = null, // URL completa de S3 desde el backend
    val imagenS3Key: String? = null, // Referencia S3 guardada en BD
    val imagenes: String? = null, // JSON array como String (referencias S3)
    val imagenesUrls: String? = null, // JSON array de URLs completas de S3
    val imagenesS3Keys: String? = null, // JSON array de referencias S3
    val precio: Double? = null,
    val precioProducto: Double? = null, // Alias del backend
    val disponible: Boolean? = null,
    val activo: Boolean? = null, // Alias del backend
    val rating: Double? = null,
    val descripcion: String? = null,
    val descripcionProducto: String? = null, // Alias del backend
    val stock: Int? = null,
    val codigoProducto: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

/**
 * DTO para filtros de productos
 */
data class ProductoFiltroDto(
    val categoria: String? = null,
    val subcategorias: List<String>? = null,
    val texto: String? = null,
    val precioMin: Double? = null,
    val precioMax: Double? = null,
    val disponible: Boolean? = null,
    val rating: Double? = null,
    val orden: String? = null,
    val pagina: Int = 0,
    val tamano: Int = 10
)

/**
 * Respuesta paginada de productos
 */
data class ProductoPaginadoResponseDto(
    val productos: List<ProductoDto>? = null,
    val content: List<ProductoDto>? = null, // Compatibilidad
    val pagina: Int? = null,
    val number: Int? = null, // Compatibilidad
    val tamano: Int? = null,
    val size: Int? = null, // Compatibilidad
    val totalElementos: Int? = null,
    val totalElements: Int? = null, // Compatibilidad
    val totalPaginas: Int? = null,
    val totalPages: Int? = null // Compatibilidad
)

/**
 * Respuesta de categorías
 */
data class CategoriasResponse(
    val categorias: List<CategoriaDto>? = null,
    val subcategorias: List<SubcategoriaDto>? = null
)

/**
 * DTO de Categoría
 */
data class CategoriaDto(
    val id: String,
    val nombre: String
)

/**
 * DTO de Subcategoría
 */
data class SubcategoriaDto(
    val id: String,
    val nombre: String,
    val categoria: CategoriaDto? = null
)

