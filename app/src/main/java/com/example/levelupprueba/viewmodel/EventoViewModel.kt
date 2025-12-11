package com.example.levelupprueba.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupprueba.data.local.getUserSessionFlow
import com.example.levelupprueba.data.remote.ApiConfig
import com.example.levelupprueba.data.remote.CanjeCodigoEventoRequest
import com.example.levelupprueba.data.remote.CanjePuntosRequest
import com.example.levelupprueba.data.remote.PuntosUsuarioResponseDto
import com.example.levelupprueba.data.repository.EventoRepositoryRemote
import com.example.levelupprueba.model.evento.Evento
import com.example.levelupprueba.model.evento.EventoUiState
import com.example.levelupprueba.model.evento.RecompensaCanje
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/* Este viewmodel se encarga de la logica de la pantalla de eventos  UI 
*/
class EventoViewModel(
    private val repository: EventoRepositoryRemote = EventoRepositoryRemote()
) : ViewModel() {

    private val _estado = MutableStateFlow(EventoUiState())//creamos el estado mutable para que se actualice en tiempo real
    val estado: StateFlow<EventoUiState> = _estado//creamos el estado para que se pueda acceder a el desde cualquier parte de la aplicacion

    private var context: Context? = null
    private var usuarioId: Long? = null

    /* el context  se usa para acceder a recursos archivos preferencias bases de datos.
    entonces con esto lo q hacemos es hacer una variable global para que se pueda acceder a ella desde cualquier parte de la aplicacion.
    luego llamamos a una funcion para cargar los puntos del usuario.
     */
    fun inicializar(ctx: Context) {
        context = ctx.applicationContext
        cargarDatosIniciales()
        viewModelScope.launch {
            val session = getUserSessionFlow(context!!).first()
            usuarioId = session.userId.takeIf { it > 0 }
            cargarPuntosUsuario()
        }
    }

    /**
     * Carga eventos y recompensas al iniciar el ViewModel.
     */
    private fun cargarDatosIniciales() {
        viewModelScope.launch {//lanzamos la corrutina
            Log.d("EventoViewModel", "Cargando eventos desde backend...")
            _estado.update { it.copy(isLoading = true, error = null) }//actualizamos el estado para que se muestre el loading y el error
            try {
                val eventos = repository.obtenerEventos()//obtenemos los eventos desde el backend usando Retrofit
                val recompensas = repository.obtenerRecompensas()// seteamos otra variable local de otra funcion 
                _estado.update {
                    it.copy(
                        eventos = eventos,
                        recompensas = recompensas,
                        isLoading = false,
                        eventoSeleccionado = eventos.firstOrNull() //first or null es para que si no hay eventos se muestre el primero o null 
                    )
                }
                Log.d("EventoViewModel", "Eventos cargados: ${eventos.size}")
            } catch (e: Exception) {
                Log.e("EventoViewModel", "Error al cargar eventos", e)
                _estado.update {
                    it.copy(
                        isLoading = false,
                        error = "Error al cargar eventos: ${e.message}"//reciclamos nuevamente el try catch 
                    )
                }
            }
        }
    }

    /*
    cargamo los puntos del usuario actual desde DataStore.
     */
    private fun cargarPuntosUsuario() {
        val id = usuarioId ?: run {
            _estado.update { it.copy(puntosUsuario = 0) }
            return
        }

        viewModelScope.launch {
            try {
                val response = ApiConfig.referidosService.getPuntosUsuario(id)
                if (response.isSuccessful && response.body() != null) {
                    val puntosDto = response.body()!!
                    val puntosDisponibles = puntosDto.puntosDisponibles ?: puntosDto.puntosTotales ?: 0
                    Log.d("EventoViewModel", "Puntos actuales del usuario: $puntosDisponibles")
                    _estado.update { it.copy(puntosUsuario = puntosDisponibles) }
                } else {
                    Log.w(
                        "EventoViewModel",
                        "No se pudieron cargar los puntos: ${response.code()} ${response.message()}"
                    )
                    _estado.update { it.copy(puntosUsuario = 0) }
                }
            } catch (e: Exception) {
                Log.w("EventoViewModel", "No se pudieron cargar los puntos del usuario", e)
                _estado.update { it.copy(puntosUsuario = 0) }
            }
        }
    }

    /* CUANDO le damos click en el evento se actualiza el estado para que se muestre el evento seleccionado en el mapa */
    fun seleccionarEvento(evento: Evento) {
        _estado.update { it.copy(eventoSeleccionado = evento) }
    }

    /* cuando ingresamos el codigo se actualiza el estado para que se muestre el codigo ingresado */
    fun onCodigoChange(codigo: String) {
        _estado.update { 
            it.copy(
                codigoIngresado = codigo.uppercase(),//validacion pára evitar errores  
                mensajeCodigo = ""//limpiamos el mensaje de codigo despues de ingresar el codigo
            ) 
        }
    }

    /* cuando le damos click en canjear codigo se actualiza el estado para que se muestre el mensaje de codigo ingresado */
    fun canjearCodigo() {
        val codigo = _estado.value.codigoIngresado.trim()//eliminamo los espacios del codigo para q si funcione 
        
        if (codigo.isEmpty()) {//si el codigo esta vacio entonces le enviamo una validacion
            _estado.update { 
                it.copy(mensajeCodigo = "Ingresa un código válido") 
            }
            return
        }

        viewModelScope.launch {//lanzamos la corrutina de canje
            try {
                val id = usuarioId
                if (id == null) {
                    _estado.update {
                        it.copy(mensajeCodigo = "Debes iniciar sesión para canjear un código")
                    }
                    return@launch // return @ launch es para que se detenga la corrutina osea se maneja async y no por tiempo de espera
                }

                val response = ApiConfig.referidosService.canjearCodigoEvento(
                    id,
                    CanjeCodigoEventoRequest(codigoEvento = codigo.uppercase())
                )

                if (response.isSuccessful) {
                    val puntosGanados = response.body()?.puntos ?: 0
                    cargarPuntosUsuario()
                    _estado.update {
                        it.copy(
                            codigoIngresado = "",
                            mensajeCodigo = "Código canjeado: +$puntosGanados pts"
                        )
                    }
                } else {
                    val mensajeError = when (response.code()) {
                        404 -> "Código inválido o expirado"
                        409 -> "Este código ya fue canjeado"
                        else -> "No fue posible canjear el código (${response.code()})"
                    }
                    _estado.update { it.copy(mensajeCodigo = mensajeError) }
                }
            } catch (e: Exception) {
                _estado.update {
                    it.copy(
                        mensajeCodigo = "Error al validar código: ${e.message}"
                    )
                }
            }
        }
    }

    /* cuando le damos click en canjear recompensa se actualiza el estado para que se muestre el mensaje de recompensa ingresada */
    /* y se actualiza el usuario en la bd*/
    fun canjearRecompensa(recompensa: RecompensaCanje) {
        val id = usuarioId
        if (id == null) {
            _estado.update {
                it.copy(mensajeCodigo = "Debes iniciar sesión para canjear puntos")
            }
            return
        }

        viewModelScope.launch {
            try {
                val puntosActuales = _estado.value.puntosUsuario
                if (puntosActuales < recompensa.costo) {
                    _estado.update {
                        it.copy(mensajeCodigo = "No tienes puntos suficientes para esta recompensa")
                    }
                    return@launch
                }

                val response = ApiConfig.referidosService.canjearPuntos(
                    id,
                    CanjePuntosRequest(
                        puntosACanjear = recompensa.costo,
                        descripcion = recompensa.descripcion,
                        codigoReferencia = recompensa.id
                    )
                )

                if (response.isSuccessful) {
                    cargarPuntosUsuario()
                    _estado.update {
                        it.copy(mensajeCodigo = "Recompensa canjeada correctamente")
                    }
                } else {
                    _estado.update {
                        it.copy(
                            mensajeCodigo = "No fue posible canjear la recompensa (${response.code()})"
                        )
                    }
                }
            } catch (e: Exception) {
                _estado.update {
                    it.copy(error = "Error al canjear recompensa: ${e.message}")
                }
            }
        }
    }

    /* cuando le damos click en limpiar mensaje se actualiza el estado para que se muestre el mensaje de recompensa ingresada */
    fun limpiarMensajeCodigo() {
        _estado.update { it.copy(mensajeCodigo = "") }//le ponemo en blanco na mas
    }

    /* cuando le damos click en refrescar eventos se actualiza el estado para que se muestre el mensaje de eventos refrescados */
    fun refrescarEventos() {
        cargarDatosIniciales()
        cargarPuntosUsuario()
    }

    /* funcion publica para actualizar los puntos desde otras pantallas */
    fun actualizarPuntosUsuario() {
        cargarPuntosUsuario()
    }
}

//creo q faltan algunas funciones pero q paja 

