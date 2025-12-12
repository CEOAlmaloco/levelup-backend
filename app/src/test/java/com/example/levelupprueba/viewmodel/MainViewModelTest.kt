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
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private lateinit var context: Context
    private lateinit var vm: MainViewModel

    private val dispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setup() = runTest {
        Dispatchers.setMain(dispatcher)

        // Mock Log antes de crear el context (incluyendo todas las variantes)
        mockkStatic(android.util.Log::class)
        every { android.util.Log.d(any(), any()) } returns 0
        every { android.util.Log.d(any(), any(), any()) } returns 0
        every { android.util.Log.e(any(), any(), any()) } returns 0
        every { android.util.Log.e(any(), any()) } returns 0
        every { android.util.Log.w(any(), any<String>()) } returns 0
        every { android.util.Log.w(any(), any(), any()) } returns 0
        every { android.util.Log.i(any(), any()) } returns 0
        every { android.util.Log.v(any(), any()) } returns 0

        context = mockk(relaxed = true)
        
        // Configurar filesDir para DataStore
        val filesDir = java.io.File(System.getProperty("java.io.tmpdir"), "test_files")
        filesDir.mkdirs()
        every { context.filesDir } returns filesDir
        every { context.applicationContext } returns context

        // Mock del archivo top-level correcto
        mockkStatic("com.example.levelupprueba.data.local.UserSessionDataStoreKt")

        // Crear un flow que se complete después del primer valor para evitar corrutinas infinitas
        every { getUserSessionFlow(context) } returns flowOf(
            UserSession(
                displayName = "Test",
                loginAt = 0L,
                userId = 1,
                role = "CLIENTE",
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
        unmockkStatic("com.example.levelupprueba.data.local.UserSessionDataStoreKt")
        unmockkStatic(android.util.Log::class)
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
            role = "ADMINISTRADOR",
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

        // Verifica cambio (MediaUrlResolver puede transformar la URL, así que verificamos que no es null y contiene el nombre)
        assertNotNull(vm.avatar.value, "El avatar debe actualizarse")
        assertTrue(
            vm.avatar.value?.contains("avatarNuevo") == true || 
            vm.avatar.value == "avatarNuevo.png",
            "El avatar debe contener 'avatarNuevo' o ser exactamente 'avatarNuevo.png'"
        )
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

