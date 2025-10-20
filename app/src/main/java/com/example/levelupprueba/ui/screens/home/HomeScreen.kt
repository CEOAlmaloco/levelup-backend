package com.example.levelupprueba.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.levelupprueba.model.producto.Producto
import com.example.levelupprueba.ui.components.buttons.LevelUpOutlinedButton
import com.example.levelupprueba.ui.components.cards.ProductoCard
import com.example.levelupprueba.ui.components.carrusel.CarruselComponent
import com.example.levelupprueba.ui.components.overlays.LevelUpLoadingOverlay
import com.example.levelupprueba.ui.theme.LocalDimens
import com.example.levelupprueba.viewmodel.CarritoViewModel
import com.example.levelupprueba.viewmodel.ProductoViewModel

@Composable
fun HomeScreenProductos(
    viewModel: ProductoViewModel,
    carritoViewModel: CarritoViewModel,
    onVerMasClick: () -> Unit = {},//esto es para que se pueda navegar a la pantalla de productos
    contentPadding: PaddingValues,
    onProductoClick: (String) -> Unit = {},
) {
    val estado by viewModel.estado.collectAsState()
    val imagenesCarrusel by viewModel.imagenesCarrusel.collectAsState()
    var mostrarTodosProductos by remember { mutableStateOf(false) }
    val dimens = LocalDimens.current
    val productosAMostrar by remember(estado.productosDestacados, mostrarTodosProductos) {
        derivedStateOf {
            if (mostrarTodosProductos) {
                estado.productosDestacados
            } else {
                estado.productosDestacados.take(6)
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(horizontal = dimens.screenPadding),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Carrusel
            if (imagenesCarrusel.isNotEmpty()) {
                item(span = { GridItemSpan(2) }) {
                    Column {
                        Spacer(modifier = Modifier.height(16.dp))
                        CarruselComponent(
                            imagenes = imagenesCarrusel,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }

            // Sección de productos destacados
            item(span = { GridItemSpan(2) }) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Productos destacados",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        IconButton(
                            onClick = onVerMasClick,
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    shape = MaterialTheme.shapes.medium
                                )
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ShoppingCart,
                                contentDescription = "Ver todos los productos",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            items(productosAMostrar) { producto ->
                ProductoCard(
                    producto = producto,
                    onClick = { onProductoClick(producto.id) },
                    onAgregarAlCarro = { carritoViewModel.onAgregar(producto) }
                )
            }

            // Botones de mostrar más/menos
            if(estado.productosDestacados.size > 6) {
                item(span = { GridItemSpan(2) }) {
                    Column {
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            if (!mostrarTodosProductos) {
                                LevelUpOutlinedButton(
                                    onClick = { mostrarTodosProductos = true },
                                    icon = Icons.Default.KeyboardArrowDown,
                                    text = "Mostrar más",
                                    modifier = Modifier.widthIn(min = 200.dp)
                                )
                            } else {
                                LevelUpOutlinedButton(
                                    onClick = { mostrarTodosProductos = false },
                                    icon = Icons.Default.KeyboardArrowUp,
                                    text = "Mostrar menos",
                                    modifier = Modifier.widthIn(min = 200.dp)
                                )
                            }
                        }
                    }
                }
            }


            // Sección de impacto comunitario
            item(span = { GridItemSpan(2) }) {
                Column {
                    Spacer(modifier = Modifier.height(32.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        ),
                        shape = MaterialTheme.shapes.large
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp)
                        ) {
                            Text(
                                text = "Impacto en la comunidad gamer",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Tus compras ayudan a financiar eventos locales, premios para torneos estudiantiles y talleres de introducción al desarrollo de videojuegos. Al apoyar a Level-Up, apoyas a la comunidad gamer chilena.",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                lineHeight = MaterialTheme.typography.bodyLarge.lineHeight
                            )
                        }
                    }
                }
            }
        }

        // Loading overlay
        if (estado.isLoading) {
            LevelUpLoadingOverlay()
        }
    }
}