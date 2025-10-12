package com.example.levelupprueba.ui.components.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.example.levelupprueba.ui.theme.Dimens

@Composable
fun LevelUpAlertDialog(
    onDismissRequest: () -> Unit,
    title: String,
    text: String,
    confirmText: String = "Aceptar",
    onConfirm: () -> Unit,
    dismissText: String? = null,
    onDismiss: (() -> Unit)? = null,
    icon: (@Composable (() -> Unit))? = null,
    dimens: Dimens
){
    AlertDialog(
        onDismissRequest = onDismissRequest,
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
                text,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = dimens.bodySize
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm
            ) {
                Text(confirmText, fontSize = dimens.bodySize)
            }
        },
        dismissButton = {
            dismissText?.let {
                TextButton(
                    onClick = {
                        onDismiss?.invoke() ?: onDismissRequest()
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.onBackground
                    )
                ) {
                    Text(text = it, fontSize = dimens.bodySize)
                }
            }
        }

    )
}