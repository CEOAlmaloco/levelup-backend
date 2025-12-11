package com.example.levelupprueba.viewmodel
// recordatorio, el viewmodel es el encargado de manejar el estado y la logica de la aplicacion

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupprueba.data.repository.ProductoRepository
import com.example.levelupprueba.model.producto.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

//el valor privado de repository sera un objeto de la clase ProductoRepository
//por ahora sin reviewDao para no romper el codigo existente, despues lo agregamos en el factory TODO
class ProductoViewModel(
    context: Context? = null,
    private val repository: ProductoRepository = ProductoRepository(context?.applicationContext)
) : ViewModel() {

    private val _estado = MutableStateFlow(ProductoUiState())//se crea un estado mutable para que el productoUiState se actualice en tiempo real
    val estado: StateFlow<ProductoUiState> = _estado// el estado sera igual a el estado mutable de productoUiState

    private val _imagenesCarrusel = MutableStateFlow<List<ImagenCarrusel>>(emptyList())
    val imagenesCarrusel: StateFlow<List<ImagenCarrusel>> = _imagenesCarrusel//lo mismo de antes pero con imagenesCarrusel

    private val _logoUrl = MutableStateFlow<String>("")
    val logoUrl: StateFlow<String> = _logoUrl

    private var todosLosProductos: List<Producto> = emptyList()//se crea una lista de productos para que se actualice en tiempo real

    init {//se carga los productos y las imagenes del carrusel antes de que se muestre la pantalla
        cargarProductos()
        cargarImagenesCarrusel()
        cargarLogo()
    }

    private fun cargarProductos(forceRefresh: Boolean = false) {//esta funcion se encarga de cargar los productos y los destacados
        viewModelScope.launch {// el scope es un contenedor para las corrutinas y la launch es para lanzar la corrutina
            android.util.Log.d("ProductoViewModel", "Iniciando carga de productos (forceRefresh=$forceRefresh)...")
            _estado.update { it.copy(isLoading = true, error = null) }//se actualiza el estado para que se muestre el loading y el error
            try {
                todosLosProductos = repository.obtenerProductos(forceRefresh)//se obtienen los productos (desde caché o backend)
                android.util.Log.d("ProductoViewModel", "Productos obtenidos: ${todosLosProductos.size}")
                val destacados = repository.obtenerProductosDestacados(forceRefresh)//se obtienen los productos destacados
                android.util.Log.d("ProductoViewModel", "Productos destacados obtenidos: ${destacados.size}")
                //se actualiza el estado para que se muestre los productos y los destacados
                _estado.update {
                    it.copy(
                        productos = todosLosProductos,
                        productosDestacados = destacados,
                        isLoading = false//y seteamos el loading y el error a false pq ya cargo
                    )
                }
                android.util.Log.d("ProductoViewModel", "Estado actualizado correctamente")
            } catch (e: Exception) {
                android.util.Log.e("ProductoViewModel", "Error al cargar productos: ${e.message}", e)
                e.printStackTrace()
                _estado.update {
                    it.copy(
                        isLoading = false,
                        error = "Error al cargar productos: ${e.message}"
                    )//manejo de errores basico en log 
                }
            }
        }
    }
    
    /**
     * Fuerza la recarga de productos desde el backend (ignorando caché)
     */
    fun recargarProductos() {
        cargarProductos(forceRefresh = true)
    }

    private fun cargarImagenesCarrusel() {//lo mismo de antes pero con las imagenes del carrusel
        viewModelScope.launch {
            try {
                val imagenes = repository.obtenerImagenesCarrusel()
                _imagenesCarrusel.value = imagenes
            } catch (e: Exception) {
                // Si falla la carga, dejamos la lista vacia (ya inicializada)
                _imagenesCarrusel.value = emptyList()
            }
        }
    }

    private fun cargarLogo() {
        viewModelScope.launch {
            try {
                val url = repository.obtenerLogoUrl()
                _logoUrl.value = url
            } catch (e: Exception) {
                // Si falla la carga, dejamos la URL vacía (se usará el drawable local como fallback)
                _logoUrl.value = ""
            }
        }
    }

    fun aplicarFiltros() {
        val filtros = _estado.value.filtros// el valor filtro sera igual al estado mutable de filtros en este caso filtrosstate
        var productosFiltrados = todosLosProductos

        // filtro por categoria en caso de que no sea todas
        if (filtros.categoriaSeleccionada != Categoria.TODAS) {
            productosFiltrados = productosFiltrados.filter {//se filtran los productos en base a la categoria seleccionada
                it.categoria == filtros.categoriaSeleccionada//it es el producto actual sera igual a la categoria seleccionada
            }
        }

        // filtro por subcategorias en caso de que no sea todas
        if (filtros.subcategoriasSeleccionadas.isNotEmpty()) {
            productosFiltrados = productosFiltrados.filter {//se filtran los productos en base a las subcategorias seleccionadas
                it.subcategoria in filtros.subcategoriasSeleccionadas//copy paste de lo de arriba 
            }
        }

        // filtro por texto en caso de que no sea blanco
        if (filtros.textoBusqueda.isNotBlank()) {
            productosFiltrados = productosFiltrados.filter {//se filtran los productos en base al texto buscado este es mas complejo 
                it.nombre.contains(filtros.textoBusqueda, ignoreCase = true) || 
                it.descripcion.contains(filtros.textoBusqueda, ignoreCase = true)
            }//si el nombre contiene el texto como el nombre o desc de el prod  entonces lo busca, el ignoreCase es para que no distinga entre mayusculas y minusculas
        }

        // filtro por precio minimo en caso de que no sea null
        filtros.precioMinimo?.let { min -> // si no es null.  entonces el let permite ejecuta donde el min ejecuta la flecha
            productosFiltrados = productosFiltrados.filter { it.precio >= min }
        }

        // filtro por precio maximo en caso de que no sea null
        filtros.precioMaximo?.let { max ->
            productosFiltrados = productosFiltrados.filter { it.precio <= max }
        }//ssi el precio es menor o igual al precio maximo entonces lo busca

        // filtro por disponibilidad en caso de que no sea false
        if (filtros.soloDisponibles) {
            productosFiltrados = productosFiltrados.filter { it.disponible }//si contiene disponible entonces lo busca
        }

        // filtro por rating en caso de que no sea 0
        if (filtros.ratingMinimo > 0) {
            productosFiltrados = productosFiltrados.filter { it.rating >= filtros.ratingMinimo }
        }//el rating va de 0 a 5 si el rating es mayor o igual al rating minimo entonces lo busca

        // ordenamiento
        productosFiltrados = when (filtros.ordenamiento) {
            OrdenProductos.PRECIO_ASC -> productosFiltrados.sortedBy { it.precio }//se ordenan los productos en base al precio
            OrdenProductos.PRECIO_DESC -> productosFiltrados.sortedByDescending { it.precio }//se ordenan los productos en base al precio de mayor a menor
            OrdenProductos.RATING_DESC -> productosFiltrados.sortedByDescending { it.rating }//se ordenan los productos en base al rating
            OrdenProductos.RELEVANCIA -> productosFiltrados.sortedByDescending { it.destacado }//se ordenan los productos en base al destacado
        }

        _estado.update { it.copy(productos = productosFiltrados) }//se actualiza el estado para que se muestren los productos filtrados
    }

    //esta funcion se ejecuta cuando se cambia la categoria  despues de pasar por las demas funciones de filtro 
    fun cambiarCategoria(categoria: Categoria) {//parametro tipo de dato 
        _estado.update {//se actualiza el estado para que se muestren los productos filtrados 
            it.copy(
                filtros = it.filtros.copy(
                    categoriaSeleccionada = categoria,
                    subcategoriasSeleccionadas = emptySet()
                )
            )
        }
        aplicarFiltros()//se aplica el filtro para que se muestren los productos filtrados
    }

    fun toggleSubcategoria(subcategoria: Subcategoria) {//parametro tipo de dato 
        val subcategorias = _estado.value.filtros.subcategoriasSeleccionadas.toMutableSet()//cambiamos a mutableSet para que se pueda modificar
        if (subcategorias.contains(subcategoria)) {
            subcategorias.remove(subcategoria)//si contiene la subcategoria entonces la removemos para no repetirla
        } else {
            subcategorias.add(subcategoria)//si no contiene la subcategoria entonces la agregamos
        }
        
        _estado.update {//actualizamos despues de cummplir con lo anterior 
            it.copy(filtros = it.filtros.copy(subcategoriasSeleccionadas = subcategorias))
        }
        aplicarFiltros()//aplicamos 
    }

    fun actualizarTextoBusqueda(texto: String) {
        _estado.update {
            it.copy(filtros = it.filtros.copy(textoBusqueda = texto))
        }
        aplicarFiltros()
    }

    fun actualizarPrecioMinimo(precio: Double?) {
        _estado.update {
            it.copy(filtros = it.filtros.copy(precioMinimo = precio))
        }
        aplicarFiltros()
    }

    fun actualizarPrecioMaximo(precio: Double?) {
        _estado.update {
            it.copy(filtros = it.filtros.copy(precioMaximo = precio))
        }
        aplicarFiltros()
    }

    fun toggleSoloDisponibles() {
        _estado.update {
            it.copy(filtros = it.filtros.copy(soloDisponibles = !it.filtros.soloDisponibles))
        }
        aplicarFiltros()
    }

    fun actualizarRatingMinimo(rating: Float) {
        _estado.update {
            it.copy(filtros = it.filtros.copy(ratingMinimo = rating))
        }
        aplicarFiltros()
    }

    fun cambiarOrdenamiento(orden: OrdenProductos) {
        _estado.update {
            it.copy(filtros = it.filtros.copy(ordenamiento = orden))
        }
        aplicarFiltros()
    }

    fun limpiarFiltros() {
        _estado.update {
            it.copy(filtros = FiltrosState())
        }
        aplicarFiltros()
    }

    fun toggleMostrarFiltros() {
        _estado.update {
            it.copy(mostrarFiltros = !it.mostrarFiltros)
        }
    }

    fun cerrarFiltros() {
        _estado.update {
            it.copy(mostrarFiltros = false)
        }
    }
    //todas estas funciones son para el filtro deslizante  copy y paste cada una 
    //en cada una llamamos a su propia funcion para que se actualice el estado y se muestren los productos filtrados
}

