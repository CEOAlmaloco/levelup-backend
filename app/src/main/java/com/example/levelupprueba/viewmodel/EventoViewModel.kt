package com.example.levelupprueba.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupprueba.data.local.UserDataStore
import com.example.levelupprueba.data.local.getUserSessionFlow
import com.example.levelupprueba.data.repository.UsuarioRepository
import com.example.levelupprueba.model.evento.Evento
import com.example.levelupprueba.model.evento.EventoRepository
import com.example.levelupprueba.model.evento.EventoUiState
import com.example.levelupprueba.model.evento.RecompensaCanje
import com.example.levelupprueba.model.usuario.Usuario
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/* Este viewmodel se encarga de la logica de la pantalla de eventos  UI 
*/
class EventoViewModel(//creamo la instancia del repositorio 
    private val repository: EventoRepository = EventoRepository(),// creamos la variable para acceder a evento repository
    private val usuarioRepository: UsuarioRepository? = null
) : ViewModel() {

    private val _estado = MutableStateFlow(EventoUiState())//creamos el estado mutable para que se actualice en tiempo real
    val estado: StateFlow<EventoUiState> = _estado//creamos el estado para que se pueda acceder a el desde cualquier parte de la aplicacion

    var userDataStore: UserDataStore? = null//creamos la variable para acceder a user data store
    private var context: Context? = null//creamos la variable para acceder a context

    init {
        cargarDatosIniciales()
    }

    /* el context  se usa para acceder a recursos archivos preferencias bases de datos.
    entonces con esto lo q hacemos es hacer una variable global para que se pueda acceder a ella desde cualquier parte de la aplicacion.
    luego llamamos a una funcion para cargar los puntos del usuario.
     */
    fun inicializar(ctx: Context) {
        context = ctx
        userDataStore = UserDataStore(ctx)
        cargarPuntosUsuario()
    }

    /**
     * Carga eventos y recompensas al iniciar el ViewModel.
     */
    private fun cargarDatosIniciales() {
        viewModelScope.launch {//lanzamos la corrutina
            _estado.update { it.copy(isLoading = true, error = null) }//actualizamos el estado para que se muestre el loading y el error
            try {
                delay(800)//800 ms es 0.8 segundos 
                val eventos = repository.obtenerEventos()//obtenemos los eventos del futuro SQLLITE 
                val recompensas = repository.obtenerRecompensas()// seteamos otra variable local de otra funcion 
                _estado.update {
                    it.copy(
                        eventos = eventos,
                        recompensas = recompensas,
                        isLoading = false,
                        eventoSeleccionado = eventos.firstOrNull() //first or null es para que si no hay eventos se muestre el primero o null 
                    )
                }
            } catch (e: Exception) {
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
        viewModelScope.launch {//lanzamos la corrutina 
            try { 
                delay(500)
                val usuario = obtenerUsuarioActual()
                _estado.update { 
                    it.copy(puntosUsuario = usuario?.points ?: 0) // si no hay usuario se muestra 0 puntos
                }
            } catch (e: Exception) {
                // user no logueado o error
            }
        }
    }

    /**
     * Obtiene el usuario actual desde la base de datos SQLite o DataStore usando la sesión.
     * lo usamos para tener la validacion de usuario logueado y tener seguridad
     * creo q igual lo podemos globalizar esta funcion en el viewmodel principal
     */
    private suspend fun obtenerUsuarioActual(): Usuario? {
        val ctx = context ?: return null
        
        val session = getUserSessionFlow(ctx).first()
        if (session.userId.isEmpty()) return null
        
        // Priorizar base de datos SQLite si esta disponible
        return if (usuarioRepository != null) {
            usuarioRepository.getUsuarioById(session.userId)
        } else {
            // Fallback a DataStore si no hay repository
            val dataStore = userDataStore ?: return null
            val usuarios = dataStore.getUsuarios()
            usuarios.find { it.id == session.userId }
        }
    }

    /* cuando cajeamos codigo o recompensa actualizamos el usuario en la base de datos */
    private suspend fun actualizarUsuario(usuarioActualizado: Usuario) {
        // Priorizar base de datos SQLite si está disponible
        if (usuarioRepository != null) {
            usuarioRepository.updateUsuario(usuarioActualizado)
        } else {
            // Fallback a DataStore si no hay repository
            val dataStore = userDataStore ?: return
            
            val usuarios = dataStore.getUsuarios().toMutableList()//ya q era inmutable la lista lo convertimos a mutable
            val idx = usuarios.indexOfFirst { it.id == usuarioActualizado.id }//buscamos el primer indice del usuario por la id pero el actualizado no el antiguo 
            
            if (idx != -1) {//creo q era string LUEGO REVISAR 
                usuarios[idx] = usuarioActualizado
                dataStore.saveUsuarios(usuarios)
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
                // verificar nuevamente al user con la funcion obtener usuario actual
                val usuario = obtenerUsuarioActual()
                if (usuario == null) {//le tiramo error si trata de canjear sin user logueado
                    _estado.update {
                        it.copy(mensajeCodigo = "Debes iniciar sesión para canjear un código")
                    }
                    return@launch // return @ launch es para que se detenga la corrutina osea se maneja async y no por tiempo de espera
                }

                delay(500)// tiempo entre validaciones para evitar spam
                
                // luego validamos el codigo en el repository para simular el backend pero a nivel de frontend???¡¡¿¿??
                val codigoEvento = repository.validarCodigo(codigo)// llamamo a la funcion con el codigo 
                
                if (codigoEvento == null) {
                    _estado.update {
                        it.copy(mensajeCodigo = "Código inválido o expirado")
                    }
                    return@launch
                }//corrutina async para validar el codigo en el repository

                // validamo q el redemcodes no contenga el codigo ya canjeado para evitar duplicados
                if (usuario.redeemedCodes.contains(codigo.uppercase())) {
                    _estado.update {
                        it.copy(mensajeCodigo = "Este código ya fue canjeado en tu cuenta")
                    }
                    return@launch
                }

                // ahora podemos canjear el codigo sumando los puntos al usuario y agregando el codigo a la lista de canjeados
                val codigosActualizados = usuario.redeemedCodes + codigo.uppercase()
                val nuevosPuntos = usuario.points + codigoEvento.puntos
                val usuarioActualizado = usuario.copy(
                    points = nuevosPuntos,
                    redeemedCodes = codigosActualizados
                )//generamos la copia del original para guardar la actualizaciom 

                // datastore luego pasarlo a SQL LITE 
                actualizarUsuario(usuarioActualizado)

                // luego actualizamos el estado para que se muestre el nuevo puntaje y el codigo canjeado
                _estado.update {
                    it.copy(
                        puntosUsuario = nuevosPuntos,
                        codigoIngresado = "",
                        mensajeCodigo = "Código canjeado: +${codigoEvento.puntos} pts"
                    )
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
    fun canjearRecompensa(recompensa: RecompensaCanje): Boolean {//la funcion recibe el parametro recompensa que retorna un booleano
        viewModelScope.launch {//lanzamos la corrutina de canje
            try {
                // verificar nuevamente al user con la funcion obtener usuario actual
                val usuario = obtenerUsuarioActual()
                if (usuario == null) {//le tiramo error si trata de canjear sin user logueado
                    _estado.update {
                        it.copy(mensajeCodigo = "Debes iniciar sesión para canjear puntos")
                    }
                    return@launch
                }

                val puntosActuales = usuario.points
                
                if (puntosActuales >= recompensa.costo) {//si los puntos actuales son mayores o iguales al costo de la recompensa entonces podemos canjear
                    val nuevosPuntos = puntosActuales - recompensa.costo//restamos los puntos actuales al costo de la recompensa
                    val usuarioActualizado = usuario.copy(points = nuevosPuntos)//generamos la copia del original para guardar la actualizaciom 
                    
                    // datastore luego pasarlo a SQL LITE 
                    actualizarUsuario(usuarioActualizado)
                    
                    // luego actualizamos el estado para que se muestre el nuevo puntaje en el UI 
                    _estado.update {
                        it.copy(puntosUsuario = nuevosPuntos)
                    }
                }
            } catch (e: Exception) {
                _estado.update {
                    it.copy(error = "Error al canjear recompensa: ${e.message}")
                }
            }
        } 
        // retornamos el nuevo puntaje actualizado si es mayor o igual al costo de la recompensa como ultima validacion
        val puntosActuales = _estado.value.puntosUsuario
        return puntosActuales >= recompensa.costo
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

