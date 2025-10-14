package com.example.levelupprueba.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import com.example.levelupprueba.ui.components.inputs.levelUpOutlinedTextFieldColors
import com.example.levelupprueba.ui.theme.Dimens
import com.example.levelupprueba.ui.theme.LocalDimens

@Composable
fun LevelUpSearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier,
    placeholder: String = "Buscar...",
    dimens: Dimens = LocalDimens.current,
    onFocusChanged: (Boolean) -> Unit = {},
    focusRequester: FocusRequester? = null
){
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder) },
        modifier = modifier
            .fillMaxWidth()
            .then(if (focusRequester != null) Modifier.focusRequester(focusRequester) else Modifier)
            .onFocusChanged{ focusState ->
                onFocusChanged(focusState.isFocused)
            }
        ,
        singleLine = true,
        trailingIcon = {
            LevelUpIconButton(
                onClick = {
                    onSearch(value)
                    focusManager.clearFocus()
                },
                imageVector = Icons.Default.Search,
                contentDescription = "Buscar",
                tint = MaterialTheme.colorScheme.onSurface,
            )
        },
        colors = levelUpOutlinedTextFieldColors(),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch(value)
                focusManager.clearFocus()
            }
        ),
        textStyle = MaterialTheme.typography.bodyMedium.copy(fontSize = dimens.bodySize)
    )
}