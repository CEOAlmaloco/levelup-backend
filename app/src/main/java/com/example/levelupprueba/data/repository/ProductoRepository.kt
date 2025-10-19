package com.example.levelupprueba.data.repository

import com.example.levelupprueba.data.local.ReviewDao
import com.example.levelupprueba.model.producto.Categoria
import com.example.levelupprueba.model.producto.ImagenCarrusel
import com.example.levelupprueba.model.producto.Producto
import com.example.levelupprueba.model.producto.Review
import com.example.levelupprueba.model.producto.Subcategoria

//ahora el repository recibe el ReviewDao para acceder a la bd
class ProductoRepository(
    private val reviewDao: ReviewDao? = null //lo hacemos nullable para no romper el codigo existente q no lo usa
) {

    fun obtenerImagenesCarrusel(): List<ImagenCarrusel> {
        return listOf(
            ImagenCarrusel(
                id = 1,
                imagenUrl = "carrusel",
                titulo = "¡Nuevos lanzamientos!",
                descripcion = "Descubre los últimos juegos y accesorios",
                enlace = ""
            ),
            ImagenCarrusel(
                id = 2,
                imagenUrl = "carrusel",
                titulo = "Ofertas especiales",
                descripcion = "Hasta 50% de descuento en productos seleccionados",
                enlace = ""
            ),
            ImagenCarrusel(
                id = 3,
                imagenUrl = "carrusel",
                titulo = "Setup gamer completo",
                descripcion = "Arma tu estación de juego perfecta",
                enlace = ""
            )
        )
    }
    fun obtenerProductos(): List<Producto> {
        return listOf(
            Producto(
                id = "CO001",
                nombre = "PlayStation 5",
                descripcion = "La consola de última generación de Sony, que ofrece gráficos impresionantes y tiempos de carga ultrarrápidos para una experiencia de juego inmersiva.",
                precio = 549990.0,
                imagenUrl = "play5",
                categoria = Categoria.CONSOLA,
                subcategoria = Subcategoria.HARDWARE,
                rating = 5f,
                disponible = true,
                destacado = true,
                stock = 15,
                imagenesUrls = listOf("play5"),
                fabricante = "Sony",
                distribuidor = "Sony Chile",
                descuento = 10,
                reviews = emptyList(),
                productosRelacionados = emptyList()
            ),
            Producto(
                id = "CO002",
                nombre = "PlayStation 4 Slim",
                descripcion = "Una consola versátil y compacta que sigue siendo ideal para disfrutar de un extenso catálogo de juegos con gran rendimiento y entretenimiento garantizado.",
                precio = 179990.0,
                imagenUrl = "play4",
                categoria = Categoria.CONSOLA,
                subcategoria = Subcategoria.HARDWARE,
                rating = 5f,
                disponible = true,
                destacado = true,
                stock = 12,
                imagenesUrls = listOf("play4"),
                fabricante = "Sony",
                distribuidor = "Sony Chile",
                descuento = 5,
                reviews = emptyList(),
                productosRelacionados = emptyList()
            ),
            Producto(
                id = "CO003",
                nombre = "DualShock 4",
                descripcion = "Control oficial de PlayStation 4 con diseño ergonómico y funciones avanzadas que ofrecen precisión y comodidad durante tus sesiones de juego.",
                precio = 60000.0,
                imagenUrl = "mandoplay",
                categoria = Categoria.CONSOLA,
                subcategoria = Subcategoria.MANDOS,
                rating = 4f,
                disponible = true,
                destacado = false,
                stock = 30,
                imagenesUrls = listOf("mandoplay"),
                fabricante = "Sony",
                distribuidor = "Sony Chile",
                descuento = null,
                reviews = emptyList(),
                productosRelacionados = emptyList()
            ),
            Producto(
                id = "CO005",
                nombre = "Dualsense Azul",
                descripcion = "Control de PlayStation 5 en elegante color azul, con retroalimentación háptica y gatillos adaptativos que llevan la experiencia de juego a otro nivel.",
                precio = 69990.0,
                imagenUrl = "mandoplayazul",
                categoria = Categoria.CONSOLA,
                subcategoria = Subcategoria.MANDOS,
                rating = 4f,
                disponible = true,
                destacado = true,
                stock = 25,
                imagenesUrls = listOf("mandoplayazul"),
                fabricante = "Sony",
                distribuidor = "Sony Chile",
                descuento = 15,
                reviews = emptyList(),
                productosRelacionados = emptyList()
            ),
            Producto(
                id = "CO006",
                nombre = "Auriculares PS4",
                descripcion = "Auriculares diseñados para PlayStation 4, con sonido envolvente y micrófono integrado para comunicación clara en partidas multijugador.",
                precio = 150000.0,
                imagenUrl = "audifono",
                categoria = Categoria.CONSOLA,
                subcategoria = Subcategoria.ACCESORIOS,
                rating = 3f,
                disponible = false,
                destacado = false,
                stock = 0,
                imagenesUrls = listOf("audifono"),
                fabricante = "Sony",
                distribuidor = "Sony Chile",
                descuento = null,
                reviews = emptyList(),
                productosRelacionados = emptyList()
            ),
            Producto(
                id = "PE001",
                nombre = "Auriculares Logitech",
                descripcion = "Auriculares de alto rendimiento con gran calidad de sonido y micrófono ajustable, ideales para juegos, streaming y comunicación online.",
                precio = 100000.0,
                imagenUrl = "audifonoazul",
                categoria = Categoria.PERIFERICOS,
                subcategoria = Subcategoria.AURICULARES,
                rating = 4f,
                disponible = true,
                destacado = true,
                stock = 20,
                imagenesUrls = listOf("audifonoazul"),
                fabricante = "Logitech",
                distribuidor = "Logitech Chile",
                descuento = 10,
                reviews = emptyList(),
                productosRelacionados = emptyList()
            ),
            Producto(
                id = "PE002",
                nombre = "Teclado Redragon RGB",
                descripcion = "Teclado mecánico con iluminación RGB y switches de alto rendimiento, ideal para juegos y escritura intensiva.",
                precio = 145990.0,
                imagenUrl = "tecladogamer",
                categoria = Categoria.PERIFERICOS,
                subcategoria = Subcategoria.TECLADOS,
                rating = 4f,
                disponible = true,
                destacado = true,
                stock = 25,
                imagenesUrls = listOf("tecladogamer"),
                fabricante = "Redragon",
                distribuidor = "Redragon Chile",
                descuento = 20,
                reviews = emptyList(),
                productosRelacionados = emptyList()
            ),
            Producto(
                id = "PE003",
                nombre = "Mouse Cougar",
                descripcion = "Mouse gamer ergonómico con alta precisión y diseño personalizable, perfecto para sesiones intensas y competitivas.",
                precio = 85990.0,
                imagenUrl = "ratongamer",
                categoria = Categoria.PERIFERICOS,
                subcategoria = Subcategoria.MOUSES,
                rating = 4f,
                disponible = true,
                destacado = false,
                stock = 30,
                imagenesUrls = listOf("ratongamer"),
                fabricante = "Cougar",
                distribuidor = "Cougar Chile",
                descuento = null,
                reviews = emptyList(),
                productosRelacionados = emptyList()
            ),
            Producto(
                id = "PE004",
                nombre = "Monitor ASUS",
                descripcion = "Monitor de alto rendimiento con gran calidad de imagen y refresco rápido, ideal para disfrutar de gráficos nítidos en juegos y multimedia.",
                precio = 175990.0,
                imagenUrl = "monitorgamer",
                categoria = Categoria.PERIFERICOS,
                subcategoria = Subcategoria.MONITORES,
                rating = 4f,
                disponible = true,
                destacado = true,
                stock = 8,
                imagenesUrls = listOf("monitorgamer"),
                fabricante = "ASUS",
                distribuidor = "ASUS Chile",
                descuento = 12,
                reviews = emptyList(),
                productosRelacionados = emptyList()
            ),
            Producto(
                id = "PE005",
                nombre = "Webcam Logitech",
                descripcion = "Webcam de alta definición con gran calidad de imagen y audio, perfecta para streaming, videollamadas y creación de contenido.",
                precio = 67990.0,
                imagenUrl = "camaramonitor",
                categoria = Categoria.PERIFERICOS,
                subcategoria = Subcategoria.CAMARAS_WEB,
                rating = 4f,
                disponible = true,
                destacado = false,
                stock = 18,
                imagenesUrls = listOf("camaramonitor"),
                fabricante = "Logitech",
                distribuidor = "Logitech Chile",
                descuento = null,
                reviews = emptyList(),
                productosRelacionados = emptyList()
            ),
            Producto(
                id = "PE006",
                nombre = "Microfono Logitech",
                descripcion = "Micrófono de alta fidelidad con captación clara y profesional, ideal para streaming, grabaciones y comunicación en equipo.",
                precio = 86990.0,
                imagenUrl = "microfono",
                categoria = Categoria.PERIFERICOS,
                subcategoria = Subcategoria.MICROFONOS,
                rating = 4f,
                disponible = true,
                destacado = false,
                stock = 15,
                imagenesUrls = listOf("microfono"),
                fabricante = "Logitech",
                distribuidor = "Logitech Chile",
                descuento = null,
                reviews = emptyList(),
                productosRelacionados = emptyList()
            ),
            Producto(
                id = "RO001",
                nombre = "Poleron StarCraft",
                descripcion = "Polerón inspirado en StarCraft con diseño gamer único y tela cómoda, perfecto para mostrar tu pasión por el universo de Blizzard.",
                precio = 40000.0,
                imagenUrl = "poleronstarcraf",
                categoria = Categoria.ROPA,
                subcategoria = Subcategoria.POLERONES,
                rating = 4.5f,
                disponible = true,
                destacado = false,
                stock = 50,
                imagenesUrls = listOf("poleronstarcraf"),
                fabricante = "Blizzard",
                distribuidor = "Level-Up",
                descuento = null,
                reviews = emptyList(),
                productosRelacionados = emptyList()
            ),
            Producto(
                id = "RO002",
                nombre = "Poleron Super Papá Gamer",
                descripcion = "Polerón divertido y cómodo diseñado para los papás que disfrutan tanto del gaming como de la familia.",
                precio = 40000.0,
                imagenUrl = "polerongrande",
                categoria = Categoria.ROPA,
                subcategoria = Subcategoria.POLERONES,
                rating = 4.5f,
                disponible = true,
                destacado = false,
                stock = 50,
                imagenesUrls = listOf("polerongrande"),
                fabricante = "Level-Up",
                distribuidor = "Level-Up",
                descuento = 10,
                reviews = emptyList(),
                productosRelacionados = emptyList()
            ),
            Producto(
                id = "RO003",
                nombre = "Poleron Hollow Knight",
                descripcion = "Polerón con diseño inspirado en Hollow Knight, ideal para fanáticos del juego indie y su mundo misterioso.",
                precio = 40000.0,
                imagenUrl = "poleronthekin",
                categoria = Categoria.ROPA,
                subcategoria = Subcategoria.POLERONES,
                rating = 4.5f,
                disponible = true,
                destacado = false,
                stock = 50,
                imagenesUrls = listOf("poleronthekin"),
                fabricante = "Team Cherry",
                distribuidor = "Level-Up",
                descuento = null,
                reviews = emptyList(),
                productosRelacionados = emptyList()
            ),
            Producto(
                id = "RO004",
                nombre = "Poleron PlayStation Retro Negro",
                descripcion = "Polerón retro en color negro con el clásico logo de PlayStation, perfecto para los nostálgicos del gaming.",
                precio = 40000.0,
                imagenUrl = "poleronplay",
                categoria = Categoria.ROPA,
                subcategoria = Subcategoria.POLERONES,
                rating = 4.5f,
                disponible = true,
                destacado = false,
                stock = 50,
                imagenesUrls = listOf("poleronplay"),
                fabricante = "Sony",
                distribuidor = "Level-Up",
                descuento = null,
                reviews = emptyList(),
                productosRelacionados = emptyList()
            ),
            Producto(
                id = "RO005",
                nombre = "Poleron Stumble Guys",
                descripcion = "Polerón colorido y divertido inspirado en Stumble Guys, ideal para gamers que disfrutan de las partidas llenas de caos y risas.",
                precio = 40000.0,
                imagenUrl = "stumblepoleron",
                categoria = Categoria.ROPA,
                subcategoria = Subcategoria.POLERONES,
                rating = 4.5f,
                disponible = true,
                destacado = false,
                stock = 50,
                imagenesUrls = listOf("stumblepoleron"),
                fabricante = "Kitka Games",
                distribuidor = "Level-Up",
                descuento = 5,
                reviews = emptyList(),
                productosRelacionados = emptyList()
            ),
            Producto(
                id = "RO006",
                nombre = "Poleron S.T.A.R.S",
                descripcion = "Polerón con diseño de la unidad S.T.A.R.S de Resident Evil, pensado para los fanáticos del survival horror.",
                precio = 40000.0,
                imagenUrl = "poleronstars",
                categoria = Categoria.ROPA,
                subcategoria = Subcategoria.POLERONES,
                rating = 4.5f,
                disponible = true,
                destacado = false,
                stock = 50,
                imagenesUrls = listOf("poleronstars"),
                fabricante = "Capcom",
                distribuidor = "Level-Up",
                descuento = null,
                reviews = emptyList(),
                productosRelacionados = emptyList()
            ),
            Producto(
                id = "EN001",
                nombre = "Catan",
                descripcion = "Un clásico juego de estrategia donde los jugadores compiten por colonizar y expandirse en la isla de Catan. Ideal para 3-4 jugadores y perfecto para noches de juego en familia o con amigos.",
                precio = 29990.0,
                imagenUrl = "catan",
                categoria = Categoria.ENTRETENIMIENTO,
                subcategoria = Subcategoria.JUEGOS_MESA,
                rating = 4f,
                disponible = true,
                destacado = false,
                stock = 30,
                imagenesUrls = listOf("catan"),
                fabricante = "KOSMOS",
                distribuidor = "Devir",
                descuento = 15,
                reviews = emptyList(),
                productosRelacionados = emptyList()
            ),
            Producto(
                id = "EN002",
                nombre = "Carcassonne",
                descripcion = "Un juego de colocación de fichas donde los jugadores construyen el paisaje alrededor de la fortaleza medieval de Carcassonne. Ideal para 2-5 jugadores y fácil de aprender.",
                precio = 24990.0,
                imagenUrl = "carcassone",
                categoria = Categoria.ENTRETENIMIENTO,
                subcategoria = Subcategoria.JUEGOS_MESA,
                rating = 4f,
                disponible = true,
                destacado = false,
                stock = 25,
                imagenesUrls = listOf("carcassone"),
                fabricante = "Hans im Glück",
                distribuidor = "Devir",
                descuento = 10,
                reviews = emptyList(),
                productosRelacionados = emptyList()
            )
        )
    }

    fun obtenerProductosDestacados(): List<Producto> {
        return obtenerProductos().filter { it.destacado }
    }

    fun obtenerProductoPorId(id: String): Producto? {
        return obtenerProductos().find { it.id == id }
    } //esta funcion sirve para detalle producto para buscar el producto por id al darle click
    //ojala no sea dificil implementarlo con postman luego

    fun obtenerProductosRelacionados(producto: Producto): List<Producto> {
        return obtenerProductos()//filtrar de la lista de producto y retornamos
        //de lafuncion obtener productos y filtramos por id y categoria
            .filter { it.id != producto.id && it.categoria == producto.categoria }//filter segun los parametros que le pasamos
            .shuffled()//shuffle es para mezclar la lista y q no se repitan los productos
            .take(4)//take es para tomar los primeros 4 productos y no sobrecargar
    }

    //ahora las reviews se traen de SQLite en vez de estar hardcodeadas
    suspend fun obtenerReviews(productoId: String): List<Review> { //ahora es suspend pq es una operacion asincrona
        return reviewDao?.getReviewsByProductoId(productoId) ?: listOf( //si el dao no es null, traemos las reviews de la bd
            //si es null (modo compatibilidad), retornamos datos mock como antes
            Review(
                id = "1",
                productoId = productoId, //ahora agregamos el productoId
                usuarioNombre = "Juan Pérez",
                rating = 5f,
                comentario = "Excelente producto, llegó en perfectas condiciones y funciona perfecto.",
                fecha = "2024-10-10"
            ),
            Review(
                id = "2",
                productoId = productoId,
                usuarioNombre = "María González",
                rating = 4f,
                comentario = "Muy buena calidad, aunque el envío tardó un poco más de lo esperado.",
                fecha = "2024-10-08"
            ),
            Review(
                id = "3",
                productoId = productoId,
                usuarioNombre = "Carlos Ramírez",
                rating = 5f,
                comentario = "Increíble! Superó mis expectativas. Totalmente recomendado.",
                fecha = "2024-10-05"
            )
        )
    }

    //ahora guardamos la review en SQLite
    suspend fun agregarReview(productoId: String, review: Review): Boolean { //ahora es suspend
        return try {
            reviewDao?.insertReview(review) //insertamos en la bd con el DAO
            true //si todo sale bien retornamos true
        } catch (e: Exception) {
            e.printStackTrace() //si hay error lo imprimimos en el log
            false //retornamos false si falla
        }
    }

    //funcion extra para borrar una review si el usuario quiere
    suspend fun borrarReview(review: Review): Boolean {
        return try {
            reviewDao?.deleteReview(review) //borramos de la bd
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    //funcion para obtener el rating promedio desde la bd
    suspend fun obtenerRatingPromedio(productoId: String): Float {
        return reviewDao?.getAverageRating(productoId) ?: 0f //si no hay reviews retorna 0
    }

    //funcion para contar cuantas reviews tiene un producto
    suspend fun contarReviews(productoId: String): Int {
        return reviewDao?.getReviewCount(productoId) ?: 0 //si no hay reviews retorna 0
    }
}