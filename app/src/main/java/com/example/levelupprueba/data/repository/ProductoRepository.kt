package com.example.levelupprueba.data.repository

import android.content.Context
import com.example.levelupprueba.data.local.*
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
 * Con soporte para caché local usando DataStore
 */
class ProductoRepository(private val context: Context? = null) {
    
    private val productosService = ApiConfig.productosService
    private val reseniaService = ApiConfig.reseniaService
    
    /**
     * Obtiene imágenes del carrusel desde el backend
     */
    suspend fun obtenerImagenesCarrusel(): List<ImagenCarrusel> = withContext(Dispatchers.IO) {
        try {
            android.util.Log.d("ProductoRepository", "Obteniendo imágenes del carrusel desde el backend...")
            val response = productosService.getCarrusel()
            
            if (response.isSuccessful && response.body() != null) {
                val carruselDto = response.body()!!
                android.util.Log.d("ProductoRepository", "Carrusel recibido: ${carruselDto.size} imágenes")
                
                val imagenes = carruselDto.mapNotNull { item ->
                    val id = item["id"]?.toIntOrNull() ?: return@mapNotNull null
                    val url = item["url"] ?: ""
                    val titulo = item["titulo"] ?: item["nombre"] ?: ""
                    val descripcion = item["descripcion"] ?: ""
                    val enlace = item["enlace"] ?: ""
                    
                    android.util.Log.d("ProductoRepository", "Carrusel item $id: url=$url, titulo=$titulo")
                    
                    ImagenCarrusel(
                        id = id,
                        imagenUrl = url,
                        titulo = titulo,
                        descripcion = descripcion,
                        enlace = enlace
                    )
                }
                
                android.util.Log.d("ProductoRepository", "Carrusel mapeado: ${imagenes.size} imágenes")
                imagenes
            } else {
                android.util.Log.w("ProductoRepository", "Error al obtener carrusel: code=${response.code()}")
                emptyList()
            }
        } catch (e: Exception) {
            android.util.Log.e("ProductoRepository", "Excepción al obtener carrusel: ${e.message}", e)
            e.printStackTrace()
            emptyList()
        }
    }
    
    /**
     * Obtiene la URL del logo desde el backend
     * Intenta primero con la URL principal, si falla usa el fallback
     */
    suspend fun obtenerLogoUrl(): String = withContext(Dispatchers.IO) {
        try {
            android.util.Log.d("ProductoRepository", "Obteniendo logo desde el backend...")
            val response = productosService.getLogo()
            
            if (response.isSuccessful && response.body() != null) {
                val logoDto = response.body()!!
                val logoUrl = logoDto["url"] ?: ""
                val fallbackUrl = logoDto["fallback"] ?: ""
                
                android.util.Log.d("ProductoRepository", "Logo recibido: url=$logoUrl, fallback=$fallbackUrl")
                
                // Devolver la URL principal, el MediaUrlResolver puede manejar el fallback si es necesario
                // O devolver ambas para que el frontend decida
                if (logoUrl.isNotBlank()) {
                    logoUrl
                } else if (fallbackUrl.isNotBlank()) {
                    fallbackUrl
                } else {
                    ""
                }
            } else {
                android.util.Log.w("ProductoRepository", "Error al obtener logo: code=${response.code()}")
                ""
            }
        } catch (e: Exception) {
            android.util.Log.e("ProductoRepository", "Excepción al obtener logo: ${e.message}", e)
            e.printStackTrace()
            ""
        }
    }
    
    /**
     * Obtiene todos los productos: primero desde caché, si no existe o expiró, desde el backend
     */
    suspend fun obtenerProductos(forceRefresh: Boolean = false): List<Producto> = withContext(Dispatchers.IO) {
        // Intentar cargar desde caché primero (si no se fuerza refresh y hay context)
        if (!forceRefresh && context != null) {
            val cached = getProductosCache(context)
            if (cached != null && cached.isNotEmpty()) {
                android.util.Log.d("ProductoRepository", "Productos cargados desde caché: ${cached.size}")
                return@withContext cached
            }
        }
        
        // Si no hay caché válido, cargar desde backend
        return@withContext obtenerProductosDesdeBackend()
    }
    
