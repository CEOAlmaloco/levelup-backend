package com.example.levelupprueba.ui.screens.carrito

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.levelupprueba.data.remote.MediaUrlResolver
import com.example.levelupprueba.model.carrito.CarritoItem
import com.example.levelupprueba.ui.components.cards.LevelUpCard
import com.example.levelupprueba.ui.theme.BrandSuccess
import com.example.levelupprueba.ui.theme.Dimens
import com.example.levelupprueba.ui.theme.LocalDimens
import com.example.levelupprueba.ui.theme.TextHigh
import com.example.levelupprueba.ui.theme.TextMedium
import com.example.levelupprueba.utils.formatCLP

// Fila compacta de ítem de carrito: imagen pequeña, nombre, descripción, precio real, cantidad, eliminar
@Composable
fun CarritoItemRow(
    item: CarritoItem,
    onIncrement: (itemId: String) -> Unit,
    onDecrement: (itemId: String) -> Unit,
    onRemove: (itemId: String) -> Unit,
    modifier: Modifier = Modifier,
    dimens: Dimens = LocalDimens.current
) {
    val context = LocalContext.current
    val cs = MaterialTheme.colorScheme

    LevelUpCard {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = dimens.mediumSpacing, vertical = dimens.smallSpacing),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen pequeña (lado izquierdo)
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                val resolvedImageUrl = MediaUrlResolver.resolve(item.producto.imagenUrl)
                val imageData = if (resolvedImageUrl.isNotBlank()) {
                    resolvedImageUrl
                } else if (item.producto.imagenUrl.isNotBlank()) {
                    val imageResourceId = context.resources.getIdentifier(
                        item.producto.imagenUrl,
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
                        contentDescription = item.producto.nombre,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Sin img",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 9.sp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(dimens.mediumSpacing))

            // Información del producto (centro) - compacta en una columna
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                // Nombre del producto
                Text(
                    text = item.producto.nombre,
                    style = MaterialTheme.typography.titleSmall.copy(fontSize = 14.sp),
                    color = TextHigh,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(2.dp))

                // Descripción (una línea)
                if (item.producto.descripcion.isNotBlank()) {
                    Text(
                        text = item.producto.descripcion,
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                        color = TextMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }

                // Precio real (con descuento si aplica)
                val precioConDescuento = item.producto.precioConDescuento
                if (item.producto.descuento != null && item.producto.descuento > 0 && precioConDescuento != null) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = formatCLP(precioConDescuento),
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                            color = TextHigh,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = formatCLP(item.producto.precio),
                            style = MaterialTheme.typography.bodySmall.copy(
                                textDecoration = TextDecoration.LineThrough,
                                fontSize = 11.sp
                            ),
                            color = TextMedium
                        )
                    }
                } else {
                    Text(
                        text = formatCLP(item.producto.precio),
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                        color = TextHigh,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.width(dimens.smallSpacing))

            // Controles cantidad y eliminar (lado derecho)
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                // Controles cantidad
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FilledTonalIconButton(
                        onClick = { onDecrement(item.id) },
                        modifier = Modifier.size(32.dp),
                        shape = MaterialTheme.shapes.small,
                        colors = IconButtonDefaults.filledTonalIconButtonColors(
                            containerColor = cs.secondaryContainer,
                            contentColor = cs.onSecondaryContainer
                        )
                    ) {
                        Icon(
                            Icons.Default.Remove,
                            contentDescription = "Disminuir",
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = item.cantidad.toString(),
                        style = MaterialTheme.typography.titleSmall.copy(fontSize = 14.sp),
                        color = TextHigh,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    FilledTonalIconButton(
                        onClick = { onIncrement(item.id) },
                        modifier = Modifier.size(32.dp),
                        shape = MaterialTheme.shapes.small,
                        colors = IconButtonDefaults.filledTonalIconButtonColors(
                            containerColor = cs.secondaryContainer,
                            contentColor = cs.onSecondaryContainer
                        )
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Aumentar",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Botón eliminar
                IconButton(
                    onClick = { onRemove(item.id) },
                    modifier = Modifier.size(32.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = BrandSuccess
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
