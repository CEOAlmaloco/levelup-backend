package com.example.levelupprueba.ui.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.levelupprueba.model.producto.Producto
import com.example.levelupprueba.ui.components.LevelUpProductBadge
import com.example.levelupprueba.ui.components.buttons.LevelUpButton
import com.example.levelupprueba.ui.theme.Dimens
import com.example.levelupprueba.ui.theme.LocalDimens
import com.example.levelupprueba.ui.theme.SemanticColors
import com.example.levelupprueba.utils.formatCLP
import kotlinx.coroutines.delay
import java.text.NumberFormat
import java.util.*

@Composable
fun ProductoCard(
    producto: Producto,
    onAgregarAlCarro: (Producto) -> Unit,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    dimens: Dimens = LocalDimens.current
) {
    val ratingInt = producto.ratingPromedio.toInt()
    val context = LocalContext.current
    val imageResId = context.resources.getIdentifier(
        producto.imagenUrl,
        "drawable",
        context.packageName
    )

    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(dimens.cardElevation),
        shape = RoundedCornerShape(dimens.cardCornerRadius),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Imagen del producto cubriendo toda la parte superior
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(4f/ 3f)
                    .background(MaterialTheme.colorScheme.onBackground)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(imageResId)
                        .crossfade(true)
                        .build(),
                    contentDescription = producto.nombre,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Badge de disponibilidad/agotado en la esquina superior derecha
                LevelUpProductBadge(
                    modifier = Modifier
                        .align(Alignment.TopEnd),
                    text = if (producto.disponible) "Disponible" else "Agotado",
                    backgroundColor = if (producto.disponible)
                        SemanticColors.AccentGreenSoft.copy(0.85f)
                    else
                        SemanticColors.AccentRedSoft.copy(0.85f)
                )

                // Badge descuento (si hay) en la esquina superior izquierda
                if ((producto.descuento ?: 0) > 0) {
                    LevelUpProductBadge(
                        modifier = Modifier
                            .align(Alignment.TopStart),
                        text = "${producto.descuento}% OFF",
                        backgroundColor = SemanticColors.AccentRedSoft.copy(0.85f),
                    )
                }

                // Código del producto en la esquina inferior izquierda (opcional)
                LevelUpProductBadge(
                    modifier = Modifier
                        .align(Alignment.BottomStart),
                    text = producto.id,
                    backgroundColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    font = FontWeight.Medium
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimens.mediumSpacing)
            ) {
                producto.fabricante?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.labelMedium.copy(fontSize = dimens.captionSize),
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(dimens.smallSpacing))
                }
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = dimens.titleSize),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .basicMarquee(
                            iterations = Int.MAX_VALUE,
                            repeatDelayMillis = 1000,
                            initialDelayMillis = 0,
                        ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                val subcategoriaTexto = producto.subcategoria?.let { sub ->
                    "${producto.categoria.nombre} - ${sub.nombre}"
                } ?: producto.categoria.nombre
                Text(
                    text = subcategoriaTexto,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = dimens.bodySize),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .fillMaxWidth()
                        .basicMarquee(
                            iterations = Int.MAX_VALUE,
                            repeatDelayMillis = 1000,
                            initialDelayMillis = 0,
                        ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(dimens.mediumSpacing))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    repeat(5) { i ->
                        Icon(
                            imageVector = if (i < ratingInt) Icons.Default.Star else Icons.Outlined.StarOutline,
                            contentDescription = "Rating",
                            tint = SemanticColors.AccentYellow,
                            modifier = Modifier.size(dimens.smallIconSize)
                        )
                    }
                    Spacer(modifier = Modifier.width(dimens.smallSpacing))
                    Text(
                        text = String.format("%.1f", producto.ratingPromedio) +
                                if (producto.reviews.isNotEmpty()) " (${producto.reviews.size})" else "",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = dimens.captionSize),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.height(dimens.mediumSpacing))
                Text(
                    text = formatCLP(producto.precioConDescuento ?: producto.precio),
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = dimens.titleSize),
                    fontWeight = FontWeight.Bold,
                    color = SemanticColors.AccentGreenSoft
                )
                if ((producto.descuento ?: 0) > 0) {
                    Text(
                        text = formatCLP(producto.precio),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textDecoration = TextDecoration.LineThrough,
                            fontSize = dimens.bodySize
                        ),
                        modifier = Modifier.padding(top = 2.dp)
                    )
                } else {
                    Spacer(modifier = Modifier.height(21.dp))
                }
                Spacer(modifier = Modifier.height(dimens.mediumSpacing))
                Spacer(modifier = Modifier.weight(1f))
                LevelUpButton(
                    onClick = { onAgregarAlCarro(producto) },
                    icon = Icons.Default.ShoppingCart,
                    text = if (producto.disponible) "Agregar" else "Sin stock",
                    enabled = producto.disponible,
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}
