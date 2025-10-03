package com.example.levelupprueba.viewmodel

import androidx.lifecycle.ViewModel
import com.example.levelupprueba.model.RegistroUiEstado
import com.example.levelupprueba.model.UsuarioErrores
import com.example.levelupprueba.model.UsuarioUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class UsuarioViewModel: ViewModel() {

    //Estado interno mutable
    private val _estado = MutableStateFlow(UsuarioUiState())
    // Estado expuesto para la UI
    val estado: StateFlow<UsuarioUiState> = _estado

    // Estado del proceso de registro (Loading, success, error)
    private val _registroEstado = MutableStateFlow<RegistroUiEstado>(RegistroUiEstado.Idle)
    val registroEstado: StateFlow<RegistroUiEstado> = _registroEstado

    //Actualiza el campo nombre y limpia su error
    fun onNombreChange(valor: String){
        _estado.update { it.copy(nombre = valor, errores = it.errores.copy(nombre = null)) }
    }

    //Actualiza el campo apellidos y limpia su error
    fun onApellidosChange(valor: String){
        _estado.update { it.copy(apellidos = valor, errores = it.errores.copy(apellidos = null)) }
    }

    //Actualiza el campo email y limpia su error
    fun onEmailChange(valor: String){
        _estado.update { it.copy(email = valor, errores = it.errores.copy(email = null)) }
    }

    //Actualiza el campo password y limpia su error
    fun onPasswordChange(valor: String){
        _estado.update { it.copy(password = valor, errores = it.errores.copy(password = null)) }
    }

    fun onConfirmPasswordChange(valor: String){
        _estado.update { it.copy(confirmPassword = valor, errores = it.errores.copy(confirmPassword = null)) }
    }

    //Actualiza el campo telefono y limpia su error
    fun onTelefonoChange(valor: String){
        _estado.update { it.copy(telefono = valor, errores = it.errores.copy(telefono = null)) }
    }

    //Actualiza el campo fechaNacimiento y limpia su error
    fun onFechaNacimientoChange(valor: String){
        _estado.update { it.copy(fechaNacimiento = valor, errores = it.errores.copy(fechaNacimiento = null)) }
    }

    //Actualiza el campo region y limpia su error
    fun onRegionChange(valor: String){
        _estado.update { it.copy(region = valor, errores = it.errores.copy(region = null)) }
    }

    //Actualiza el campo comuna y limpia su error
    fun onComunaChange(valor: String){
        _estado.update { it.copy(comuna = valor, errores = it.errores.copy(comuna = null)) }
    }

    //Actualiza el campo direccion y limpia su error
    fun onDireccionChange(valor: String){
        _estado.update { it.copy(direccion = valor, errores = it.errores.copy(direccion = null)) }
    }

    //Actualiza el campo terminos y limpia su error
    fun onTerminosChange(valor: Boolean){
        _estado.update { it.copy(terminos = valor, errores = it.errores.copy(terminos = null)) }
    }

    fun validarRegistro(): Boolean {
        val estadoActual = _estado.value
        val errores = UsuarioErrores(
            nombre = if (estadoActual.nombre.isBlank()) "El nombre es obligatorio" else null,
            apellidos = if (estadoActual.apellidos.isBlank()) "Los apellidos son obligatorios" else null,
            email = when {
                estadoActual.email.isBlank() -> "El email es obligatorio"
                !estadoActual.email.contains("@") -> "Correo invalido"
                else -> null
            },
            password = if (estadoActual.password.length < 4 || estadoActual.password.length > 10) "La contraseña debe tener entre 4 y 10 caracteres" else null,
            confirmPassword = if (estadoActual.confirmPassword != estadoActual.password) "Las contraseñas no coinciden" else null,
            telefono = when {
                estadoActual.telefono.isBlank() -> null //Opcional
                !estadoActual.telefono.all { it.isDigit() } -> "El teléfono solo debe contener números"
                estadoActual.telefono.length < 9 -> "El teléfono debe tener al menos 9 dígitos"
                else -> null
            },
            fechaNacimiento = if (estadoActual.fechaNacimiento.isBlank()) "La fecha de nacimiento es obligatoria" else null,
            region = if (estadoActual.region.isBlank()) "La region es obligatoria" else null,
            comuna = if (estadoActual.comuna.isBlank()) "La comuna es obligatoria" else null,
            direccion = when {
                estadoActual.direccion.isBlank() -> null // Es opcional
                estadoActual.direccion.length > 300 -> "La dirección no puede tener más de 300 caracteres"
                else -> null
            },
            terminos = if (!estadoActual.terminos) "Debes aceptar los terminos" else null
        )

        val hayErrores = listOfNotNull(
            errores.nombre,
            errores.apellidos,
            errores.email,
            errores.password,
            errores.confirmPassword,
            errores.telefono,
            errores.fechaNacimiento,
            errores.region,
            errores.comuna,
            errores.direccion,
            errores.terminos
        ).isNotEmpty()

        _estado.update { it.copy(errores = errores) }

        return !hayErrores
    }

    //Simula el proceso de registro con delay utilizando suspend fun y estados
    suspend fun registrarUsuario(){
        _registroEstado.value = RegistroUiEstado.Loading
        try {
            delay(2000) //Simula 2 segundos de espera
            //Futura llamada a backend
            _registroEstado.value = RegistroUiEstado.Success
        } catch (e: Exception) {
            _registroEstado.value = RegistroUiEstado.Error("Ocurrio un error: ${e.message}")
        }
    }

    //Permite volver al estado inicial (Despues de mostrar mensaje de exito)
    fun resetRegistroEstado(){
        _registroEstado.value = RegistroUiEstado.Idle
    }
}