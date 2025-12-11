package com.example.levelupprueba.viewmodel

import android.util.Log
import com.example.levelupprueba.data.repository.CarritoRepository
import com.example.levelupprueba.data.repository.ProductoRepository
import com.example.levelupprueba.model.carrito.Carrito
import com.example.levelupprueba.model.carrito.CarritoItem
import com.example.levelupprueba.model.producto.Producto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CarritoViewModelTest {

    // Producto base para armar carritos de prueba
    private lateinit var baseProducto: Producto

    @BeforeEach
    fun setUp() = runTest {
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.w(any(), any<String>()) } returns 0
        every { Log.e(any(), any(), any()) } returns 0
        baseProducto = ProductoRepository().obtenerProductos().first()
    }

    @AfterEach
    fun tearDown() {
        unmockkStatic(Log::class)
        Dispatchers.resetMain()
    }

    @Test
    fun `init carga carrito desde el repositorio`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val repo = mockk<CarritoRepository>()
        val carritoSimulado = Carrito(
            items = listOf(
                CarritoItem(
                    id = "ITEM1",
                    producto = baseProducto,
                    cantidad = 2
                )
            )
        )
        coEvery { repo.getCarrito() } returns carritoSimulado

        val vm = CarritoViewModel(repo)

        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(carritoSimulado, vm.carrito.value)
        assertFalse(vm.loading.value)
        assertNull(vm.error.value)
    }

    @Test
    fun `onAgregar actualiza carrito y limpia error`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val repo = mockk<CarritoRepository>()

        // Carrito inicial vacío (para el init)
        coEvery { repo.getCarrito() } returns Carrito()

        val carritoDespuesAgregar = Carrito(
            items = listOf(
                CarritoItem(
                    id = "ITEM1",
                    producto = baseProducto,
                    cantidad = 1
                )
            )
        )
        coEvery { repo.agregarProducto(baseProducto, 1) } returns carritoDespuesAgregar

        val vm = CarritoViewModel(repo)
        dispatcher.scheduler.advanceUntilIdle()

        vm.onAgregar(baseProducto)        // cantidad por defecto = 1
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(carritoDespuesAgregar, vm.carrito.value)
        assertNull(vm.error.value)
        assertFalse(vm.loading.value)
    }

    @Test
    fun `onIncrement usa actualizarCantidad con delta positivo`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val repo = mockk<CarritoRepository>()

        coEvery { repo.getCarrito() } returns Carrito()
        coEvery { repo.actualizarCantidad("ITEM1", 1) } returns Carrito()

        val vm = CarritoViewModel(repo)
        dispatcher.scheduler.advanceUntilIdle()

        vm.onIncrement("ITEM1")
        dispatcher.scheduler.advanceUntilIdle()

        coVerify { repo.actualizarCantidad("ITEM1", 1) }
    }

    @Test
    fun `onCheckout captura excepcion y expone mensaje de error`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val repo = mockk<CarritoRepository>()

        coEvery { repo.getCarrito() } returns Carrito()
        coEvery { repo.checkout() } throws RuntimeException("Fallo backend")

        val vm = CarritoViewModel(repo)
        dispatcher.scheduler.advanceUntilIdle()

        vm.onCheckout()
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals("Fallo backend", vm.error.value)
        assertFalse(vm.loading.value)
    }
    // NUEVOS
    @Test
    fun `onEliminar elimina item y deja carrito vacio`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val repo = mockk<CarritoRepository>()
        val carritoInicial = Carrito(
            items = listOf(
                CarritoItem(
                    id = "ITEM1",
                    producto = baseProducto,
                    cantidad = 1
                )
            )
        )
        // Carrito inicial con 1 ítem para el init
        coEvery { repo.getCarrito() } returns carritoInicial
        // LLamamos al repositorio para eliminar y devolver vacío
        coEvery { repo.eliminarItem("ITEM1") } returns Carrito()

        val vm = CarritoViewModel(repo)
        dispatcher.scheduler.advanceUntilIdle()

        // LLamamos al repositorio
        vm.onEliminar("ITEM1")
        dispatcher.scheduler.advanceUntilIdle()

        assertTrue(vm.carrito.value.items.isEmpty())
        assertNull(vm.error.value)
        assertFalse(vm.loading.value)
        coVerify { repo.eliminarItem("ITEM1") }
    }

    @Test
    fun `onDecrement usa actualizarCantidad con delta negativo y reduce cantidad`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val repo = mockk<CarritoRepository>()
        val conDos = Carrito(
            items = listOf(
                CarritoItem(
                    id = "ITEM1",
                    producto = baseProducto,
                    cantidad = 2
                )
            )
        )
        val conUno = conDos.copy(items = listOf(conDos.items.first().copy(cantidad = 1)))

        // Carrito inicial (para el init) con cantidad 2
        coEvery { repo.getCarrito() } returns conDos
        // LLamamos al repositorio con -1 y devuelve cantidad 1
        coEvery { repo.actualizarCantidad("ITEM1", -1) } returns conUno

        val vm = CarritoViewModel(repo)
        dispatcher.scheduler.advanceUntilIdle()

        // LLamamos al repositorio
        vm.onDecrement("ITEM1")
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(1, vm.carrito.value.items.first().cantidad)
        assertNull(vm.error.value)
        assertFalse(vm.loading.value)
        coVerify { repo.actualizarCantidad("ITEM1", -1) }
    }

    @Test
    fun `onAgregar dos veces mismo producto acumula cantidades`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val repo = mockk<CarritoRepository>()
        // init vacío
        coEvery { repo.getCarrito() } returns Carrito()

        val unaUnidad = Carrito(
            items = listOf(
                CarritoItem(id = "ITEM1", producto = baseProducto, cantidad = 1)
            )
        )
        val dosUnidades = unaUnidad.copy(
            items = listOf(unaUnidad.items.first().copy(cantidad = 2))
        )

        // LLamadas consecutivas a agregar → 1 y luego 2
        coEvery { repo.agregarProducto(baseProducto, 1) } returnsMany listOf(
            unaUnidad,
            dosUnidades
        )

        val vm = CarritoViewModel(repo)
        dispatcher.scheduler.advanceUntilIdle()

        // LLamamos al repositorio (1ª vez)
        vm.onAgregar(baseProducto)
        dispatcher.scheduler.advanceUntilIdle()
        assertEquals(1, vm.carrito.value.items.sumOf { it.cantidad })

        // LLamamos al repositorio (2ª vez)
        vm.onAgregar(baseProducto)
        dispatcher.scheduler.advanceUntilIdle()
        assertEquals(2, vm.carrito.value.items.sumOf { it.cantidad })

        assertNull(vm.error.value)
        assertFalse(vm.loading.value)
        coVerify(exactly = 2) { repo.agregarProducto(baseProducto, 1) }
    }

    @Test
    fun `onCheckout exitoso limpia carrito`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val repo = mockk<CarritoRepository>()
        val carritoInicial = Carrito(
            items = listOf(
                CarritoItem(
                    id = "ITEM1",
                    producto = baseProducto,
                    cantidad = 2
                )
            )
        )

        // init con ítem
        coEvery { repo.getCarrito() } returns carritoInicial
        // LLamamos al repositorio para checkout
        coEvery { repo.checkout() } returns Carrito()

        val vm = CarritoViewModel(repo)
        dispatcher.scheduler.advanceUntilIdle()

        // LLamamos al repositorio
        vm.onCheckout()
        dispatcher.scheduler.advanceUntilIdle()

        assertTrue(vm.carrito.value.items.isEmpty())
        assertNull(vm.error.value)
        assertFalse(vm.loading.value)
        coVerify { repo.checkout() }
    }

    @Test
    fun `onDecrement desde 1 elimina item (regla 0 elimina)`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val repo = mockk<CarritoRepository>()
        val carritoConUno = Carrito(
            items = listOf(
                CarritoItem(
                    id = "ITEM1",
                    producto = baseProducto,
                    cantidad = 1
                )
            )
        )

        // init con 1 unidad
        coEvery { repo.getCarrito() } returns carritoConUno
        // LLamamos al repositorio con delta -1 → repo aplica regla y devuelve carrito vacío
        coEvery { repo.actualizarCantidad("ITEM1", -1) } returns Carrito()

        val vm = CarritoViewModel(repo)
        dispatcher.scheduler.advanceUntilIdle()

        // LLamamos al repositorio
        vm.onDecrement("ITEM1")
        dispatcher.scheduler.advanceUntilIdle()

        assertTrue(vm.carrito.value.items.isEmpty())
        assertNull(vm.error.value)
        assertFalse(vm.loading.value)
        coVerify { repo.actualizarCantidad("ITEM1", -1) }
    }

    @Test
    fun `onIncrement sube cantidad y actualiza estado a 2`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val repo = mockk<CarritoRepository>()
        val conUno = Carrito(items = listOf(
            CarritoItem(id = "ITEM1", producto = baseProducto, cantidad = 1)
        ))
        val conDos = conUno.copy(items = listOf(conUno.items.first().copy(cantidad = 2)))

        // init con 1 unidad
        coEvery { repo.getCarrito() } returns conUno
        // LLamamos al repositorio con +1 y devuelve 2
        coEvery { repo.actualizarCantidad("ITEM1", 1) } returns conDos

        val vm = CarritoViewModel(repo)
        dispatcher.scheduler.advanceUntilIdle()

        // LLamamos al repositorio
        vm.onIncrement("ITEM1")
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(2, vm.carrito.value.items.first().cantidad)
        assertNull(vm.error.value)
        assertFalse(vm.loading.value)
        coVerify { repo.actualizarCantidad("ITEM1", 1) }
    }

    @Test
    fun `onEliminar captura excepcion y mantiene carrito`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val repo = mockk<CarritoRepository>()
        val carritoInicial = Carrito(items = listOf(
            CarritoItem(id = "ITEM1", producto = baseProducto, cantidad = 1)
        ))

        // init con 1 item
        coEvery { repo.getCarrito() } returns carritoInicial
        // LLamamos al repositorio y falla
        coEvery { repo.eliminarItem("ITEM1") } throws RuntimeException("fallo eliminar")

        val vm = CarritoViewModel(repo)
        dispatcher.scheduler.advanceUntilIdle()

        // LLamamos al repositorio
        vm.onEliminar("ITEM1")
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(carritoInicial, vm.carrito.value)
        assertEquals("fallo eliminar", vm.error.value)
        assertFalse(vm.loading.value)
        coVerify { repo.eliminarItem("ITEM1") }
    }

    @Test
    fun `onIncrement con error mantiene carrito y expone mensaje`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val repo = mockk<CarritoRepository>()
        val inicial = Carrito(items = listOf(
            CarritoItem(id = "ITEM1", producto = baseProducto, cantidad = 1)
        ))

        // init con 1 item
        coEvery { repo.getCarrito() } returns inicial
        // LLamamos al repositorio y falla
        coEvery { repo.actualizarCantidad("ITEM1", 1) } throws RuntimeException("fallo incrementar")

        val vm = CarritoViewModel(repo)
        dispatcher.scheduler.advanceUntilIdle()

        // LLamamos al repositorio
        vm.onIncrement("ITEM1")
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(inicial, vm.carrito.value)
        assertEquals("fallo incrementar", vm.error.value)
        assertFalse(vm.loading.value)
        coVerify { repo.actualizarCantidad("ITEM1", 1) }
    }

    @Test
    fun `onIncrement no supera stock mantiene cantidad y expone error`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val repo = mockk<CarritoRepository>()
        // Producto con stock 2
        val prod = baseProducto.copy(stock = 2)
        // Carrito ya en el tope (2 unidades)
        val tope = Carrito(items = listOf(
            CarritoItem(id = "ITEM1", producto = prod, cantidad = 2)
        ))

        // init: carrito en tope
        coEvery { repo.getCarrito() } returns tope
        // LLamamos al repositorio y responde error de stock
        coEvery { repo.actualizarCantidad("ITEM1", 1) } throws RuntimeException("sin stock disponible")

        val vm = CarritoViewModel(repo)
        dispatcher.scheduler.advanceUntilIdle()

        // Intentamos +1
        vm.onIncrement("ITEM1")
        dispatcher.scheduler.advanceUntilIdle()

        // Mantiene cantidad en 2
        assertEquals(2, vm.carrito.value.items.first().cantidad)
        // Mensaje menciona stock (texto flexible para no acoplar)
        assertTrue(vm.error.value?.contains("stock", ignoreCase = true) == true)
        assertFalse(vm.loading.value)
    }

    @Test
    fun `si repo reporta stock agotado al actualizar elimina item (sin error)`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val repo = mockk<CarritoRepository>()
        val prod = baseProducto.copy(stock = 1)
        val conUno = Carrito(
            items = listOf(
                CarritoItem(id = "ITEM1", producto = prod, cantidad = 1)
            )
        )

        // init: 1 unidad
        coEvery { repo.getCarrito() } returns conUno
        // (+1) → repo devuelve carrito vacío (simula sin stock tras refresh)
        coEvery { repo.actualizarCantidad("ITEM1", 1) } returns Carrito()

        val vm = CarritoViewModel(repo)
        dispatcher.scheduler.advanceUntilIdle()

        // Intentamos +1 (repo responde vacío)
        vm.onIncrement("ITEM1")
        dispatcher.scheduler.advanceUntilIdle()

        // Ítem eliminado y sin mensaje de error
        assertTrue(vm.carrito.value.items.isEmpty())
        assertNull(vm.error.value)
        assertFalse(vm.loading.value)
    }



    @Test
    fun `subtotal vs total con descuentos se calcula correctamente`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val repo = mockk<CarritoRepository>()

        // P1: 2 x 10.000 = 20.000 (sin descuento)
        val p1 = baseProducto.copy(id = "P1", nombre = "Sin desc", precio = 10_000.0, descuento = null)
        // P2: 1 x 40.000 con 25% OFF = 30.000
        val p2 = baseProducto.copy(id = "P2", nombre = "Con 25%", precio = 40_000.0, descuento = 25)

        val carrito = Carrito(
            items = listOf(
                CarritoItem(id = "I1", producto = p1, cantidad = 2),
                CarritoItem(id = "I2", producto = p2, cantidad = 1)
            )
        )
        // init: ese carrito
        coEvery { repo.getCarrito() } returns carrito

        val vm = CarritoViewModel(repo)
        dispatcher.scheduler.advanceUntilIdle()

        // Cálculo esperado
        val subtotalEsperado = 2 * 10_000.0 + 1 * 40_000.0              // 60.000
        val totalEsperado = 2 * 10_000.0 + 1 * (40_000.0 * 0.75)         // 50.000

        // Derivamos del estado (regla de negocio) para validar:
        val subtotalVM = vm.carrito.value.items.sumOf { it.cantidad * it.producto.precio }
        val totalVM = vm.carrito.value.items.sumOf { item ->
            val d = item.producto.descuento ?: 0
            val factor = 1.0 - d / 100.0
            item.cantidad * item.producto.precio * factor
        }

        assertEquals(subtotalEsperado, subtotalVM, 0.01)
        assertEquals(totalEsperado, totalVM, 0.01)
    }

    @Test
    fun `init llama getCarrito solo una vez`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val repo = mockk<CarritoRepository>()
        coEvery { repo.getCarrito() } returns Carrito()

        val vm = CarritoViewModel(repo)
        // Avanzamos varias veces para asegurar que no hay otras cargas
        dispatcher.scheduler.advanceUntilIdle()
        dispatcher.scheduler.advanceUntilIdle()

        io.mockk.coVerify(exactly = 1) { repo.getCarrito() }
        assertFalse(vm.loading.value)
    }

    @Test
    fun `onAgregar actualiza carrito en exito (loading queda false)`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val repo = mockk<CarritoRepository>()
        // init vacío
        coEvery { repo.getCarrito() } returns Carrito()

        val despues = Carrito(items = listOf(
            CarritoItem(id = "I1", producto = baseProducto, cantidad = 1)
        ))
        // LLamamos al repositorio (simulamos demora)
        coEvery { repo.agregarProducto(baseProducto, 1) } coAnswers {
            kotlinx.coroutines.delay(100)
            despues
        }

        val vm = CarritoViewModel(repo)
        dispatcher.scheduler.advanceUntilIdle()

        // LLamamos al repositorio
        vm.onAgregar(baseProducto)

        // Avanzamos hasta completar
        dispatcher.scheduler.advanceUntilIdle()

        // Estado final: carrito actualizado, sin error, loading false
        assertEquals(despues, vm.carrito.value)
        assertNull(vm.error.value)
        assertFalse(vm.loading.value)
    }

    @Test
    fun `onIncrement en error mantiene carrito, setea mensaje y deja loading false`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val repo = mockk<CarritoRepository>()
        val conUno = Carrito(items = listOf(
            CarritoItem(id = "ITEM1", producto = baseProducto, cantidad = 1)
        ))
        coEvery { repo.getCarrito() } returns conUno
        coEvery { repo.actualizarCantidad("ITEM1", 1) } coAnswers {
            kotlinx.coroutines.delay(100)
            throw RuntimeException("fallo incrementar")
        }

        val vm = CarritoViewModel(repo)
        dispatcher.scheduler.advanceUntilIdle()

        // Ejecutar acción
        vm.onIncrement("ITEM1")
        dispatcher.scheduler.advanceUntilIdle()

        // Estado final esperado
        assertEquals(conUno, vm.carrito.value)              // carrito no cambia
        assertEquals("fallo incrementar", vm.error.value)   // error seteado
        assertFalse(vm.loading.value)                       // loading en false
    }

}
