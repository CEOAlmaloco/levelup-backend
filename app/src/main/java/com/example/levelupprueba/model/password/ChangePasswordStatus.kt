package com.example.levelupprueba.model.password

sealed class PasswordStatus {
    data object Idle : PasswordStatus()
    data object Saving : PasswordStatus()
    data object Success : PasswordStatus()
    data class ValidationError(val fields: List<String>, val errorMessage: String) : PasswordStatus()
    data class Error(val mensaje: String) : PasswordStatus()
}
