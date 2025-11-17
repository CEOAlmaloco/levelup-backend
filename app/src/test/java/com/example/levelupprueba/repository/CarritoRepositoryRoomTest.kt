package com.example.levelupprueba.data.repository

import android.util.Log
import com.example.levelupprueba.data.local.room.CarritoDao
import com.example.levelupprueba.data.local.room.CarritoItemEntity
import com.example.levelupprueba.model.carrito.Carrito
import com.example.levelupprueba.model.producto.Producto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CarritoRepositoryRoomTest {

    // DAO simulado (relaxed = true → las funciones que no se stubbean no fallan)
    private val carritoDao: CarritoDao = mockk(relaxed = true)

    // Repositorio que vamos a probar
    private val repository = CarritoRepositoryRoom(carritoDao)

    // Producto base reutilizado en los tests
    private lateinit var baseProducto: Producto

    @BeforeEach
    fun setUp() = runTest {
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.w(any(), any<String>()) } returns 0
        every { Log.e(any(), any(), any()) } returns 0
        baseProducto = ProductoRepository().obtenerProductos().first()
    }


    @Test
    fun `getCarrito mapea entidades del DAO a modelo Carrito`() = runTest {
        // Simula un item almacenado en la base de datos
        val entity = CarritoItemEntity(
            id = "ITEM1",
            producto = baseProducto,
            cantidad = 2
        )
        coEvery { carritoDao.getAll() } returns listOf(entity)

        // Ejecuta el repositorio
        val carrito: Carrito = repository.getCarrito()

        // Verifica el mapeo
        assertEquals(1, carrito.items.size)
        val item = carrito.items.first()
        assertEquals("ITEM1", item.id)
        assertEquals(2, item.cantidad)
        assertEquals(baseProducto.id, item.producto.id)
    }

    @Test
    fun `agregarProducto inserta un nuevo item cuando no existe en el carrito`() = runTest {
        // Carrito vacío inicialmente
        coEvery { carritoDao.getAll() } returns emptyList()

        // Ejecuta agregarProducto
        repository.agregarProducto(baseProducto, cantidad = 2)

        // Verifica que se inserta un item nuevo con cantidad 2
        coVerify {
            carritoDao.upsert(
                match<CarritoItemEntity> { entity ->
                    entity.producto.id == baseProducto.id &&
                            entity.cantidad == 2
                }
            )
        }
    }

    @Test
    fun `agregarProducto suma cantidad cuando el producto ya existe`() = runTest {
        // Ya existe un item con cantidad 1
        val existente = CarritoItemEntity(
            id = "ITEM1",
            producto = baseProducto,
            cantidad = 1
        )
        coEvery { carritoDao.getAll() } returns listOf(existente)

        // Ejecuta agregarProducto con cantidad 3
        repository.agregarProducto(baseProducto, cantidad = 3)

        // 1 + 3 = 4
        coVerify {
            carritoDao.updateCantidad("ITEM1", 4)
        }
    }

    @Test
    fun `actualizarCantidad elimina item cuando la cantidad resultante es cero`() = runTest {
        // Item actual con cantidad 1
        val existente = CarritoItemEntity(
            id = "ITEM1",
            producto = baseProducto,
            cantidad = 1
        )
        coEvery { carritoDao.getAll() } returns listOf(existente)

        // delta -1 -> cantidad nueva 0 => se debe eliminar
        repository.actualizarCantidad(itemId = "ITEM1", delta = -1)

        coVerify { carritoDao.delete("ITEM1") }
    }

    @Test
    fun `actualizarCantidad actualiza cantidad cuando sigue siendo positiva`() = runTest {
        // Item actual con cantidad 2
        val existente = CarritoItemEntity(
            id = "ITEM1",
            producto = baseProducto,
            cantidad = 2
        )
        coEvery { carritoDao.getAll() } returns listOf(existente)

        // delta +3 -> cantidad nueva 5
        repository.actualizarCantidad(itemId = "ITEM1", delta = 3)

        coVerify { carritoDao.updateCantidad("ITEM1", 5) }
    }

    @Test
    fun `eliminarItem borra el item en el DAO`() = runTest {
        repository.eliminarItem("ITEM1")

        coVerify { carritoDao.delete("ITEM1") }
    }

    @Test
    fun `checkout limpia el carrito y devuelve un carrito vacio`() = runTest {
        // Después de limpiar, el carrito queda vacío
        coEvery { carritoDao.getAll() } returns emptyList()

        val carritoFinal = repository.checkout()

        coVerify { carritoDao.clear() }
        assertTrue(carritoFinal.items.isEmpty())
    }

}
