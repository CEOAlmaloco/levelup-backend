package com.example.levelupprueba.ui.screens.productos

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.levelupprueba.model.producto.Producto
import com.example.levelupprueba.model.producto.Review
import com.example.levelupprueba.ui.components.LevelUpProductBadge
import com.example.levelupprueba.ui.components.ProductoRatingStars
import com.example.levelupprueba.ui.components.buttons.LevelUpButton
import com.example.levelupprueba.ui.components.buttons.LevelUpOutlinedButton
import com.example.levelupprueba.ui.components.cards.LevelUpCard
import com.example.levelupprueba.ui.components.cards.ProductoCard
import com.example.levelupprueba.ui.components.common.LevelUpBadge
import com.example.levelupprueba.ui.components.common.LevelUpSpacedColumn
import com.example.levelupprueba.ui.components.inputs.LevelUpIconButton
import com.example.levelupprueba.ui.components.inputs.LevelUpOutlinedTextField
import com.example.levelupprueba.ui.components.overlays.LevelUpLoadingOverlay
import com.example.levelupprueba.ui.components.sliders.LevelUpRatingSlider
import com.example.levelupprueba.ui.theme.LocalDimens
import com.example.levelupprueba.ui.theme.SemanticColors
import com.example.levelupprueba.viewmodel.CarritoViewModel
import com.example.levelupprueba.viewmodel.ProductoDetalleViewModel
import kotlinx.coroutines.flow.first
import java.text.NumberFormat
import java.util.*

//ODIO  KOTLIN HERMANO COMO 35 LINEAS DE IMPORT ODIO KOTLIN SOBRETODO EL SCREEN DIOS MIO COMO 600 LINEAS AAA
//Q DOLOR DE CABEZA DIOS MIO COMO 600 LINEAS AAA


@RequiresApi(Build.VERSION_CODES.O)
@Composable //esta es la funcion principal del detalle, es como el contenedor de todo
fun ProductoDetalleScreen(
    productoId: String, //el id del producto q viene de la navegacion cuando le damos click
    viewModel: ProductoDetalleViewModel, //el viewmodel q maneja la logica de negocio
    carritoViewModel: CarritoViewModel,
    onProductoClick: (String) -> Unit = {}, //callback para cuando le damos click a un producto relacionado
    onNavigateBack: () -> Unit = {}, //callback para volver atras
    contentPadding: PaddingValues,
    isLoggedIn: Boolean,
    userDisplayName: String,
    userId: Long? = null
) {
    val estado by viewModel.estado.collectAsState() //traemos el estado del viewmodel, el by es para q se actualice solo
    val dimens = LocalDimens.current //traemos las dimensiones del tema para q sea responsive
    val carrito by carritoViewModel.carrito.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(productoId) { //esto se ejecuta cuando cambia el productoId, es como el useEffect de react
        viewModel.cargarProducto(productoId) //cargamos el producto con el id q nos llega
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) { //el box es como un div en html, lo usamos para apilar cosas
        when { //el when es como el switch en java, aca manejamos los 3 estados posibles
            estado.isLoading -> { //si esta cargando, mostramos el overlay de loading
                LevelUpLoadingOverlay() //este es el componente q ya teniamos hecho para el loading
            }
            estado.error != null -> { //si hay un error, mostramos el mensaje de error
                LevelUpSpacedColumn( //usamos LevelUpSpacedColumn para el espaciado automatico
                    modifier = Modifier
                        .fillMaxSize(),
                    spacing = dimens.mediumSpacing, //espaciado automatico entre elementos
                    horizontalAlignment = Alignment.CenterHorizontally //centramos horizontalmente
                ) {
                    Text(
                        text = estado.error ?: "Error desconocido", //mostramos el error o un mensaje por defecto
                        style = MaterialTheme.typography.bodyLarge,
                        color = SemanticColors.Error //usamos el color de error del tema
                    )
                    Button(onClick = onNavigateBack) { //boton para volver atras
                        Text("Volver")
                    }
                }
            }
            estado.producto != null -> { //si el producto no es null, mostramos el contenido
                val producto = estado.producto!!
                val itemEnCarrito = carrito.items.firstOrNull { it.producto.id == producto.id }
                val cantidadEnCarrito = itemEnCarrito?.cantidad ?: 0
                val itemId = itemEnCarrito?.id
                ProductoDetalleContent( //aca llamamos a la funcion q tiene todo el contenido del detalle
                    producto = producto, //le pasamos el producto, el !! es pq estamos seguros q no es null
                    cantidadEnCarrito = cantidadEnCarrito, //le pasamos la cantidad del carrito
                    itemId = itemId, //el id del item en el carrito, null si no esta en el carrito
                    carritoViewModel = carritoViewModel, //el viewmodel del carrito
                    mostrarFormularioReview = estado.mostrarFormularioReview, //si mostramos el formulario de review o no
                    imagenSeleccionada = estado.imagenSeleccionada, //la imagen seleccionada del carrusel (por si agregamos mas imagenes dsp)
                    onToggleFormularioReview = { viewModel.toggleFormularioReview() }, //callback para mostrar/ocultar el formulario
                    onAgregarReview = { rating, comentario, usuario, idUsuario -> //callback para agregar una review
                        viewModel.agregarReview(rating, comentario, usuario, idUsuario) //le pasamos los parametros al viewmodel
                    },
                    userId = userId,
                    onProductoRelacionadoClick = onProductoClick, //callback para cuando le dan click a un producto relacionado
                    userDisplayName = userDisplayName,
                    isLoggedIn = isLoggedIn
                )
            }
        }
    }
}
//hasta aca es la funcion principal, lo demas son las secciones q se muestran en el detalle

