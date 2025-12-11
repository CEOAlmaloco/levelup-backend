package com.example.levelupprueba.model

import android.util.Log
import com.example.levelupprueba.data.repository.ProductoRepository
import com.example.levelupprueba.model.producto.Producto
import com.example.levelupprueba.model.producto.Review
import io.mockk.every
import io.mockk.mockkStatic
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ProductoTest {

    // Producto base tomado del repositorio en memoria
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
    fun `precio con descuento es null cuando no hay descuento`() {
        // Crea una copia del producto sin descuento definido
        val productoSinDescuento = baseProducto.copy(descuento = null)

        // Valida que precioConDescuento sea null cuando no existe descuento
        Assertions.assertNull(productoSinDescuento.precioConDescuento)
    }

    @Test
    fun `precioConDescuento aplica el porcentaje correctamente`() {
        // Crea una copia del producto con descuento del 20%
        val productoConDescuento = baseProducto.copy(descuento = 20)

        // Calcula el precio esperado aplicando el 20% de descuento
        val esperado = baseProducto.precio * 0.8

        // Obtiene el precioConDescuento, esperando que no sea null
        val precioConDescuento = productoConDescuento.precioConDescuento
            ?: error("precioConDescuento debería no ser nulo cuando hay descuento")

        // Compara el valor calculado con el valor de la propiedad del modelo
        Assertions.assertEquals(esperado, precioConDescuento, 0.01)
        // Define un margen de tolerancia de 0.01 para evitar errores por redondeo en números decimales
    }

    @Test
    fun `ratingPromedio usa rating base cuando no hay reviews`() {
        // Crea producto sin reviews pero con un rating base definido
        val productoSinReviews = baseProducto.copy(
            reviews = emptyList(),
            rating = 4.5f
        )

        // Verifica que ratingPromedio coincide con el rating base
        Assertions.assertEquals(4.5f, productoSinReviews.ratingPromedio)
    }

    @Test
    fun `ratingPromedio calcula el promedio de las reviews cuando existen`() {
        // Crea una lista de reviews con distintos ratings
        val reviews = listOf(
            Review(
                id = "1",
                productoId = baseProducto.id,
                usuarioNombre = "User A",
                rating = 4f,
                comentario = "",
                fecha = "2025-01-01"
            ),
            Review(
                id = "2",
                productoId = baseProducto.id,
                usuarioNombre = "User B",
                rating = 5f,
                comentario = "",
                fecha = "2025-01-02"
            ),
            Review(
                id = "3",
                productoId = baseProducto.id,
                usuarioNombre = "User C",
                rating = 3f,
                comentario = "",
                fecha = "2025-01-03"
            )
        )

        // Crea producto con las reviews y un rating base que no se debería usar
        val productoConReviews = baseProducto.copy(
            reviews = reviews,
            rating = 1f
        )

        // Verifica que ratingPromedio corresponde al promedio de las reviews (4.0)
        Assertions.assertEquals(4f, productoConReviews.ratingPromedio)
    }
}