package com.example.levelupprueba.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupprueba.data.remote.ApiConfig
import com.example.levelupprueba.data.remote.CambiarPasswordRequest
import com.example.levelupprueba.model.password.PasswordStatus
import com.example.levelupprueba.model.password.PasswordUiState
import com.example.levelupprueba.model.usuario.UsuarioValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChangePasswordViewModel : ViewModel() {
    private val _estado = MutableStateFlow(PasswordUiState())
    val estado: StateFlow<PasswordUiState> = _estado

    private val _status = MutableStateFlow<PasswordStatus>(PasswordStatus.Idle)
    val status: StateFlow<PasswordStatus> = _status

    private fun actualizarCampo(
        update: (PasswordUiState) -> PasswordUiState
    ) {
        _estado.update { update(it) }
    }

    fun onActualChange(valor: String) = actualizarCampo {
        it.copy(
            actual = it.actual.copy(
                valor = valor,
                error = UsuarioValidator.validarPassword(valor)
            ),
        )
    }

    fun onNuevaChange(valor: String) = actualizarCampo {
        it.copy(
            nueva = it.nueva.copy(
                valor = valor,
                error = UsuarioValidator.validarPassword(valor)
            ),
            confirmar = it.confirmar.copy(
                error = UsuarioValidator.validarConfirmPassword(valor, it.confirmar.valor)
            )
        )
    }

    fun onConfirmarChange(valor: String) = actualizarCampo {
        it.copy(
            confirmar = it.confirmar.copy(
                valor = valor,
                error = UsuarioValidator.validarConfirmPassword(
                    it.nueva.valor,
                    valor)
            ),
        )
    }

    fun validarFormulario(): Boolean{
        val estadoActual = _estado.value
        val nuevoEstado = estadoActual.copy(
            actual = estadoActual.actual.copy(
                error = UsuarioValidator.validarPassword(estadoActual.actual.valor)
            ),
            nueva = estadoActual.nueva.copy(
                error = UsuarioValidator.validarPassword(estadoActual.nueva.valor)
            ),
            confirmar = estadoActual.confirmar.copy(
                error = UsuarioValidator.validarConfirmPassword(
                    estadoActual.nueva.valor,
                    estadoActual.confirmar.valor
                )
            )
        )

        _estado.value = nuevoEstado

        val hayErrores = listOfNotNull(
            nuevoEstado.actual.error?.let { "Contraseña actual" },
            nuevoEstado.nueva.error?.let { "Contraseña nueva" },
            nuevoEstado.confirmar.error?.let { "Confirmar contraseña" }
        )

        return if (hayErrores.isNotEmpty()) {
            _status.value = PasswordStatus.ValidationError(
                fields = hayErrores,
                errorMessage = "Errores en los campos: ${hayErrores.joinToString(", ")}"
            )
            false
        } else true
    }

    fun puedeGuardar(): Boolean {
        val estadoActual = _estado.value

        val todosLlenos = listOf(
            estadoActual.actual.valor,
            estadoActual.nueva.valor,
            estadoActual.confirmar.valor
        ).all {it.isNotBlank()}

        val sinErrores = listOf(
            estadoActual.actual.error,
            estadoActual.nueva.error,
            estadoActual.confirmar.error
        ).all {it == null}

        return todosLlenos && sinErrores
    }

    fun cambiarPassword(email: String) {
        if (!validarFormulario()) return

        viewModelScope.launch {
            _status.value = PasswordStatus.Saving
            try {
                val response = ApiConfig.usuarioService.cambiarPassword(
                    CambiarPasswordRequest(
                        passwordActual = _estado.value.actual.valor,
                        passwordNueva = _estado.value.nueva.valor
                    )
                )

                _status.value = if (response.isSuccessful) {
                    PasswordStatus.Success
                } else {
                    PasswordStatus.Error("La contraseña actual es incorrecta")
                }
            } catch (e: Exception) {
                _status.value = PasswordStatus.Error("Error: ${e.message}")
            }
        }
    }

    fun resetPasswordStatus() {
        _status.value = PasswordStatus.Idle
    }
}