package com.example.levelupprueba.ui.screens.profile

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.levelupprueba.model.errors.FieldErrors
import com.example.levelupprueba.model.usuario.UsuarioValidator

data class PerfilEditable(
    val nombre: String = "",
    val apellidos: String = "",
    val telefono: String = "",
    val fechaNacimiento: String = "",
    val region: String = "",
    val comuna: String = "",
    val direccion: String = "",
    val avatar: String? = null
){
    @RequiresApi(Build.VERSION_CODES.O)
    fun validar(): Map<String, FieldErrors?> = mapOf(
        "nombre" to UsuarioValidator.validarNombre(nombre),
        "apellidos" to UsuarioValidator.validarApellidos(apellidos),
        "telefono" to UsuarioValidator.validarTelefono(telefono),
        "fechaNacimiento" to UsuarioValidator.validarFechaNacimiento(fechaNacimiento),
        "region" to UsuarioValidator.validarRegion(region),
        "comuna" to UsuarioValidator.validarComuna(comuna),
        "direccion" to UsuarioValidator.validarDireccion(direccion)
    )

    @RequiresApi(Build.VERSION_CODES.O)
    fun esValido(): Boolean = validar().values.all { it == null }
}
