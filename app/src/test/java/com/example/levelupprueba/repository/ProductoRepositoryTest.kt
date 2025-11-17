import android.util.Log
import com.example.levelupprueba.data.repository.ProductoRepository
import com.example.levelupprueba.model.producto.Producto
import io.mockk.every
import io.mockk.mockkStatic
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ProductoRepositoryTest {

    // Repositorio real que vamos a probar
    private val repository = ProductoRepository()

    @BeforeEach
    fun setUp() = runTest {
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.w(any(), any<String>()) } returns 0
        every { Log.e(any(), any(), any()) } returns 0
    }

    @Test
    fun `obtenerProductos devuelve lista no vacia`() = runTest {
        // Ejecuta la obtención de productos
        val productos: List<Producto> = repository.obtenerProductos()

        // Verifica que exista al menos un producto
        assertTrue(productos.isNotEmpty())
    }

    @Test
    fun `todos los productos tienen precio mayor que cero`() = runTest {
        val productos = repository.obtenerProductos()

        // Verifica que ningún producto tenga precio negativo o cero
        assertTrue(productos.all { it.precio > 0.0 })
    }

    @Test
    fun `todos los productos tienen nombre no vacio`() = runTest {
        val productos = repository.obtenerProductos()

        // Verifica que todos los nombres estén definidos
        assertTrue(productos.all { it.nombre.isNotBlank() })
    }

    @Test
    fun `ids de productos son unicos`() = runTest {
        val productos = repository.obtenerProductos()
        val ids = productos.map { it.id }

        // Verifica que no haya ids repetidos
        assertEquals(ids.size, ids.toSet().size)
    }
}
