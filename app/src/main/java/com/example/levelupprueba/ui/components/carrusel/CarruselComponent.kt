package com.example.levelupprueba.ui.components.carrusel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.levelupprueba.model.producto.ImagenCarrusel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CarruselComponent(
    imagenes: List<ImagenCarrusel>,
    modifier: Modifier = Modifier,
    autoPlay: Boolean = true,
    autoPlayDelay: Long = 5000L
) {
    if (imagenes.isEmpty()) return

    val pagerState = rememberPagerState(pageCount = { imagenes.size })
    val scope = rememberCoroutineScope()

    // Auto-play
    LaunchedEffect(pagerState.currentPage) {
        if (autoPlay) {
            delay(autoPlayDelay)
            val nextPage = (pagerState.currentPage + 1) % imagenes.size
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            CarruselItem(imagen = imagenes[page])
        }

        // boton Atras
        IconButton(
            onClick = {
                scope.launch {
                    val prevPage = if (pagerState.currentPage > 0)
                        pagerState.currentPage - 1
                    else
                        imagenes.size - 1
                    pagerState.animateScrollToPage(prevPage)
                }
            },
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 16.dp)
                .size(48.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = Icons.Filled.ChevronLeft,
                contentDescription = "Anterior",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        // boton Adelante
        IconButton(
            onClick = {
                scope.launch {
                    val nextPage = (pagerState.currentPage + 1) % imagenes.size
                    pagerState.animateScrollToPage(nextPage)
                }
            },
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp)
                .size(48.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = "Siguiente",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        // indicadores de puntos
        Row(
            Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(imagenes.size) { iteration ->
                val color = if (pagerState.currentPage == iteration)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(8.dp)
                )
            }
        }
    }
}

@Composable
fun CarruselItem(
    imagen: ImagenCarrusel,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(16.dp))
    ) {
        val context = LocalContext.current
        val imageResourceId = context.resources.getIdentifier(
            imagen.imagenUrl,
            "drawable",
            context.packageName
        )

        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(imageResourceId)
                .crossfade(true)
                .build(),
            contentDescription = imagen.titulo,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // gradiente oscuro para texto legible
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.7f)
                        ),
                        startY = 300f
                    )
                )
        )

        // texto superpuesto
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(24.dp)
        ) {
            Text(
                text = imagen.titulo,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = imagen.descripcion,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.9f)
            )
        }
    }
}

