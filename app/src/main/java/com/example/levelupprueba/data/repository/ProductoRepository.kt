package com.example.levelupprueba.data.repository

import com.example.levelupprueba.data.local.ReviewDao
import com.example.levelupprueba.data.remote.ApiConfig
import com.example.levelupprueba.data.remote.ProductoMapper
import com.example.levelupprueba.data.remote.ReseniaDto
import com.example.levelupprueba.data.remote.ReseniaRequest
import com.example.levelupprueba.model.producto.ImagenCarrusel
import com.example.levelupprueba.model.producto.Producto
import com.example.levelupprueba.model.producto.Review
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.util.Log

/**
 * Repositorio de productos que obtiene datos desde el backend Spring Boot usando Retrofit
 */
class ProductoRepository(
    private val reviewDao: ReviewDao? = null // Mantenemos ReviewDao para reviews locales
) {
    
    private val productosService = ApiConfig.productosService
    private val reseniaService = ApiConfig.reseniaService
    
    /**
     * Obtiene imágenes del carrusel (hardcodeadas por ahora, se puede mover al backend)
     */
    fun obtenerImagenesCarrusel(): List<ImagenCarrusel> {
        return listOf(
            ImagenCarrusel(
                id = 1,
                imagenUrl = "carrusel",
                titulo = "¡Nuevos lanzamientos!",
                descripcion = "Descubre los últimos juegos y accesorios",
                enlace = ""
            ),
            ImagenCarrusel(
                id = 2,
                imagenUrl = "carrusel",
                titulo = "Ofertas especiales",
                descripcion = "Hasta 50% de descuento en productos seleccionados",
                enlace = ""
            ),
            ImagenCarrusel(
                id = 3,
                imagenUrl = "carrusel",
                titulo = "Setup gamer completo",
                descripcion = "Arma tu estación de juego perfecta",
                enlace = ""
            )
        )
    }
    
    /**
     * Obtiene todos los productos desde el backend
     */
    suspend fun obtenerProductos(): List<Producto> = withContext(Dispatchers.IO) {
        try {
            android.util.Log.d("ProductoRepository", "Intentando obtener productos desde el backend...")
            val response = productosService.getProductos()
            android.util.Log.d("ProductoRepository", "Response code: ${response.code()}")
            android.util.Log.d("ProductoRepository", "Response isSuccessful: ${response.isSuccessful}")
            
            if (response.isSuccessful && response.body() != null) {
                val dtos = response.body()!!
                android.util.Log.d("ProductoRepository", "Productos recibidos: ${dtos.size}")
                val productos = ProductoMapper.mapProductosDto(dtos)
                android.util.Log.d("ProductoRepository", "Productos mapeados: ${productos.size}")
                productos
            } else {
                val errorBody = response.errorBody()?.string()
                android.util.Log.e("ProductoRepository", "Error en respuesta: code=${response.code()}, message=${response.message()}")
                android.util.Log.e("ProductoRepository", "Error body: $errorBody")
                emptyList()
            }
        } catch (e: Exception) {
            android.util.Log.e("ProductoRepository", "Excepción al obtener productos: ${e.message}", e)
            e.printStackTrace()
            emptyList()
        }
    }
    
    /**
     * Obtiene un producto por ID desde el backend
     */
    suspend fun obtenerProductoPorId(id: String): Producto? = withContext(Dispatchers.IO) {
        try {
            val idLong = id.toLongOrNull() ?: return@withContext null
            val response = productosService.getProductoById(idLong)
            if (response.isSuccessful && response.body() != null) {
                val dto = response.body()!!
                ProductoMapper.mapProductoDto(dto)
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    
    /**
     * Obtiene productos destacados (filtra por disponible = true y rating > 4.0)
     */
    suspend fun obtenerProductosDestacados(): List<Producto> = withContext(Dispatchers.IO) {
        try {
            val productos = obtenerProductos()
            productos.filter { it.disponible && it.rating >= 4.0f }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
    
    /**
     * Obtiene productos relacionados (misma categoría, excluyendo el producto actual)
     */
    suspend fun obtenerProductosRelacionados(producto: Producto): List<Producto> = withContext(Dispatchers.IO) {
        try {
            val productos = obtenerProductos()
            productos
                .filter { it.id != producto.id && it.categoria == producto.categoria }
                .shuffled()
                .take(4)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
    
    /**
     * Obtiene productos por categoría desde el backend
     */
    suspend fun obtenerProductosPorCategoria(categoriaId: String): List<Producto> = withContext(Dispatchers.IO) {
        try {
            val response = productosService.getProductosPorCategoria(categoriaId)
            if (response.isSuccessful && response.body() != null) {
                val dtos = response.body()!!
                ProductoMapper.mapProductosDto(dtos)
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
    
    /**
     * Busca productos por nombre desde el backend
     */
    suspend fun buscarProductosPorNombre(nombre: String): List<Producto> = withContext(Dispatchers.IO) {
        try {
            val response = productosService.buscarProductos(nombre)
            if (response.isSuccessful && response.body() != null) {
                val dtos = response.body()!!
                ProductoMapper.mapProductosDto(dtos)
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
    
    /**
     * Obtiene productos disponibles desde el backend
     */
    suspend fun obtenerProductosDisponibles(): List<Producto> = withContext(Dispatchers.IO) {
        try {
            val response = productosService.getProductosDisponibles()
            if (response.isSuccessful && response.body() != null) {
                val dtos = response.body()!!
                ProductoMapper.mapProductosDto(dtos)
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    /**
     * Mapea ReseniaDto del backend a Review del modelo local
     */
    private fun mapearReseniaDtoToReview(dto: ReseniaDto, productoId: String): Review {
        return Review(
            id = dto.id?.toString() ?: System.currentTimeMillis().toString(),
            productoId = productoId,
            usuarioNombre = dto.usuarioNombre,
            rating = dto.rating.toFloat(),
            comentario = dto.comentario,
            fecha = dto.fechaCreacion ?: ""
        )
    }

    // Métodos de reviews usando el backend
    suspend fun obtenerReviews(productoId: String): List<Review> = withContext(Dispatchers.IO) {
        try {
            val productoIdLong = productoId.toLongOrNull() ?: return@withContext emptyList()
            Log.d("ProductoRepository", "Obteniendo reseñas del backend para producto: $productoId")
            
            val response = reseniaService.getReseniasPorProducto(productoIdLong)
            
            if (response.isSuccessful && response.body() != null) {
                val reseniasDto = response.body()!!
                Log.d("ProductoRepository", "Reseñas recibidas: ${reseniasDto.size}")
                val reviews = reseniasDto.map { mapearReseniaDtoToReview(it, productoId) }
                Log.d("ProductoRepository", "Reseñas mapeadas: ${reviews.size}")
                reviews
            } else {
                Log.e("ProductoRepository", "Error al obtener reseñas: code=${response.code()}, message=${response.message()}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("ProductoRepository", "Excepción al obtener reseñas: ${e.message}", e)
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun agregarReview(productoId: String, review: Review, idUsuario: Long? = null): Boolean = withContext(Dispatchers.IO) {
        try {
            val productoIdLong = productoId.toLongOrNull() ?: return@withContext false
            
            if (idUsuario == null) {
                Log.e("ProductoRepository", "Error: idUsuario es null, no se puede crear reseña")
                return@withContext false
            }
            
            val reseniaRequest = ReseniaRequest(
                idUsuario = idUsuario,
                usuarioNombre = review.usuarioNombre,
                rating = review.rating.toInt(),
                comentario = review.comentario
            )
            
            Log.d("ProductoRepository", "Creando reseña en backend para producto: $productoId")
            val response = reseniaService.crearResenia(productoIdLong, reseniaRequest)
            
            if (response.isSuccessful && response.body() != null) {
                Log.d("ProductoRepository", "Reseña creada exitosamente")
                true
            } else {
                Log.e("ProductoRepository", "Error al crear reseña: code=${response.code()}, message=${response.message()}")
                false
            }
        } catch (e: Exception) {
            Log.e("ProductoRepository", "Excepción al agregar reseña: ${e.message}", e)
            e.printStackTrace()
            false
        }
    }

    suspend fun borrarReview(review: Review): Boolean = withContext(Dispatchers.IO) {
        try {
            val reviewId = review.id.toLongOrNull() ?: return@withContext false
            
            Log.d("ProductoRepository", "Eliminando reseña del backend: $reviewId")
            val response = reseniaService.eliminarResenia(reviewId)
            
            if (response.isSuccessful) {
                Log.d("ProductoRepository", "Reseña eliminada exitosamente")
                true
            } else {
                Log.e("ProductoRepository", "Error al eliminar reseña: code=${response.code()}, message=${response.message()}")
                false
            }
        } catch (e: Exception) {
            Log.e("ProductoRepository", "Excepción al borrar reseña: ${e.message}", e)
            e.printStackTrace()
            false
        }
    }

    suspend fun obtenerRatingPromedio(productoId: String): Float = withContext(Dispatchers.IO) {
        try {
            val reviews = obtenerReviews(productoId)
            if (reviews.isEmpty()) {
                return@withContext 0f
            }
            val promedio = reviews.map { it.rating }.average().toFloat()
            Log.d("ProductoRepository", "Rating promedio calculado: $promedio")
            promedio
        } catch (e: Exception) {
            Log.e("ProductoRepository", "Excepción al calcular rating promedio: ${e.message}", e)
            0f
        }
    }

    suspend fun contarReviews(productoId: String): Int = withContext(Dispatchers.IO) {
        try {
            val reviews = obtenerReviews(productoId)
            reviews.size
        } catch (e: Exception) {
            Log.e("ProductoRepository", "Excepción al contar reseñas: ${e.message}", e)
            0
        }
    }
}
