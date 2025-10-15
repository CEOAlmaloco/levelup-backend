package com.example.levelupprueba.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupprueba.data.local.UserDataStore
import com.example.levelupprueba.data.local.clearUserSession
import com.example.levelupprueba.data.local.saveUserSession
import com.example.levelupprueba.data.repository.UsuarioRepository
import com.example.levelupprueba.model.auth.LoginStatus
import com.example.levelupprueba.model.auth.LoginUiState
import com.example.levelupprueba.model.auth.LoginValidator
import com.example.levelupprueba.model.auth.UserSession
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// ViewModel que gestiona el estado y la logica del formulario de login
class LoginViewModel(
    private val usuarioRepository: UsuarioRepository
) : ViewModel(){
    //Estado interno mutable: se guarda el estado actual del formulario
    private val _estado = MutableStateFlow(LoginUiState())
    //Estado expuesto: la UI observa este estado para mostrar los valores y errores
    val estado: StateFlow<LoginUiState> = _estado

    private val _loginEstado = MutableStateFlow<LoginStatus>(LoginStatus.Idle)

    val loginEstado: StateFlow<LoginStatus> = _loginEstado

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

    fun loginUsuario(
        context: Context,
        emailOrName: String,
        password: String,
        mainViewModel: MainViewModel
    ){
        viewModelScope.launch {
            _loginEstado.value = LoginStatus.Loading
            delay(2000)
            try {

                //Consulta usuarios desde Room
                val usuarios = usuarioRepository.getAllUsuarios()
                // Buscar usuario por email o nombre, y comparar password
                val usuario = usuarios.find {
                    (it.email.equals(emailOrName.trim(), ignoreCase = true) ||
                            it.nombre.equals(emailOrName.trim(), ignoreCase = true)) &&
                            it.password == password
                }

                if (usuario != null) {
                    _loginEstado.value = LoginStatus.Success
                    val session = UserSession(
                        displayName = usuario.nombre.split(" ")[0],
                        loginAt = System.currentTimeMillis(),
                        userId = usuario.id,
                        role = usuario.role
                    )
                    saveUserSession(context, session)
                    mainViewModel.setUserSession(session)
                } else {
                    _loginEstado.value = LoginStatus.Error("Credenciales inválidas")
                }
            } catch (e: Exception) {
                _loginEstado.value = LoginStatus.Error("Ocurrió un error: ${e.message}")
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

    fun logout(context: Context, mainViewModel: MainViewModel){
        viewModelScope.launch {
            clearUserSession(context)
            mainViewModel.setUserSession(null)
        }
    }
}