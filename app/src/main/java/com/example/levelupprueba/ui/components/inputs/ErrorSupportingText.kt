package com.example.levelupprueba.ui.components.inputs

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.levelupprueba.model.errors.FieldErrors
import com.example.levelupprueba.model.errors.UsuarioFieldErrors

/**
 * Funcion reutilizable que permite mostrar un mensaje de error debajo de un field
 *
 * @param error Error que se mostrara debajo del campo
 *
 */
@Composable
fun errorSupportingText(error: FieldErrors?): (@Composable () -> Unit)? =
    error?.let { {Text(it.mensaje(), color = MaterialTheme.colorScheme.error)} }
