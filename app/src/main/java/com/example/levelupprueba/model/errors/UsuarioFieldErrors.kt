package com.example.levelupprueba.model.errors

sealed class UsuarioFieldErrors : FieldErrors() {
    object EmailInvalido : UsuarioFieldErrors() {
        override fun mensaje(): String = "Correo inválido"
    }

    object EmailDominioNoPermitido : UsuarioFieldErrors(){
        override fun mensaje(): String = "Solo se permite correo @gmail.com o @duoc.cl"
    }

    object PasswordNoCoincide : UsuarioFieldErrors() {
        override fun mensaje(): String = "Las contraseñas no coinciden"
    }
    
    object PasswordSinMinuscula : UsuarioFieldErrors() {
        override fun mensaje(): String = "La contraseña debe contener al menos una minúscula"
    }
    
    object PasswordSinMayuscula : UsuarioFieldErrors() {
        override fun mensaje(): String = "La contraseña debe contener al menos una mayúscula"
    }
    
    object PasswordSinNumero : UsuarioFieldErrors() {
        override fun mensaje(): String = "La contraseña debe contener al menos un número"
    }
    object TelefonoInvalido : UsuarioFieldErrors() {
        override fun mensaje(): String = "El teléfono solo debe contener números"
    }
    object MenorEdad : UsuarioFieldErrors() {
        override fun mensaje(): String = "Debes ser mayor de 18 años"
    }
    object DireccionInvalida : UsuarioFieldErrors() {
        override fun mensaje(): String = "La dirección contiene caracteres no válidos"
    }
    object TerminosNoAceptados : UsuarioFieldErrors() {
        override fun mensaje(): String = "Debes aceptar los términos"
    }

    object RunInvalido : UsuarioFieldErrors() {
        override fun mensaje(): String = "RUN inválido. Debe tener formato: 12345678-9 o 12345678-K"
    }

    object RunYaExiste : UsuarioFieldErrors() {
        override fun mensaje(): String = "Ya existe un usuario con este RUN"
    }

    object PasswordInvalido : UsuarioFieldErrors() {
        override fun mensaje(): String = "La contraseña debe contener al menos: 1 minúscula, 1 mayúscula y 1 número"
    }
    
    object EmailYaExiste : UsuarioFieldErrors() {
        override fun mensaje(): String = "Ya existe un usuario con este correo electrónico"
    }
}