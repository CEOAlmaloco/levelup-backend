package com.example.levelupprueba.model.usuario

import android.os.Build
import android.util.Patterns
import androidx.annotation.RequiresApi
import com.example.levelupprueba.model.FieldErrors
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object UsuarioValidator {

    fun validarNombre(nombre: String): FieldErrors? =
        if (nombre.isBlank()) FieldErrors.Obligatorio("nombre") else null

    fun validarApellidos(apellidos: String): FieldErrors? =
        if (apellidos.isBlank()) FieldErrors.Obligatorio("apellidos") else null

    fun validarEmail(email: String): FieldErrors? =
        when {
            email.isBlank() -> FieldErrors.Obligatorio("correo")
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> FieldErrors.EmailInvalido
            else -> null
        }

    fun validarPassword(password: String): FieldErrors? =
        when {
            password.isBlank() -> FieldErrors.Obligatorio("contraseña")
            password.length < 4 -> FieldErrors.MinLength("contraseña", 4)
            password.length > 10 -> FieldErrors.MaxLength("contraseña", 10)
            else -> null
        }

    fun validarConfirmPassword(password: String, confirm: String): FieldErrors? =
        if (confirm != password) FieldErrors.PasswordNoCoincide else null

    fun validarTelefono(telefono: String): FieldErrors? =
        when {
            telefono.isBlank() -> null // Campo opcional
            !telefono.all { it.isDigit() } -> FieldErrors.TelefonoInvalido
            telefono.length < 9 -> FieldErrors.MinLength("teléfono", 9)
            else -> null
        }

    @RequiresApi(Build.VERSION_CODES.O)
    fun validarFechaNacimiento(fecha: String): FieldErrors? =
        when {
            fecha.isBlank() -> FieldErrors.Obligatorio("fecha de nacimiento")
            else -> {
                try {
                    val fechaNacimiento = LocalDate.parse(fecha, DateTimeFormatter.ISO_LOCAL_DATE)
                    val hoy = LocalDate.now()
                    val edad = fechaNacimiento.until(hoy).years
                    if (edad < 18) {
                        FieldErrors.MenorEdad
                    } else {
                        null
                    }
                } catch (e: DateTimeParseException) {
                    FieldErrors.FormatoInvalido
                }
            }
        }
    fun validarRegion(region: String): FieldErrors? =
        if (region.isBlank()) FieldErrors.Obligatorio("región") else null

    fun validarComuna(comuna: String): FieldErrors? =
        if (comuna.isBlank()) FieldErrors.Obligatorio("comuna") else null

    fun validarDireccion(direccion: String): FieldErrors? =
        when {
            direccion.isBlank() -> null // Campo opcional
            direccion.length > 300 -> FieldErrors.MaxLength("dirección", 300)
            !direccion.matches(Regex("^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ\\s.,#-]+$")) ->
                FieldErrors.DireccionInvalida
            else -> null
        }

    fun validarTerminos(aceptado: Boolean): FieldErrors? =
        if (!aceptado) FieldErrors.TerminosNoAceptados else null
}