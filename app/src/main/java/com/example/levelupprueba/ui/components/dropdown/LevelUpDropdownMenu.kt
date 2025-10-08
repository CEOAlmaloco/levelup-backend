package com.example.levelupprueba.ui.components.dropdown

import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.levelupprueba.ui.components.inputs.LevelUpTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LevelUpDropdownMenu(
    label: String,
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit,
    isError: Boolean = false,
    isSuccess: Boolean = false,
    supportingText: (@Composable (() -> Unit))? = null,
    enabled: Boolean = true,
    placeholder: String? = null,
    modifier: Modifier = Modifier
){
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {
        LevelUpTextField(
            value = selectedOption ?: "",
            onValueChange = {},
            label = label,
            readOnly = true,
            enabled = enabled,
            isError = isError,
            isSuccess = isSuccess,
            supportingText = supportingText,
            placeholder = placeholder?.let { { Text(it) } },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor(type = MenuAnchorType.SecondaryEditable, enabled)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.heightIn(max = 285.dp)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}