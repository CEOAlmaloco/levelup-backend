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
import com.example.levelupprueba.ui.theme.BrandSuccess

// Dibuja una fila de ítem de carrito con nombre, precio, cantidad y acciones +, -, eliminar
@Composable
fun CarritoItemRow(
    item: CarritoItem,
    onIncrement: (itemId: String) -> Unit,
    onDecrement: (itemId: String) -> Unit,
    onRemove: (itemId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val cs = MaterialTheme.colorScheme

    Column(modifier.padding(vertical = 12.dp)) {

        // Título + eliminar
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.producto.nombre,
                style = MaterialTheme.typography.titleMedium,
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
                    contentDescription = "Eliminar"
                )
            }
        }

        // Secundarios (precio / total línea)
        Spacer(Modifier.height(4.dp))
        Text(
            text = "Precio c/u: ${formatCLP(item.producto.precio)}",
            style = MaterialTheme.typography.bodySmall,
            color = TextMedium                                // ← texto secundario
        )
        // Muestra "Total línea" solo si hay más de 1 unidad
        if (item.cantidad > 1) {
            Text(
                text = "Total: ${formatCLP(item.producto.precio * item.cantidad)}",
                style = MaterialTheme.typography.bodySmall,
                color = TextMedium
            )
        }

        // Controles cantidad con contraste
        Spacer(Modifier.height(8.dp))
        val cs = MaterialTheme.colorScheme
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // — (decrementar)
            FilledTonalIconButton(
                onClick = { onDecrement(item.id) },
                modifier = Modifier.size(30.dp),                  // cuadrado
                shape = RoundedCornerShape(10.dp),               // esquinas suaves
                colors = IconButtonDefaults.filledTonalIconButtonColors(
                    containerColor = cs.secondaryContainer,
                    contentColor   = cs.onSecondaryContainer
                )
            ) {
                Icon(Icons.Default.Remove, contentDescription = "Disminuir", modifier = Modifier.size(18.dp))
            }

            Spacer(Modifier.width(12.dp))

            Text(
                text  = item.cantidad.toString(),
                style = MaterialTheme.typography.titleMedium,
                color = TextHigh
            )

            Spacer(Modifier.width(12.dp))

            // + (incrementar)
            FilledTonalIconButton(
                onClick = { onIncrement(item.id) },
                modifier = Modifier.size(30.dp),                  // ← cuadrado
                shape = RoundedCornerShape(10.dp),
                colors = IconButtonDefaults.filledTonalIconButtonColors(
                    containerColor = cs.secondaryContainer,
                    contentColor   = cs.onSecondaryContainer
                )
            ) {
                Icon(Icons.Default.Add, contentDescription = "Aumentar", modifier = Modifier.size(18.dp))
            }
        }
    }
}
