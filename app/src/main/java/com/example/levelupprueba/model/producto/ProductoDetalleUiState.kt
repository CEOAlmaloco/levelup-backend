package com.example.levelupprueba.model.producto

//que vamos actualizar en tiempo real? para pensar...
//seteamos el estado del producto detalle
data class ProductoDetalleUiState(
    val producto: Producto? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val cantidadCarrito: Int = 0,
    val mostrarFormularioReview: Boolean = false,
    val imagenSeleccionada: Int = 0
)

