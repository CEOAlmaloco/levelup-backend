package com.example.levelupprueba.model.usuario

import com.example.levelupprueba.model.FieldErrors

data class UsuarioCampo(
    val valor: String = "",
    val error: FieldErrors? = null
)