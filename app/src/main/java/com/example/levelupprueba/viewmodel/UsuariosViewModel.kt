package com.example.levelupprueba.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupprueba.data.remote.ApiConfig
import com.example.levelupprueba.data.remote.UsuarioDto
import com.example.levelupprueba.model.admin.users.UsuariosStatus
import com.example.levelupprueba.model.admin.users.UsuariosUiState
import com.example.levelupprueba.model.usuario.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel para la pantalla de usuarios en vista de administrador.
 * Consume endpoints administrativos para listar y gestionar usuarios.
 *
 * @author Christian Mesa
 * */
class UsuariosViewModel : ViewModel() {

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

    // Cargar usuarios desde la base de datos interna
    private suspend fun cargarUsuariosInterno(){
        setLoading()
        try {
            val response = ApiConfig.usuarioService.getAllUsuarios()
            if (response.isSuccessful && response.body() != null) {
                val usuarios = response.body()!!.content.map { it.toUsuarioModel() }
                setSuccess(usuarios)
            } else {
                setError("Error al cargar usuarios: ${response.code()} ${response.message()}")
            }
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

    // Funcion para eliminar un usuario
    fun eliminarUsuario(userId: String){
        setDeleting()
        viewModelScope.launch {
            try {
                val response = ApiConfig.usuarioService.eliminarUsuario(userId)
                if (response.isSuccessful) {
                    cargarUsuariosInterno()
                    _estado.update { it.copy(lastAction = "delete") }
                } else {
                    setError("Error al eliminar usuario: ${response.code()} ${response.message()}")
                }
            } catch (e: Exception){
                setError("Error al eliminar usuario: ${e.message}")
            }
        }
    }

    private fun UsuarioDto.toUsuarioModel(): Usuario {
        val resolvedId = id ?: idUsuario ?: ""
        val resolvedNombre = nombre ?: ""
        val resolvedApellidos = apellidos ?: apellido ?: ""
        val resolvedEmail = email ?: correo ?: ""
        val puntosTotales = puntosLevelUp ?: puntos ?: 0
        val resolvedAvatar = avatar ?: avatarUrl

        return Usuario(
            id = resolvedId,
            nombre = resolvedNombre,
            apellidos = resolvedApellidos,
            email = resolvedEmail,
            telefono = telefono,
            fechaNacimiento = fechaNacimiento,
            region = region ?: pais,
            comuna = comuna ?: ciudad,
            direccion = direccion,
            referralCode = codigoReferido,
            points = puntosTotales,
            role = tipoUsuario ?: role.orEmpty(),
            avatar = resolvedAvatar
        )
    }
}