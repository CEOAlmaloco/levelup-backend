package com.example.levelupprueba.viewmodel

import androidx.lifecycle.ViewModel
import com.example.levelupprueba.model.LoginErrores
import com.example.levelupprueba.model.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

// ViewModel que gestiona el estado y la logica del formulario de login
class LoginViewModel : ViewModel(){
    //Estado interno mutable: se guarda el estado actual del formulario
    private val _estado = MutableStateFlow(LoginUiState())
    //Estado expuesto: la UI observa este estado para mostrar los valores y errores
    val estado: StateFlow<LoginUiState> = _estado

    // Actualiza el campo email/nombre en el estado y limpia su error
    fun onEmailOrNameChange(valor: String){
        _estado.update {
            it.copy(emailOrName = valor, // Nuevo valor
            errores = it.errores.copy(emailOrName = null) // Limpia error de este campo
            )
        }
    }

    // Actualiza el campo contraseña en el estado y limpia su error
    fun onPasswordChange(valor: String) {
        _estado.update {
            it.copy(
                password = valor, // Nuevo valor
                errores = it.errores.copy(password = null) // Limpia error de este campo
            )
        }
    }

    //Valida el formulario de login
    //Retorna true si es valido, false si hay errores
    fun validarLogin(): Boolean {
        val estadoActual = _estado.value // Obtiene el estado actual
        // Crea un objeto de errores según lo que falta o está mal
        val errores = LoginErrores(
            emailOrName = if (estadoActual.emailOrName.isBlank()) "El email o nombre son obligatorios" else null,
            password = if (estadoActual.password.isBlank()) "La contraseña es obligatoria" else null
        )

        // Verifica si hay errores (Si alguno no es null)
        val hayErrores = listOfNotNull(
            errores.emailOrName,
            errores.password
        ).isNotEmpty()


        //Actualiza el estado con los errores encontrados
        _estado.update { it.copy(errores = errores) }

        //Retorna true si NO hay errores
        return !hayErrores

    }

}