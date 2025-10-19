package com.example.levelupprueba.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.levelupprueba.ui.components.inputs.levelUpOutlinedTextFieldColors
import com.example.levelupprueba.ui.components.topbars.LevelUpTopBar
import com.example.levelupprueba.ui.theme.LocalDimens

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun LevelUpMainTopBar(
    isLoggedIn: Boolean,
    nombre: String?,
    title: String = "Inicio",
    onMenuClick: () -> Unit,
    onCartClick: () -> Unit,
    onProfileClick: () -> Unit,
    onSearchClick: (String) -> Unit,
    showSearch: Boolean = true
) {
    val dimens = LocalDimens.current
    var search by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }
    val searchFocusRequester = remember { FocusRequester() }

    // Si se desactiva el buscador desde afuera, cerramos el modo búsqueda
    LaunchedEffect(showSearch) {
        if (!showSearch) isSearching = false
    }

    Surface(
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 4.dp,
        shape = RoundedCornerShape(
            bottomStart = dimens.cardCornerRadius,
            bottomEnd = dimens.cardCornerRadius
        ),
    ) {
        AnimatedContent(
            targetState = isSearching &&  showSearch,
            transitionSpec = {
                fadeIn().togetherWith(fadeOut())
            }
        ) { searching ->
            if (searching) {
                // Modo búsqueda: TopBar muestra solo el SearchBar expandido
                LaunchedEffect(isSearching) {
                    if (isSearching) {
                        searchFocusRequester.requestFocus()
                    }
                }
                Column {
                    CenterAlignedTopAppBar(
                        navigationIcon = {
                            LevelUpIconButton(
                                onClick = {
                                    isSearching = false
                                },
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Atrás",
                            )
                        },
                        title = {
                            LevelUpSearchBar(
                                value = search,
                                onValueChange = { search = it },
                                onSearch = { query ->
                                    onSearchClick(query)
                                },
                                modifier = Modifier
                                    .fillMaxWidth(),
                                focusRequester = searchFocusRequester
                            )
                        },
                        actions = {
                            LevelUpProfileIconButton(
                                isLoggedIn = isLoggedIn,
                                nombre = nombre,
                                onClick = onProfileClick
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(dimens.smallSpacing))
                }
            } else {
                // TopBar normal
                Column {
                    LevelUpTopBar(
                        showNavigationIcon = true,

                        navigationIcon = {
                            LevelUpIconButton(
                                onClick = onMenuClick,
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu"
                            )
                        },
                        actions = {
                            LevelUpIconButton(
                                onClick = onCartClick,
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Carrito"
                            )
                            LevelUpProfileIconButton(
                                isLoggedIn = isLoggedIn,
                                nombre = nombre,
                                onClick = onProfileClick
                            )
                        },

                        titleText = title,
                        shadowElevation = 0.dp,
                        backgroundColor = Color.Transparent
                    )

                    if (showSearch){
                        LevelUpSearchBar(
                            value = search,
                            onValueChange = { search = it },
                            onSearch = { query -> onSearchClick(query) },
                            modifier = Modifier
                                .padding(
                                    start = dimens.screenPadding,
                                    end = dimens.screenPadding,
                                    bottom = dimens.mediumSpacing
                                ),
                            onFocusChanged = { focused ->
                                if (focused) isSearching = true
                        }
                    )
                }
            }
        }
    }
}
}
