import com.example.levelupprueba.data.repository.ProductoRepository
import com.example.levelupprueba.model.producto.Producto
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
}
