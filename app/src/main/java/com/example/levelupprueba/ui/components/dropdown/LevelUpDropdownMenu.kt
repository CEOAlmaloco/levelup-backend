package com.example.levelupprueba.ui.components.dropdown

import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun LevelUpDropdownMenu(
    label: String,
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit,
    isError: Boolean = false,
    supportingText: (@Composable (() -> Unit))? = null,
    enabled: Boolean = false,
    placeholder: String? = null,
    modifier: Modifier = Modifier
){
    var expanded by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = selectedOption ?: "",
        onValueChange = {},
        label = { Text(label) },
        readOnly = true,
        enabled = enabled,
        isError = isError,
        supportingText = supportingText,
        placeholder = placeholder?.let { { Text(it) } },
        trailingIcon = {
            IconButton(
                onClick = { if (enabled) expanded = true },
                modifier = Modifier.semantics {
                    contentDescription = if (expanded) "Cerrar menú" else "Abrir menú"
                }
            ) {
                Icon(
                    imageVector = if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                    contentDescription = null
                )
            }
        },
        modifier = modifier
    )

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = {expanded = false},
        modifier = Modifier.heightIn(max = 300.dp)
    ) {
        options.forEach { option ->
            DropdownMenuItem(
                text = { Text(option)},
                onClick = {
                    onOptionSelected(option)
                    expanded = false
                }
            )
        }
    }
}