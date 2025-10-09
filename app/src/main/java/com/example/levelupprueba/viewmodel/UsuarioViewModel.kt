package com.example.levelupprueba.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupprueba.model.registro.RegistroUiState
import com.example.levelupprueba.model.usuario.UsuarioCampo
import com.example.levelupprueba.model.usuario.UsuarioUiState
import com.example.levelupprueba.model.usuario.UsuarioValidator
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsuarioViewModel: ViewModel() {

    // Estado interno mutable
    private val _estado = MutableStateFlow(UsuarioUiState())
    // Estado expuesto para la UI
    val estado: StateFlow<UsuarioUiState> = _estado

    // Estado del proceso de registro (Loading, success, error)
    private val _registroEstado = MutableStateFlow<RegistroUiState>(RegistroUiState.Idle)
    val registroEstado: StateFlow<RegistroUiState> = _registroEstado

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
            _registroEstado.value = RegistroUiState.Loading
            try {
                delay(2000) // simula backend
                _registroEstado.value = RegistroUiState.Success
            } catch (e: Exception) {
                _registroEstado.value = RegistroUiState.Error("Ocurrió un error: ${e.message}")
            }
        }
    }

    // Permite volver al estado inicial (después de mostrar mensaje de éxito)
    fun resetRegistroEstado() {
        _registroEstado.value = RegistroUiState.Idle
    }
}