@OptIn(ExperimentalMaterial3Api::class) //esto es pq usamos apis experimentales de material3, si no kotlin se enoja
@Composable //aca empieza el contenido real del detalle, es como el body del html
private fun ProductoDetalleContent( //esta funcion tiene TODO el contenido del detalle, por eso son 600 lineas jaja
    producto: Producto, //el producto completo con toda la info
    cantidadEnCarrito: Int, //cuantos productos hay en el carrito
    itemId: String?,
    carritoViewModel: CarritoViewModel,
    mostrarFormularioReview: Boolean, //si mostramos el formulario de review
    imagenSeleccionada: Int, //la imagen seleccionada (por si agregamos mas imagenes)
    onToggleFormularioReview: () -> Unit, //cuando le dan al boton de calificar
    onAgregarReview: (Float, String, String, Long?) -> Unit, //cuando envian una review
    onProductoRelacionadoClick: (String) -> Unit, //cuando le dan click a un producto relacionado
    userDisplayName: String,
    isLoggedIn: Boolean,
    userId: Long?
) {
    val dimens = LocalDimens.current //dimensiones del tema
    val context = LocalContext.current //el contexto de android, lo necesitamos para compartir y eso

    LazyColumn( //usamos LazyColumn q es como un scroll vertical q solo renderiza lo visible, es como el virtualizado en react
        modifier = Modifier
            .fillMaxSize()
    ) {
        item { //cada item es una seccion del detalle
            LevelUpSpacedColumn( //usamos LevelUpSpacedColumn para espaciado automatico entre todas las secciones
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimens.screenPadding), //padding de pantalla del tema
                spacing = dimens.sectionSpacing, //espaciado automatico entre secciones
                horizontalAlignment = Alignment.Start //alineamos todo a la izquierda
            ) {
                SeccionVisual( //la imagen grande del producto arriba de todo
                    imagenUrl = producto.imagenUrl,
                    nombre = producto.nombre,
                    dimens = dimens
                )

                SeccionInformacion( //el titulo, precio, rating, descripcion, todo eso
                    producto = producto,
                    dimens = dimens
                )

                SeccionOrigen( //el fabricante y distribuidor, como pedia el caso
                    fabricante = producto.fabricante ?: "-", //si es null ponemos "-"
                    distribuidor = producto.distribuidor ?: "-",
                    dimens = dimens
                )

                SeccionAccionesRealtime( //los botones de agregar al carrito y los + y -
                    producto = producto,
                    cantidadEnCarrito = cantidadEnCarrito,
                    itemId = itemId,
                    carritoViewModel = carritoViewModel,
                    dimens = dimens
                )

                SeccionCompartir(context = context, producto = producto, dimens = dimens) //los botones para compartir en redes sociales
            }
        }

        if (producto.productosRelacionados.isNotEmpty()) { //si hay productos relacionados, los mostramos
            item {
                SeccionRelacionados( //el carrusel horizontal de productos similares
                    productos = producto.productosRelacionados,
                    onProductoClick = onProductoRelacionadoClick,
                    dimens = dimens,
                    onAgregarAlCarro = { carritoViewModel.onAgregar(it) }
                )
                Spacer(modifier = Modifier.height(dimens.sectionSpacing)) //espaciado al final
            }
        }

        item { //las reviews siempre se muestran, aunque no haya ninguna
            SeccionReviews( //la seccion de reseñas y calificaciones
                reviews = producto.reviews,
                ratingPromedio = producto.ratingPromedio,
                mostrarFormulario = mostrarFormularioReview,
                onToggleFormulario = onToggleFormularioReview,
                onAgregarReview = onAgregarReview,
                userId = userId,
                dimens = dimens,
                usuarioNombre = userDisplayName,
                isLoggedIn = isLoggedIn
            )
        }
    }
}
//ACA TERMINAN LAS FUNCIONES PRINCIPALES, AHORA VIENEN LAS SECCIONES INDIVIDUALES

