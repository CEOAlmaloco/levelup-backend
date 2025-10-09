package com.example.levelupprueba.model.producto

//esta clase sirve para filtrar los productos y funciona como un estado para el viewmodel
data class FiltrosState(
    val categoriaSeleccionada: Categoria = Categoria.TODAS,
    val subcategoriasSeleccionadas: Set<Subcategoria> = emptySet(),//set es una lista de elementos que no se repiten y seteamos un set vacio para ir llenando luego de seleccionar 
    val textoBusqueda: String = "",
    val precioMinimo: Double? = null,
    val precioMaximo: Double? = null,
    val soloDisponibles: Boolean = false,
    val ratingMinimo: Float = 0f,
    val ordenamiento: OrdenProductos = OrdenProductos.RELEVANCIA
)

enum class OrdenProductos(val valor: String, val etiqueta: String) {// esto seria para setear un intervalo entre cada una de las opciones
    RELEVANCIA("relevancia", "Relevancia"),//esto es una lista 
    /*RELEVANCIA es el nombre de constante osea algo asi como ordenproductos.RELEVANCIA
    "relevancia " es elvalor para logica interna
     "Relevancia" etiqueta que se va a mostrar en la interfaz*/
    PRECIO_ASC("precio-asc", "Precio: menor a mayor"),
    PRECIO_DESC("precio-desc", "Precio: mayor a menor"),
    RATING_DESC("rating-desc", "Rating: mayor a menor")
}

