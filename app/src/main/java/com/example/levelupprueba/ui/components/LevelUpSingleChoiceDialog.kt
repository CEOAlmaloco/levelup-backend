package com.example.levelupprueba.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun LevelUpSingleChoiceDialog(
    show: Boolean,
    title: String,
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit,
    onDismiss: () -> Unit
){
    if (show){
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = title) },
            text = {
                Column {
                    options.forEach { option ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onOptionSelected(option) }
                        ) {
                            RadioButton(
                                selected = option == selectedOption,
                                onClick = { onOptionSelected(option) }
                            )
                            Text(
                                text = option
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(onClick = onDismiss){
                    Text(text = "Aceptar")
                }
            }
        )
    }
}