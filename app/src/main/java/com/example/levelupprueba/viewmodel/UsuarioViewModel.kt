package com.example.levelupprueba.viewmodel

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupprueba.data.local.UserDataStore
import com.example.levelupprueba.data.repository.UsuarioRepository
import com.example.levelupprueba.model.registro.RegisterStatus
import com.example.levelupprueba.model.usuario.Usuario
import com.example.levelupprueba.model.usuario.UsuarioUiState
import com.example.levelupprueba.model.usuario.UsuarioValidator
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class UsuarioViewModel(
    private val usuarioRepository: UsuarioRepository,
    private val eventoViewModel: EventoViewModel? = null
): ViewModel() {

    // Estado interno mutable
    private val _estado = MutableStateFlow(UsuarioUiState())
    // Estado expuesto para la UI
    val estado: StateFlow<UsuarioUiState> = _estado

    // Estado del proceso de registro (Loading, success, error)
    private val _registroEstado = MutableStateFlow<RegisterStatus>(RegisterStatus.Idle)
    val registroEstado: StateFlow<RegisterStatus> = _registroEstado

    /**
     * Helper para actualizar el estado de un campo evitando repetición de código.
     */
    private fun actualizarCampo(
        update: (UsuarioUiState) -> UsuarioUiState
    ) {
        _estado.update { update(it) }
    }

    // Cambios de valor

    // Actualiza el campo nombre y valida en tiempo real
    fun onNombreChange(valor: String) = actualizarCampo {
        it.copy(
            nombre = it.nombre.copy(
                valor = valor,
                error = UsuarioValidator.validarNombre(valor)
            )
        )
    }

    // Actualiza el campo apellidos y valida en tiempo real
    fun onApellidosChange(valor: String) = actualizarCampo {
        it.copy(
            apellidos = it.apellidos.copy(
                valor = valor,
                error = UsuarioValidator.validarApellidos(valor)
            )
        )
    }


    // Actualiza el campo email y valida en tiempo real
    fun onEmailChange(valor: String) = actualizarCampo {
        it.copy(
            email = it.email.copy(
                valor = valor,
                error = UsuarioValidator.validarEmail(valor)
            )
        )
    }

    // Actualiza el campo password y valida en tiempo real
    fun onPasswordChange(valor: String) = actualizarCampo {
        it.copy(
            password = it.password.copy(
                valor = valor,
                error = UsuarioValidator.validarPassword(valor)
            ),
            confirmPassword = it.confirmPassword.copy(
                error = UsuarioValidator.validarConfirmPassword(valor, it.confirmPassword.valor)
            )
        )
    }

    // Actualiza el campo confirmPassword y valida contra la contraseña actual
    fun onConfirmPasswordChange(valor: String) = actualizarCampo {
        it.copy(
            confirmPassword = it.confirmPassword.copy(
                valor = valor,
                error = UsuarioValidator.validarConfirmPassword(it.password.valor, valor)
            )
        )
    }

    // Actualiza el campo telefono y valida en tiempo real
    fun onTelefonoChange(valor: String) = actualizarCampo {
        it.copy(
            telefono = it.telefono.copy(
                valor = valor,
                error = UsuarioValidator.validarTelefono(valor)
            )
        )
    }

    // Actualiza el campo fechaNacimiento y valida en tiempo real
    @RequiresApi(Build.VERSION_CODES.O)
    fun onFechaNacimientoChange(valor: String) = actualizarCampo {
        it.copy(
            fechaNacimiento = it.fechaNacimiento.copy(
                valor = valor,
                error = UsuarioValidator.validarFechaNacimiento(valor)
            )
        )
    }

    // Actualiza el campo región y valida en tiempo real
    fun onRegionChange(valor: String) = actualizarCampo {
        it.copy(
            region = it.region.copy(
                valor = valor,
                error = UsuarioValidator.validarRegion(valor)
            )
        )
    }

    // Actualiza el campo comuna y valida en tiempo real
    fun onComunaChange(valor: String) = actualizarCampo {
        it.copy(
            comuna = it.comuna.copy(
                valor = valor,
                error = UsuarioValidator.validarComuna(valor)
            )
        )
    }

    // Actualiza el campo dirección y valida en tiempo real
    fun onDireccionChange(valor: String) = actualizarCampo {
        it.copy(
            direccion = it.direccion.copy(
                valor = valor,
                error = UsuarioValidator.validarDireccion(valor)
            )
        )
    }

    // Actualiza el campo codigo de referido 
    fun onCodigoReferidoChange(valor: String) = actualizarCampo {
        it.copy(
            codigoReferido = it.codigoReferido.copy(
                valor = valor.uppercase().trim()
            )
        )
    }


    // Actualiza el campo términos y valida en tiempo real
    fun onTerminosChange(valor: Boolean) = actualizarCampo {
        it.copy(
            terminos = it.terminos.copy(
                valor = valor.toString(), // Si quieres guardar como String, si no, cambia UsuarioCampo a Boolean para valor
                error = UsuarioValidator.validarTerminos(valor)
            )
        )
    }

    /**
     * Validaciones del formulario completo.
     * Devuelve true si el formulario es válido.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun validarRegistro(): Boolean {
        val estadoActual = _estado.value

        // Valida todos los campos y actualiza errores
        val nuevoEstado = estadoActual.copy(
            nombre = estadoActual.nombre.copy(error = UsuarioValidator.validarNombre(estadoActual.nombre.valor)),
            apellidos = estadoActual.apellidos.copy(error = UsuarioValidator.validarApellidos(estadoActual.apellidos.valor)),
            email = estadoActual.email.copy(error = UsuarioValidator.validarEmail(estadoActual.email.valor)),
            password = estadoActual.password.copy(error = UsuarioValidator.validarPassword(estadoActual.password.valor)),
            confirmPassword = estadoActual.confirmPassword.copy(
                error = UsuarioValidator.validarConfirmPassword(
                    estadoActual.password.valor,
                    estadoActual.confirmPassword.valor
                )
            ),
            telefono = estadoActual.telefono.copy(error = UsuarioValidator.validarTelefono(estadoActual.telefono.valor)),
            fechaNacimiento = estadoActual.fechaNacimiento.copy(error = UsuarioValidator.validarFechaNacimiento(estadoActual.fechaNacimiento.valor)),
            region = estadoActual.region.copy(error = UsuarioValidator.validarRegion(estadoActual.region.valor)),
            comuna = estadoActual.comuna.copy(error = UsuarioValidator.validarComuna(estadoActual.comuna.valor)),
            direccion = estadoActual.direccion.copy(error = UsuarioValidator.validarDireccion(estadoActual.direccion.valor)),
            terminos = estadoActual.terminos.copy(error = UsuarioValidator.validarTerminos(estadoActual.terminos.valor == "true"))
        )

        _estado.value = nuevoEstado

        // Si algún campo tiene error, el registro no es válido
        val hayErrores = listOf(
            nuevoEstado.nombre.error,
            nuevoEstado.apellidos.error,
            nuevoEstado.email.error,
            nuevoEstado.password.error,
            nuevoEstado.confirmPassword.error,
            nuevoEstado.telefono.error,
            nuevoEstado.fechaNacimiento.error,
            nuevoEstado.region.error,
            nuevoEstado.comuna.error,
            nuevoEstado.direccion.error,
            nuevoEstado.terminos.error
        ).any { it != null }

        return !hayErrores
    }

    fun puedeRegistrar(): Boolean {
        val estadoActual = _estado.value

        val camposObligatorios = listOf(
            estadoActual.nombre,
            estadoActual.apellidos,
            estadoActual.email,
            estadoActual.password,
            estadoActual.confirmPassword,
            estadoActual.fechaNacimiento,
            estadoActual.region,
            estadoActual.comuna,
            estadoActual.terminos
        )

        val todosLlenos = camposObligatorios.all {
            it.valor.isNotBlank() && (it != estadoActual.terminos || it.valor == "true")
        }

        val sinErrores = camposObligatorios.all { it.error == null }

        return todosLlenos && sinErrores
    }

    // Simula el proceso de registro con delay utilizando launch y estados
    // En el futuro aquí se hará la petición HTTP al backend para validar
    fun registrarUsuario() {
        viewModelScope.launch {
            _registroEstado.value = RegisterStatus.Loading
            try {
                delay(2000) // simula backend
                if(usuarioRepository.emailExists(_estado.value.email.valor)){
                    _registroEstado.value = RegisterStatus.Error("El email ya está registrado")
                    return@launch // Detener aquí, NO guardar usuario
                }
                
                // Validar codigo de referido si se ingreso
                var referrerUser: Usuario? = null
                val codigoReferido = _estado.value.codigoReferido.valor.trim().uppercase()
                if (codigoReferido.isNotEmpty()) {
                    referrerUser = usuarioRepository.getUsuarioByReferralCode(codigoReferido) //obtener usuario por codigo de referido
                    if (referrerUser == null) {
                        _registroEstado.value = RegisterStatus.Error("El código de referido no existe")
                        return@launch //detener la corrutina
                    }
                }
                
                val usuario = Usuario(
                    id = "",
                    nombre = _estado.value.nombre.valor,
                    apellidos = _estado.value.apellidos.valor,
                    email = _estado.value.email.valor,
                    password = _estado.value.password.valor,
                    telefono = _estado.value.telefono.valor,
                    fechaNacimiento = _estado.value.fechaNacimiento.valor,
                    region = _estado.value.region.valor,
                    comuna = _estado.value.comuna.valor,
                    direccion = _estado.value.direccion.valor,
                    referralCode = usuarioRepository.generateReferralCode(_estado.value.nombre.valor),
                    points = 0,
                    referredBy = referrerUser?.id,
                    role = "cliente",
                    avatar = null
                )
                
                // Guardar el nuevo usuario
                usuarioRepository.saveUsuario(usuario)
                
                // Asignar puntos al referente si corresponde
                if (referrerUser != null) {
                    val REFERRAL_POINTS = 50 // Puntos por referido TODO se me olvido cuantos puntos era por persona 
                    val usuarioReferenteActualizado = referrerUser.copy(
                        points = referrerUser.points + REFERRAL_POINTS
                    )
                    usuarioRepository.saveUsuario(usuarioReferenteActualizado)
                    
                    // Actualizar puntos en EventoViewModel si está disponible
                    eventoViewModel?.actualizarPuntosUsuario()
                }
                
                _registroEstado.value = RegisterStatus.Success
            } catch (e: Exception) {
                _registroEstado.value = RegisterStatus.Error("Ocurrió un error: ${e.message}")
            }
        }
    }

    // Permite volver al estado inicial (después de mostrar mensaje de éxito)
    fun resetRegistroEstado() {
        _registroEstado.value = RegisterStatus.Idle
    }
}