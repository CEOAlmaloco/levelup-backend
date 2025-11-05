package com.example.levelupprueba.viewmodel

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupprueba.AuthActivity
import com.example.levelupprueba.data.local.UserDataStore
import com.example.levelupprueba.data.local.clearUserSession
import com.example.levelupprueba.data.local.getUserSession
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
            try {
                // Llamar al backend para autenticación
                val loginRequest = com.example.levelupprueba.data.remote.LoginRequest(
                    correoUsuario = emailOrName.trim(),
                    password = password
                )
                
                val response = com.example.levelupprueba.data.remote.ApiConfig.authService.login(loginRequest)
                
                if (response.isSuccessful && response.body() != null) {
                    val authResponse = response.body()!!
                    val usuarioInfo = authResponse.usuario
                    
                    // Crear sesión con tokens
                    val session = UserSession(
                        displayName = usuarioInfo.nombre.split(" ").firstOrNull() ?: usuarioInfo.nombre,
                        loginAt = System.currentTimeMillis(),
                        userId = usuarioInfo.id,
                        role = usuarioInfo.tipoUsuario.lowercase(),
                        accessToken = authResponse.accessToken,
                        refreshToken = authResponse.refreshToken,
                        expiresIn = authResponse.expiresIn,
                        email = usuarioInfo.correo,
                        nombre = usuarioInfo.nombre,
                        apellidos = usuarioInfo.apellidos,
                        tipoUsuario = usuarioInfo.tipoUsuario,
                        descuentoDuoc = usuarioInfo.descuentoDuoc,
                        region = usuarioInfo.region,
                        comuna = usuarioInfo.comuna
                    )
                    
                    // Guardar sesión en local storage
                    saveUserSession(context, session)
                    
                    // Configurar token en ApiConfig para futuras peticiones
                    com.example.levelupprueba.data.remote.ApiConfig.setAuthToken(authResponse.accessToken)
                    com.example.levelupprueba.data.remote.ApiConfig.setUserId(usuarioInfo.id.toString())
                    
                    // Actualizar MainViewModel
                    mainViewModel.setUserSession(session)
                    
                    _loginEstado.value = LoginStatus.Success
                } else {
                    val errorMessage = com.example.levelupprueba.data.remote.ErrorHandler.getErrorMessage(
                        Exception("Error de autenticación: ${response.code()}")
                    )
                    _loginEstado.value = LoginStatus.Error(errorMessage)
                }
            } catch (e: Exception) {
                val errorMessage = com.example.levelupprueba.data.remote.ErrorHandler.getErrorMessage(e)
                _loginEstado.value = LoginStatus.Error(errorMessage)
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
            try {
                // Llamar al backend para cerrar sesión
                val session = getUserSession(context)
                if (session != null && session.accessToken.isNotEmpty()) {
                    // Configurar token antes de llamar al logout
                    com.example.levelupprueba.data.remote.ApiConfig.setAuthToken(session.accessToken)
                    com.example.levelupprueba.data.remote.ApiConfig.authService.logout()
                }
            } catch (e: Exception) {
                // Si falla el logout en el backend, continuar con el logout local
                android.util.Log.e("LoginViewModel", "Error al cerrar sesión en backend: ${e.message}")
            } finally {
                // Limpiar sesión local
                clearUserSession(context)
                com.example.levelupprueba.data.remote.ApiConfig.clear()
                mainViewModel.setUserSession(null)

                // Iniciar la actividad de autenticación en WelcomeScreen
                val intent = Intent(context, AuthActivity::class.java)
                intent.putExtra("startDestination", "welcome")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context.startActivity(intent)
                (context as? Activity)?.finish()
            }
        }
    }
}