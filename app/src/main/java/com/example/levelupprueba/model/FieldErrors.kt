package com.example.levelupprueba.model

sealed class FieldErrors{
    // Errores de obligatoriedad (Data Class al ser informacion variable)
    data class Obligatorio(val campo: String) : FieldErrors()

    // Errores con parámetros dinámicos (Data Class al ser informacion variable)
    data class MinLength(val campo: String, val min: Int) : FieldErrors()
    data class MaxLength(val campo: String, val max: Int) : FieldErrors()

    // Errores unicos e iguales (Object al ser siempre iguales)
    object EmailInvalido : FieldErrors()
    object PasswordLimite : FieldErrors()
    object PasswordNoCoincide : FieldErrors()
    object TelefonoInvalido : FieldErrors()
    object TelefonoLimite : FieldErrors()
    object DireccionLimite : FieldErrors()
    object DireccionInvalida : FieldErrors()
    object TerminosNoAceptados : FieldErrors()

    // Error personalizado (Data Class al ser informacion variable)
    data class Personalizado (val mensaje: String) : FieldErrors()

    //Mensaje que convierte cada tipo de error en un mensaje de texto
    fun mensaje(): String = when (this){
        is Obligatorio -> "El campo ${campo} es obligatorio"
        is MinLength -> "El campo ${campo} debe tener al menos $min caracteres"
        is MaxLength -> "El campo ${campo} no puede tener mas de $max caracteres"
        EmailInvalido -> "Correo inválido"
        PasswordLimite -> "La contraseña debe tener entre 4 y 10 caracteres"
        PasswordNoCoincide -> "Las contraseñas no coinciden"
        TelefonoInvalido -> "El teléfono solo debe contener"
        TelefonoLimite -> "El teléfono debe tener al menos 9 dígitos"
        DireccionLimite -> "La dirección no puede tener más de 300 caracteres"
        DireccionInvalida -> "La dirección contiene caracteres no válidos"
        TerminosNoAceptados -> "Debes aceptar los términos"
        is Personalizado -> this.mensaje
    }
}