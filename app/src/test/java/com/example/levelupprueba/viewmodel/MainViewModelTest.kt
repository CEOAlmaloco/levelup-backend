package com.example.levelupprueba.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.levelupprueba.data.local.getUserSessionFlow
import com.example.levelupprueba.data.remote.MediaUrlResolver
import com.example.levelupprueba.data.repository.UsuarioRepository
import com.example.levelupprueba.model.auth.UserSession
import com.example.levelupprueba.navigation.NavigationEvents
import com.example.levelupprueba.ui.components.GlobalSnackbarState
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

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

        // Mock MediaUrlResolver to return the raw path, isolating the test
        mockkObject(MediaUrlResolver)
        every { MediaUrlResolver.resolve(any()) } answers { it.invocation.args[0] as? String ?: "" }


        // Mock del archivo top-level correcto
        mockkStatic("com.example.levelupprueba.data.local.UserSessionDataStoreKt")

        // Crear un flow que se complete después del primer valor para evitar corrutinas infinitas
        every { getUserSessionFlow(context) } returns flowOf(
            UserSession(
                displayName = "Test",
                loginAt = 0L,
                userId = 123L,
                role = "cliente",
                accessToken = "test_access_token",
                refreshToken = "test_refresh_token",
                expiresIn = 3600L,
                email = "test@example.com",
                nombre = "Test",
                apellidos = "User",
                tipoUsuario = "cliente"
            )
        )

        coEvery { repo.getUsuarioById("123") } returns mockk {
            every { avatar } returns "avatar.png"
        }

        vm = MainViewModel(repo, context)

        // Espera a que init termine
        dispatcher.scheduler.advanceUntilIdle()
    }

    @AfterEach
    fun tearDown() {
        // Cancela todas las corrutinas del ViewModel para evitar UncompletedCoroutinesError
        vm.viewModelScope.cancel()
        unmockkStatic("com.example.levelupprueba.data.local.UserSessionDataStoreKt")
        unmockkObject(MediaUrlResolver)
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
            userId = 1L,
            role = "admin",
            accessToken = "ariel_access_token",
            refreshToken = "ariel_refresh_token",
            expiresIn = 3600L,
            email = "ariel@example.com",
            nombre = "Ariel",
            apellidos = "Prueba",
            tipoUsuario = "admin"
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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `navigateTo emite evento correcto`(): TestResult = runTest {
        var eventoRecibido: NavigationEvents? = null
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            eventoRecibido = vm.navigationEvent.first()
        }

        vm.navigateTo("home")

        assertEquals(NavigationEvents.NavigateTo("home"), eventoRecibido)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `navigateBack emite evento correcto`(): TestResult = runTest {
        var eventoRecibido: NavigationEvents? = null
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            eventoRecibido = vm.navigationEvent.first()
        }
        vm.navigateBack()

        assertEquals(NavigationEvents.NavigateBack, eventoRecibido)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `navigateUp emite evento correcto`(): TestResult = runTest {
        var eventoRecibido: NavigationEvents? = null
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            eventoRecibido = vm.navigationEvent.first()
        }

        vm.navigateUp()

        assertEquals(NavigationEvents.NavigateUp, eventoRecibido)
    }
}