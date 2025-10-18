package com.example.levelupprueba.ui.components.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.levelupprueba.ui.theme.Dimens
import com.example.levelupprueba.ui.theme.LocalDimens

@Composable
fun LevelUpAlertDialog(
    title: String,
    text: String,
    confirmText: String = "Aceptar",
    onConfirm: () -> Unit,
    dismissText: String? = null, // ← ahora puede ser null
    onDismiss: (() -> Unit)? = null, // ← puede ser null
    icon: (@Composable (() -> Unit))? = null,
    confirmButtonColor: Color = MaterialTheme.colorScheme.primary,
    dismissButtonColor: Color = MaterialTheme.colorScheme.onBackground,
    dimens: Dimens = LocalDimens.current
) {
    AlertDialog(
        onDismissRequest = { onDismiss?.invoke() },
        icon = icon,
        title = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = dimens.titleSize
            )
        },
        text = {
            Text(
                text = text,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = dimens.bodySize
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(containerColor = confirmButtonColor)
            ) {
                Text(confirmText, fontSize = dimens.bodySize)
            }
        },
        dismissButton = {
            dismissText?.let {
                TextButton(
                    onClick = { onDismiss?.invoke() },
                    colors = ButtonDefaults.textButtonColors(contentColor = dismissButtonColor)
                ) {
                    Text(text = it, fontSize = dimens.bodySize)
                }
            }
        }
    )
}