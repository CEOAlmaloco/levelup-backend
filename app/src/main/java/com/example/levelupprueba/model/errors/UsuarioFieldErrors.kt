package com.example.levelupprueba.model.errors

sealed class UsuarioFieldErrors : FieldErrors() {
    object EmailInvalido : UsuarioFieldErrors() {
        override fun mensaje(): String = "Correo inválido"
    }
    object PasswordLimite : UsuarioFieldErrors() {
        override fun mensaje(): String = "La contraseña debe tener entre 4 y 10 caracteres"
    }
    object PasswordNoCoincide : UsuarioFieldErrors() {
        override fun mensaje(): String = "Las contraseñas no coinciden"
    }
    object TelefonoInvalido : UsuarioFieldErrors() {
        override fun mensaje(): String = "El teléfono solo debe contener números"
    }
    object TelefonoLimite : UsuarioFieldErrors() {
        override fun mensaje(): String = "El teléfono debe tener al menos 9 dígitos"
    }
    object MenorEdad : UsuarioFieldErrors() {
        override fun mensaje(): String = "Debes ser mayor de 18 años"
    }
    object DireccionLimite : UsuarioFieldErrors() {
        override fun mensaje(): String = "La dirección no puede tener más de 300 caracteres"
    }
    object DireccionInvalida : UsuarioFieldErrors() {
        override fun mensaje(): String = "La dirección contiene caracteres no válidos"
    }
    object TerminosNoAceptados : UsuarioFieldErrors() {
        override fun mensaje(): String = "Debes aceptar los términos"
    }
}