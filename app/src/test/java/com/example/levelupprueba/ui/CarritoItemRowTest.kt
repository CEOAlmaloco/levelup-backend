package com.example.levelupprueba.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import com.example.levelupprueba.model.carrito.CarritoItem
import com.example.levelupprueba.model.producto.Categoria
import com.example.levelupprueba.model.producto.Producto
import com.example.levelupprueba.model.producto.Subcategoria
import com.example.levelupprueba.ui.screens.carrito.CarritoItemRow
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class CarritoItemRowTest {

    @get:Rule val rule = createComposeRule()

    @Test
    fun botones_llaman_callbacks_incremento_decremento_eliminar() {
        val prod = Producto(
            id = "P1", nombre = "Prod", descripcion = "", precio = 1000.0, imagenUrl = "",
            categoria = Categoria.CONSOLA, subcategoria = Subcategoria.MANDOS,
            rating = 0f, disponible = true, destacado = false, stock = 10
        )
        val item = CarritoItem(id = "I1", producto = prod, cantidad = 1)

        var inc = 0; var dec = 0; var del = 0

        rule.setContent {
            CarritoItemRow(
                item = item,
                onIncrement = { inc++ },
                onDecrement = { dec++ },
                onRemove = { del++ } // si tu prop se llama onEliminar, usa ese nombre
            )
        }

        // LLamamos a los botones por contentDescription
        rule.onNodeWithContentDescription("Aumentar").performClick()
        rule.onNodeWithContentDescription("Disminuir").performClick()
        rule.onNodeWithContentDescription("Eliminar").performClick()

        assertEquals(1, inc)
        assertEquals(1, dec)
        assertEquals(1, del)
    }
}
