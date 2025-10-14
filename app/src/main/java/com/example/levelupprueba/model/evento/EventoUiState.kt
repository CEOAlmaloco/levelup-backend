package com.example.levelupprueba.model.evento

// esto es un estado para la pantalla de eventos
// sirve para almacenar los datos que se muestran en la interfaz y se actualizan en tiempo real
data class EventoUiState(
    val eventos: List<Evento> = emptyList(),
    val recompensas: List<RecompensaCanje> = emptyList(),
    val puntosUsuario: Int = 0,
    val eventoSeleccionado: Evento? = null,
    val codigoIngresado: String = "",
    val mensajeCodigo: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

