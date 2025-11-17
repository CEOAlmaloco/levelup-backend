package com.example.levelupprueba.data.repository

import com.example.levelupprueba.data.local.UsuarioDao
import com.example.levelupprueba.model.usuario.Usuario
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class UsuarioRepositoryTest {

    // DAO simulado con MockK
    private val usuarioDao = mockk<UsuarioDao>(relaxed = true)

    // Repositorio que vamos a probar
    private val repository = UsuarioRepository(usuarioDao)

    // Usuario base para reutilizar en los tests
    private val baseUsuario = Usuario(
        id = "",
        nombre = "Ariel",
        apellidos = "Tester",
        email = "test@example.com",
        password = "1234",
        telefono = null,
        fechaNacimiento = null,
        region = null,
        comuna = null,
        direccion = null,
        referralCode = "",
        points = 0,
        redeemedCodes = emptyList(),
        referredBy = null,
        role = "user",
        avatar = null
    )

    @Test
    fun `saveUsuario inserta cuando el id esta vacio`() = runTest {
        // Ejecuta saveUsuario con id vacío (usuario nuevo)
        repository.saveUsuario(baseUsuario)

        // Verifica que se llame a insertUsuario con un id no vacío
        coVerify {
            usuarioDao.insertUsuario(
                match { it.id.isNotEmpty() && it.email == baseUsuario.email }
            )
        }

        // Verifica que NO se haya llamado a updateUsuario
        coVerify(exactly = 0) { usuarioDao.updateUsuario(any()) }
    }

    @Test
    fun `saveUsuario actualiza cuando el id ya existe`() = runTest {
        // Usuario existente con id definido
        val existente = baseUsuario.copy(id = "USER123")

        // Ejecuta saveUsuario con id no vacío
        repository.saveUsuario(existente)

        // Verifica que se llame a updateUsuario con el mismo id
        coVerify {
            usuarioDao.updateUsuario(
                match { it.id == "USER123" && it.email == existente.email }
            )
        }

        // Verifica que NO se haya llamado a insertUsuario
        coVerify(exactly = 0) { usuarioDao.insertUsuario(any()) }
    }

    @Test
    fun `changePasswordUsuario actualiza cuando contrasena actual es correcta`() = runTest {
        // Usuario en la base con password actual "1234"
        val usuarioEnBd = baseUsuario.copy(id = "USER1", password = "1234")
        coEvery { usuarioDao.getUsuarioByEmail("test@example.com") } returns usuarioEnBd

        // Ejecuta cambio de contraseña correcto
        val resultado = repository.changePasswordUsuario(
            email = "test@example.com",
            currentPassword = "1234",
            newPassword = "nuevaClave"
        )

        // Verifica que devuelva true
        assertTrue(resultado)

        // Verifica que se haya actualizado el usuario con la nueva contraseña
        coVerify {
            usuarioDao.updateUsuario(
                match { it.id == "USER1" && it.password == "nuevaClave" }
            )
        }
    }

    @Test
    fun `changePasswordUsuario devuelve false cuando la contrasena actual es incorrecta`() = runTest {
        // Usuario en la base con password "1234"
        val usuarioEnBd = baseUsuario.copy(id = "USER1", password = "1234")
        coEvery { usuarioDao.getUsuarioByEmail("test@example.com") } returns usuarioEnBd

        // Ejecuta cambio con contraseña actual incorrecta
        val resultado = repository.changePasswordUsuario(
            email = "test@example.com",
            currentPassword = "malaClave",
            newPassword = "nuevaClave"
        )

        // Verifica que devuelva false
        assertFalse(resultado)

        // Verifica que NO se llame a updateUsuario
        coVerify(exactly = 0) { usuarioDao.updateUsuario(any()) }
    }

    @Test
    fun `generateReferralCode genera codigo con prefijo del nombre y longitud correcta`() {
        // Genera código de referido para un nombre
        val code = repository.generateReferralCode("Ariel Dev")

        // "Ariel Dev" -> "ARIELDEV" -> primeros 6 = "ARIELD"
        assertTrue(code.startsWith("ARIELD"))

        // Prefijo (6) + parte aleatoria (4) = 10 caracteres
        assertEquals(10, code.length)
    }

    @Test
    fun `emailExists devuelve true cuando el usuario existe`() = runTest {
        // Simula que el DAO encuentra un usuario para ese email
        coEvery { usuarioDao.getUsuarioByEmail("existe@correo.com") } returns baseUsuario

        val resultado = repository.emailExists("existe@correo.com")

        assertTrue(resultado)
    }

    @Test
    fun `emailExists devuelve false cuando el usuario no existe`() = runTest {
        // Simula que el DAO no encuentra usuario
        coEvery { usuarioDao.getUsuarioByEmail("noexiste@correo.com") } returns null

        val resultado = repository.emailExists("noexiste@correo.com")

        assertFalse(resultado)
    }
}