//SECCION VISUAL - LA IMAGEN GRANDE DEL PRODUCTO
@Composable //esta funcion muestra la imagen principal del producto en un card bonito
private fun SeccionVisual(
    imagenUrl: String, //URL de S3, Base64, o referencia a drawable
    nombre: String, //el nombre del producto para accesibilidad
    dimens: com.example.levelupprueba.ui.theme.Dimens //dimensiones del tema
) {
    val context = LocalContext.current
    
    // Logs para debugging
    android.util.Log.d("ProductoDetalleScreen", "SeccionVisual - Nombre: $nombre")
    android.util.Log.d("ProductoDetalleScreen", "SeccionVisual - Imagen original: $imagenUrl")
    
    // Usar MediaUrlResolver para resolver la URL de la imagen (S3, HTTP, Base64, etc.)
    val resolvedImageUrl = com.example.levelupprueba.data.remote.MediaUrlResolver.resolve(imagenUrl)
    android.util.Log.d("ProductoDetalleScreen", "SeccionVisual - Imagen resuelta: $resolvedImageUrl")
    
    // Si no se resolvió, intentar buscar en drawable como fallback
    val imageData = if (resolvedImageUrl.isNotBlank()) {
        resolvedImageUrl
    } else if (imagenUrl.isNotBlank()) {
        // Fallback: buscar en drawable resources
        val imageResourceId = context.resources.getIdentifier(
            imagenUrl,
            "drawable",
            context.packageName
        )
        if (imageResourceId != 0) imageResourceId else null
    } else {
        null
    }

    Card( //envolvemos la imagen en un card para q tenga sombra y bordes redondeados, usamos Card de material3 pq LevelUpCard tiene padding interno
        modifier = Modifier
            .fillMaxWidth()
            .height(dimens.imageHeight), //altura desde las dimensiones del tema
        shape = RoundedCornerShape(dimens.imageCornerRadius), //bordes redondeados desde el tema
        elevation = CardDefaults.cardElevation(defaultElevation = dimens.cardElevation), //sombra desde el tema
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        if (imageData != null) {
            AsyncImage( //usamos coil para cargar las imagenes de forma asincrona
                model = ImageRequest.Builder(context)
                    .data(imageData) //le pasamos la URL de S3, Base64, o drawable resource
                    .crossfade(true) //animacion de fade cuando carga
                    .build(),
                contentDescription = nombre, //para accesibilidad
                contentScale = ContentScale.Fit, //recorta la imagen para q llene todo el espacio
                modifier = Modifier.fillMaxSize()
            )
        } else {
            // Placeholder si no hay imagen
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Sin imagen",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
//FIN SECCION VISUAL

//SECCION INFORMACION - NOMBRE, PRECIO, RATING, DESCRIPCION
@Composable //aca mostramos toda la info principal del producto
private fun SeccionInformacion(
    producto: Producto, //el producto completo
    dimens: com.example.levelupprueba.ui.theme.Dimens //dimensiones del tema
) {

    // Categoría - Subcategoría
    val categoriaDisplay = producto.categoriaNombre ?: producto.categoria.nombre
    val subcategoriaDisplay = producto.subcategoriaNombre ?: producto.subcategoria?.nombre
    val subcategoriaTexto = subcategoriaDisplay?.let { "$categoriaDisplay - $it" } ?: categoriaDisplay

    // Logs para debugging
    android.util.Log.d("ProductoDetalleScreen", "SeccionInformacion - Producto: ${producto.nombre}")
    android.util.Log.d("ProductoDetalleScreen", "SeccionInformacion - Rating: ${producto.rating}, RatingPromedio: ${producto.ratingPromedio}")
    android.util.Log.d("ProductoDetalleScreen", "SeccionInformacion - Descripción: ${producto.descripcion.take(50)}...")

    LevelUpSpacedColumn( //usamos LevelUpSpacedColumn para espaciado automatico
        spacing = dimens.fieldSpacing, //espaciado entre campos
        horizontalAlignment = Alignment.Start
    ) {

        LevelUpBadge( //el codigo del producto chiquitito
            text = producto.codigo ?: producto.id,
            textColor = MaterialTheme.colorScheme.onSurface.copy(0.60f),
            backgroundColor = MaterialTheme.colorScheme.surface.copy(0.80f)
        )

        Text( //el nombre del producto bien grande y bold
            text = producto.nombre,
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = dimens.titleSize),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text( //La categoria y subcategoria del producto.
            text = subcategoriaTexto,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = dimens.subtitleSize),
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onSurface
        )

        Row( //el rating con las estrellas y el numero
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Usar ratingPromedio si es > 0, sino usar rating base (igual que ProductoCard)
            val rating = if (producto.ratingPromedio > 0f) producto.ratingPromedio else producto.rating
            ProductoRatingStars( //componente q hicimos para mostrar las estrellas
                rating = rating, //el rating con fallback
                starSize = dimens.iconSize, //tamaño de icono pequeño del tema
                tint = SemanticColors.AccentYellow
            )
            Spacer(modifier = Modifier.width(dimens.smallSpacing))
            Text( //el numero del rating al lado de las estrellas
                text = String.format("%.1f", rating), //formateamos a 1 decimal
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = dimens.bodySize),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Row( //el precio y el descuento si hay
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Precio actual: con descuento si existe, sino el normal
            val precioFinal = if ((producto.descuento ?: 0) > 0) producto.precioConDescuento ?: producto.precio else producto.precio

            Text(
                text = NumberFormat.getCurrencyInstance(Locale("es", "CL")).format(precioFinal),
                style = MaterialTheme.typography.headlineLarge.copy(fontSize = dimens.titleSize),
                fontWeight = FontWeight.Bold,
                color = if ((producto.descuento ?: 0) > 0) SemanticColors.AccentGreenSoft else MaterialTheme.colorScheme.onBackground,
                fontSize = 32.sp
            )

            // Si hay descuento, muestra el badge y el precio original tachado
            if ((producto.descuento ?: 0) > 0) {
                Spacer(modifier = Modifier.width(dimens.mediumSpacing))
                LevelUpBadge(
                    text = "-${producto.descuento}%",
                    backgroundColor = SemanticColors.AccentRed
                )
                Spacer(modifier = Modifier.width(dimens.smallSpacing))
                Text(
                    text = NumberFormat.getCurrencyInstance(Locale("es", "CL")).format(producto.precio),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textDecoration = TextDecoration.LineThrough,
                        fontSize = dimens.captionSize
                    )
                )
            }
        }

        Text( //la descripcion del producto
            text = producto.descripcion.takeIf { it.isNotBlank() } ?: "Descripción no disponible",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            lineHeight = 24.sp //espaciado entre lineas para q se lea mejor
        )
    }
}
//FIN SECCION INFORMACION

//SECCION ORIGEN - FABRICANTE Y DISTRIBUIDOR
@Composable //esta seccion la pidio el caso, muestra de donde viene el producto
private fun SeccionOrigen(
    fabricante: String, //el fabricante del producto
    distribuidor: String, //el distribuidor del producto
    dimens: com.example.levelupprueba.ui.theme.Dimens //dimensiones del tema
) {
    LevelUpCard( //usamos LevelUpCard que ya tiene los estilos del tema
        paddingValues = PaddingValues(dimens.mediumSpacing) //padding interno del card
    ) {
        LevelUpSpacedColumn( //columna con espaciado automatico
            spacing = dimens.fieldSpacing,
            horizontalAlignment = Alignment.Start
        ) {
            Text( //el titulo de la seccion
                text = "Origen del producto",
                style = MaterialTheme.typography.titleMedium.copy(fontSize = dimens.titleSize),
                fontWeight = FontWeight.Bold
            )

            Row { //fabricante en bold y el valor normal
                Text(
                    text = "Fabricante: ",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = dimens.subtitleSize),
                    fontWeight = FontWeight.Bold
                )
                Text( //el nombre del fabricante
                    text = fabricante,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = dimens.bodySize)
                )
            }

            Row { //distribuidor en bold y el valor normal
                Text(
                    text = "Distribuidor: ",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = dimens.subtitleSize),
                    fontWeight = FontWeight.Bold
                )
                Text( //el nombre del distribuidor
                    text = distribuidor,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = dimens.bodySize)
                )
            }
        }
    }
}
//FIN SECCION ORIGEN

