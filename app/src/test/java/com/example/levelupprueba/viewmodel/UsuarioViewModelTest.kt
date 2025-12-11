package com.example.levelupprueba.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.levelupprueba.model.registro.RegisterStatus
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class UsuarioViewModelTest {

    private lateinit var vm: UsuarioViewModel

    @BeforeEach
    fun setup() {
        vm = UsuarioViewModel()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Test
    fun `validarRegistro retorna false y marca errores cuando los campos estan vacios`() {
        // Estado inicial -> todo vacío
        val esValido = vm.validarRegistro()

        // No debería permitir el registro
        assertFalse(esValido)

        val estado = vm.estado.value

        assertNotNull(estado.nombre.error)
        assertNotNull(estado.email.error)
        assertNotNull(estado.password.error)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Test
    fun `puedeRegistrar es true solo cuando campos obligatorios llenos y sin errores`() {
        vm.onNombreChange("Andrés")
        vm.onApellidosChange("Pérez")
        vm.onEmailChange("andres@gmail.com")
        vm.onPasswordChange("Abc12345")
        vm.onConfirmPasswordChange("Abc12345")
        vm.onFechaNacimientoChange("2000-01-01")
        vm.onRegionChange("Metropolitana")
        vm.onComunaChange("Santiago")
        vm.onTerminosChange(true)

        // Ejecuta validación para actualizar errores
        vm.validarRegistro()

        // Debería poder registrar
        assertTrue(vm.puedeRegistrar())

        // Ahora rompe una condición (por ejemplo, términos en falso)
        vm.onTerminosChange(false)

        // Ya no debería permitir registrar
        assertFalse(vm.puedeRegistrar())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Test
    fun `validarRegistro retorna true cuando todos los datos son validos`() {
        // GIVEN: todos los campos con datos válidos según UsuarioValidator
        vm.onNombreChange("Andrés")
        vm.onApellidosChange("Pérez")
        vm.onEmailChange("andres@gmail.com")
        vm.onPasswordChange("Abc12345")
        vm.onConfirmPasswordChange("Abc12345")
        vm.onTelefonoChange("987654321")
        vm.onFechaNacimientoChange("2000-01-01")
        vm.onRegionChange("Metropolitana")
        vm.onComunaChange("Santiago")
        vm.onDireccionChange("Calle Falsa 123")
        vm.onTerminosChange(true)

        // WHEN: se valida el registro completo
        val esValido = vm.validarRegistro()

        // THEN: la función debe devolver true
        assertTrue(esValido)

        // Los campos no deben tener errores
        val estado = vm.estado.value
        assertNull(estado.nombre.error)
        assertNull(estado.apellidos.error)
        assertNull(estado.email.error)
        assertNull(estado.password.error)
        assertNull(estado.confirmPassword.error)
        assertNull(estado.telefono.error)
        assertNull(estado.fechaNacimiento.error)
        assertNull(estado.region.error)
        assertNull(estado.comuna.error)
        assertNull(estado.direccion.error)
        assertNull(estado.terminos.error)
    }
}

