package com.example.levelupprueba.model.usuario

import com.example.levelupprueba.model.errors.FieldErrors

data class UsuarioCampo(
    val valor: String = "",
    val error: FieldErrors? = null
)

val UsuarioCampo.isSuccess: Boolean
    get() = valor.isNotBlank() && error == null