    /**
     * Obtiene productos directamente desde el backend (sin caché)
     */
    private suspend fun obtenerProductosDesdeBackend(): List<Producto> = withContext(Dispatchers.IO) {
        try {
            android.util.Log.d("ProductoRepository", "Cargando productos desde el backend...")
            val response = productosService.getProductos()
            android.util.Log.d("ProductoRepository", "Response code: ${response.code()}")
            android.util.Log.d("ProductoRepository", "Response isSuccessful: ${response.isSuccessful}")
            
            if (response.isSuccessful && response.body() != null) {
                val dtos = response.body()!!
                android.util.Log.d("ProductoRepository", "Productos recibidos: ${dtos.size}")
                val productos = ProductoMapper.mapProductosDto(dtos)
                android.util.Log.d("ProductoRepository", "Productos mapeados: ${productos.size}")
                
                // Guardar en caché si hay context
                if (context != null) {
                    saveProductosCache(context, productos)
                }
                
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
            android.util.Log.d("ProductoRepository", "Obteniendo producto por ID: $id")
            val idLong = id.toLongOrNull()
            if (idLong == null) {
                android.util.Log.e("ProductoRepository", "Error: ID '$id' no es un número válido. El backend requiere un ID numérico (Long).")
                return@withContext null
            }
            
            android.util.Log.d("ProductoRepository", "Buscando producto con ID numérico: $idLong")
            val response = productosService.getProductoById(idLong)
            
            if (response.isSuccessful && response.body() != null) {
                val dto = response.body()!!
                android.util.Log.d("ProductoRepository", "Producto encontrado: ${dto.nombreProducto ?: dto.titulo}")
                ProductoMapper.mapProductoDto(dto)
            } else {
                val errorBody = try {
                    response.errorBody()?.string()
                } catch (e: Exception) {
                    "Error al leer errorBody: ${e.message}"
                }
                android.util.Log.e("ProductoRepository", "Error al obtener producto por ID $idLong: code=${response.code()}, message=${response.message()}, body=$errorBody")
                null
            }
        } catch (e: Exception) {
            android.util.Log.e("ProductoRepository", "Excepción al obtener producto por ID '$id': ${e.message}", e)
            e.printStackTrace()
            null
        }
    }
    
    /**
     * Obtiene productos destacados: primero desde caché, si no existe, desde el backend
     */
    suspend fun obtenerProductosDestacados(forceRefresh: Boolean = false): List<Producto> = withContext(Dispatchers.IO) {
        // Intentar cargar desde caché primero (si no se fuerza refresh y hay context)
        if (!forceRefresh && context != null) {
            val cached = getProductosDestacadosCache(context)
            if (cached != null && cached.isNotEmpty()) {
                android.util.Log.d("ProductoRepository", "Productos destacados cargados desde caché: ${cached.size}")
                return@withContext cached
            }
        }
        
        // Si no hay caché válido, calcular desde productos
        try {
            val productos = obtenerProductos(forceRefresh)
            android.util.Log.d("ProductoRepository", "Total productos: ${productos.size}")
            
            // Log de productos para debugging
            productos.forEach { producto ->
                android.util.Log.d("ProductoRepository", 
                    "Producto: ${producto.nombre}, disponible: ${producto.disponible}, destacado: ${producto.destacado}, rating: ${producto.rating}")
            }
            
            // Priorizar productos marcados como destacados, luego los de alto rating
            // Si no hay destacados, mostrar los de mejor rating (>= 3.5) o los primeros disponibles
            // Nota: Si destacado es null, se considera false, pero usamos rating como fallback
            val destacados = productos.filter { producto ->
                producto.disponible && (
                    producto.destacado == true || 
                    producto.rating >= 3.5f
                )
            }
            
            android.util.Log.d("ProductoRepository", "Productos destacados encontrados: ${destacados.size}")
            
            if (destacados.isEmpty() && productos.isNotEmpty()) {
                // Si no hay destacados, tomar los primeros 6 disponibles
                val primerosDisponibles = productos.filter { it.disponible }.take(6)
                android.util.Log.d("ProductoRepository", "No hay destacados, usando primeros disponibles: ${primerosDisponibles.size}")
                return@withContext primerosDisponibles.sortedByDescending { it.rating }
            }
            
            val resultado = destacados.sortedByDescending { producto ->
                // Ordenar: primero los destacados, luego por rating
                when {
                    producto.destacado -> 10f + producto.rating
                    else -> producto.rating
                }
            }
            
            // Guardar en caché si hay context
            if (context != null && resultado.isNotEmpty()) {
                saveProductosDestacadosCache(context, resultado)
            }
            
            resultado
        } catch (e: Exception) {
            android.util.Log.e("ProductoRepository", "Error al obtener productos destacados: ${e.message}", e)
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
            Log.d("ProductoRepository", "Enviando petición GET /resenias/producto/$productoIdLong")
            
            val response = reseniaService.getReseniasPorProducto(productoIdLong)
            Log.d("ProductoRepository", "Respuesta recibida - Code: ${response.code()}, Success: ${response.isSuccessful}")
            
            if (response.isSuccessful && response.body() != null) {
                val reseniasDto = response.body()!!
                Log.d("ProductoRepository", "Reseñas recibidas: ${reseniasDto.size}")
                val reviews = reseniasDto.map { mapearReseniaDtoToReview(it, productoId) }
                Log.d("ProductoRepository", "Reseñas mapeadas: ${reviews.size}")
                reviews
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e("ProductoRepository", "Error al obtener reseñas: code=${response.code()}, message=${response.message()}, body=$errorBody")
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
            Log.d("ProductoRepository", "Datos de reseña - UsuarioId: $idUsuario, Rating: ${review.rating}, Comentario: ${review.comentario.take(50)}...")
            Log.d("ProductoRepository", "Enviando petición POST /resenias/producto/$productoIdLong")
            val response = reseniaService.crearResenia(productoIdLong, reseniaRequest)
            Log.d("ProductoRepository", "Respuesta recibida - Code: ${response.code()}, Success: ${response.isSuccessful}")
            
            if (response.isSuccessful && response.body() != null) {
                Log.d("ProductoRepository", "Reseña creada exitosamente")
                true
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e("ProductoRepository", "Error al crear reseña: code=${response.code()}, message=${response.message()}, body=$errorBody")
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
            Log.d("ProductoRepository", "Enviando petición DELETE /resenias/$reviewId")
            val response = reseniaService.eliminarResenia(reviewId)
            Log.d("ProductoRepository", "Respuesta recibida - Code: ${response.code()}, Success: ${response.isSuccessful}")
            
            if (response.isSuccessful) {
                Log.d("ProductoRepository", "Reseña eliminada exitosamente")
                true
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e("ProductoRepository", "Error al eliminar reseña: code=${response.code()}, message=${response.message()}, body=$errorBody")
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
