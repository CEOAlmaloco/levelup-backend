package com.example.levelupprueba.model.carrito

import com.example.levelupprueba.data.repository.ProductoRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CarritoTest {

    // Producto base tomado del repositorio en memoria
    private val baseProducto = ProductoRepository().obtenerProductos().first()

    @Test
    fun `totalLinea usa precio sin descuento cuando no hay descuento`() {
        // Crea producto sin descuento
        val productoSinDescuento = baseProducto.copy(descuento = null)

        // Crea item de carrito con cantidad 2
        val item = CarritoItem(
            id = productoSinDescuento.id,
            producto = productoSinDescuento,
            cantidad = 2
        )

        // Calcula total esperado sin descuento
        val esperado = productoSinDescuento.precio * 2

        // Verifica que totalLinea use el precio normal
        assertEquals(esperado, item.totalLinea, 0.01)
    }

    @Test
    fun `totalLinea usa precio con descuento cuando existe descuento`() {
        // Crea producto con descuento del 10%
        val productoConDescuento = baseProducto.copy(descuento = 10)

        // Crea item de carrito con cantidad 3
        val item = CarritoItem(
            id = productoConDescuento.id,
            producto = productoConDescuento,
            cantidad = 3
        )

        // Calcula precio con 10% de descuento (90%)
        val precioConDescuento = productoConDescuento.precio * 0.9

        // Calcula total esperado con descuento aplicado
        val esperado = precioConDescuento * 3

        // Verifica que totalLinea use el precio con descuento
        assertEquals(esperado, item.totalLinea, 0.01)
    }

    @Test
    fun `subtotal y total corresponden a la suma de los totalLinea`() {
        // Crea dos productos con distintos precios
        val p1 = baseProducto.copy(id = "P1", precio = 1000.0, descuento = null)
        val p2 = baseProducto.copy(id = "P2", precio = 2000.0, descuento = 50)

        // Item 1: 2 unidades sin descuento
        val item1 = CarritoItem(
            id = "1",
            producto = p1,
            cantidad = 2
        )

        // Item 2: 1 unidad con 50% de descuento
        val item2 = CarritoItem(
            id = "2",
            producto = p2,
            cantidad = 1
        )

        // Crea carrito con ambos items
        val carrito = Carrito(items = listOf(item1, item2))

        // Calcula subtotal esperado manualmente
        val esperado = (1000.0 * 2) + (2000.0 * 0.5)

        // Verifica que subtotal y total sean iguales al cálculo esperado
        assertEquals(esperado, carrito.subtotal, 0.01)
        assertEquals(esperado, carrito.total, 0.01)
    }

    @Test
    fun `unidadesTotales corresponde a la suma de las cantidades`() {
        // Crea dos productos distintos
        val p1 = baseProducto.copy(id = "P1")
        val p2 = baseProducto.copy(id = "P2")

        // Crea carrito con cantidades diferentes por item
        val carrito = Carrito(
            items = listOf(
                CarritoItem(id = "1", producto = p1, cantidad = 1),
                CarritoItem(id = "2", producto = p2, cantidad = 3)
            )
        )

        // Verifica que unidadesTotales sea la suma de las cantidades (1 + 3 = 4)
        assertEquals(4, carrito.unidadesTotales)
    }
}
