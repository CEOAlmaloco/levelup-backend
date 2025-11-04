package com.example.levelupprueba.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupprueba.data.repository.UsuarioRepository
import com.example.levelupprueba.model.admin.users.UsuariosState
import com.example.levelupprueba.model.admin.users.UsuariosUiState
import com.example.levelupprueba.model.usuario.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsuariosManagementViewModel (
    private val usuarioRepository: UsuarioRepository
) : ViewModel() {

    // Estado interno mutable
    private val _estado = MutableStateFlow(UsuariosUiState())

    // Estado externo inmutable
    val estado: StateFlow<UsuariosUiState> = _estado

    // Helper para setear el estado de Loading
    private fun setLoading() = _estado.update {
        it.copy(state = UsuariosState.Loading)
    }

    // Helper para setear el estado de Error
    private fun setError(errorMessage: String) = _estado.update {
        it.copy(state = UsuariosState.Error(errorMessage = errorMessage))
    }

    // Helper para setear el estado de Success
    private fun setSuccess(usuarios: List<Usuario>) = _estado.update {
        it.copy(
            state = if (usuarios.isEmpty()) UsuariosState.Empty
                    else UsuariosState.Success(usuarios)
        )
    }

    // Cargar usuarios desde la base de datos interna
    private suspend fun cargarUsuariosInterno(){
        setLoading()
        try {
            val usuarios = usuarioRepository.getAllUsuarios()
            setSuccess(usuarios)
        } catch (e: Exception){
            setError("Error al cargar usuarios: ${e.message}")
        }
    }

    // Cargar usuarios desde la base de datos
    init {
        cargarUsuarios()
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
    fun eliminarUsuario(usuario: Usuario){
        // Corrutina para eliminar el usuario
        viewModelScope.launch {
            try {
                usuarioRepository.deleteUsuario(usuario)
                cargarUsuariosInterno()
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
}