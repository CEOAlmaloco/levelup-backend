package com.example.levelupprueba.model.usuario

import android.os.Build
import android.util.Patterns
import androidx.annotation.RequiresApi
import com.example.levelupprueba.model.errors.FieldErrors
import com.example.levelupprueba.model.errors.UsuarioFieldErrors
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object UsuarioValidator {

    fun validarRun(run: String): FieldErrors? =
        when {
            run.isBlank() -> FieldErrors.Obligatorio("RUN")
            else -> {
                // Remover guiones y espacios para validar
                val runLimpio = run.replace("-", "").replace(" ", "").uppercase()
                if (!runLimpio.matches(Regex("^[0-9]{7,8}[0-9K]$"))) {
                    UsuarioFieldErrors.RunInvalido
                } else {
                    null
                }
            }
        }

    fun validarNombre(nombre: String): FieldErrors? =
        if (nombre.isBlank()) FieldErrors.Obligatorio("nombre") else null

    fun validarApellidos(apellidos: String): FieldErrors? =
        if (apellidos.isBlank()) FieldErrors.Obligatorio("apellidos") else null

    fun validarEmail(email: String): FieldErrors? =
        when {
            email.isBlank() -> FieldErrors.Obligatorio("correo")
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> UsuarioFieldErrors.EmailInvalido
            !email.endsWith("@gmail.com") && !email.endsWith("@duoc.cl") -> UsuarioFieldErrors.EmailDominioNoPermitido
            else -> null
        }

    fun validarPassword(password: String): FieldErrors? =
        when {
            password.isBlank() -> FieldErrors.Obligatorio("contraseña")
            password.length < 8 -> FieldErrors.MinLength("contraseña", 8)
            password.length > 100 -> FieldErrors.MaxLength("contraseña", 100)
            !password.matches(Regex(".*[a-z].*")) -> UsuarioFieldErrors.PasswordSinMinuscula
            !password.matches(Regex(".*[A-Z].*")) -> UsuarioFieldErrors.PasswordSinMayuscula
            !password.matches(Regex(".*\\d.*")) -> UsuarioFieldErrors.PasswordSinNumero
            else -> null
        }

    fun validarConfirmPassword(password: String, confirm: String): UsuarioFieldErrors? =
        if (confirm != password) UsuarioFieldErrors.PasswordNoCoincide else null

    fun validarTelefono(telefono: String): FieldErrors? =
        when {
            telefono.isBlank() -> null // Campo opcional
            !telefono.all { it.isDigit() } -> UsuarioFieldErrors.TelefonoInvalido
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
                        UsuarioFieldErrors.MenorEdad
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
                UsuarioFieldErrors.DireccionInvalida
            else -> null
        }

    fun validarTerminos(aceptado: Boolean): FieldErrors? =
        if (!aceptado) UsuarioFieldErrors.TerminosNoAceptados else null
}