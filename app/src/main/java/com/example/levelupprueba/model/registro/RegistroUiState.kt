package com.example.levelupprueba.model.registro

//Modela los posibles estados del proceso de registro
sealed class RegistroUiState {
    object Idle : RegistroUiState()            //Estado inicial, no hace nada
    object Loading : RegistroUiState()         //Procesando registro(Esperando delay)
    object Success : RegistroUiState()         //Registro exitoso
    data class Error(val mensajeError: String) : RegistroUiState() //Fallo en el registro
}