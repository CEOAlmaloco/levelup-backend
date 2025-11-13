package com.example.levelupprueba.model.auth

import com.example.levelupprueba.model.errors.FieldErrors
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class LoginValidatorTest {

    @Test
    fun `validarEmailOrName devuelve Obligatorio cuando esta vacio`() {
        // Valor vacío para correo o nombre
        val resultado = LoginValidator.validarEmailOrName("")

        // Verifica que se devuelva error de campo obligatorio
        assertEquals(FieldErrors.Obligatorio("correo o nombre"), resultado)
    }

    @Test
    fun `validarEmailOrName devuelve null cuando tiene algun valor`() {
        // Valor no vacío para correo o nombre
        val resultado = LoginValidator.validarEmailOrName("usuario")

        // Verifica que no haya error (null)
        assertNull(resultado)
    }

    @Test
    fun `validarPassword devuelve Obligatorio cuando esta vacia`() {
        // Contraseña vacía
        val resultado = LoginValidator.validarPassword("")

        // Verifica que se devuelva error de campo obligatorio
        assertEquals(FieldErrors.Obligatorio("contraseña"), resultado)
    }

    @Test
    fun `validarPassword devuelve MinLength cuando es demasiado corta`() {
        // Contraseña con menos de 4 caracteres
        val resultado = LoginValidator.validarPassword("123")

        // Verifica que se devuelva error de longitud mínima
        assertEquals(FieldErrors.MinLength("contraseña", 4), resultado)
    }

    @Test
    fun `validarPassword devuelve MaxLength cuando es demasiado larga`() {
        // Contraseña con mas de 10 caracteres
        val resultado = LoginValidator.validarPassword("12345678901")

        // Verifica que se devuelva error de longitud máxima
        assertEquals(FieldErrors.MaxLength("contraseña", 10), resultado)
    }

    @Test
    fun `validarPassword devuelve null cuando esta dentro del rango permitido`() {
        // Contraseña dentro del rango [4, 10]
        val resultado = LoginValidator.validarPassword("1234")

        // Verifica que no haya error (null)
        assertNull(resultado)
    }
}
