package com.example.levelupprueba.model.auth

import com.example.levelupprueba.model.errors.FieldErrors

object LoginValidator {

    fun validarEmailOrName(emailName: String): FieldErrors? =
        when {
            emailName.isBlank() -> FieldErrors.Obligatorio("correo o nombre")
            else -> null
        }

    fun validarPassword(password: String): FieldErrors? =
        when {
            password.isBlank() -> FieldErrors.Obligatorio("contraseña")
            password.length < 4 -> FieldErrors.MinLength("contraseña", 4)
            password.length > 10 -> FieldErrors.MaxLength("contraseña", 10)
            else -> null
        }
}