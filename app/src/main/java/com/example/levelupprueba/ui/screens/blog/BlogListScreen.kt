package com.example.levelupprueba.ui.screens.blog

// IMPORTAMOS LITERALMENTE TODO
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.levelupprueba.model.blog.Blog
import com.example.levelupprueba.model.blog.FiltroBlog
import com.example.levelupprueba.ui.components.buttons.MenuButton
import com.example.levelupprueba.ui.components.levelUpFilterChipColors
import com.example.levelupprueba.ui.theme.LocalDimens
import com.example.levelupprueba.ui.theme.SemanticColors
import com.example.levelupprueba.viewmodel.BlogViewModel

@Composable//al decir esto estamos diciendo que se puede reutilizar en otra pantalla
fun BlogListScreen(//aca creamos la pantalla de blogs
    viewModel: BlogViewModel,//aca le pasamos el viewmodel para que se pueda usar en la pantalla
    contentPadding: PaddingValues
) {
    val estado by viewModel.estado.collectAsState()//el by sirve como un for , recorre el estado y te dice el estado actual
    val dimens = LocalDimens.current//el current es para obtener el valor actual de las dimensiones

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .padding(horizontal = dimens.screenPadding, vertical = dimens.screenPadding)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(dimens.fieldSpacing)
    ) {
        // Hero Section
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Gaming hub",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Tu portal de noticias y guías de videojuegos",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
        }

        // Filtros
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(FiltroBlog.values()) { filtro ->
                FilterChip(
                    selected = estado.filtroActivo == filtro.valor,
                    onClick = { viewModel.cambiarFiltro(filtro.valor) },
                    label = { Text(filtro.etiqueta) },
                    colors = levelUpFilterChipColors()
                )
            }
        }


        // Grid de Blogs
        val blogsFiltrados = viewModel.obtenerBlogsFiltrados()
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            blogsFiltrados.forEach { blog ->
                BlogCard(blog = blog)
            }
        }

        // Sección Impacto Comunitario
        Column(
            modifier = Modifier.padding(top = 32.dp)
        ) {
            Text(
                text = "Impacto en la comunidad gamer",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Tus compras ayudan a financiar eventos locales, premios para torneos estudiantiles y talleres de introducción al desarrollo de videojuegos. Al apoyar a Level-Up, apoyas a la comunidad gamer chilena.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = MaterialTheme.typography.bodyMedium.lineHeight
            )
        }
    }

    // Loading Overlay
    if (estado.isLoading) {

    }
}

@Composable
fun BlogCard(blog: Blog) {//aca creamos la funcion para el blog card
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)//el elevation es para que el card tenga una sombra
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
        ) {
            // Imagen del blog
            Box(//dentro de la columna creamos un box para la imagen
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                // Usar MediaUrlResolver para resolver la URL de la imagen (S3, HTTP, Base64, drawable)
                val context = LocalContext.current
                val resolvedImageUrl = com.example.levelupprueba.data.remote.MediaUrlResolver.resolve(blog.imagenUrl)
                
                Log.d("BlogCard", "Blog: ${blog.titulo}")
                Log.d("BlogCard", "Imagen original: ${blog.imagenUrl}")
                Log.d("BlogCard", "Imagen resuelta: $resolvedImageUrl")
                
                // Si no se resolvió, intentar buscar en drawable como fallback
                val imageData = if (resolvedImageUrl.isNotBlank()) {
                    resolvedImageUrl
                } else if (blog.imagenUrl.isNotBlank()) {
                    val imageResourceId = context.resources.getIdentifier(
                        blog.imagenUrl,
                        "drawable",
                        context.packageName
                    )
                    if (imageResourceId != 0) imageResourceId else null
                } else {
                    null
                }

                if (imageData != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(imageData)
                            .crossfade(true)
                            .error(android.R.drawable.ic_menu_report_image)
                            .placeholder(android.R.drawable.ic_menu_gallery)
                            .build(),
                        contentDescription = blog.titulo,
                        contentScale = ContentScale.Crop,
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

                // Etiqueta de categoría
                Card(
                    modifier = Modifier
                        .padding(12.dp)
                        .align(Alignment.TopEnd),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = blog.categoria.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            // Contenido del blog
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                // Metadatos (fecha y autor)
                Row(//dentro de la columna en una fila se muestran los metadatos
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(//aca mostramos el icono de la fecha, un vector es como un svg
                            imageVector = Icons.Filled.CalendarToday,
                            contentDescription = "Fecha",
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = blog.fechaPublicacion,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Autor",
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = blog.autor,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                //vamos parametrizando el contenido del blog
                //por secciones o desfragmentando por partes de las interfaces

                Spacer(modifier = Modifier.height(8.dp))

                // Título del blog
                Text(
                    text = blog.titulo,//importamos el titulo del blog desde el blog
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Resumen del blog
                Text(
                    text = blog.resumen,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = MaterialTheme.typography.bodyMedium.lineHeight
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Botón "Leer más"
                MenuButton(
                    text = "Leer más",
                    containerColor = SemanticColors.AccentBlue,
                    contentColor = MaterialTheme.colorScheme.onBackground,
                    onClick = { /* TODO: Navegar a detalle del blog */ },//cuando le hacemos click le enviamos el enlace
                    modifier = Modifier
                        .fillMaxWidth()//hay q achicar este
                )
            }
        }
    }
}
