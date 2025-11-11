package com.example.levelupprueba.model.usuario

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.levelupprueba.model.errors.FieldErrors
import com.example.levelupprueba.model.errors.UsuarioFieldErrors
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class UsuarioValidatorTest {

    @Test
    fun `validarNombre devuelve Obligatorio cuando esta vacio`() {
        // Ejecuta validación con nombre vacío
        val resultado = UsuarioValidator.validarNombre("")

        // Verifica que se devuelva error de campo obligatorio
        assertEquals(FieldErrors.Obligatorio("nombre"), resultado)
    }

    @Test
    fun `validarApellidos devuelve Obligatorio cuando esta vacio`() {
        // Ejecuta validación con apellidos vacíos
        val resultado = UsuarioValidator.validarApellidos("")

        // Verifica que se devuelva error de campo obligatorio
        assertEquals(FieldErrors.Obligatorio("apellidos"), resultado)
    }

    @Test
    fun `validarEmail devuelve Obligatorio cuando esta vacio`() {
        // Ejecuta validación con correo vacío
        val resultado = UsuarioValidator.validarEmail("")

        // Verifica que se devuelva error de campo obligatorio
        assertEquals(FieldErrors.Obligatorio("correo"), resultado)
    }

    @Test
    fun `validarEmail devuelve EmailInvalido cuando el formato no es correcto`() {
        // Ejecuta validación con correo sin formato válido
        val resultado = UsuarioValidator.validarEmail("no-es-correo")

        // Verifica que se devuelva error de formato de correo
        assertEquals(UsuarioFieldErrors.EmailInvalido, resultado)
    }

    @Test
    fun `validarEmail devuelve EmailDominioNoPermitido cuando el dominio no es permitido`() {
        // Ejecuta validación con dominio distinto a los aceptados
        val resultado = UsuarioValidator.validarEmail("user@yahoo.com")

        // Verifica que se devuelva error de dominio no permitido
        assertEquals(UsuarioFieldErrors.EmailDominioNoPermitido, resultado)
    }

    @Test
    fun `validarEmail devuelve null cuando el correo es valido`() {
        // Ejecuta validación con correo válido
        val resultado = UsuarioValidator.validarEmail("user@gmail.com")

        // Verifica que no haya error
        assertNull(resultado)
    }

    @Test
    fun `validarPassword devuelve MinLength cuando es demasiado corta`() {
        // Ejecuta validación con contraseña corta
        val resultado = UsuarioValidator.validarPassword("123")

        // Verifica que se devuelva error de longitud mínima
        assertEquals(FieldErrors.MinLength("contraseña", 4), resultado)
    }

    @Test
    fun `validarConfirmPassword devuelve error cuando no coinciden`() {
        // Ejecuta validación con contraseñas distintas
        val resultado = UsuarioValidator.validarConfirmPassword("abcd", "abce")

        // Verifica que se devuelva error de no coincidencia
        assertEquals(UsuarioFieldErrors.PasswordNoCoincide, resultado)
    }

    @Test
    fun `validarTelefono devuelve null cuando esta vacio porque es opcional`() {
        // Ejecuta validación con teléfono vacío
        val resultado = UsuarioValidator.validarTelefono("")

        // Verifica que no haya error
        assertNull(resultado)
    }

    @Test
    fun `validarTelefono devuelve TelefonoInvalido cuando tiene caracteres no numericos`() {
        // Ejecuta validación con teléfono que contiene letras
        val resultado = UsuarioValidator.validarTelefono("123ABC")

        // Verifica que se devuelva error de formato de teléfono
        assertEquals(UsuarioFieldErrors.TelefonoInvalido, resultado)
    }

    @Test
    fun `validarTelefono devuelve MinLength cuando tiene menos de 9 digitos`() {
        // Ejecuta validación con menos dígitos de los requeridos
        val resultado = UsuarioValidator.validarTelefono("12345678")

        // Verifica que se devuelva error de longitud mínima
        assertEquals(FieldErrors.MinLength("teléfono", 9), resultado)
    }

    @Test
    fun `validarTelefono devuelve null cuando el telefono es valido`() {
        // Ejecuta validación con teléfono válido
        val resultado = UsuarioValidator.validarTelefono("123456789")

        // Verifica que no haya error
        assertNull(resultado)
    }

    @Test
    @RequiresApi(Build.VERSION_CODES.O)
    fun `validarRegion devuelve Obligatorio cuando esta vacia`() {
        // Ejecuta validación con región vacía
        val resultado = UsuarioValidator.validarRegion("")

        // Verifica que se devuelva error de campo obligatorio
        assertEquals(FieldErrors.Obligatorio("región"), resultado)
    }

    @Test
    @RequiresApi(Build.VERSION_CODES.O)
    fun `validarComuna devuelve Obligatorio cuando esta vacia`() {
        // Ejecuta validación con comuna vacía
        val resultado = UsuarioValidator.validarComuna("")

        // Verifica que se devuelva error de campo obligatorio
        assertEquals(FieldErrors.Obligatorio("comuna"), resultado)
    }

    @Test
    fun `validarDireccion permite valor vacio`() {
        // Ejecuta validación con dirección vacía
        val resultado = UsuarioValidator.validarDireccion("")

        // Verifica que no haya error
        assertNull(resultado)
    }

    @Test
    fun `validarTerminos devuelve error cuando no estan aceptados`() {
        // Ejecuta validación con términos no aceptados
        val resultado = UsuarioValidator.validarTerminos(false)

        // Verifica que se devuelva error de términos no aceptados
        assertEquals(UsuarioFieldErrors.TerminosNoAceptados, resultado)
    }

    @Test
    fun `validarTerminos devuelve null cuando estan aceptados`() {
        // Ejecuta validación con términos aceptados
        val resultado = UsuarioValidator.validarTerminos(true)

        // Verifica que no haya error
        assertNull(resultado)
    }
}
