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

    // Actualiza el campo RUN y valida en tiempo real
    fun onRunChange(valor: String) = actualizarCampo {
        // Formatear RUN con guión (12345678-9)
        val runFormateado = if (valor.length > 1 && !valor.contains("-")) {
            val runLimpio = valor.replace("-", "").replace(" ", "")
            if (runLimpio.length > 1) {
                val numero = runLimpio.dropLast(1)
                val digitoVerificador = runLimpio.last()
                if (numero.length in 7..8) {
                    "$numero-$digitoVerificador"
                } else {
                    valor.uppercase().trim()
                }
            } else {
                valor.uppercase().trim()
            }
        } else {
            valor.uppercase().trim()
        }
        it.copy(
            run = it.run.copy(
                valor = runFormateado,
                error = UsuarioValidator.validarRun(runFormateado)
            )
        )
    }

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
            run = estadoActual.run.copy(error = UsuarioValidator.validarRun(estadoActual.run.valor)),
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
            nuevoEstado.run.error,
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
            estadoActual.run,
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

        // Campos opcionales (no deben tener errores si están llenos)
        val camposOpcionales = listOf(
            estadoActual.telefono,
            estadoActual.direccion
        )

        val sinErroresObligatorios = camposObligatorios.all { it.error == null }
        val sinErroresOpcionales = camposOpcionales.all { 
            it.valor.isBlank() || it.error == null 
        }

        return todosLlenos && sinErroresObligatorios && sinErroresOpcionales
    }

    /**
     * Convierte fecha de formato DD/MM/YYYY a YYYY-MM-DD
     */
    private fun convertirFechaFormato(fecha: String): String {
        return try {
            val partes = fecha.split("/")
            if (partes.size == 3) {
                "${partes[2]}-${partes[1].padStart(2, '0')}-${partes[0].padStart(2, '0')}"
            } else {
                fecha
            }
        } catch (e: Exception) {
            fecha
        }
    }

    /**
     * Registra usuario en el backend
     * Usa /api/v1/auth/register que hace auto-login después del registro
     */
    fun registrarUsuario(context: Context) {
        viewModelScope.launch {
            _registroEstado.value = RegisterStatus.Loading
            try {
                // Convertir fecha de DD/MM/YYYY a YYYY-MM-DD
                val fechaFormatoBackend = convertirFechaFormato(_estado.value.fechaNacimiento.valor)
                
                // Limpiar RUN (remover guiones) para enviar al backend
                val runLimpio = _estado.value.run.valor.replace("-", "").replace(" ", "").uppercase()
                
                // Crear request para el backend
                val registerRequest = com.example.levelupprueba.data.remote.RegisterRequest(
                    runUsuario = runLimpio,
                    nombreUsuario = _estado.value.nombre.valor.trim(),
                    apellidosUsuario = _estado.value.apellidos.valor.trim(),
                    correoUsuario = _estado.value.email.valor.trim().lowercase(),
                    password = _estado.value.password.valor,
                    fechaNacimiento = fechaFormatoBackend,
                    region = _estado.value.region.valor.trim(),
                    comuna = _estado.value.comuna.valor.trim(),
                    direccionUsuario = _estado.value.direccion.valor.trim()
                )
                
                // Llamar al backend para registro
                val response = com.example.levelupprueba.data.remote.ApiConfig.authService.register(registerRequest)
                
                if (response.isSuccessful && response.body() != null) {
                    val authResponse = response.body()!!
                    val usuarioInfo = authResponse.usuario
                    
                    // El backend hace auto-login después del registro, crear sesión
                    val session = com.example.levelupprueba.model.auth.UserSession(
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
                    com.example.levelupprueba.data.local.saveUserSession(context, session)
                    
                    // Configurar token en ApiConfig para futuras peticiones
                    com.example.levelupprueba.data.remote.ApiConfig.setAuthToken(authResponse.accessToken)
                    com.example.levelupprueba.data.remote.ApiConfig.setUserId(usuarioInfo.id.toString())
                    
                    _registroEstado.value = RegisterStatus.Success
                } else {
                    // Intentar parsear errores específicos del backend
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = if (errorBody != null) {
                        try {
                            // Parsear JSON de error del backend
                            val errorJson = org.json.JSONObject(errorBody)
                            val errorsObject = errorJson.optJSONObject("errors")
                            if (errorsObject != null) {
                                val errorMessages = mutableListOf<String>()
                                var usuarioYaExiste = false
                                
                                // Mapear errores del backend a campos del formulario
                                errorsObject.keys().forEach { key ->
                                    val errorValue = errorsObject.getString(key)
                                    
                                    // Si el error contiene "Ya existe un usuario", es un error 409
                                    if (errorValue.contains("Ya existe un usuario") || errorValue.contains("ya existe")) {
                                        usuarioYaExiste = true
                                        // Verificar si es error de RUN o correo
                                        if (errorValue.contains("RUN:") || errorValue.contains("run:")) {
                                            // Error de RUN duplicado
                                            val mensajeLimpio = "Ya existe un usuario con este RUN"
                                            errorMessages.add(mensajeLimpio)
                                            _estado.update { it.copy(
                                                run = it.run.copy(error = com.example.levelupprueba.model.errors.UsuarioFieldErrors.RunYaExiste)
                                            ) }
                                        } else if (errorValue.contains("correo:") || errorValue.contains("correo")) {
                                            // Error de correo duplicado
                                            val mensajeLimpio = "Ya existe un usuario con este correo electrónico"
                                            errorMessages.add(mensajeLimpio)
                                            _estado.update { it.copy(
                                                email = it.email.copy(error = com.example.levelupprueba.model.errors.UsuarioFieldErrors.EmailYaExiste)
                                            ) }
                                        } else {
                                            // Error genérico
                                            errorMessages.add(errorValue)
                                            _estado.update { it.copy(
                                                email = it.email.copy(error = com.example.levelupprueba.model.errors.UsuarioFieldErrors.EmailYaExiste)
                                            ) }
                                        }
                                    } else {
                                        errorMessages.add("$key: $errorValue")
                                        
                                        // Actualizar errores en los campos correspondientes basados en el mensaje del backend
                                        when (key) {
                                            "password" -> {
                                                val passwordError = if (errorValue.contains("mayúscula") || errorValue.contains("minúscula") || errorValue.contains("número")) {
                                                    com.example.levelupprueba.model.errors.UsuarioFieldErrors.PasswordInvalido
                                                } else {
                                                    com.example.levelupprueba.model.errors.FieldErrors.Obligatorio("contraseña")
                                                }
                                                _estado.update { it.copy(
                                                    password = it.password.copy(error = passwordError)
                                                ) }
                                            }
                                            "direccionUsuario" -> {
                                                if (errorValue.contains("requerida")) {
                                                    _estado.update { it.copy(
                                                        direccion = it.direccion.copy(error = com.example.levelupprueba.model.errors.FieldErrors.Obligatorio("dirección"))
                                                    ) }
                                                }
                                            }
                                            "runUsuario" -> {
                                                _estado.update { it.copy(
                                                    run = it.run.copy(error = com.example.levelupprueba.model.errors.UsuarioFieldErrors.RunInvalido)
                                                ) }
                                            }
                                            "correoUsuario" -> {
                                                val emailError = if (errorValue.contains("dominio") || errorValue.contains("@")) {
                                                    com.example.levelupprueba.model.errors.UsuarioFieldErrors.EmailDominioNoPermitido
                                                } else if (errorValue.contains("ya existe")) {
                                                    com.example.levelupprueba.model.errors.UsuarioFieldErrors.EmailYaExiste
                                                } else {
                                                    com.example.levelupprueba.model.errors.UsuarioFieldErrors.EmailInvalido
                                                }
                                                _estado.update { it.copy(
                                                    email = it.email.copy(error = emailError)
                                                ) }
                                            }
                                        }
                                    }
                                }
                                
                                // Si hay un error embebido (como en el caso de 409), intentar extraerlo
                                if (errorMessages.isEmpty() && !usuarioYaExiste) {
                                    val errorString = errorsObject.optString("error", "")
                                    if (errorString.isNotEmpty()) {
                                        // Intentar parsear el error embebido si es JSON
                                        if (errorString.contains("{") && errorString.contains("}")) {
                                            try {
                                                val errorEmbebido = org.json.JSONObject(errorString)
                                                val errorsEmbebidos = errorEmbebido.optJSONObject("errors")
                                                if (errorsEmbebidos != null) {
                                                    errorsEmbebidos.keys().forEach { keyEmbebido ->
                                                        val errorValueEmbebido = errorsEmbebidos.getString(keyEmbebido)
                                                        if (errorValueEmbebido.contains("Ya existe un usuario")) {
                                                            usuarioYaExiste = true
                                                            errorMessages.add("Ya existe un usuario con este correo electrónico")
                                                            _estado.update { it.copy(
                                                                email = it.email.copy(error = com.example.levelupprueba.model.errors.UsuarioFieldErrors.EmailYaExiste)
                                                            ) }
                                                        } else {
                                                            errorMessages.add(errorValueEmbebido)
                                                        }
                                                    }
                                                }
                                            } catch (e: Exception) {
                                                // Si no se puede parsear, usar el string directamente
                                                if (errorString.contains("Ya existe un usuario")) {
                                                    usuarioYaExiste = true
                                                    errorMessages.add("Ya existe un usuario con este correo electrónico")
                                                    _estado.update { it.copy(
                                                        email = it.email.copy(error = com.example.levelupprueba.model.errors.UsuarioFieldErrors.EmailYaExiste)
                                                    ) }
                                                } else {
                                                    errorMessages.add(errorString)
                                                }
                                            }
                                        } else {
                                            if (errorString.contains("Ya existe un usuario")) {
                                                usuarioYaExiste = true
                                                errorMessages.add("Ya existe un usuario con este correo electrónico")
                                                _estado.update { it.copy(
                                                    email = it.email.copy(error = com.example.levelupprueba.model.errors.UsuarioFieldErrors.EmailYaExiste)
                                                ) }
                                            } else {
                                                errorMessages.add(errorString)
                                            }
                                        }
                                    }
                                }
                                
                                if (errorMessages.isNotEmpty()) {
                                    errorMessages.joinToString("\n")
                                } else {
                                    com.example.levelupprueba.data.remote.ErrorHandler.getErrorMessage(
                                        Exception("Error de registro: ${response.code()}")
                                    )
                                }
                            } else {
                                com.example.levelupprueba.data.remote.ErrorHandler.getErrorMessage(
                                    Exception("Error de registro: ${response.code()}")
                                )
                            }
                        } catch (e: Exception) {
                            com.example.levelupprueba.data.remote.ErrorHandler.getErrorMessage(
                                Exception("Error de registro: ${response.code()}")
                            )
                        }
                    } else {
                        com.example.levelupprueba.data.remote.ErrorHandler.getErrorMessage(
                            Exception("Error de registro: ${response.code()}")
                        )
                    }
                    _registroEstado.value = RegisterStatus.Error(errorMessage)
                }
            } catch (e: Exception) {
                val errorMessage = com.example.levelupprueba.data.remote.ErrorHandler.getErrorMessage(e)
                _registroEstado.value = RegisterStatus.Error(errorMessage)
            }
        }
    }

    // Permite volver al estado inicial (después de mostrar mensaje de éxito)
    fun resetRegistroEstado() {
        _registroEstado.value = RegisterStatus.Idle
    }
}