package com.example.levelupprueba.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.levelupprueba.ui.components.topbars.LevelUpTopBar
import com.example.levelupprueba.ui.theme.LocalDimens

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun LevelUpMainTopBar(
    avatar: String?,
    isLoggedIn: Boolean,
    nombre: String?,
    title: String = "Inicio",
    onMenuClick: () -> Unit,
    onCartClick: () -> Unit,
    onProfileClick: () -> Unit,
    onSearchClick: (String) -> Unit,
    showMenu: Boolean = true,
    showCart: Boolean = true,
    showProfile: Boolean = true,
    showSearch: Boolean = true,
    showBackArrow: Boolean = false,
    onBackClick: (() -> Unit)? = null
) {
    val dimens = LocalDimens.current
    var search by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }
    val searchFocusRequester = remember { FocusRequester() }

    Surface(
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 4.dp,
        shape = RoundedCornerShape(
            bottomStart = dimens.cardCornerRadius,
            bottomEnd = dimens.cardCornerRadius
        ),
    ) {
        AnimatedContent(
            targetState = isSearching,
            transitionSpec = {
                fadeIn().togetherWith(fadeOut())
            }
        ) { searching ->
            if (searching && showSearch) {
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
                            if (showProfile) {
                                LevelUpProfileAvatarButton(
                                    isLoggedIn = isLoggedIn,
                                    nombre = nombre,
                                    onClick = onProfileClick,
                                    avatar = avatar
                                )
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(dimens.smallSpacing))
                }
            } else {
                // TopBar normal
                Column {
                    LevelUpTopBar(
                        showNavigationIcon = showMenu || showBackArrow,
                        navigationIcon = {
                            when {
                                showBackArrow && onBackClick != null -> {
                                    LevelUpIconButton(
                                        onClick = onBackClick,
                                        imageVector = Icons.Default.ArrowBack,
                                        contentDescription = "Atrás"
                                    )
                                }
                                showMenu -> {
                                    LevelUpIconButton(
                                        onClick = onMenuClick,
                                        imageVector = Icons.Default.Menu,
                                        contentDescription = "Menu"
                                    )
                                }
                            }
                        },
                        actions = {
                            if (showSearch) {
                                LevelUpIconButton(
                                    onClick = { isSearching = true },
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Buscar"
                                )
                            }
                            if (showCart) {
                                LevelUpIconButton(
                                    onClick = onCartClick,
                                    imageVector = Icons.Default.ShoppingCart,
                                    contentDescription = "Carrito"
                                )
                            }
                            if (showProfile) {
                                LevelUpProfileAvatarButton(
                                    isLoggedIn = isLoggedIn,
                                    nombre = nombre,
                                    onClick = onProfileClick,
                                    avatar = avatar
                                )
                            }
                        },
                        titleText = title,
                        shadowElevation = 0.dp,
                        backgroundColor = Color.Transparent
                    )
                }
            }
        }
    }
}