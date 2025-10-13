package com.example.levelupprueba.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.levelupprueba.ui.theme.LocalDimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LevelUpMainTopBar(
    isLoggedIn: Boolean,
    nombre: String?,
    apellidos: String?,
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
        color = MaterialTheme.colorScheme.surface
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
                        apellidos = apellidos,
                        onClick = onProfileClick
                    )
                }
            )

            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                placeholder = { Text("Buscar...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = dimens.mediumSpacing,
                        ),
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = { onSearchClick(search) }) {
                        Icon(Icons.Default.Search, contentDescription = "Buscar")
                    }
                },
                colors = levelUpTextFieldColors(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { onSearchClick(search) })
            )
        }
    }
}
