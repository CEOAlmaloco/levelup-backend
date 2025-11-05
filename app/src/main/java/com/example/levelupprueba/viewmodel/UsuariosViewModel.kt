package com.example.levelupprueba.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupprueba.data.repository.UsuarioRepository
import com.example.levelupprueba.model.admin.users.UsuariosStatus
import com.example.levelupprueba.model.admin.users.UsuariosUiState
import com.example.levelupprueba.model.usuario.Usuario
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel para la pantalla de usuarios en vista de administrador.
 * @param usuarioRepository Repositorio de usuarios.
 *
 * @author Christian Mesa
 * */
class UsuariosViewModel (
    private val usuarioRepository: UsuarioRepository
) : ViewModel() {

    // Estado interno mutable
    private val _estado = MutableStateFlow(UsuariosUiState())

    // Estado externo inmutable
    val estado: StateFlow<UsuariosUiState> = _estado

    // Helper para setear el estado de Loading
    private fun setLoading() = _estado.update {
        it.copy(state = UsuariosStatus.Loading)
    }

    private fun setDeleting() = _estado.update {
        it.copy(state = UsuariosStatus.Deleting)
    }

    // Helper para setear el estado de Error
    private fun setError(errorMessage: String) = _estado.update {
        it.copy(state = UsuariosStatus.Error(errorMessage = errorMessage))
    }

    // Helper para setear el estado de Success
    private fun setSuccess(usuarios: List<Usuario>) = _estado.update {
        it.copy(
            state = if (usuarios.isEmpty()) UsuariosStatus.Empty
                    else UsuariosStatus.Success(usuarios)
        )
    }

    // Helper para limpiar la ultima accion
    fun clearLastAction() = _estado.update {
        it.copy(lastAction = null)
    }

    // ⚠️⚠️⚠️⚠️ ESTO ES UNA PRUEBA PARA CREAR UN USUARIO DE PRUEBA (BORRAR DESPUES)

    init {
        viewModelScope.launch {
            crearUsuarioDePrueba()
        }
    }

    // Cargar usuarios desde la base de datos interna
    private suspend fun cargarUsuariosInterno(){
        setLoading()
        try {
            delay(1500)
            val usuarios = usuarioRepository.getAllUsuarios()
            setSuccess(usuarios)
        } catch (e: Exception){
            setError("Error al cargar usuarios: ${e.message}")
        }
    }

    // Funcion para cargar usuarios desde la base de datos
    fun cargarUsuarios() {
        viewModelScope.launch {
            cargarUsuariosInterno()
        }
    }

    // Funcion para seleccionar un usuario
    fun seleccionarUsuario(usuario: Usuario) {
        _estado.update {
            it.copy(selected = usuario)
        }
    }

    // Funcion para crear un usuario
    fun crearUsuario(usuario: Usuario){
        viewModelScope.launch {
            try {
                usuarioRepository.saveUsuario(usuario)
                cargarUsuariosInterno()
            } catch (e: Exception){
                setError("Error al añadir usuario: ${e.message}")
            }
        }
    }

    // Funcion para actualizar un usuario
    fun actualizarUsuario(usuario: Usuario){
        viewModelScope.launch {
            try {
                usuarioRepository.updateUsuario(usuario)
                cargarUsuariosInterno()
            } catch (e: Exception){
                setError("Error al actualizar usuario: ${e.message}")
            }
        }
    }

    // Funcion para eliminar un usuario
    fun eliminarUsuario(userId: String){
        // Corrutina para eliminar el usuario
        setDeleting()
        viewModelScope.launch {
            try {
                delay(1500)
                val usuario = usuarioRepository.getUsuarioById(userId)
                if (usuario != null){
                    usuarioRepository.deleteUsuario(usuario)
                    cargarUsuariosInterno()
                    _estado.update { it.copy(lastAction = "delete") }
                }else{
                    setError("Usuario no encontrado")
                }
            } catch (e: Exception){
                setError("Error al eliminar usuario: ${e.message}")
            }
        }
    }

    // Funcion para eliminar todos los usuarios
    fun eliminarTodosLosUsuarios() {
        // Corrutina para eliminar todos los usuarios
        viewModelScope.launch {
            try {
                usuarioRepository.deleteAllUsuarios()
                cargarUsuariosInterno()
            } catch (e: Exception){
                setError("Error al eliminar todos los usuarios: ${e.message}")
            }
        }
    }

    // USUARIO DE PRUEBA (BORRAR DESPUES) ⚠️⚠️⚠️⚠️⚠️⚠️
    private suspend fun crearUsuarioDePrueba() {
        val usuarioDePrueba = Usuario(
            id = "",
            nombre = "Prueba",
            apellidos = "AA",
            email = "example@gmail.com",
            password = "1234",
            telefono = "123456789",
            fechaNacimiento = "24/12/2005",
            region = "Valparaiso",
            comuna = "Valparaiso",
            direccion = "",
            referralCode = "",
            points = 0,
            referredBy = "",
            role = "cliente",
            avatar = null
        )
        try {
            usuarioRepository.saveUsuario(usuarioDePrueba)
        } catch (e: Exception) {
            setError("Error al crear el usuario de prueba: ${e.message}")
        }
    }
}