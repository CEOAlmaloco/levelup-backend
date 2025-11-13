package com.example.levelupprueba.ui.screens.carrito

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import com.example.levelupprueba.model.carrito.CarritoItem
import com.example.levelupprueba.ui.theme.TextHigh
import com.example.levelupprueba.ui.theme.TextMedium
import com.example.levelupprueba.utils.formatCLP
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.style.TextDecoration
import com.example.levelupprueba.ui.components.cards.LevelUpCard
import com.example.levelupprueba.ui.theme.BrandSuccess
import com.example.levelupprueba.ui.theme.Dimens
import com.example.levelupprueba.ui.theme.LocalDimens

// Dibuja una fila de ítem de carrito con nombre, precio, cantidad y acciones +, -, eliminar
@Composable
fun CarritoItemRow(
    item: CarritoItem,
    onIncrement: (itemId: String) -> Unit,
    onDecrement: (itemId: String) -> Unit,
    onRemove: (itemId: String) -> Unit,
    modifier: Modifier = Modifier,
    dimens: Dimens = LocalDimens.current
) {
    val cs = MaterialTheme.colorScheme

    LevelUpCard{
        Column(modifier.padding(vertical = dimens.fieldSpacing)) {

            // Título + eliminar
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.producto.nombre,
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = dimens.titleSize),
                    color = TextHigh,                              // ← como Eventos
                    modifier = Modifier.weight(1f)
                )

                // Botón eliminar (acción destructiva)
                Spacer(modifier = Modifier.weight(1f)) // empuja el icono a la derecha

                IconButton(
                    onClick = { onRemove(item.id) },
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = BrandSuccess   // verde LevelUp
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete, // o ShoppingCart
                        contentDescription = "Eliminar",
                        modifier = Modifier.size(dimens.buttonIconSize)
                    )
                }
            }

            // Secundarios (precio / total línea)
            Spacer(Modifier.height(dimens.smallSpacing))
            val precioConDescuento = item.producto.precioConDescuento
            if (item.producto.descuento != null && item.producto.descuento > 0 && precioConDescuento != null) {
                Text(
                    text = "Precio c/u: ${formatCLP(precioConDescuento)}",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = dimens.bodySize),
                    color = TextMedium
                )
                Text(
                    text = "Antes: ${formatCLP(item.producto.precio)}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        textDecoration = TextDecoration.LineThrough,
                        fontSize = dimens.bodySize
                    ),
                    color = TextMedium
                )
            } else {
                Text(
                    text = "Precio c/u: ${formatCLP(item.producto.precio)}",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = dimens.bodySize),
                    color = TextMedium
                )
            }
            // Muestra "Total línea" solo si hay más de 1 unidad
            if (item.cantidad > 1) {
                Text(
                    text = "Total: ${formatCLP(item.totalLinea)}",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = dimens.bodySize),
                    color = TextMedium
                )
            }

            // Controles cantidad con contraste
            Spacer(Modifier.height(dimens.fieldSpacing))
            val cs = MaterialTheme.colorScheme
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // — (decrementar)
                FilledTonalIconButton(
                    onClick = { onDecrement(item.id) },
                    modifier = Modifier
                        .size(dimens.buttonHeight),                  // cuadrado
                    shape = MaterialTheme.shapes.small,               // esquinas suaves
                    colors = IconButtonDefaults.filledTonalIconButtonColors(
                        containerColor = cs.secondaryContainer,
                        contentColor = cs.onSecondaryContainer
                    )
                ) {
                    Icon(
                        Icons.Default.Remove,
                        contentDescription = "Disminuir",
                        modifier = Modifier
                            .size(dimens.buttonIconSize)
                    )
                }

                Spacer(Modifier.width(dimens.mediumSpacing))

                Text(
                    text = item.cantidad.toString(),
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = dimens.titleSize),
                    color = TextHigh
                )

                Spacer(Modifier.width(dimens.mediumSpacing))

                // + (incrementar)
                FilledTonalIconButton(
                    onClick = { onIncrement(item.id) },
                    modifier = Modifier
                        .size(dimens.buttonHeight),                  // ← cuadrado
                    shape = MaterialTheme.shapes.small,
                    colors = IconButtonDefaults.filledTonalIconButtonColors(
                        containerColor = cs.secondaryContainer,
                        contentColor = cs.onSecondaryContainer
                    )
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Aumentar",
                        modifier = Modifier
                            .size(dimens.buttonIconSize)
                    )
                }
            }
        }
    }
}
