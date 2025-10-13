package com.example.levelupprueba.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.levelupprueba.ui.components.inputs.levelUpOutlinedTextFieldColors
import com.example.levelupprueba.ui.theme.LocalDimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LevelUpMainTopBar(
    isLoggedIn: Boolean,
    nombre: String?,
    title: String = "Inicio",
    onMenuClick: () -> Unit,
    onCartClick: () -> Unit,
    onProfileClick: () -> Unit,
    onSearchClick: (String) -> Unit
) {
    val dimens = LocalDimens.current
    var search by remember { mutableStateOf("") }

    Surface(
        shadowElevation = 4.dp,
        color = MaterialTheme.colorScheme.surface,
    ) {
        Column {
            CenterAlignedTopAppBar(
                title = { Text(title, style = MaterialTheme.typography.titleMedium) },
                navigationIcon = {
                    IconButton(onClick = onMenuClick) {
                        Icon(Icons.Default.Menu, contentDescription = "Menú")
                    }
                },
                actions = {
                    IconButton(onClick = onCartClick) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito")
                    }
                    LevelUpProfileIconButton(
                        isLoggedIn = isLoggedIn,
                        nombre = nombre,
                        onClick = onProfileClick
                    )
                }
            )

            LevelUpSearchBar(
                value = search,
                onValueChange = {search = it},
                onSearch = {query -> onSearchClick(query)},
                modifier = Modifier
                    .padding(
                        start = dimens.screenPadding,
                        end = dimens.screenPadding,
                        bottom = dimens.mediumSpacing
                    )
            )
        }
    }
}
