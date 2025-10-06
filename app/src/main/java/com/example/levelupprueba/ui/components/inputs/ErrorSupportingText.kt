package com.example.levelupprueba.ui.components.inputs

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.levelupprueba.model.FieldErrors

@Composable
fun errorSupportingText(error: FieldErrors?): (@Composable () -> Unit)? =
    error?.let { {Text(it.mensaje(), color = MaterialTheme.colorScheme.error)} }
