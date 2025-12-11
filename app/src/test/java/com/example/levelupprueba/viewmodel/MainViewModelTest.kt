package com.example.levelupprueba.viewmodel

import android.content.Context
import com.example.levelupprueba.data.local.getUserSessionFlow
import com.example.levelupprueba.model.auth.UserSession
import com.example.levelupprueba.navigation.NavigationEvents
import com.example.levelupprueba.ui.components.GlobalSnackbarState
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private lateinit var context: Context
    private lateinit var vm: MainViewModel

    private val dispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(dispatcher)

        context = mockk(relaxed = true)

        // Mock del archivo top-level correcto
        mockkStatic("com.example.levelupprueba.data.local.UserSessionDataStoreKt")

        // Crear un flow que se complete después del primer valor para evitar corrutinas infinitas
        every { getUserSessionFlow(context) } returns flowOf(
            UserSession(
                displayName = "Test",
                loginAt = 0L,
                userId = 1,
                tipoUsuario = "CLIENTE",
                accessToken = "token",
                refreshToken = "refresh",
                expiresIn = 1800,
                email = "test@test.com",
                nombre = "Test",
                apellidos = "User"
            )
        )

        vm = MainViewModel(context)

        // Espera a que init termine
        advanceUntilIdle()
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
        val session = UserSession(
            displayName = "Ariel",
            loginAt = 123L,
            userId = 1,
            tipoUsuario = "ADMINISTRADOR",
            accessToken = "token",
            refreshToken = "refresh",
            expiresIn = 1800,
            email = "ariel@test.com",
            nombre = "Ariel",
            apellidos = "Dev"
        )
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
        var eventoRecibido: NavigationEvents? = null
        val job = launch(UnconfinedTestDispatcher()) {
            eventoRecibido = vm.navigationEvent.first()
        }

        vm.navigateTo("home")
        advanceUntilIdle()

        assertEquals(NavigationEvents.NavigateTo("home"), eventoRecibido)
        job.cancel()
    }

    @Test
    fun `navigateBack emite evento correcto`() = runTest {
        var eventoRecibido: NavigationEvents? = null
        val job = launch(UnconfinedTestDispatcher()) {
            eventoRecibido = vm.navigationEvent.first()
        }
        vm.navigateBack()
        advanceUntilIdle()

        assertEquals(NavigationEvents.NavigateBack, eventoRecibido)
        job.cancel()
    }

    @Test
    fun `navigateUp emite evento correcto`() = runTest {
        var eventoRecibido: NavigationEvents? = null
        val job = launch(UnconfinedTestDispatcher()) {
            eventoRecibido = vm.navigationEvent.first()
        }

        vm.navigateUp()
        advanceUntilIdle()

        assertEquals(NavigationEvents.NavigateUp, eventoRecibido)
        job.cancel()
    }
}

