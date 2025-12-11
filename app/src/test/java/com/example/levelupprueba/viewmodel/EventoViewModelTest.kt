@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.levelupprueba.viewmodel

import android.content.Context
import com.example.levelupprueba.data.local.getUserSessionFlow
import com.example.levelupprueba.data.repository.EventoRepositoryRemote
import com.example.levelupprueba.model.evento.CodigoEvento
import com.example.levelupprueba.model.evento.Evento
import com.example.levelupprueba.model.evento.RecompensaCanje
import com.example.levelupprueba.model.evento.TipoRecompensa
import com.example.levelupprueba.model.auth.UserSession
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

class EventoViewModelTest {

    private lateinit var repoEvento: EventoRepositoryRemote
    private lateinit var vm: EventoViewModel

    private val dispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(dispatcher)

        // Mock repositorio
        repoEvento = mockk(relaxed = true)

        // Mock de getUserSessionFlow (función top-level)
        mockkStatic("com.example.levelupprueba.data.local.UserSessionDataStoreKt")

        every { getUserSessionFlow(any()) } returns flowOf(
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

        vm = EventoViewModel(repository = repoEvento)

        // Inyectamos el contexto manualmente
        val context: Context = mockk(relaxed = true)
        vm.inicializar(context)

        // Avanza el init del ViewModel
        advanceUntilIdle()
    }

    @AfterEach
    fun tearDown() {
        unmockkStatic("com.example.levelupprueba.data.local.UserSessionDataStoreKt")
        Dispatchers.resetMain()
    }

    // TEST 1: cargarDatosIniciales() — ÉXITO
    @Test
    fun `cargarDatosIniciales carga eventos y recompensas correctamente`() = runTest {
        // Datos mock
        val eventosFake = listOf(Evento(id = "1"))
        val recompensasFake = listOf(RecompensaCanje("R1", "Descuento", "desc", 100, tipo = TipoRecompensa.DESCUENTO))

        coEvery { repoEvento.obtenerEventos() } returns eventosFake
        coEvery { repoEvento.obtenerRecompensas() } returns recompensasFake

        vm.refrescarEventos()

        advanceUntilIdle()

        val estado = vm.estado.value
        assertEquals(eventosFake, estado.eventos)
        assertEquals(recompensasFake, estado.recompensas)
        assertEquals(eventosFake.first(), estado.eventoSeleccionado)
        assertFalse(estado.isLoading)
    }

    // TEST 2: cargarDatosIniciales() — ERROR
    @Test
    fun `cargarDatosIniciales maneja excepciones correctamente`() = runTest {
        coEvery { repoEvento.obtenerEventos() } throws RuntimeException("Falla total")

        vm.refrescarEventos()
        advanceUntilIdle()

        val estado = vm.estado.value
        assertTrue(estado.error!!.contains("Error al cargar"))
        assertFalse(estado.isLoading)
    }

    // TEST 3: seleccionarEvento()
    @Test
    fun `seleccionarEvento actualiza el estado con el evento seleccionado`() {
        val evento = Evento(id = "7", titulo = "Game Event")
        vm.seleccionarEvento(evento)

        assertEquals(evento, vm.estado.value.eventoSeleccionado)
    }

    // TEST 4: onCodigoChange()
    @Test
    fun `onCodigoChange limpia mensaje y guarda codigo en mayusculas`() {
        vm.onCodigoChange("abc123")
        val estado = vm.estado.value

        assertEquals("ABC123", estado.codigoIngresado)
        assertEquals("", estado.mensajeCodigo)
    }

    // TEST 5: limpiarMensajeCodigo()
    @Test
    fun `limpiarMensajeCodigo limpia el mensaje correctamente`() {
        vm.onCodigoChange("TEST")
        vm.limpiarMensajeCodigo()

        assertEquals("", vm.estado.value.mensajeCodigo)
    }

    // Tests para canjearCodigo()
    // 6) Código vacío
    @Test
    fun `canjearCodigo con codigo vacio muestra mensaje de validacion`() {
        vm.onCodigoChange("   ")
        vm.canjearCodigo()

        val estado = vm.estado.value
        assertEquals("Ingresa un código válido", estado.mensajeCodigo)
    }

    // Nota: Los tests de canjearCodigo que requieren usuario logueado y validación de código
    // son más complejos porque requieren mockear ApiConfig.referidosService y ApiConfig.usuarioService.
    // Por ahora, solo verificamos que los métodos existen y no lanzan excepciones inmediatas.
    
    @Test
    fun `refrescarEventos recarga eventos y puntos`() = runTest {
        // Mock eventos y recompensas
        val mockEventos = listOf(
            Evento(
                id = "1",
                titulo = "Evento Test",
                lugar = "Lugar X",
                direccion = "Calle 123",
                ciudad = "Santiago",
                fecha = "2025-01-01",
                hora = "10:00",
                puntos = 50,
                latitud = -33.4,
                longitud = -70.6,
                descripcion = "Evento de prueba"
            )
        )

        val mockRecompensas = listOf(
            RecompensaCanje(
                id = "1",
                titulo = "Gift test",
                descripcion = "Prueba",
                costo = 100,
                tipo = TipoRecompensa.DESCUENTO
            )
        )

        coEvery { repoEvento.obtenerEventos() } returns mockEventos
        coEvery { repoEvento.obtenerRecompensas() } returns mockRecompensas

        // Ejecutar
        vm.refrescarEventos()
        advanceUntilIdle()

        // Validar estado final
        val estado = vm.estado.value

        assertEquals(1, estado.eventos.size)               // 1 evento cargado
        assertEquals("Evento Test", estado.eventos.first().titulo)
        assertEquals(1, estado.recompensas.size)
    }
}

