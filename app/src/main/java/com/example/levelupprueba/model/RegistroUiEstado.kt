package com.example.levelupprueba.model

//Modela los posibles estados del proceso de registro
sealed class RegistroUiEstado {
    object Idle : RegistroUiEstado()            //Estado inicial, no hace nada
    object Loading : RegistroUiEstado()         //Procesando registro(Esperando delay)
    object Success : RegistroUiEstado()         //Registro exitoso
    data class Error(val mensajeError: String) : RegistroUiEstado() //Fallo en el registro
}