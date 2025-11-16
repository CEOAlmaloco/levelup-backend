@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.levelupprueba.viewmodel

import android.content.Context
import com.example.levelupprueba.data.local.UserDataStore
import com.example.levelupprueba.data.local.getUserSessionFlow
import com.example.levelupprueba.data.repository.EventoRepository
import com.example.levelupprueba.data.repository.UsuarioRepository
import com.example.levelupprueba.model.evento.CodigoEvento
import com.example.levelupprueba.model.evento.Evento
import com.example.levelupprueba.model.evento.RecompensaCanje
import com.example.levelupprueba.model.usuario.Usuario
import com.example.levelupprueba.model.evento.TipoRecompensa
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*


class EventoViewModelTest {

    private lateinit var repoEvento: EventoRepository
    private lateinit var repoUsuario: UsuarioRepository
    private lateinit var userDataStore: UserDataStore
    private lateinit var context: Context
    private lateinit var vm: EventoViewModel

    private val dispatcher = StandardTestDispatcher()

    // Usuario base reutilizable para todos los tests
    private val usuarioBase = Usuario(
        id = "USER1",
        nombre = "Test",
        apellidos = "User",
        email = "a@a.com",
        password = "123",
        telefono = "999999999",
        fechaNacimiento = "2000-01-01",
        region = "RM",
        comuna = "Santiago",
        direccion = "Calle falsa 123",
        referralCode = "1234",
        role = "cliente",
        avatar = null,
        points = 0,
        redeemedCodes = emptyList()
    )

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(dispatcher)

        // Mock repositorios
        repoEvento = mockk(relaxed = true)
        repoUsuario = mockk(relaxed = true)
        userDataStore = mockk(relaxed = true)
        context = mockk(relaxed = true)

        // Mock de getUserSessionFlow (función top-level)
        mockkStatic("com.example.levelupprueba.data.local.UserSessionDataStoreKt")

        every { getUserSessionFlow(any()) } returns flowOf(
            com.example.levelupprueba.model.auth.UserSession(
                displayName = "Test",
                loginAt = 0L,
                userId = "USER1",
                role = "cliente"
            )
        )

        vm = EventoViewModel(
            repository = repoEvento,
            usuarioRepository = repoUsuario
        )

        // Inyectamos el contexto y datastore manualmente
        vm.inicializar(context)
        vm.userDataStore = userDataStore

        // Avanza el init del ViewModel
        dispatcher.scheduler.advanceUntilIdle()
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
        val recompensasFake = listOf(RecompensaCanje("R1", "Descuento", "desc", 100, tipo = com.example.levelupprueba.model.evento.TipoRecompensa.DESCUENTO))

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

    // 7) Usuario NO logueado
    @Test
    fun `canjearCodigo requiere usuario logueado`() = runTest {
        // 1) No hay usuario logueado
        coEvery { repoUsuario.getUsuarioById(any()) } returns null

        // 2) Código ingresado
        vm.onCodigoChange("LVUP-SANTIAGO-100")

        // 3) Ejecutar
        vm.canjearCodigo()
        advanceUntilIdle()

        // 4) Validar
        assertEquals("Debes iniciar sesión para canjear un código", vm.estado.value.mensajeCodigo)
    }


    // 8) Código inválido
    @Test
    fun `canjearCodigo muestra error cuando el codigo no existe`() = runTest {
        vm.onCodigoChange("CODIGO-FAKE")

        coEvery { repoEvento.validarCodigo(any()) } returns null

        vm.canjearCodigo()
        advanceUntilIdle()

        assertEquals("Código inválido o expirado", vm.estado.value.mensajeCodigo)
    }

    // 9) Código ya canjeado
    @Test
    fun `canjearCodigo muestra error si codigo es invalido`() = runTest {
        // 1) Ingresamos el código
        vm.onCodigoChange("CODIGO-FAKE")

        // 2) Usuario válido
        coEvery { repoUsuario.getUsuarioById("USER1") } returns usuarioBase

        // 3) Mock código inválido
        coEvery { repoEvento.validarCodigo("CODIGO-FAKE") } returns null

        // 4) Ejecutar
        vm.canjearCodigo()
        advanceUntilIdle()

        // 5) Validar
        assertEquals("Código inválido o expirado", vm.estado.value.mensajeCodigo)
    }



