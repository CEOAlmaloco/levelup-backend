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
                val usuario = usuarioRepository.getUsuarioById(id)
                _estado.update {
                    it.copy(
                        nombre = it.nombre.copy(valor = usuario?.nombre ?: ""),
                        apellidos = it.apellidos.copy(valor = usuario?.apellidos ?: ""),
                        email = it.email.copy(valor = usuario?.email ?: ""),
                        telefono = it.telefono.copy(valor = usuario?.telefono ?: ""),
                        fechaNacimiento = it.fechaNacimiento.copy(valor = usuario?.fechaNacimiento ?: ""),
                        region = it.region.copy(valor = usuario?.region ?: ""),
                        comuna = it.comuna.copy(valor = usuario?.comuna ?: ""),
                        direccion = it.direccion.copy(valor = usuario?.direccion ?: ""),
                        avatar = usuario?.avatar,
                        isLoading = false,
                        profileStatus = ProfileStatus.Loaded
                    )
                }
            } catch (e: Exception) {
                _estado.update {
                    it.copy(
                        isLoading = false,
                        profileStatus = ProfileStatus.Error(e.message ?: "Error al cargar perfil")
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
                val usuarioActual = usuarioRepository.getUsuarioByEmail(estadoActual.email.valor)
                val usuario = Usuario(
                    id = usuarioActual?.id ?: "",
                    nombre = nuevoEstado.nombre.valor,
                    apellidos = nuevoEstado.apellidos.valor,
                    email = nuevoEstado.email.valor,
                    password = usuarioActual?.password ?: "",
                    telefono = nuevoEstado.telefono.valor,
                    fechaNacimiento = nuevoEstado.fechaNacimiento.valor,
                    region = nuevoEstado.region.valor,
                    comuna = nuevoEstado.comuna.valor,
                    direccion = nuevoEstado.direccion.valor,
                    referralCode = usuarioActual?.referralCode ?: "",
                    points = usuarioActual?.points ?: 0,
                    role = usuarioActual?.role ?: "cliente",
                    avatar = nuevoEstado.avatar
                )
                usuarioRepository.saveUsuario(usuario)
                _estado.update { it.copy(profileStatus = ProfileStatus.Saved) }

            } catch (e: Exception) {
                _estado.update { it.copy(profileStatus = ProfileStatus.Error("Error al guardar perfil")) }
            }

        }
    }
}