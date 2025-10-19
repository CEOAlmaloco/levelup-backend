package com.example.levelupprueba.ui.components.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.levelupprueba.data.repository.CarritoProvider
import com.example.levelupprueba.model.producto.Producto
import kotlinx.coroutines.launch

/**
 * Botón listo para usar. Guarda en Room usando el repo del carrito.
 */
@Composable
fun AddToCartButton(
    producto: Producto,
    onAdded: (() -> Unit)? = null
) {
    val ctx = LocalContext.current
    val repo = remember { CarritoProvider.get(ctx) }
    val scope = rememberCoroutineScope()

    Button(
        onClick = {
            scope.launch {
                repo.agregarProducto(producto, 1)
                onAdded?.invoke()
            }
        },
        // Asegura altura/padding suficientes
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        modifier = Modifier.heightIn(min = 40.dp) // 40–44dp va bien
    ) {
        Text(
            "Agregar al carrito",
            style = MaterialTheme.typography.labelLarge,
            maxLines = 1
        )
    }
}

