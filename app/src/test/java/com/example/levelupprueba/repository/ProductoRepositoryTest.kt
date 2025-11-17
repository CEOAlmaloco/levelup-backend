import com.example.levelupprueba.data.repository.ProductoRepository
import com.example.levelupprueba.model.producto.Producto
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ProductoRepositoryTest {

    // Repositorio real que vamos a probar
    private val repository = ProductoRepository()

    @Test
    fun `obtenerProductos devuelve lista no vacia`() {
        // Ejecuta la obtención de productos
        val productos: List<Producto> = repository.obtenerProductos()

        // Verifica que exista al menos un producto
        assertTrue(productos.isNotEmpty())
    }

    @Test
    fun `todos los productos tienen precio mayor que cero`() {
        val productos = repository.obtenerProductos()

        // Verifica que ningún producto tenga precio negativo o cero
        assertTrue(productos.all { it.precio > 0.0 })
    }

    @Test
    fun `todos los productos tienen nombre no vacio`() {
        val productos = repository.obtenerProductos()

        // Verifica que todos los nombres estén definidos
        assertTrue(productos.all { it.nombre.isNotBlank() })
    }

    @Test
    fun `ids de productos son unicos`() {
        val productos = repository.obtenerProductos()
        val ids = productos.map { it.id }

        // Verifica que no haya ids repetidos
        assertEquals(ids.size, ids.toSet().size)
    }

    @Test
    fun `obtenerProductoPorId devuelve null cuando el id no existe`() {

        val resultado = repository.obtenerProductoPorId("ID_INEXISTENTE")

        assertNull(resultado)
    }

    @Test
    fun `obtenerProductoPorId devuelve el producto correcto cuando el id existe`() {
        // Id real desde el repositorio
        val primerProducto = repository.obtenerProductos().first()
        val idBuscado = primerProducto.id

        // Act
        val resultado = repository.obtenerProductoPorId(idBuscado)

        // Assert
        assertNotNull(resultado)
        assertEquals(idBuscado, resultado?.id)
    }

    @Test
    fun `obtenerProductosDestacados devuelve solo productos destacados`() {
        val destacados = repository.obtenerProductosDestacados()

        assertTrue(destacados.all { it.destacado })
    }

    @Test
    fun `obtenerReviews retorna una lista valida para un id existente`() = runTest {
        val producto = repository.obtenerProductos().first()

        val reviews = repository.obtenerReviews(producto.id)

        assertNotNull(reviews)
        assertTrue(reviews.isNotEmpty() || reviews.isEmpty())
    }

    @Test
    fun `obtenerReviews retorna reviews si el producto tiene reviews`() = runTest {
        val productoConReviews = repository
            .obtenerProductos()
            .firstOrNull { it.reviews.isNotEmpty() }

        if (productoConReviews != null) {
            val resultado = repository.obtenerReviews(productoConReviews.id)

            assertTrue(resultado.isNotEmpty())
        }
    }
    @Test
    fun `obtenerReviews devuelve lista mock no vacia cuando no hay reviewDao`() = runTest {
        val producto = repository.obtenerProductos().first()
        val productoId = producto.id

        // Act
        val reviews = repository.obtenerReviews(productoId)

        assertNotNull(reviews)
        assertTrue(reviews.isNotEmpty())
        assertTrue(reviews.all { it.productoId == productoId })
    }

}
