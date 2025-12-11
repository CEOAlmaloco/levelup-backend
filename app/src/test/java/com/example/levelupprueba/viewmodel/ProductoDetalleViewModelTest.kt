package com.example.levelupprueba.viewmodel

import com.example.levelupprueba.data.repository.ProductoRepository
import com.example.levelupprueba.model.producto.Producto
import com.example.levelupprueba.model.producto.Review
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductoDetalleViewModelTest {

    private fun base(): Producto = Producto(
        id = "PX", nombre = "Producto X", descripcion = "",
        precio = 100000.0, imagenUrl = "img",
        categoria = com.example.levelupprueba.model.producto.Categoria.CONSOLA,
        subcategoria = com.example.levelupprueba.model.producto.Subcategoria.MANDOS,
        rating = 4.5f, disponible = true, destacado = false, stock = 5
    )

    @Test
    fun `cargarProducto exitoso compone detalle con reviews y relacionados`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val repo = mockk<ProductoRepository>()
        val prod = base()

        // LLamamos al repositorio para producto base (suspend)
        coEvery { repo.obtenerProductoPorId("PX") } returns prod

        // LLamamos al repositorio para reviews (suspend) usando mocks relajados
        val r1 = mockk<Review>(relaxed = true)
        val r2 = mockk<Review>(relaxed = true)
        coEvery { repo.obtenerReviews("PX") } returns listOf(r1, r2)

        // LLamamos al repositorio para relacionados (suspend)
        coEvery { repo.obtenerProductosRelacionados(prod) } returns listOf(prod.copy(id = "PY"))

        val vm = ProductoDetalleViewModel(repo)

        // LLamamos a cargar
        vm.cargarProducto("PX")
        advanceUntilIdle()

        val st = vm.estado.value
        assertFalse(st.isLoading)
        assertNull(st.error)
        assertNotNull(st.producto)
        assertEquals("PX", st.producto!!.id)
        // relacionados presentes
        assertTrue(st.producto!!.productosRelacionados.any { it.id == "PY" })
        // reviews presentes
        assertTrue(st.producto!!.reviews.isNotEmpty())

        Dispatchers.resetMain()
    }


    @Test
    fun `cargarProducto con error expone mensaje y deja producto null`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val repo = mockk<ProductoRepository>()
        // LLamamos al repositorio y falla (suspend)
        coEvery { repo.obtenerProductoPorId("BAD") } throws RuntimeException("fallo detalle")

        val vm = ProductoDetalleViewModel(repo)

        // LLamamos a cargar
        vm.cargarProducto("BAD")
        advanceUntilIdle()

        val st = vm.estado.value
        assertNull(st.producto)
        // Mensaje envuelto → validamos contenido en vez de igualdad exacta
        assertNotNull(st.error)
        assertTrue(st.error!!.contains("fallo detalle"))
        assertTrue(st.error!!.startsWith("Error al cargar el producto"))
        assertFalse(st.isLoading)

        Dispatchers.resetMain()
    }

    @Test
    fun `cargarProducto sin reviews ni relacionados funciona sin error`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val repo = mockk<ProductoRepository>()
        val prod = base()

        // LLamamos al repositorio para producto base
        coEvery { repo.obtenerProductoPorId("PX") } returns prod
        // LLamamos al repositorio con listas vacías
        coEvery { repo.obtenerReviews("PX") } returns emptyList()
        coEvery { repo.obtenerProductosRelacionados(prod) } returns emptyList()

        val vm = ProductoDetalleViewModel(repo)

        // Disparamos la carga
        vm.cargarProducto("PX")
        advanceUntilIdle()

        val st = vm.estado.value
        assertFalse(st.isLoading)
        assertNull(st.error)
        assertNotNull(st.producto)
        assertTrue(st.producto!!.reviews.isEmpty())
        assertTrue(st.producto!!.productosRelacionados.isEmpty())

        Dispatchers.resetMain()
    }

}
