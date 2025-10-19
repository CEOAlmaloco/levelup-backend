package com.example.levelupprueba.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupprueba.data.repository.ProductoRepository
import com.example.levelupprueba.model.producto.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

//logica a nivel de usuario - este viewmodel maneja el detalle del producto y las reviews
class ProductoDetalleViewModel(
    private val repository: ProductoRepository = ProductoRepository() //el repository para acceder a la bd de reviews
) : ViewModel() {

    private val _estado = MutableStateFlow(ProductoDetalleUiState()) 
    val estado: StateFlow<ProductoDetalleUiState> = _estado 

    fun cargarProducto(productoId: String) {//cargamos el producto
        viewModelScope.launch { 
            _estado.update { it.copy(isLoading = true, error = null) } //actualizamos el estado para que se muestre el loading y el error
            try {
                delay(300)//simulamos el delay de la api
                val productoBase = repository.obtenerProductoPorId(productoId)//traemos el producto base
                //si el producto base no es null, traemos las reviews y los productos relacionados
                if (productoBase != null) {
                    val reviews = repository.obtenerReviews(productoId)//traemos las reviews
                    val relacionados = repository.obtenerProductosRelacionados(productoBase)//traemos los productos relacionados
                    
                    // Creamos el producto completo con toda la información de detalle
                    val productoCompleto = productoBase.copy(//copiamos el producto base y le agregamos la informacion de detalle
                        imagenesUrls = listOf(productoBase.imagenUrl),//agregamos la imagen principal
                        fabricante = obtenerFabricante(productoBase),
                        distribuidor = obtenerDistribuidor(productoBase),
                        reviews = reviews, 
                        productosRelacionados = relacionados//agregamos los productos relacionados
                    )
                    
                    _estado.update { //actualizamo el estado para que se muestre el producto completo
                        it.copy(
                            producto = productoCompleto, 
                            isLoading = false//y seteamos el loading y el error a false pq ya cargo
                        )
                    }
                } else {
                    _estado.update {
                        it.copy(
                            isLoading = false,
                            error = "Producto no encontrado"
                        )
                    }
                }
            } catch (e: Exception) {
                _estado.update {
                    it.copy(
                        isLoading = false,
                        error = "Error al cargar el producto: ${e.message}"
                    )
                }
            }
        }
    }

    private fun obtenerFabricante(producto: Producto): String { // obtenemos el fabricante segun la categoria, no tiene mucho sentido pq deberia ser por producto y no por categoria
        return when (producto.categoria) { //pero creo q me equivoque luego lo arreglo XD TODO todo 
            Categoria.CONSOLA -> "Sony / Microsoft / Nintendo"
            Categoria.PERIFERICOS -> "Logitech / HyperX / Razer"
            Categoria.ROPA -> "LevelUp Apparel"
            Categoria.ENTRETENIMIENTO -> "Asmodee / Devir" //retornamos cuando el producto en categoria sea categoria.categoria  igual a el nombre de la categoria para ser especificos en la asignacion de comparacion para devolver el valor real
            else -> "LevelUp Partners"
        }
    }

    private fun obtenerDistribuidor(producto: Producto): String {
        return producto.subcategoria?.name?.let { // retornamos el producto cuando la subcategoria y el nombre no sean null y con el let accedemos a el.  
            "$it LATAM Distribución"  //con esto concatenamos el nombre de la subcategoria y el texto LATAM Distribución, asi siempre tiene un distribuidor , igual pensaba en cambiarlo a una bd con el distribuidor para no hacer tanta chachara
        } ?: "Distribuidor autorizado LATAM" //si el producto es null, retornamos el texto Distribuidor autorizado LATAM
    }

    fun agregarAlCarrito() {
        _estado.update { //actualizamos el estado para que se muestre la cantidad de carrito
            it.copy(cantidadCarrito = 1)
        }
    }

    fun aumentarCantidad() { // la suma del carrito
        _estado.update {
            it.copy(cantidadCarrito = it.cantidadCarrito + 1)
        }
    }

    fun disminuirCantidad() { // la resta del carrito
        val cantidadActual = _estado.value.cantidadCarrito
        if (cantidadActual > 1) {
            _estado.update {//validacion de q siempre sea mayor a 1 para q no se pueda restar mas de 1
                it.copy(cantidadCarrito = cantidadActual - 1)
            }
        } else {
            _estado.update {//si es 1, seteamos el carrito a 0
                it.copy(cantidadCarrito = 0)    
            }
        }
    }

    fun toggleFormularioReview() {
        _estado.update {
            it.copy(mostrarFormularioReview = !it.mostrarFormularioReview) //actualizamos el estado para que se muestre el formulario de review 
        }//seria como la actualizacion en la misma variable !it.mostrarFormularioReview para q sea verdadero o falso
    }

    fun seleccionarImagen(index: Int) {
        _estado.update {
            it.copy(imagenSeleccionada = index)//el index es el numero de la imagen que se selecciono
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun agregarReview(rating: Float, comentario: String, usuarioNombre: String) {//le pasamos los parametros para agregar la review
        viewModelScope.launch { //lanzamos una corrutina para hacer la operacion asincrona
            try {
                val productoId = _estado.value.producto?.id ?: return@launch //validacion de q el producto no sea null
                //si el producto no es null, creamos la nueva review
                val nuevaReview = Review(
                    id = System.currentTimeMillis().toString(),//generamos el id de la review con el tiempo actual en milisegundos ya que es unico
                    productoId = productoId, //agregamos el productoId para relacionar la review con el producto
                    usuarioNombre = usuarioNombre,
                    rating = rating,
                    comentario = comentario,
                    fecha = java.time.LocalDate.now().toString() //me da miedo ver q dice java en kt, pero es de java.time q kotlin usa
                )
                
                val exito = repository.agregarReview(productoId, nuevaReview) //guardamos en SQLite
                
                if (exito) { //si se guardo bien, actualizamos el estado
                    val reviewsActualizadas = repository.obtenerReviews(productoId) //traemos las reviews actualizadas de la bd
                    _estado.update { 
                        it.copy(
                            producto = it.producto?.copy(
                                reviews = reviewsActualizadas //actualizamos las reviews del producto
                            ),
                            mostrarFormularioReview = false //ocultamos el formulario
                        )
                    }
                } else {
                    _estado.update {
                        it.copy(error = "No se pudo guardar la reseña") //si falla mostramos error
                    }
                }
            } catch (e: Exception) {
                _estado.update {
                    it.copy(error = "Error al agregar reseña: ${e.message}") //si hay excepcion mostramos el error
                }
            }
        }
    }
    
    //funcion extra para borrar una review si implementamos esa funcionalidad despues
    fun borrarReview(review: Review) {
        viewModelScope.launch {
            try {
                val exito = repository.borrarReview(review) //borramos de SQLite
                if (exito) {
                    val productoId = _estado.value.producto?.id ?: return@launch
                    val reviewsActualizadas = repository.obtenerReviews(productoId) //actualizamos la lista
                    _estado.update {
                        it.copy(
                            producto = it.producto?.copy(
                                reviews = reviewsActualizadas
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                _estado.update {
                    it.copy(error = "Error al borrar reseña: ${e.message}")
                }
            }
        }
    }
}

