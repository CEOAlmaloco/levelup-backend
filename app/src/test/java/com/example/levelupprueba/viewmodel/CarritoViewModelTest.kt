package com.example.levelupprueba.viewmodel

import com.example.levelupprueba.data.repository.CarritoRepository
import com.example.levelupprueba.data.repository.ProductoRepository
import com.example.levelupprueba.model.carrito.Carrito
import com.example.levelupprueba.model.carrito.CarritoItem
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CarritoViewModelTest {

    // Producto base para armar carritos de prueba
    private val baseProducto = ProductoRepository().obtenerProductos().first()

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
        // Para el init { loadCarrito() }
        coEvery { repo.getCarrito() } returns carritoSimulado

        val vm = CarritoViewModel(repo)

        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(carritoSimulado, vm.carrito.value)
        assertFalse(vm.loading.value)
        assertNull(vm.error.value)

        Dispatchers.resetMain()
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

        Dispatchers.resetMain()
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

        Dispatchers.resetMain()
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

        Dispatchers.resetMain()
    }
}
