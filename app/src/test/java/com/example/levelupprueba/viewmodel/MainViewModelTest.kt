package com.example.levelupprueba.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.levelupprueba.data.local.getUserSessionFlow
import com.example.levelupprueba.data.repository.UsuarioRepository
import com.example.levelupprueba.model.auth.UserSession
import com.example.levelupprueba.navigation.NavigationEvents
import com.example.levelupprueba.ui.components.GlobalSnackbarState
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.Job

class MainViewModelTest {

    private lateinit var repo: UsuarioRepository
    private lateinit var context: Context
    private lateinit var vm: MainViewModel

    private val dispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(dispatcher)

        repo = mockk(relaxed = true)
        context = mockk(relaxed = true)

        // Mock del archivo top-level correcto
        mockkStatic("com.example.levelupprueba.data.local.UserSessionDataStoreKt")

        // Crear un flow que se complete después del primer valor para evitar corrutinas infinitas
        every { getUserSessionFlow(context) } returns flowOf(
            UserSession(
                displayName = "Test",
                loginAt = 0L,
                userId = "USER123",
                role = "cliente"
            )
        )

        coEvery { repo.getUsuarioById("USER123") } returns mockk {
            every { avatar } returns "avatar.png"
        }

        vm = MainViewModel(repo, context)

        // Espera a que init termine
        dispatcher.scheduler.advanceUntilIdle()
        println(">>> DEBUG: scheduler = ${dispatcher.scheduler}")
        println(">>> DEBUG: active tasks = ${dispatcher.scheduler.runCurrent()}")

        // TODAVIA MEJOR: ver el árbol de jobs del VM
        println(">>> DEBUG: ViewModel job = ${vm.viewModelScope.coroutineContext[Job]}")

        // Debug: verificar jobs activos
        val job = vm.viewModelScope.coroutineContext[Job]
        println(">>> Jobs hijos activos: ${job?.children?.count()}")

        job?.children?.forEach { childJob ->
            println(">>> Job activo: $childJob")
        }
    }

    @AfterEach
    fun tearDown() {
        // Cancela todas las corrutinas del ViewModel para evitar UncompletedCoroutinesError
        vm.viewModelScope.cancel()
        unmockkStatic("com.example.levelupprueba.data.local.UserSessionDataStoreKt")
        Dispatchers.resetMain()
    }

    // ------------------------------
    //       TESTS LÓGICOS
    // ------------------------------

    @Test
    fun `setUserSession actualiza el flujo correctamente`() = runTest {
        // Actualiza sesión en el VM
        val session = UserSession("Ariel", 123L, "ID1", "admin")
        vm.setUserSession(session)

        // Verifica que se actualizó correctamente
        assertEquals(session, vm.userSessionFlow.value)
    }

    @Test
    fun `updateAvatar modifica el avatar del usuario`() {
        // Cambia avatar
        vm.updateAvatar("avatarNuevo.png")

        // Verifica cambio
        assertEquals("avatarNuevo.png", vm.avatar.value)
    }

    @Test
    fun `showSuccessSnackbar actualiza a Success`() {
        // Dispara snackbar de éxito
        vm.showSuccessSnackbar("OK")

        // Debe actualizarse al estado correcto
        assertEquals(GlobalSnackbarState.Success("OK"), vm.globalSnackbarState.value)
    }

    @Test
    fun `showErrorSnackbar actualiza a Error`() {
        vm.showErrorSnackbar("Error grave")

        assertEquals(GlobalSnackbarState.Error("Error grave"), vm.globalSnackbarState.value)
    }

    @Test
    fun `showInfoSnackbar actualiza a Info`() {
        vm.showInfoSnackbar("Info X")

        assertEquals(GlobalSnackbarState.Info("Info X"), vm.globalSnackbarState.value)
    }

    @Test
    fun `clearSnackbar vuelve a Idle`() {
        vm.clearSnackbar()

        assertEquals(GlobalSnackbarState.Idle, vm.globalSnackbarState.value)
    }

    // ------------------------------
    //        TESTS DE NAVEGACIÓN
    // ------------------------------

    @Test
    fun `navigateTo emite evento correcto`() = runTest {
        // Usar first() en lugar de collect para evitar corrutinas colgadas
        var eventoRecibido: NavigationEvents? = null

        val job = launch {
            eventoRecibido = vm.navigationEvent.first()
        }

        vm.navigateTo("home")
        advanceUntilIdle()
        job.join()

        assertEquals(NavigationEvents.NavigateTo("home"), eventoRecibido)
    }

    @Test
    fun `navigateBack emite evento correcto`() = runTest {
        var eventoRecibido: NavigationEvents? = null

        val job = launch {
            eventoRecibido = vm.navigationEvent.first()
        }

        vm.navigateBack()
        advanceUntilIdle()
        job.join()

        assertEquals(NavigationEvents.NavigateBack, eventoRecibido)
    }

    @Test
    fun `navigateUp emite evento correcto`() = runTest {
        var eventoRecibido: NavigationEvents? = null

        val job = launch {
            eventoRecibido = vm.navigationEvent.first()
        }

        vm.navigateUp()
        advanceUntilIdle()
        job.join()

        assertEquals(NavigationEvents.NavigateUp, eventoRecibido)
    }
}
