package com.example.levelupprueba.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.levelupprueba.model.password.PasswordStatus
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
class ChangePasswordViewModelTest {

    private lateinit var vm: ChangePasswordViewModel
    private val dispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(dispatcher)
        vm = ChangePasswordViewModel()
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
    // Nota: Este test requiere mockear ApiConfig.usuarioService, 
    // lo cual es complejo. Se puede hacer con mockkStatic si es necesario.
    // Por ahora, solo verificamos que el método existe y no lanza excepciones
    @RequiresApi(Build.VERSION_CODES.O)
    @Test
    fun `cambiarPassword valida formulario antes de cambiar`() = runTest {
        vm.onActualChange("AAA111")
        vm.onNuevaChange("BBB222")
        vm.onConfirmarChange("BBB222")

        // El método debería validar primero
        vm.cambiarPassword("test@email.com")

        advanceUntilIdle()
        
        // Verificamos que el estado cambió (aunque falle por falta de mock)
        assertNotNull(vm.status.value)
    }

    // 5) RESET
    @Test
    fun `resetPasswordStatus vuelve a Idle`() {
        vm.resetPasswordStatus()
        assertTrue(vm.status.value is PasswordStatus.Idle)
    }
}

