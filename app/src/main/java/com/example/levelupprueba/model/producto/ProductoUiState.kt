package com.example.levelupprueba.model.producto
//recordatorio, el UI state es el estado de la interfaz de usuario y se usa para almacenar los datos que se muestran en la interfaz


data class ProductoUiState(
    val productos: List<Producto> = emptyList(),
    val productosDestacados: List<Producto> = emptyList(),
    val filtros: FiltrosState = FiltrosState(),
    val isLoading: Boolean = false,
    val error: String? = null,//si no es string es null 
    val mostrarFiltros: Boolean = false
)

