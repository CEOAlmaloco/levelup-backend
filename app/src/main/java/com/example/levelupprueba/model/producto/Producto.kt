package com.example.levelupprueba.model.producto
//atributos que tiene un producto
data class Producto(
    val id: String,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val imagenUrl: String,//esta es la imagen principal
    val categoria: Categoria,
    val subcategoria: Subcategoria?,
    val rating: Float = 0f,
    val disponible: Boolean = true,
    val destacado: Boolean = false,
    val stock: Int = 0,
    //detalle de producto los agregados para q funcione el resto
    val imagenesUrls: List<String> = listOf(imagenUrl),//y esta las imagenes chicas
    val fabricante: String? = null,//los datos adicionales q pedia el caso
    val distribuidor: String? = null,
    val descuento: Int? = null,//descuento aplicado
    val reviews: List<Review> = emptyList(),//reviews del producto
    val productosRelacionados: List<Producto> = emptyList()//productos relacionados segun la categoria
) {
    // Precio con descuento aplicado
    val precioConDescuento: Double?
        get() = descuento?.let { precio * (1 - it / 100.0) }
    //cuando llamamos a la funcion con el get calculamos el descuento segun el porcentaje 
    // el promedio calculado desde las reviews
    val ratingPromedio: Float
        get() = if (reviews.isEmpty()) rating // si la lista de reviews esta vacia, devolvemos el rating del producto
                else reviews.map { it.rating }.average().toFloat()//sino, mapeamos las review y segun el rating y devolvemos el promedio decimal 
} 

