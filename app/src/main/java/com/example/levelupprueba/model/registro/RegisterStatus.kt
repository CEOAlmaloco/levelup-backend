package com.example.levelupprueba.model.registro

//Modela los posibles estados del proceso de registro
sealed class RegisterStatus {
    object Idle : RegisterStatus()            //Estado inicial, no hace nada
    object Loading : RegisterStatus()         //Procesando registro(Esperando delay)
    object Success : RegisterStatus()         //Registro exitoso
    data class Error(val mensajeError: String) : RegisterStatus() //Fallo en el registro
}