package com.example.levelupprueba.ui.components.inputs

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.TextUnit
import com.example.levelupprueba.model.errors.FieldErrors

/**
 * Funcion reutilizable que permite mostrar un mensaje de error debajo de un field
 *
 * @param error Error que se mostrara debajo del campo
 *
 */
@Composable
fun errorSupportingText(
    error: FieldErrors?,
    fontSize: TextUnit
): (@Composable () -> Unit)? =
    error?.let {
        {
            Text(
                text = it.mensaje(),
                color = MaterialTheme.colorScheme.error,
                fontSize = fontSize
            )
        }
    }


