package com.example.levelupprueba.data.repository

import com.example.levelupprueba.data.local.ReviewDao
import com.example.levelupprueba.data.remote.ApiConfig
import com.example.levelupprueba.data.remote.ProductoMapper
import com.example.levelupprueba.model.producto.ImagenCarrusel
import com.example.levelupprueba.model.producto.Producto
import com.example.levelupprueba.model.producto.Review
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repositorio de productos que obtiene datos desde el backend Spring Boot usando Retrofit
 */
class ProductoRepository(
    private val reviewDao: ReviewDao? = null // Mantenemos ReviewDao para reviews locales
) {
    
    private val productosService = ApiConfig.productosService
    
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

    // Métodos de reviews (siguen usando Room local)
    suspend fun obtenerReviews(productoId: String): List<Review> = withContext(Dispatchers.IO) {
        return@withContext reviewDao?.getReviewsByProductoId(productoId) ?: listOf(
            Review(
                id = "1",
                productoId = productoId,
                usuarioNombre = "Juan Pérez",
                rating = 5f,
                comentario = "Excelente producto, llegó en perfectas condiciones y funciona perfecto.",
                fecha = "2024-10-10"
            ),
            Review(
                id = "2",
                productoId = productoId,
                usuarioNombre = "María González",
                rating = 4f,
                comentario = "Muy buena calidad, aunque el envío tardó un poco más de lo esperado.",
                fecha = "2024-10-08"
            ),
            Review(
                id = "3",
                productoId = productoId,
                usuarioNombre = "Carlos Ramírez",
                rating = 5f,
                comentario = "Increíble! Superó mis expectativas. Totalmente recomendado.",
                fecha = "2024-10-05"
            )
        )
    }

    suspend fun agregarReview(productoId: String, review: Review): Boolean = withContext(Dispatchers.IO) {
        return@withContext try {
            reviewDao?.insertReview(review)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun borrarReview(review: Review): Boolean = withContext(Dispatchers.IO) {
        return@withContext try {
            reviewDao?.deleteReview(review)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun obtenerRatingPromedio(productoId: String): Float = withContext(Dispatchers.IO) {
        return@withContext reviewDao?.getAverageRating(productoId) ?: 0f
    }

    suspend fun contarReviews(productoId: String): Int = withContext(Dispatchers.IO) {
        return@withContext reviewDao?.getReviewCount(productoId) ?: 0
    }
}