    // 10) Código válido — suma puntos y limpia input ESTE ES EL 1
    @Test
    fun `canjearCodigo valida correctamente y actualiza puntos`() = runTest {
        // 1) Ingresamos el código
        vm.onCodigoChange("LVUP-SANTIAGO-100")

        // 2) Usuario inicial con 200 puntos
        val usuario = usuarioBase.copy(points = 200)

        // 3) Mock: obtener usuario
        coEvery { repoUsuario.getUsuarioById("USER1") } returns usuario

        // 4) Mock: updateUsuario solo verificar
        coEvery { repoUsuario.updateUsuario(any()) } just Runs

        // 5) Mock: código válido
        coEvery { repoEvento.validarCodigo("LVUP-SANTIAGO-100") } returns
                CodigoEvento("LVUP-SANTIAGO-100", 100, false)

        // 6) Ejecutamos la función
        vm.canjearCodigo()
        advanceUntilIdle()

        // 7) Estado final
        val estado = vm.estado.value

        // 8) Validar nuevos puntos
        assertEquals(300, estado.puntosUsuario)
        assertEquals("", estado.codigoIngresado)
        assertEquals("Código canjeado: +100 pts", estado.mensajeCodigo)
    }


    // 11) updateUsuario es llamado correctamente este es el 2
    @Test
    fun `canjearCodigo llama updateUsuario con datos correctos`() = runTest {
        // 1) Ingresamos código
        vm.onCodigoChange("LVUP-SANTIAGO-100")

        // 2) Usuario inicial con 50 puntos
        val usuario = usuarioBase.copy(points = 50)

        // 3) Mock usuario actual
        coEvery { repoUsuario.getUsuarioById("USER1") } returns usuario

        // 4) Mock actualizar usuario
        coEvery { repoUsuario.updateUsuario(any()) } just Runs

        // 5) Mock código válido
        coEvery { repoEvento.validarCodigo("LVUP-SANTIAGO-100") } returns
                CodigoEvento("LVUP-SANTIAGO-100", 100, false)

        // 6) Ejecutar
        vm.canjearCodigo()
        advanceUntilIdle()

        // 7) Usuario esperado
        val usuarioEsperado = usuario.copy(
            points = 150,
            redeemedCodes = listOf("LVUP-SANTIAGO-100")
        )

        // 8) Verificar llamada exacta
        coVerify(exactly = 1) { repoUsuario.updateUsuario(usuarioEsperado) }
    }

    // 12) Excepción interna: muestra mensaje de error
    @Test
    fun `canjearCodigo muestra error si ocurre una excepcion`() = runTest {
        // 1) Usuario inicial
        coEvery { repoUsuario.getUsuarioById(any()) } throws RuntimeException("Fallo DB")

        // 2) Código ingresado
        vm.onCodigoChange("LVUP-SANTIAGO-100")

        // 3) Ejecutar
        vm.canjearCodigo()
        advanceUntilIdle()

        // 4) Validar
        assertEquals(true, vm.estado.value.mensajeCodigo.contains("Error al validar código"))
    }

    @Test
    fun `canjearCodigo detecta codigo duplicado`() = runTest {
        // 1) Usuario ya tiene el código
        val usuario = usuarioBase.copy(redeemedCodes = listOf("LVUP-SANTIAGO-100"))

        // 2) Mock usuario
        coEvery { repoUsuario.getUsuarioById("USER1") } returns usuario

        // 3) Mock código válido
        vm.onCodigoChange("LVUP-SANTIAGO-100")
        coEvery { repoEvento.validarCodigo("LVUP-SANTIAGO-100") } returns
                CodigoEvento("LVUP-SANTIAGO-100", 100, false)

        // 4) Ejecutar
        vm.canjearCodigo()
        advanceUntilIdle()

        // 5) Validar
        assertEquals("Este código ya fue canjeado en tu cuenta", vm.estado.value.mensajeCodigo)
    }

    @Test
    fun `refrescarEventos recarga eventos y puntos`() = runTest {
        // 1) Mock usuario
        coEvery { repoUsuario.getUsuarioById(id = "USER1") } returns usuarioBase.copy(points = 10)

        // 2) Mock eventos y recompensas
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

        // 3) Ejecutar
        vm.refrescarEventos()
        advanceUntilIdle()

        // 4) Validar estado final
        val estado = vm.estado.value

        assertEquals(10, estado.puntosUsuario)             // puntos actualizados
        assertEquals(1, estado.eventos.size)               // 1 evento cargado
        assertEquals("Evento Test", estado.eventos.first().titulo)
        assertEquals(1, estado.recompensas.size)
    }


}
