package com.example.levelupprueba.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupprueba.data.repository.UsuarioRepository
import com.example.levelupprueba.model.profile.ProfileStatus
import com.example.levelupprueba.model.profile.ProfileUiState
import com.example.levelupprueba.model.usuario.Usuario
import com.example.levelupprueba.model.usuario.UsuarioValidator
import com.example.levelupprueba.ui.screens.profile.PerfilEditable
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val usuarioRepository: UsuarioRepository
): ViewModel() {

    private val _estado = MutableStateFlow(ProfileUiState())

    val estado: StateFlow<ProfileUiState> = _estado

    fun actualizarAvatarGlobal(nuevoAvatar: String, mainViewModel: MainViewModel){
        mainViewModel.updateAvatar(nuevoAvatar)
    }
    fun cargarDatosUsuario(id: String){
        viewModelScope.launch {
            _estado.update { it.copy(isLoading = true, profileStatus = ProfileStatus.Loading) }
            try {
                // Cargar perfil desde el backend
                val response = com.example.levelupprueba.data.remote.ApiConfig.usuarioService.getPerfil()
                
                if (response.isSuccessful && response.body() != null) {
                    val usuarioDto = response.body()!!
                    
                    // Obtener código de referido desde el backend
                    var codigoReferido = ""
                    try {
                        val codigoResponse = com.example.levelupprueba.data.remote.ApiConfig.referidosService.getCodigoReferido(usuarioDto.id.toLongOrNull() ?: 0L)
                        if (codigoResponse.isSuccessful && codigoResponse.body() != null) {
                            codigoReferido = codigoResponse.body()!!["codigoReferido"] ?: ""
                        }
                    } catch (e: Exception) {
                        // Si falla obtener código de referido, continuar sin él
                        android.util.Log.e("ProfileViewModel", "Error al obtener código de referido: ${e.message}")
                    }
                    
                    _estado.update {
                        it.copy(
                            nombre = it.nombre.copy(valor = usuarioDto.nombre ?: ""),
                            apellidos = it.apellidos.copy(valor = ""), // El backend no devuelve apellidos en UsuarioDto
                            email = it.email.copy(valor = usuarioDto.email),
                            telefono = it.telefono.copy(valor = usuarioDto.telefono ?: ""),
                            fechaNacimiento = it.fechaNacimiento.copy(valor = usuarioDto.fechaNacimiento),
                            region = it.region.copy(valor = ""), // El backend no devuelve región en UsuarioDto
                            comuna = it.comuna.copy(valor = ""), // El backend no devuelve comuna en UsuarioDto
                            direccion = it.direccion.copy(valor = usuarioDto.direccion ?: ""),
                            avatar = usuarioDto.avatar,
                            referralCode = codigoReferido,
                            points = usuarioDto.puntos,
                            isLoading = false,
                            profileStatus = ProfileStatus.Loaded
                        )
                    }
                } else {
                    val errorMessage = com.example.levelupprueba.data.remote.ErrorHandler.getErrorMessage(
                        Exception("Error al cargar perfil: ${response.code()}")
                    )
                    _estado.update {
                        it.copy(
                            isLoading = false,
                            profileStatus = ProfileStatus.Error(errorMessage)
                        )
                    }
                }
            } catch (e: Exception) {
                val errorMessage = com.example.levelupprueba.data.remote.ErrorHandler.getErrorMessage(e)
                _estado.update {
                    it.copy(
                        isLoading = false,
                        profileStatus = ProfileStatus.Error(errorMessage)
                    )
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun actualizarPerfil(perfilEditable: PerfilEditable) {
        _estado.update {
            it.copy(
                nombre = it.nombre.copy(
                    valor = perfilEditable.nombre,
                    error = UsuarioValidator.validarNombre(perfilEditable.nombre)
                ),
                apellidos = it.apellidos.copy(
                    valor = perfilEditable.apellidos,
                    error = UsuarioValidator.validarApellidos(perfilEditable.apellidos)
                ),
                telefono = it.telefono.copy(
                    valor = perfilEditable.telefono,
                    error = UsuarioValidator.validarTelefono(perfilEditable.telefono)
                ),
                fechaNacimiento = it.fechaNacimiento.copy(
                    valor = perfilEditable.fechaNacimiento,
                    error = UsuarioValidator.validarFechaNacimiento(perfilEditable.fechaNacimiento)
                ),
                region = it.region.copy(
                    valor = perfilEditable.region,
                    error = UsuarioValidator.validarRegion(perfilEditable.region)
                ),
                comuna = it.comuna.copy(
                    valor = perfilEditable.comuna,
                    error = UsuarioValidator.validarComuna(perfilEditable.comuna)
                ),
                direccion = it.direccion.copy(
                    valor = perfilEditable.direccion,
                    error = UsuarioValidator.validarDireccion(perfilEditable.direccion)
                ),
                avatar = perfilEditable.avatar
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun guardarPerfil(perfilEditable: PerfilEditable, mainViewModel: MainViewModel){
        viewModelScope.launch {
            _estado.update {
                it.copy(
                    profileStatus = ProfileStatus.Saving
                )
            }

            actualizarPerfil(perfilEditable)
            actualizarAvatarGlobal(perfilEditable.avatar ?: "", mainViewModel)

            delay(2000)

            val estadoActual = _estado.value

            val nuevoEstado = estadoActual.copy(
                nombre = estadoActual.nombre.copy(error = UsuarioValidator.validarNombre(estadoActual.nombre.valor)),
                apellidos = estadoActual.apellidos.copy(error = UsuarioValidator.validarApellidos(estadoActual.apellidos.valor)),
                email = estadoActual.email.copy(error = UsuarioValidator.validarEmail(estadoActual.email.valor)),
                telefono = estadoActual.telefono.copy(error = UsuarioValidator.validarTelefono(estadoActual.telefono.valor)),
                fechaNacimiento = estadoActual.fechaNacimiento.copy(error = UsuarioValidator.validarFechaNacimiento(estadoActual.fechaNacimiento.valor)),
                region = estadoActual.region.copy(error = UsuarioValidator.validarRegion(estadoActual.region.valor)),
                comuna = estadoActual.comuna.copy(error = UsuarioValidator.validarComuna(estadoActual.comuna.valor)),
                direccion = estadoActual.direccion.copy(error = UsuarioValidator.validarDireccion(estadoActual.direccion.valor))
            )

            _estado.value = nuevoEstado

            val hayErrores = listOfNotNull(
                if (nuevoEstado.nombre.error != null) "nombre" else null,
                if (nuevoEstado.apellidos.error != null) "apellidos" else null,
                if (nuevoEstado.email.error != null) "email" else null,
                if (nuevoEstado.telefono.error != null) "telefono" else null,
                if (nuevoEstado.fechaNacimiento.error != null) "fechaNacimiento" else null,
                if (nuevoEstado.region.error != null) "region" else null,
                if (nuevoEstado.comuna.error != null) "comuna" else null,
                if (nuevoEstado.direccion.error != null) "direccion" else null
            )

            if(hayErrores.isNotEmpty()){
                _estado.update {
                    it.copy(
                        profileStatus = ProfileStatus.ValidationError(
                            fields = hayErrores,
                            errorMessage = "Hay errores en los campos: ${hayErrores.joinToString(",")}"
                        )
                    )
                }
                return@launch
            }

            try {
                // Actualizar perfil en el backend
                val updateRequest = com.example.levelupprueba.data.remote.ActualizarPerfilRequest(
                    nombre = nuevoEstado.nombre.valor.takeIf { it.isNotBlank() },
                    telefono = nuevoEstado.telefono.valor.takeIf { it.isNotBlank() },
                    direccion = nuevoEstado.direccion.valor.takeIf { it.isNotBlank() },
                    avatar = nuevoEstado.avatar?.takeIf { it.isNotBlank() }
                )
                
                val response = com.example.levelupprueba.data.remote.ApiConfig.usuarioService.actualizarPerfil(updateRequest)
                
                if (response.isSuccessful && response.body() != null) {
                    val usuarioDto = response.body()!!
                    
                    // Actualizar avatar en el estado
                    _estado.update { 
                        it.copy(
                            avatar = usuarioDto.avatar,
                            profileStatus = ProfileStatus.Saved
                        )
                    }
                } else {
                    val errorMessage = com.example.levelupprueba.data.remote.ErrorHandler.getErrorMessage(
                        Exception("Error al actualizar perfil: ${response.code()}")
                    )
                    _estado.update { it.copy(profileStatus = ProfileStatus.Error(errorMessage)) }
                }

            } catch (e: Exception) {
                val errorMessage = com.example.levelupprueba.data.remote.ErrorHandler.getErrorMessage(e)
                _estado.update { it.copy(profileStatus = ProfileStatus.Error(errorMessage)) }
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun eliminarUsuario(userId: String){
        viewModelScope.launch {
            _estado.update { it.copy(profileStatus = ProfileStatus.Deleting) }
            delay(2000)

            try {
                val usuario = usuarioRepository.getUsuarioById(userId)
                if (usuario != null){
                    usuarioRepository.deleteUsuario(usuario)
                    _estado.update { it.copy(profileStatus = ProfileStatus.Deleted) }
                } else {
                    _estado.update { it.copy(profileStatus = ProfileStatus.Error("Usuario no encontrado")) }
                }
            } catch (e: Exception){
                _estado.update { it.copy(profileStatus = ProfileStatus.Error("Error al eliminar usuario: ${e.message}")) }
            }
        }
    }

    fun resetProfileStatus(){
        _estado.update { it.copy(profileStatus = ProfileStatus.Idle) }
    }
}