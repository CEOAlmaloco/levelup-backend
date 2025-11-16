package com.example.levelupprueba.viewmodel

import com.example.levelupprueba.data.repository.ProductoRepository
import com.example.levelupprueba.model.producto.Categoria
import com.example.levelupprueba.model.producto.ImagenCarrusel
import com.example.levelupprueba.model.producto.Producto
import com.example.levelupprueba.model.producto.Subcategoria
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
class ProductoViewModelTest {

    // Dataset mínimo controlado para filtros/orden
    private fun productosFake(): List<Producto> = listOf(
        // destacado y disponible, CONSOLA / MANDOS, precio medio, sin descuento
        Producto(
            id = "P1", nombre = "Control Pro", descripcion = "",
            precio = 50000.0, imagenUrl = "img1",
            categoria = Categoria.CONSOLA, subcategoria = Subcategoria.MANDOS,
            rating = 4.8f, disponible = true, destacado = true, stock = 10
        ),
        // no destacado, CONSOLA / CONSOLAS, precio alto, con descuento 20%
        Producto(
            id = "P2", nombre = "PlayBox X", descripcion = "",
            precio = 300000.0, imagenUrl = "img2",
            categoria = Categoria.CONSOLA, subcategoria = Subcategoria.MANDOS,
            rating = 4.4f, disponible = true, destacado = false, stock = 5,
            descuento = 20
        ),
        // PERIFÉRICOS / TECLADOS, precio bajo, no disponible
        Producto(
            id = "P3", nombre = "Teclado Mecanico", descripcion = "",
            precio = 19990.0, imagenUrl = "img3",
            categoria = Categoria.PERIFERICOS, subcategoria = Subcategoria.TECLADOS,
            rating = 4.0f, disponible = false, destacado = false, stock = 0
        )
    )

    @Test
    fun `init carga productos, destacados e imagenes carrusel`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val repo = mockk<ProductoRepository>()

        // LLamamos al repositorio para productos
        val data = productosFake()
        every { repo.obtenerProductos() } returns data
        // LLamamos al repositorio para destacados (¡faltaba este stub!)
        every { repo.obtenerProductosDestacados() } returns data.filter { it.destacado }
        // LLamamos al repositorio para carrusel
        every { repo.obtenerImagenesCarrusel() } returns listOf(
            ImagenCarrusel(1, "banner1", "Promo", "Descuentos"),
            ImagenCarrusel(2, "banner2", "Novedades", "Lanzamientos")
        )

        val vm = ProductoViewModel(repo)
        advanceUntilIdle()

        val st = vm.estado.value
        assertFalse(st.isLoading)
        assertNull(st.error)
        // productos cargados
        assertEquals(3, st.productos.size)
        // destacados presentes
        assertTrue(st.productosDestacados.any { it.destacado })
        // carrusel cargado
        assertTrue(vm.imagenesCarrusel.value.isNotEmpty())

