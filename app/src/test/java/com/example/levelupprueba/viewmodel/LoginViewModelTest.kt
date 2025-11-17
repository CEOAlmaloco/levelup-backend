package com.example.levelupprueba.viewmodel

import com.example.levelupprueba.data.repository.UsuarioRepository
import com.example.levelupprueba.model.auth.LoginStatus
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class LoginViewModelTest {

    // Crea el ViewModel con un repositorio mock (no usamos el repo en estos tests)
    private fun buildVM(): LoginViewModel {
        val repo: UsuarioRepository = mockk(relaxed = true)
        return LoginViewModel(repo)
    }

    @Test
    fun `onEmailOrNameChange actualiza valor y deja sin error cuando es valido`() {
        val vm = buildVM()

        vm.onEmailOrNameChange("andres@gmail.com")

        val estado = vm.estado.value
        assertEquals("andres@gmail.com", estado.emailOrName.valor)
        // Para un valor válido no debería haber error
        assertNull(estado.emailOrName.error)
    }

    @Test
    fun `onEmailOrNameChange asigna error cuando el campo queda vacio`() {
        val vm = buildVM()

        vm.onEmailOrNameChange("")  // vacío

        val estado = vm.estado.value
        assertEquals("", estado.emailOrName.valor)
        // Cualquier error distinto de null indica que se marcó como obligatorio
        assertNotNull(estado.emailOrName.error)
    }

    @Test
    fun `onPasswordChange actualiza valor y deja sin error cuando es valido`() {
        val vm = buildVM()

        vm.onPasswordChange("1234")  // dentro del rango 4-10

        val estado = vm.estado.value
        assertEquals("1234", estado.password.valor)
        assertNull(estado.password.error)
    }

    @Test
    fun `onPasswordChange asigna error cuando la password es demasiado corta`() {
        val vm = buildVM()

        vm.onPasswordChange("12")  // menos de 4 caracteres

        val estado = vm.estado.value
        assertEquals("12", estado.password.valor)
        assertNotNull(estado.password.error)
    }

    @Test
    fun `validarLogin retorna false y marca errores cuando los campos son invalidos`() {
        val vm = buildVM()

        // Dejo ambos campos inválidos (vacíos)
        vm.onEmailOrNameChange("")
        vm.onPasswordChange("")

        val esValido = vm.validarLogin()

        assertFalse(esValido)
        val estado = vm.estado.value
        assertNotNull(estado.emailOrName.error)
        assertNotNull(estado.password.error)
    }

    @Test
    fun `validarLogin retorna true y deja errores en null cuando los campos son validos`() {
        val vm = buildVM()

        vm.onEmailOrNameChange("andres@gmail.com")
        vm.onPasswordChange("123456")

        val esValido = vm.validarLogin()

        assertTrue(esValido)
        val estado = vm.estado.value
        assertNull(estado.emailOrName.error)
        assertNull(estado.password.error)
    }

    @Test
    fun `puedeIniciarSesion es false si hay campos vacios o con error`() {
        val vm = buildVM()

        // Solo lleno uno y dejo el otro vacío
        vm.onEmailOrNameChange("andres@gmail.com")
        vm.onPasswordChange("")

        val puede = vm.puedeIniciarSesion()

        assertFalse(puede)
    }

    @Test
    fun `puedeIniciarSesion es true cuando los campos estan llenos y sin errores`() {
        val vm = buildVM()

        vm.onEmailOrNameChange("andres@gmail.com")
        vm.onPasswordChange("123456")

        // Ambos campos válidos
        // Los errores se ponen en null por la validación en onXChange y validarLogin
        vm.validarLogin()

        val puede = vm.puedeIniciarSesion()

        assertTrue(puede)
    }

    // Test que no comprendo
    @Test
    fun `resetLoginEstado vuelve a Idle`() {
        val vm = buildVM()

        vm.resetLoginEstado()

        assertTrue(vm.loginEstado.value is LoginStatus.Idle)
    }


}
