package com.example.levelupprueba.model.auth

import com.example.levelupprueba.model.FieldErrors

data class LoginCampo(
    val valor: String = "",
    val error: FieldErrors? = null
)

val LoginCampo.isSuccess: Boolean
    get() = valor.isNotBlank() && error == null