//SECCION ACCIONES - BOTON AGREGAR AL CARRITO Y CONTROLES DE CANTIDAD
@Composable //esta es la parte mas importante, donde el usuario agrega productos al carrito
private fun SeccionAccionesRealtime(
    producto: Producto,
    cantidadEnCarrito: Int,
    itemId: String?,
    carritoViewModel: CarritoViewModel,
    dimens: com.example.levelupprueba.ui.theme.Dimens
) {
    Column {
        if (cantidadEnCarrito == 0) {
            LevelUpButton(
                text = if (producto.disponible) "Agregar al carrito" else "AGOTADO",
                onClick = { carritoViewModel.onAgregar(producto) },
                dimens = dimens,
                enabled = producto.disponible,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(dimens.mediumSpacing),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilledIconButton(
                    onClick = { itemId?.let { carritoViewModel.onDecrement(it) } },
                    modifier = Modifier.size(dimens.buttonHeight),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = SemanticColors.AccentBlue
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Remove,
                        contentDescription = "Disminuir",
                        modifier = Modifier.size(dimens.buttonIconSize),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }

                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    shape = RoundedCornerShape(dimens.cardCornerRadius)
                ) {
                    Text(
                        text = cantidadEnCarrito.toString(),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = dimens.smallSpacing),
                        textAlign = TextAlign.Center
                    )
                }

                FilledIconButton(
                    onClick = { itemId?.let { carritoViewModel.onIncrement(it) } },
                    modifier = Modifier.size(dimens.buttonHeight),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = SemanticColors.AccentBlue
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Aumentar",
                        modifier = Modifier.size(dimens.buttonIconSize),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}
//FIN SECCION ACCIONES - esta es la parte q conecta con el carrito, despues hay q hacer el carrito completo TODO


//SECCION RELACIONADOS - CARRUSEL HORIZONTAL DE PRODUCTOS SIMILARES
@Composable //esta seccion muestra productos relacionados para q el usuario siga comprando jaja
private fun SeccionRelacionados(
    productos: List<Producto>, //la lista de productos relacionados
    onProductoClick: (String) -> Unit, //callback para cuando le dan click a un producto
    onAgregarAlCarro: (Producto) -> Unit, //callback para agregar un producto al carrito
    dimens: com.example.levelupprueba.ui.theme.Dimens //dimensiones del tema
) {
    LevelUpSpacedColumn( //columna con espaciado automatico
        modifier = Modifier.padding(horizontal = dimens.screenPadding),
        spacing = dimens.mediumSpacing,
        horizontalAlignment = Alignment.Start
    ) {
        Text( //titulo de la seccion
            text = "Productos relacionados",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        LazyRow( //un scroll horizontal de productos, como un carrusel
            horizontalArrangement = Arrangement.spacedBy(dimens.mediumSpacing) //espaciado entre productos desde el tema
        ) {
            items(productos) { producto -> //por cada producto relacionado
                ProductoCard( //reutilizamos el ProductoCard q ya teniamos
                    producto = producto,
                    onClick = { onProductoClick(producto.id) }, //cuando le dan click navegamos al detalle de ese producto
                    modifier = Modifier.width(250.dp), //ancho fijo para q se vean bien en el carrusel
                    onAgregarAlCarro = onAgregarAlCarro
                ) //esto hace q cuando estes viendo un producto puedas ir a otro y asi infinitamente
            }
        }
    }
}
//FIN SECCION RELACIONADOS - esto esta bueno para q el usuario compre mas cosas

//SECCION REVIEWS - RESEÑAS Y CALIFICACIONES DE LOS USUARIOS
@Composable //esta es la seccion mas compleja, tiene el form para agregar reviews y la lista de reviews
private fun SeccionReviews(
    reviews: List<Review>, //la lista de reviews del producto
    ratingPromedio: Float, //el rating promedio calculado
    mostrarFormulario: Boolean, //si mostramos el formulario de agregar review
    onToggleFormulario: () -> Unit, //callback para mostrar/ocultar el formulario
    onAgregarReview: (Float, String, String, Long?) -> Unit, //callback para agregar una review
    userId: Long?,
    dimens: com.example.levelupprueba.ui.theme.Dimens, //dimensiones del tema
    usuarioNombre: String,
    isLoggedIn: Boolean
) {
    LevelUpSpacedColumn( //columna con espaciado automatico
        modifier = Modifier.padding(horizontal = dimens.screenPadding),
        spacing = dimens.sectionSpacing,
        horizontalAlignment = Alignment.Start
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Row( //header con el titulo, rating y boton de calificar
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween, //separamos el titulo del boton
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Reseñas y calificaciones",
                    style = MaterialTheme.typography.headlineSmall.copy(fontSize = dimens.titleSize),
                    fontWeight = FontWeight.Bold
                )

                Crossfade(
                    targetState = mostrarFormulario, label = "AnimIcon"
                ) { isOpen ->
                    LevelUpIconButton(
                        contentDescription = if (isOpen) "Cerrar formulario" else "Calificar producto",
                        onClick = onToggleFormulario,
                        imageVector = if (isOpen) Icons.Default.Close else Icons.Default.Add
                    )
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) { //estrellas y numero del rating
                ProductoRatingStars(
                    rating = ratingPromedio,
                    starSize = dimens.iconSize,
                    tint = SemanticColors.AccentYellow) //estrellas desde el tema
                Spacer(modifier = Modifier.width(dimens.smallSpacing))
                Text( //el numero del rating al lado
                    text = String.format("%.1f", ratingPromedio),
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = dimens.subtitleSize),
                    fontWeight = FontWeight.Bold
                )
            }
        }

        AnimatedVisibility(
            visible = mostrarFormulario,
            enter = fadeIn(),
            exit = fadeOut()
        ) {  //si el usuario le dio al boton, mostramos el formulario con animacion
            FormularioReview(
                onAgregarReview = onAgregarReview,
                dimens = dimens,
                usuarioNombre = usuarioNombre,
                userId = userId,
                isLoggedIn = isLoggedIn,
            ) //el formulario para agregar una review
        }

        if (reviews.isEmpty()) { //si no hay reviews mostramos un mensaje
            LevelUpCard( //usamos LevelUpCard con los estilos del tema
                paddingValues = PaddingValues(dimens.mediumSpacing)
            ) {
                Text( //mensaje motivando a ser el primero en opinar
                    text = "Sin reseñas aún. ¡Sé el primero en opinar!",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = dimens.bodySize),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else { //si hay reviews las mostramos
            reviews.reversed().forEach { review -> //las invertimos para mostrar las mas recientes primero
                ReviewCard(review = review, dimens = dimens) //cada review en su propio card
                Spacer(modifier = Modifier.height(dimens.mediumSpacing))
            }
        }
    }
}
//FIN SECCION REVIEWS - esto esta bueno pq le da confianza a los usuarios q compren

//FORMULARIO REVIEW - FORM PARA Q EL USUARIO AGREGUE SU OPINION
@OptIn(ExperimentalMaterial3Api::class) //usamos apis experimentales
@Composable //este es el formulario q aparece cuando le dan al boton de calificar
private fun FormularioReview(
    onAgregarReview: (Float, String, String, Long?) -> Unit, //callback para enviar la review
    usuarioNombre: String,
    userId: Long?,
    isLoggedIn: Boolean,
    dimens: com.example.levelupprueba.ui.theme.Dimens //dimensiones del tema
) {
    var rating by remember { mutableStateOf(5f) } //estado local del rating, empieza en 5
    var comentario by remember { mutableStateOf("") } //estado local del comentario

    LevelUpCard( //usamos LevelUpCard con los estilos del tema
        paddingValues = PaddingValues(dimens.mediumSpacing)
    ) {
        LevelUpSpacedColumn( //columna con espaciado automatico
            spacing = dimens.sectionSpacing,
            horizontalAlignment = Alignment.Start
        ) {
            if (isLoggedIn){
                Text( //label del slider
                    text = "Tu calificación",
                    style = MaterialTheme.typography.labelMedium.copy(fontSize = dimens.bodySize)
                )

                LevelUpRatingSlider( //slider para seleccionar el rating de 1 a 5
                    value = rating, //el valor actual
                    onValueChange = { rating = it }, //cuando cambia actualizamos el estado
                    valueRange = 1f..5f, //rango de 1 a 5
                    steps = 3, //4 pasos (1, 2, 3, 4, 5),
                )

                LevelUpOutlinedTextField( //campo de texto para el comentario
                    value = comentario,
                    onValueChange = { comentario = it }, //cuando escriben actualizamos el estado
                    label = "Tu reseña",
                    placeholder = { Text("Cuéntanos tu experiencia") },
                    minLines = 3 //minimo 3 lineas de alto
                )

                LevelUpButton( //boton para enviar la review
                    onClick = {
                        if (comentario.isNotBlank()) { //validamos q no este vacio
                            onAgregarReview(
                                rating,
                                comentario,
                                usuarioNombre.ifBlank { "Usuario" },
                                userId
                            ) //enviamos la review al viewmodel con el id del usuario
                            comentario = "" //limpiamos el form
                            rating = 5f //volvemos al rating por defecto
                        } //TODO: agregar validacion de q el usuario este logueado
                    },
                    text = "Enviar reseña",
                    enabled = comentario.isNotBlank(),
                    modifier = Modifier
                        .fillMaxWidth()
                )
            } else {
                Text(
                    text = "Debes iniciar sesión para reseñar",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = dimens.bodySize),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
//FIN FORMULARIO REVIEW - despues hay q conectar esto con el usuario logueado

//REVIEW CARD - CARD INDIVIDUAL PARA CADA REVIEW
@Composable //este componente muestra una review individual
private fun ReviewCard(
    review: Review, //recibe una review para mostrar
    dimens: com.example.levelupprueba.ui.theme.Dimens //dimensiones del tema
) {
    LevelUpCard( //usamos LevelUpCard con los estilos del tema
        paddingValues = PaddingValues(dimens.mediumSpacing)
    ) {
        LevelUpSpacedColumn( //columna con espaciado automatico
            spacing = dimens.smallSpacing,
            horizontalAlignment = Alignment.Start
        ) {
            Row( //header con las estrellas y la fecha
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween, //separamos las estrellas de la fecha
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProductoRatingStars(
                    rating = review.rating,
                    starSize = dimens.smallIconSize,
                    tint = SemanticColors.AccentYellow
                ) //las estrellas del rating
                Text( //la fecha de la review
                    text = review.fecha,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Text( //el nombre del usuario q hizo la review
                text = review.usuarioNombre,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )

            Text( //el comentario de la review
                text = review.comentario,
                style = MaterialTheme.typography.bodyMedium,
                lineHeight = 20.sp //espaciado entre lineas
            )
        }
    }
}
//FIN REVIEW CARD - cada review se ve asi, simple y claro

//SECCION COMPARTIR - botones para compartir y contactar soporte
@Composable
private fun SeccionCompartir(
    context: android.content.Context,
    producto: Producto,
    dimens: com.example.levelupprueba.ui.theme.Dimens
) {
    LevelUpCard(
        paddingValues = PaddingValues(dimens.mediumSpacing)
    ) {
        LevelUpSpacedColumn(
            spacing = dimens.smallSpacing,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Soporte y Compartir",
                style = MaterialTheme.typography.titleMedium.copy(fontSize = dimens.titleSize),
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = "¿Necesitas ayuda con este producto?",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = dimens.bodySize),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(dimens.mediumSpacing)
            ) {
                // Botón WhatsApp para soporte
                LevelUpOutlinedButton(
                    icon = Icons.Default.Message,
                    text = "Whatsapp",
                    onClick = {
                        val mensaje = "Hola, necesito ayuda con el producto: ${producto.nombre}. ¿Podrían asistirme?"
                        val numeroWhatsApp = "+56912345678" // Número de soporte (cambiar por el real)
                        val url = "https://wa.me/$numeroWhatsApp?text=${Uri.encode(mensaje)}"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                    },
                    modifier = Modifier.weight(1f),
                )
                // Botón para compartir producto
                LevelUpOutlinedButton(
                    icon = Icons.Default.Share,
                    text = "Compartir",
                    onClick = {
                        val mensaje = "¡Mira este producto increíble: ${producto.nombre}! ${producto.descripcion}"
                        val intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, mensaje)
                            type = "text/plain"
                        }
                        context.startActivity(Intent.createChooser(intent, "Compartir producto"))
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
//FIN SECCION COMPARTIR

//Y ACA TERMINA TODO EL ARCHIVO, SON COMO 600 LINEAS PERO AHORA ESTA TODO COMENTADO
//ESPERO Q SE ENTIENDA MEJOR AHORA, ES BASICAMENTE COMO EL HTML PERO EN KOTLIN CON MAS COMPLICACIONES JAJA
