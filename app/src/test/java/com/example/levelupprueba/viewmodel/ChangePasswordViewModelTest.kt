package com.example.levelupprueba.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.levelupprueba.data.repository.UsuarioRepository
import com.example.levelupprueba.model.password.PasswordStatus
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
class ChangePasswordViewModelTest {

    private lateinit var repo: UsuarioRepository
    private lateinit var vm: ChangePasswordViewModel
    private val dispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(dispatcher)
        repo = mockk(relaxed = true)
        vm = ChangePasswordViewModel(repo)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // 1) VALIDACIONES INDIVIDUALES
    @Test
    fun `onActualChange actualiza valor y valida campo`() {
        vm.onActualChange("1234")
        val estado = vm.estado.value
        assertEquals("1234", estado.actual.valor)
        assertNull(estado.actual.error)
    }

    @Test
    fun `onNuevaChange actualiza valor y valida confirmacion`() {
        vm.onNuevaChange("abcd1234")
        val estado = vm.estado.value
        assertEquals("abcd1234", estado.nueva.valor)
        assertNull(estado.nueva.error)
    }

    @Test
    fun `onConfirmarChange marca error cuando no coincide`() {
        vm.onNuevaChange("abcd1234")
        vm.onConfirmarChange("xxxx")

        val estado = vm.estado.value
        assertNotNull(estado.confirmar.error)
    }

    // 2) VALIDAR FORMULARIO
    @RequiresApi(Build.VERSION_CODES.O)
    @Test
    fun `validarFormulario retorna false cuando hay campos invalidos`() {
        val valido = vm.validarFormulario()

        assertFalse(valido)
        assertTrue(vm.status.value is PasswordStatus.ValidationError)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Test
    fun `validarFormulario retorna true cuando todos los datos son validos`() {
        vm.onActualChange("Abc1234")
        vm.onNuevaChange("Abc12345")
        vm.onConfirmarChange("Abc12345")

        val valido = vm.validarFormulario()
        assertTrue(valido)
    }

    // 3) PUEDE GUARDAR
    @Test
    fun `puedeGuardar false si hay errores`() {
        vm.onActualChange("")
        vm.onNuevaChange("1234")
        vm.onConfirmarChange("1234")

        assertFalse(vm.puedeGuardar())
    }

    @Test
    fun `puedeGuardar true cuando todo es valido y sin errores`() {
        vm.onActualChange("abcd1234")
        vm.onNuevaChange("abcd12345")
        vm.onConfirmarChange("abcd12345")

        assertTrue(vm.puedeGuardar())
    }

    // 4) CAMBIAR PASSWORD (SUCCESS)
    @RequiresApi(Build.VERSION_CODES.O)
    @Test
    fun `cambiarPassword retorna Success cuando repo devuelve true`() = runTest {
        vm.onActualChange("AAA111")
        vm.onNuevaChange("BBB222")
        vm.onConfirmarChange("BBB222")

        coEvery {
            repo.changePasswordUsuario("test@email.com", "AAA111", "BBB222")
        } returns true

        vm.cambiarPassword("test@email.com")

        dispatcher.scheduler.advanceUntilIdle()

        assertTrue(vm.status.value is PasswordStatus.Success)
        coVerify(exactly = 1) { repo.changePasswordUsuario(any(), any(), any()) }
    }

    // 5) CAMBIAR PASSWORD (ERROR - contraseña incorrecta)
    @RequiresApi(Build.VERSION_CODES.O)
    @Test
    fun `cambiarPassword retorna Error cuando repo devuelve false`() = runTest {
        vm.onActualChange("AAA111")
        vm.onNuevaChange("BBB222")
        vm.onConfirmarChange("BBB222")

        coEvery {
            repo.changePasswordUsuario(any(), any(), any())
        } returns false

        vm.cambiarPassword("a@a.com")

        dispatcher.scheduler.advanceUntilIdle()

        val estado = vm.status.value
        assertTrue(estado is PasswordStatus.Error)
    }

    // 6) EXCEPCION EN REPO
    @RequiresApi(Build.VERSION_CODES.O)
    @Test
    fun `cambiarPassword captura excepcion y retorna Error`() = runTest {
        vm.onActualChange("AAA111")
        vm.onNuevaChange("BBB222")
        vm.onConfirmarChange("BBB222")

        coEvery { repo.changePasswordUsuario(any(), any(), any()) }
            .throws(RuntimeException("fallo interno"))

        vm.cambiarPassword("a@a.com")

        dispatcher.scheduler.advanceUntilIdle()

        val estado = vm.status.value
        assertTrue(estado is PasswordStatus.Error)
    }

    // 7) RESET
    @Test
    fun `resetPasswordStatus vuelve a Idle`() {
        vm.resetPasswordStatus()
        assertTrue(vm.status.value is PasswordStatus.Idle)
    }
}
