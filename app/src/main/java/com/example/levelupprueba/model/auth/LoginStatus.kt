package com.example.levelupprueba.model.auth

//Modela los posibles estados del proceso de registro
sealed class LoginStatus {
    object Idle : LoginStatus()
    object Loading : LoginStatus()
    object Success : LoginStatus()
    data class Error(val mensajeError: String) : LoginStatus()
}