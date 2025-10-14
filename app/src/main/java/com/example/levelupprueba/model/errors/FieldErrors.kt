package com.example.levelupprueba.model.errors

sealed class FieldErrors {
    data class Obligatorio(val campo: String) : FieldErrors() {
        override fun mensaje(): String = "El campo $campo es obligatorio"
    }
    data class MinLength(val campo: String, val min: Int) : FieldErrors() {
        override fun mensaje(): String = "El campo $campo debe tener al menos $min caracteres"
    }
    data class MaxLength(val campo: String, val max: Int) : FieldErrors() {
        override fun mensaje(): String = "El campo $campo no puede tener más de $max caracteres"
    }
    object FormatoInvalido : FieldErrors() {
        override fun mensaje(): String = "Formato inválido"
    }
    data class Personalizado(val mensajePersonalizado: String) : FieldErrors() {
        override fun mensaje(): String = mensajePersonalizado
    }

    open fun mensaje(): String = "Error de campo desconocido"
}