        Dispatchers.resetMain()
    }


    @Test
    fun `cambiarCategoria y toggleSubcategoria filtran correctamente`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val repo = mockk<ProductoRepository>()
        every { repo.obtenerProductos() } returns productosFake()
        every { repo.obtenerImagenesCarrusel() } returns emptyList()

        val vm = ProductoViewModel(repo)
        advanceUntilIdle()

        // LLamamos a cambiar categoria → CONSOLA
        vm.cambiarCategoria(Categoria.CONSOLA)
        advanceUntilIdle()
        assertTrue(vm.estado.value.productos.all { it.categoria == Categoria.CONSOLA })

        // LLamamos a subcategoria → MANDOS dentro de CONSOLA
        vm.toggleSubcategoria(Subcategoria.MANDOS)
        advanceUntilIdle()
        val filtrados = vm.estado.value.productos
        assertTrue(filtrados.isNotEmpty())
        assertTrue(filtrados.all { it.subcategoria == Subcategoria.MANDOS })

        Dispatchers.resetMain()
    }

    @Test
    fun `texto de busqueda, rango de precio, soloDisponibles y orden precio asc`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val repo = mockk<ProductoRepository>()
        every { repo.obtenerProductos() } returns productosFake()
        every { repo.obtenerImagenesCarrusel() } returns emptyList()

        val vm = ProductoViewModel(repo)
        advanceUntilIdle()

        // LLamamos a texto de búsqueda
        vm.actualizarTextoBusqueda("pro")
        advanceUntilIdle()
        assertTrue(vm.estado.value.productos.all { it.nombre.contains("pro", ignoreCase = true) })

        // LLamamos a precios y solo disponibles
        vm.actualizarPrecioMinimo(30000.0)
        vm.actualizarPrecioMaximo(310000.0)
        vm.toggleSoloDisponibles()
        advanceUntilIdle()
        val despuesPrecio = vm.estado.value.productos
        assertTrue(despuesPrecio.all { it.disponible && it.precio in 30000.0..310000.0 })

        // LLamamos a ordenamiento ascendente por precio
        vm.cambiarOrdenamiento(com.example.levelupprueba.model.producto.OrdenProductos.PRECIO_ASC)
        advanceUntilIdle()
        val ordenados = vm.estado.value.productos
        assertEquals(ordenados.sortedBy { it.precio }, ordenados)

        Dispatchers.resetMain()
    }

    @Test
    fun `init termina con isLoading false y productos cargados`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val repo = mockk<ProductoRepository>()
        every { repo.obtenerImagenesCarrusel() } returns emptyList()
        every { repo.obtenerProductosDestacados() } returns emptyList()
        every { repo.obtenerProductos() } returns productosFake()

        // sin argumento nombrado; usa el posicional
        val vm = ProductoViewModel(repo)

        // dejamos que termine la carga
        advanceUntilIdle()

        val st = vm.estado.value
        assertFalse(st.isLoading)
        assertNull(st.error)
        assertEquals(3, st.productos.size)

        Dispatchers.resetMain()
    }

    @Test
    fun `init en error apaga isLoading y expone mensaje (ProductoViewModel)`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val repo = mockk<ProductoRepository>()
        every { repo.obtenerImagenesCarrusel() } returns emptyList()
        every { repo.obtenerProductosDestacados() } returns emptyList()
        every { repo.obtenerProductos() } answers { throw RuntimeException("fallo productos") }

        val vm = ProductoViewModel(repo)

        advanceUntilIdle()

        val st = vm.estado.value
        assertFalse(st.isLoading)
        assertNotNull(st.error)
        assertTrue(st.error!!.contains("fallo productos"))
        assertTrue(st.productos.isEmpty())

        Dispatchers.resetMain()
    }

    @Test
    fun `init carga una sola vez productos destacados y carrusel`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val repo = mockk<ProductoRepository>()
        every { repo.obtenerProductos() } returns productosFake()
        every { repo.obtenerProductosDestacados() } returns productosFake().filter { it.destacado }
        every { repo.obtenerImagenesCarrusel() } returns emptyList()

        val vm = ProductoViewModel(repo)
        advanceUntilIdle()
        advanceUntilIdle()

        io.mockk.verify(exactly = 1) { repo.obtenerProductos() }
        io.mockk.verify(exactly = 1) { repo.obtenerProductosDestacados() }
        io.mockk.verify(exactly = 1) { repo.obtenerImagenesCarrusel() }

        val st = vm.estado.value
        assertFalse(st.isLoading)
        assertNull(st.error)

        Dispatchers.resetMain()
    }

    @Test
    fun `init en error deja isLoading false, setea error y lista vacia`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val repo = mockk<ProductoRepository>()
        every { repo.obtenerImagenesCarrusel() } returns emptyList()
        every { repo.obtenerProductosDestacados() } returns emptyList()
        every { repo.obtenerProductos() } answers { throw RuntimeException("fallo productos") }

        val vm = ProductoViewModel(repo)
        advanceUntilIdle()

        val st = vm.estado.value
        assertFalse(st.isLoading)
        assertTrue(st.productos.isEmpty())
        assertNotNull(st.error)
        assertTrue(st.error!!.contains("fallo productos"))
        assertTrue(st.error!!.startsWith("Error al cargar productos"))

        Dispatchers.resetMain()
    }




}
