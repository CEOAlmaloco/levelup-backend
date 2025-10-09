package com.example.levelupprueba.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupprueba.model.auth.LoginStatus
import com.example.levelupprueba.model.auth.LoginUiState
import com.example.levelupprueba.model.auth.LoginValidator
import com.example.levelupprueba.model.usuario.Usuario
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// ViewModel que gestiona el estado y la logica del formulario de login
class LoginViewModel : ViewModel(){
    //Estado interno mutable: se guarda el estado actual del formulario
    private val _estado = MutableStateFlow(LoginUiState())
    //Estado expuesto: la UI observa este estado para mostrar los valores y errores
    val estado: StateFlow<LoginUiState> = _estado

    private val _loginEstado = MutableStateFlow<LoginStatus>(LoginStatus.Idle)

    val loginEstado: StateFlow<LoginStatus> = _loginEstado

    //Usuario demo para login simulado
    private val usuarioDemo = Usuario(
        id = "123",
        nombre = "LevelUp User",
        apellidos = "Prueba",
        email = "demo@duoc.cl",
        password = "1234",
        telefono = "123456789",
        fechaNacimiento = "2000-01-01",
        region = "Región Metropolitana",
        comuna = "Santiago",
        direccion = "Av. Siempre Viva 123",
        referralCode = "LEVELUP1234",
        points = 0,
        role = "cliente"
    )

    private fun actualizarCampo(
        update: (LoginUiState) -> LoginUiState
    ) {
        _estado.update { update(it) }
    }

    fun onEmailOrNameChange(valor: String) = actualizarCampo {
        it.copy(
            emailOrName = it.emailOrName.copy(
                valor = valor,
                error = LoginValidator.validarEmailOrName(valor)
            )
        )
    }

    fun onPasswordChange(valor: String) = actualizarCampo {
        it.copy(
            password = it.password.copy(
                valor = valor,
                error = LoginValidator.validarPassword(valor)
            )
        )

    }

    fun validarLogin(): Boolean {
        val estadoActual = _estado.value

        //Valida todos los campos y actualiza errores
        val nuevoEstado = estadoActual.copy(
            emailOrName = estadoActual.emailOrName.copy(error = LoginValidator.validarEmailOrName(estadoActual.emailOrName.valor)),
            password = estadoActual.password.copy(error = LoginValidator.validarPassword(estadoActual.password.valor))

        )

        val hayErrores = listOf(
            nuevoEstado.emailOrName.error,
            nuevoEstado.password.error
        ).any { it != null }

        _estado.value = nuevoEstado

        return !hayErrores
    }

    fun loginUsuario(){
        viewModelScope.launch {
            _loginEstado.value = LoginStatus.Loading
            delay(2000)
            val emailOrName = _estado.value.emailOrName.valor
            val password = _estado.value.password.valor

            //Validación simulado contra usuario demo
            if ((emailOrName == usuarioDemo.email || emailOrName == usuarioDemo.nombre) &&
                password == usuarioDemo.password){
                _loginEstado.value = LoginStatus.Success
            } else {
                _loginEstado.value = LoginStatus.Error(mensajeError = "Credenciales inválidas")
            }
        }
    }

    fun puedeIniciarSesion(): Boolean {
        val estadoActual = _estado.value

        val campos = listOf(
            estadoActual.emailOrName,
            estadoActual.password
        )

        val todosLlenos = campos.all {
            it.valor.isNotBlank()
        }

        val sinErrores = campos.all { it.error == null }

        return todosLlenos && sinErrores
    }

    fun resetLoginEstado(){
        _loginEstado.value = LoginStatus.Idle
    }
}