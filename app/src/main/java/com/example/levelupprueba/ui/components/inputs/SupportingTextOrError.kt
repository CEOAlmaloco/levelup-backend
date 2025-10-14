package com.example.levelupprueba.ui.components.inputs

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.TextUnit
import com.example.levelupprueba.model.errors.FieldErrors
import com.example.levelupprueba.ui.theme.SemanticColors

@Composable
fun supportingTextOrError(
    error: FieldErrors?,
    helperText: String,
    isSuccess: Boolean,
    fontSize: TextUnit = MaterialTheme.typography.bodySmall.fontSize
): (@Composable () -> Unit)? =
    when {
        error != null -> {
            { Text(
                text = error.mensaje(),
                color = MaterialTheme.colorScheme.error,
                fontSize = fontSize
                )
            }
        }
        isSuccess -> {
            { Text(
                text = helperText,
                color = SemanticColors.Success,
                fontSize = fontSize
                )
            } // Verde, puedes usar tu color del theme
        }
        else -> {
            { Text(
                helperText,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = fontSize
                )
            }
        }
    }