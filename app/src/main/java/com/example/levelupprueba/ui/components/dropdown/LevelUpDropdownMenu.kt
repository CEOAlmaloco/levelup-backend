package com.example.levelupprueba.ui.components.dropdown

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.levelupprueba.ui.components.inputs.LevelUpTextField
import com.example.levelupprueba.ui.theme.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LevelUpDropdownMenu(
    label: String,
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit,
    isError: Boolean = false,
    isSuccess: Boolean = false,
    iconTint: Color = MaterialTheme.colorScheme.onSurface,
    supportingText: (@Composable (() -> Unit))? = null,
    enabled: Boolean = true,
    placeholder: String? = null,
    modifier: Modifier = Modifier,
    dimens: Dimens
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
            trailingIcon = {
                val rotation by animateFloatAsState(
                    targetValue = if (expanded) 180f else 0f,
                    label = "DropdownArrow Rotación"
                )
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Dropdown Flecha",
                    modifier = Modifier.rotate(rotation),
                    tint = iconTint
                )
            },
            modifier = Modifier
                .menuAnchor(type = MenuAnchorType.SecondaryEditable, enabled),
            dimens = dimens
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.heightIn(max = dimens.dropdownMenuMaxHeight)
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