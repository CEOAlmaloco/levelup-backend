package com.example.levelupprueba.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupprueba.data.repository.CarritoRepository
import com.example.levelupprueba.model.carrito.Carrito
import com.example.levelupprueba.model.producto.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel del Carrito: orquesta acciones y expone estado a la UI.
 * Recibe el repositorio por constructor (Room / Fake / Remote).
 */
class CarritoViewModel(
    private val repo: CarritoRepository
) : ViewModel() {

    // -------- Estado observable por la UI --------

    // Loading: indica operación en curso (BD/red)
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    // Carrito: snapshot actual
    private val _carrito = MutableStateFlow(Carrito())
    val carrito: StateFlow<Carrito> = _carrito.asStateFlow()

    // Error: mensaje a mostrar (null si no hay)
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    // Carga inicial
    init { loadCarrito() }

    /** Carga/recarga el carrito desde el repositorio. */
    fun loadCarrito() = viewModelScope.launch {
        _loading.value = true
        _error.value = null
        try {
            _carrito.value = repo.getCarrito()
        } catch (e: Exception) {
            _error.value = e.message ?: "Error al cargar carrito"
        } finally {
            _loading.value = false
        }
    }

    /** Agrega un producto (por defecto +1). */
    fun onAgregar(producto: Producto, cantidad: Int = 1) = viewModelScope.launch {
        _loading.value = true
        _error.value = null
        try {
            _carrito.value = repo.agregarProducto(producto, cantidad)
        } catch (e: Exception) {
            _error.value = e.message ?: "Error al agregar producto"
        } finally {
            _loading.value = false
        }
    }

    /** Incrementa cantidad (+1). */
    fun onIncrement(itemId: String) = viewModelScope.launch {
        _loading.value = true
        _error.value = null
        try {
            _carrito.value = repo.actualizarCantidad(itemId, +1)
        } catch (e: Exception) {
            _error.value = e.message ?: "Error al aumentar cantidad"
        } finally {
            _loading.value = false
        }
    }

    /** Decrementa cantidad (-1). Si queda en 0, se elimina. */
    fun onDecrement(itemId: String) = viewModelScope.launch {
        _loading.value = true
        _error.value = null
        try {
            _carrito.value = repo.actualizarCantidad(itemId, -1)
        } catch (e: Exception) {
            _error.value = e.message ?: "Error al disminuir cantidad"
        } finally {
            _loading.value = false
        }
    }

    /** Elimina un ítem del carrito. */
    fun onEliminar(itemId: String) = viewModelScope.launch {
        _loading.value = true
        _error.value = null
        try {
            _carrito.value = repo.eliminarItem(itemId)
        } catch (e: Exception) {
            _error.value = e.message ?: "Error al eliminar ítem"
        } finally {
            _loading.value = false
        }
    }

    /** Checkout (Room: limpiar; remoto: llamar backend). */
    fun onCheckout() = viewModelScope.launch {
        _loading.value = true
        _error.value = null
        try {
            _carrito.value = repo.checkout()
        } catch (e: Exception) {
            _error.value = e.message ?: "Error en checkout"
        } finally {
            _loading.value = false
        }
    }
}
