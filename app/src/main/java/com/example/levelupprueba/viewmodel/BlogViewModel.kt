package com.example.levelupprueba.viewmodel

//BlogViewModel.kt - Lógica para cargar blogs, filtrar por categoría

//importe un monton de cosas bla bla bla
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupprueba.model.blog.Blog
import com.example.levelupprueba.model.blog.BlogRepository
import com.example.levelupprueba.model.blog.BlogUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BlogViewModel(
    private val repository: BlogRepository = BlogRepository()
) : ViewModel() {

    //ponemos el estado interno mutable para que el bloguistate se actualice
    private val _estado = MutableStateFlow(BlogUiState())
    val estado: StateFlow<BlogUiState> = _estado

    init {// cuando se inicie el viewmodel se cargaran los blogs
        cargarBlogs()
    }
    //y aca el cargar blogs
    fun cargarBlogs() {
        viewModelScope.launch {//un scope es un contenedor para las corrutinas y la launch es para lanzar la corrutina
            _estado.update { it.copy(isLoading = true, error = null) }
//aca lo q hacemos es que el estado cuando se actualize genera una copia de este para no modificar el original y seteamos los valores de isLoading y error a true y null
            try {//siempre en un delay poner un try catch ya que los delay pueden siempre tirar error
                delay(1000) // aca simulamos que llamamos a la api como si fuera async
                val blogs = repository.obtenerBlogs()
                _estado.update {
                    it.copy(
                        blogs = blogs,
                        isLoading = false
                    )
                }//baicamente lo q hacemos aca es simular la llamada de la api con los blogs de la base de datos, entonces vemos cmo se ve la pantalla con los datos sin depender del backend
            } catch (e: Exception) {
                _estado.update {
                    it.copy(
                        isLoading = false,
                        error = "Error al cargar blogs: ${e.message}"
                    )
                }
            }
        }
    }

    fun cambiarFiltro(filtro: String) {
        _estado.update { it.copy(filtroActivo = filtro) }
    }//en esta funcion es lo de los botones donde cambiamos el estado solamente si clickeamos en filtro

    fun obtenerBlogsFiltrados(): List<Blog> {
        val estadoActual = _estado.value
        return if (estadoActual.filtroActivo == "todas") {
            estadoActual.blogs// si es todas entonces mostramos todos los blogs
        } else {
            estadoActual.blogs.filter { it.categoria.lowercase() == estadoActual.filtroActivo }
        }// si no es todas entonces filtramos en base a la categoria
    }

}