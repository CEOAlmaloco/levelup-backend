package com.example.levelupprueba.data.repository

import com.example.levelupprueba.data.remote.ApiConfig
import com.example.levelupprueba.data.remote.CarritoApiService
import com.example.levelupprueba.data.remote.ProductoMapper
import com.example.levelupprueba.model.carrito.Carrito
import com.example.levelupprueba.model.carrito.CarritoItem
import com.example.levelupprueba.model.producto.Producto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// Importar DTOs del paquete remote (clases de nivel superior, no anidadas)
import com.example.levelupprueba.data.remote.AgregarItemRequest
import com.example.levelupprueba.data.remote.ActualizarCantidadRequest
import com.example.levelupprueba.data.remote.CarritoDto
import com.example.levelupprueba.data.remote.ItemCarritoDto

/**
 * Implementación de CarritoRepository usando Retrofit (backend)
 * Obtiene y sincroniza el carrito con el backend Spring Boot
 */
class CarritoRepositoryRemote : CarritoRepository {

    private val carritoService: CarritoApiService = ApiConfig.carritoService
    @Volatile
    private var carritoIdCache: Long? = null

    override suspend fun getCarrito(): Carrito = withContext(Dispatchers.IO) {
        try {
            val userId = ApiConfig.getUserId()
            if (userId == null) {
                // Si no hay usuario autenticado, retornar carrito vacío
                return@withContext Carrito(items = emptyList())
            }

            // Agregar header X-User-Id a la petición
            val response = carritoService.getCarritoActivo()
            
            val carritoDto = response.body()
            if (response.isSuccessful && carritoDto != null) {
                carritoDto.id?.let { carritoIdCache = it }
                mapCarritoDtoToCarrito(carritoDto)
            } else {
                // Si no hay carrito activo, retornar carrito vacío
                Carrito(items = emptyList())
            }
        } catch (e: Exception) {
            // En caso de error, retornar carrito vacío
            Carrito(items = emptyList())
        }
    }

    override suspend fun agregarProducto(producto: Producto, cantidad: Int): Carrito = withContext(Dispatchers.IO) {
        try {
            val userId = ApiConfig.getUserId()
            if (userId == null) {
                throw Exception("Usuario no autenticado")
            }

            val carritoId = ensureCarritoId()
            val productoId = producto.id.toLongOrNull()
                ?: throw Exception("ID de producto inválido: ${producto.id}")

            val request = AgregarItemRequest(
                idCarrito = carritoId,
                idProducto = productoId,
                cantidad = cantidad
            )

            val response = carritoService.agregarItem(request)
            
            if (response.isSuccessful && response.body() != null) {
                val carritoDto = response.body()!!
                carritoDto.id?.let { carritoIdCache = it }
                mapCarritoDtoToCarrito(carritoDto)
            } else {
                throw Exception("Error al agregar producto: ${response.message()}")
            }
        } catch (e: Exception) {
            throw Exception("Error al agregar producto: ${e.message}")
        }
    }

    override suspend fun actualizarCantidad(itemId: String, delta: Int): Carrito = withContext(Dispatchers.IO) {
        try {
            val userId = ApiConfig.getUserId()
            if (userId == null) {
                throw Exception("Usuario no autenticado")
            }

            // Primero obtener el carrito actual para saber la cantidad actual
            val carritoActual = getCarrito()
            val itemActual = carritoActual.items.find { it.id == itemId }
            
            if (itemActual == null) {
                throw Exception("Item no encontrado en el carrito")
            }

            val nuevaCantidad = itemActual.cantidad + delta
            
            if (nuevaCantidad <= 0) {
                // Si la cantidad es 0 o negativa, eliminar el item
                return@withContext eliminarItem(itemId)
            }

            val request = ActualizarCantidadRequest(
                cantidad = nuevaCantidad
            )

            val response = carritoService.actualizarCantidad(itemId, request)
            
            if (response.isSuccessful && response.body() != null) {
                val carritoDto = response.body()!!
                carritoDto.id?.let { carritoIdCache = it }
                mapCarritoDtoToCarrito(carritoDto)
            } else {
                throw Exception("Error al actualizar cantidad: ${response.message()}")
            }
        } catch (e: Exception) {
            throw Exception("Error al actualizar cantidad: ${e.message}")
        }
    }

    override suspend fun eliminarItem(itemId: String): Carrito = withContext(Dispatchers.IO) {
        try {
            val userId = ApiConfig.getUserId()
            if (userId == null) {
                throw Exception("Usuario no autenticado")
            }

            val response = carritoService.eliminarItem(itemId)
            
            if (response.isSuccessful && response.body() != null) {
                val carritoDto = response.body()!!
                carritoDto.id?.let { carritoIdCache = it }
                mapCarritoDtoToCarrito(carritoDto)
            } else {
                throw Exception("Error al eliminar item: ${response.message()}")
            }
        } catch (e: Exception) {
            throw Exception("Error al eliminar item: ${e.message}")
        }
    }

    override suspend fun checkout(): Carrito = withContext(Dispatchers.IO) {
        try {
            val userId = ApiConfig.getUserId()
            if (userId == null) {
                throw Exception("Usuario no autenticado")
            }

            // Vaciar el carrito después del checkout
            val response = carritoService.vaciarCarrito()
            
            if (response.isSuccessful) {
                Carrito(items = emptyList())
            } else {
                throw Exception("Error al vaciar carrito: ${response.message()}")
            }
        } catch (e: Exception) {
            throw Exception("Error en checkout: ${e.message}")
        }
    }

    private suspend fun ensureCarritoId(): Long = withContext(Dispatchers.IO) {
        carritoIdCache?.let { return@withContext it }

        val response = carritoService.getCarritoActivo()
        val carritoDto = response.body()
        if (response.isSuccessful && carritoDto?.id != null) {
            carritoIdCache = carritoDto.id
            return@withContext carritoDto.id
        }
        throw Exception("No se pudo obtener el carrito activo: código ${response.code()}")
    }

    /**
     * Mapea CarritoDto del backend a Carrito del modelo
     */
    private fun mapCarritoDtoToCarrito(carritoDto: CarritoDto): Carrito {
        val items = carritoDto.items.map { itemDto ->
            CarritoItem(
                id = itemDto.id,
                producto = ProductoMapper.mapProductoDto(itemDto.producto),
                cantidad = itemDto.cantidad
            )
        }
        return Carrito(
            id = carritoDto.id,
            usuarioId = carritoDto.usuarioId,
            estado = carritoDto.estado,
            items = items,
            totalServidor = carritoDto.total
        )
    }
}

