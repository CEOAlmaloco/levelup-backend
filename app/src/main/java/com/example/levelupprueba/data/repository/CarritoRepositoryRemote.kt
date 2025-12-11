package com.example.levelupprueba.data.repository

import android.content.Context
import android.util.Log
import com.example.levelupprueba.data.local.clearCarritoId
import com.example.levelupprueba.data.local.getCarritoId
import com.example.levelupprueba.data.local.saveCarritoId
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
import com.example.levelupprueba.data.remote.CarritoCreationRequest
import com.example.levelupprueba.data.remote.CarritoDto
import com.example.levelupprueba.data.remote.ItemCarritoDto

/**
 * Implementación de CarritoRepository usando Retrofit (backend)
 * Obtiene y sincroniza el carrito con el backend Spring Boot
 * Persiste el ID del carrito localmente usando DataStore
 */
class CarritoRepositoryRemote(
    private val context: Context
) : CarritoRepository {

    private val carritoService: CarritoApiService = ApiConfig.carritoService
    @Volatile
    private var carritoIdCache: Long? = null

    override suspend fun getCarrito(): Carrito = withContext(Dispatchers.IO) {
        try {
            val userId = ApiConfig.getUserId()
            Log.d("CarritoRepository", "Obteniendo carrito activo - UserId: $userId")
            
            if (userId == null) {
                Log.w("CarritoRepository", "Usuario no autenticado, retornando carrito vacío")
                return@withContext Carrito(items = emptyList())
            }

            Log.d("CarritoRepository", "Enviando petición GET /carrito/activo")
            val response = carritoService.getCarritoActivo()
            Log.d("CarritoRepository", "Respuesta recibida - Code: ${response.code()}, Success: ${response.isSuccessful}")
            
            val carritoDto = response.body()
            if (response.isSuccessful && carritoDto != null) {
                val carritoId = carritoDto.idCarrito ?: carritoDto.id
                carritoId?.let { 
                    carritoIdCache = it
                    saveCarritoId(context, it) // Persistir en DataStore
                }
                val itemsCount = carritoDto.items?.size ?: 0
                Log.d("CarritoRepository", "Carrito obtenido exitosamente - ID: $carritoId, Items: $itemsCount")
                mapCarritoDtoToCarrito(carritoDto)
            } else {
                Log.w("CarritoRepository", "No hay carrito activo o respuesta inválida - Code: ${response.code()}, Message: ${response.message()}")
                Carrito(items = emptyList())
            }
        } catch (e: Exception) {
            Log.e("CarritoRepository", "Error al obtener carrito: ${e.message}", e)
            Carrito(items = emptyList())
        }
    }

    override suspend fun agregarProducto(producto: Producto, cantidad: Int): Carrito = withContext(Dispatchers.IO) {
        try {
            val userId = ApiConfig.getUserId()
            Log.d("CarritoRepository", "Agregando producto al carrito - ProductoId: ${producto.id}, Cantidad: $cantidad, UserId: $userId")
            
            if (userId == null) {
                Log.e("CarritoRepository", "Usuario no autenticado")
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

            Log.d("CarritoRepository", "Enviando petición POST /carrito/items - CarritoId: $carritoId, ProductoId: $productoId, Cantidad: $cantidad")
            val response = carritoService.agregarItem(request)
            Log.d("CarritoRepository", "Respuesta recibida - Code: ${response.code()}, Success: ${response.isSuccessful}")
            
            if (response.isSuccessful && response.body() != null) {
                val carritoDto = response.body()!!
                val carritoId = carritoDto.idCarrito ?: carritoDto.id
                carritoId?.let { 
                    carritoIdCache = it
                    saveCarritoId(context, it) // Persistir en DataStore
                }
                val itemsCount = carritoDto.items?.size ?: 0
                Log.d("CarritoRepository", "Producto agregado exitosamente - CarritoId: $carritoId, Total items: $itemsCount")
                mapCarritoDtoToCarrito(carritoDto)
            } else {
                val errorMsg = "Error al agregar producto: ${response.code()} - ${response.message()}"
                Log.e("CarritoRepository", errorMsg)
                throw Exception(errorMsg)
            }
        } catch (e: Exception) {
            Log.e("CarritoRepository", "Excepción al agregar producto: ${e.message}", e)
            throw Exception("Error al agregar producto: ${e.message}")
        }
    }

    override suspend fun actualizarCantidad(itemId: String, delta: Int): Carrito = withContext(Dispatchers.IO) {
        try {
            val userId = ApiConfig.getUserId()
            Log.d("CarritoRepository", "Actualizando cantidad - ItemId: $itemId, Delta: $delta, UserId: $userId")
            
            if (userId == null) {
                Log.e("CarritoRepository", "Usuario no autenticado")
                throw Exception("Usuario no autenticado")
            }

            // Primero obtener el carrito actual para saber la cantidad actual
            val carritoActual = getCarrito()
            val itemActual = carritoActual.items.find { it.id == itemId }
            
            if (itemActual == null) {
                Log.e("CarritoRepository", "Item no encontrado en el carrito: $itemId")
                throw Exception("Item no encontrado en el carrito")
            }

            val nuevaCantidad = itemActual.cantidad + delta
            Log.d("CarritoRepository", "Cantidad actual: ${itemActual.cantidad}, Nueva cantidad: $nuevaCantidad")
            
            if (nuevaCantidad <= 0) {
                Log.d("CarritoRepository", "Cantidad <= 0, eliminando item")
                return@withContext eliminarItem(itemId)
            }

            // Convertir itemId de String a Long
            val itemIdLong = itemId.toLongOrNull()
                ?: throw Exception("ID de item inválido: $itemId")

            // El backend espera un Map<String, Int>
            val request = ActualizarCantidadRequest(cantidad = nuevaCantidad)

            Log.d("CarritoRepository", "Enviando petición PUT /carrito/items/$itemIdLong - Nueva cantidad: $nuevaCantidad")
            val response = carritoService.actualizarCantidad(itemIdLong, request)
            Log.d("CarritoRepository", "Respuesta recibida - Code: ${response.code()}, Success: ${response.isSuccessful}")
            
            if (response.isSuccessful && response.body() != null) {
                val carritoDto = response.body()!!
                val carritoId = carritoDto.idCarrito ?: carritoDto.id
                carritoId?.let { 
                    carritoIdCache = it
                    saveCarritoId(context, it) // Persistir en DataStore
                }
                Log.d("CarritoRepository", "Cantidad actualizada exitosamente")
                mapCarritoDtoToCarrito(carritoDto)
            } else {
                val errorMsg = "Error al actualizar cantidad: ${response.code()} - ${response.message()}"
                Log.e("CarritoRepository", errorMsg)
                throw Exception(errorMsg)
            }
        } catch (e: Exception) {
            Log.e("CarritoRepository", "Excepción al actualizar cantidad: ${e.message}", e)
            throw Exception("Error al actualizar cantidad: ${e.message}")
        }
    }

    override suspend fun eliminarItem(itemId: String): Carrito = withContext(Dispatchers.IO) {
        try {
            val userId = ApiConfig.getUserId()
            Log.d("CarritoRepository", "Eliminando item del carrito - ItemId: $itemId, UserId: $userId")
            
            if (userId == null) {
                Log.e("CarritoRepository", "Usuario no autenticado")
                throw Exception("Usuario no autenticado")
            }

            // Convertir itemId de String a Long
            val itemIdLong = itemId.toLongOrNull()
                ?: throw Exception("ID de item inválido: $itemId")

            Log.d("CarritoRepository", "Enviando petición DELETE /carrito/items/$itemIdLong")
            val response = carritoService.eliminarItem(itemIdLong)
            Log.d("CarritoRepository", "Respuesta recibida - Code: ${response.code()}, Success: ${response.isSuccessful}")
            
            if (response.isSuccessful && response.body() != null) {
                val carritoDto = response.body()!!
                val carritoId = carritoDto.idCarrito ?: carritoDto.id
                carritoId?.let { 
                    carritoIdCache = it
                    saveCarritoId(context, it) // Persistir en DataStore
                }
                val itemsCount = carritoDto.items?.size ?: 0
                Log.d("CarritoRepository", "Item eliminado exitosamente - CarritoId: $carritoId, Items restantes: $itemsCount")
                mapCarritoDtoToCarrito(carritoDto)
            } else {
                val errorMsg = "Error al eliminar item: ${response.code()} - ${response.message()}"
                Log.e("CarritoRepository", errorMsg)
                throw Exception(errorMsg)
            }
        } catch (e: Exception) {
            Log.e("CarritoRepository", "Excepción al eliminar item: ${e.message}", e)
            throw Exception("Error al eliminar item: ${e.message}")
        }
    }

    override suspend fun checkout(): Carrito = withContext(Dispatchers.IO) {
        try {
            val userId = ApiConfig.getUserId()
            Log.d("CarritoRepository", "Iniciando checkout - UserId: $userId")
            
            if (userId == null) {
                Log.e("CarritoRepository", "Usuario no autenticado para checkout")
                throw Exception("Usuario no autenticado")
            }

            // Vaciar el carrito después del checkout
            Log.d("CarritoRepository", "Enviando petición DELETE /carrito/vaciar")
            val response = carritoService.vaciarCarrito()
            Log.d("CarritoRepository", "Respuesta recibida - Code: ${response.code()}, Success: ${response.isSuccessful}")
            
            if (response.isSuccessful) {
                Log.d("CarritoRepository", "Checkout completado exitosamente - Carrito vaciado")
                // Limpiar el cache del carrito (memoria y DataStore)
                carritoIdCache = null
                clearCarritoId(context)
                // Verificar que el carrito esté realmente vacío obteniéndolo de nuevo
                val carritoVerificado = getCarrito()
                Log.d("CarritoRepository", "Carrito verificado después del checkout - Items: ${carritoVerificado.items.size}")
                carritoVerificado
            } else {
                val errorMsg = "Error al vaciar carrito: ${response.code()} - ${response.message()}"
                Log.e("CarritoRepository", errorMsg)
                throw Exception(errorMsg)
            }
        } catch (e: Exception) {
            Log.e("CarritoRepository", "Excepción en checkout: ${e.message}", e)
            throw Exception("Error en checkout: ${e.message}")
        }
    }

    private suspend fun ensureCarritoId(): Long = withContext(Dispatchers.IO) {
        // Primero intentar desde caché en memoria
        carritoIdCache?.let { return@withContext it }
        
        // Si no está en memoria, intentar desde DataStore
        val carritoIdPersistido = getCarritoId(context)
        carritoIdPersistido?.let {
            carritoIdCache = it
            Log.d("CarritoRepository", "ID de carrito recuperado desde DataStore: $it")
            return@withContext it
        }

        // Si no está en ningún lado, obtener del backend
        val userId = ApiConfig.getUserId()
        if (userId == null) {
            throw Exception("Usuario no autenticado")
        }

        val userIdLong = userId.toLongOrNull()
            ?: throw Exception("ID de usuario inválido: $userId")

        Log.d("CarritoRepository", "Intentando obtener carrito activo del backend")
        val response = carritoService.getCarritoActivo()
        val carritoDto = response.body()
        
        if (response.isSuccessful && carritoDto != null) {
            val carritoId = carritoDto.idCarrito ?: carritoDto.id
            if (carritoId != null) {
                carritoIdCache = carritoId
                saveCarritoId(context, carritoId) // Persistir en DataStore
                Log.d("CarritoRepository", "ID de carrito obtenido del backend y guardado: $carritoId")
                return@withContext carritoId
            }
        }

        // Si no hay carrito activo (código 400 o 404), crear uno nuevo
        if (response.code() == 400 || response.code() == 404) {
            Log.d("CarritoRepository", "No hay carrito activo (código ${response.code()}), creando uno nuevo")
            try {
                val createRequest = CarritoCreationRequest(
                    idUsuario = userIdLong,
                    codigoPromocional = null,
                    notasCarrito = null
                )
                val createResponse = carritoService.crearCarrito(createRequest)
                
                if (createResponse.isSuccessful && createResponse.body() != null) {
                    val nuevoCarritoDto = createResponse.body()!!
                    val nuevoCarritoId = nuevoCarritoDto.idCarrito ?: nuevoCarritoDto.id
                    if (nuevoCarritoId != null) {
                        carritoIdCache = nuevoCarritoId
                        saveCarritoId(context, nuevoCarritoId) // Persistir en DataStore
                        Log.d("CarritoRepository", "Carrito nuevo creado exitosamente - ID: $nuevoCarritoId")
                        return@withContext nuevoCarritoId
                    }
                }
                throw Exception("No se pudo crear el carrito: código ${createResponse.code()}")
            } catch (e: Exception) {
                Log.e("CarritoRepository", "Error al crear carrito nuevo: ${e.message}", e)
                throw Exception("No se pudo crear el carrito: ${e.message}")
            }
        }
        
        throw Exception("No se pudo obtener el carrito activo: código ${response.code()}")
    }

    /**
     * Mapea CarritoDto del backend a Carrito del modelo
     */
    private suspend fun mapCarritoDtoToCarrito(carritoDto: CarritoDto): Carrito = withContext(Dispatchers.IO) {
        // Filtrar solo items activos (activo: true)
        val items = (carritoDto.items ?: emptyList())
            .filter { it.activo == true } // Solo items activos
            .mapNotNull { itemDto ->
            try {
                // Usar idItem si existe, sino id, sino convertir a String
                val itemId = (itemDto.idItem ?: itemDto.id)?.toString() ?: ""
                
                // Intentar obtener el producto del DTO completo si existe
                val producto = itemDto.producto?.let { ProductoMapper.mapProductoDto(it) }
                    ?: run {
                        // Si no hay producto completo, intentar obtenerlo del backend
                        val productoId = (itemDto.idProducto ?: itemDto.productoId)?.toString() ?: ""
                        if (productoId.isNotEmpty()) {
                            try {
                                // Intentar obtener el producto real desde el backend
                                val productoIdLong = productoId.toLongOrNull()
                                if (productoIdLong != null) {
                                    val productoResponse = ApiConfig.productosService.getProductoById(productoIdLong)
                                    if (productoResponse.isSuccessful && productoResponse.body() != null) {
                                        val productoDto = productoResponse.body()!!
                                        ProductoMapper.mapProductoDto(productoDto)
                                    } else {
                                        // Si falla, crear uno básico pero con los datos del item
                                        android.util.Log.w("CarritoRepository", "No se pudo obtener producto $productoId del backend, usando datos del item")
                                        crearProductoBasicoDesdeItem(itemDto, productoId)
                                    }
                                } else {
                                    crearProductoBasicoDesdeItem(itemDto, productoId)
                                }
                            } catch (e: Exception) {
                                android.util.Log.e("CarritoRepository", "Error obteniendo producto $productoId: ${e.message}", e)
                                crearProductoBasicoDesdeItem(itemDto, productoId)
                            }
                        } else {
                            return@mapNotNull null
                        }
                    }
                
                CarritoItem(
                    id = itemId,
                    producto = producto,
                    cantidad = itemDto.cantidad
                )
            } catch (e: Exception) {
                android.util.Log.e("CarritoRepository", "Error mapeando item: ${e.message}", e)
                null
            }
        }
        
        val carritoId = carritoDto.idCarrito ?: carritoDto.id
        val usuarioId = carritoDto.idUsuario ?: carritoDto.usuarioId
        val estado = carritoDto.estadoCarrito ?: carritoDto.estado
        
        // Calcular el total desde los items activos (más confiable que el total del servidor)
        val totalCalculado = items.sumOf { it.totalLinea }
        val totalServidor = carritoDto.totalFinal ?: carritoDto.totalCarrito ?: carritoDto.total
        
        // Usar el total calculado si el total del servidor parece incorrecto o es null
        // Si el total del servidor es 0 y hay items, usar el calculado
        val totalFinal = if (totalServidor != null && totalServidor > 0 && items.isNotEmpty()) {
            // Si la diferencia es muy grande (más del 10%), usar el calculado
            val diferencia = kotlin.math.abs(totalServidor - totalCalculado)
            if (diferencia > totalServidor * 0.1) {
                android.util.Log.w("CarritoRepository", "Total del servidor ($totalServidor) difiere mucho del calculado ($totalCalculado), usando calculado")
                null // null para que use el subtotal calculado
            } else {
                totalServidor
            }
        } else {
            null // null para que use el subtotal calculado
        }
        
        android.util.Log.d("CarritoRepository", "Mapeando carrito - Items activos: ${items.size}, Total servidor: $totalServidor, Total calculado: $totalCalculado, Total final: $totalFinal")
        
        Carrito(
            id = carritoId,
            usuarioId = usuarioId,
            estado = estado,
            items = items,
            totalServidor = totalFinal
        )
    }

    /**
     * Crea un producto básico desde los datos del item cuando no se puede obtener el producto completo
     */
    private fun crearProductoBasicoDesdeItem(itemDto: ItemCarritoDto, productoId: String): com.example.levelupprueba.model.producto.Producto {
        return com.example.levelupprueba.model.producto.Producto(
            id = productoId,
            codigo = "",
            nombre = itemDto.nombreProducto ?: "Producto $productoId",
            descripcion = itemDto.descripcionProducto ?: "",
            precio = itemDto.precioUnitario ?: 0.0,
            precioConDescuentoBackend = itemDto.precioUnitario?.let { precio ->
                itemDto.descuentoAplicado?.let { descuento ->
                    precio - descuento
                } ?: precio
            },
            imagenUrl = "",
            categoria = com.example.levelupprueba.model.producto.Categoria.TODAS,
            categoriaNombre = null,
            subcategoria = null,
            subcategoriaNombre = null,
            rating = 0f,
            disponible = itemDto.activo ?: true,
            destacado = false,
            enOferta = itemDto.descuentoAplicado != null && itemDto.descuentoAplicado!! > 0,
            stock = 0,
            imagenesUrls = emptyList(),
            fabricante = null,
            distribuidor = null,
            descuento = itemDto.descuentoAplicado?.toInt(),
            reviews = emptyList(),
            productosRelacionados = emptyList()
        )
    }
}

