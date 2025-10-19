package com.example.levelupprueba.ui.screens.productos


//TO-DO TERMINAR ESTO MAÑANA O OTRO DIA ME TIENE CHATO SOLO Q FUNCIONE TA BIEN
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.levelupprueba.ui.components.cards.ProductoCard
import com.example.levelupprueba.ui.components.filtros.FiltrosComponent
import com.example.levelupprueba.ui.components.overlays.LevelUpLoadingOverlay
import com.example.levelupprueba.viewmodel.ProductoViewModel
import com.example.levelupprueba.ui.components.buttons.AddToCartButton
import kotlinx.coroutines.launch


@Composable
fun ProductosScreen(
    viewModel: ProductoViewModel
) {
    val estado by viewModel.estado.collectAsState()
    // NUEVO: estado del snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize())  {
        Row(modifier = Modifier.fillMaxSize()) {
            // filtros laterales (solo desktop/tablet)
            if (!estado.mostrarFiltros) {
                // aqui podria mostrar los filtros en desktop
                // por ahora se manejaran con el drawer
            }

            // contenido principal
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(16.dp)
            ) {
                // header con boton de filtros
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { viewModel.toggleMostrarFiltros() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Filled.FilterList,
                            contentDescription = "Filtros"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Filtros")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Grid de productos
                if (estado.productos.isEmpty() && !estado.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No se encontraron productos",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    Box(Modifier.fillMaxSize()){
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(minSize = 280.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(estado.productos) { producto ->
                                ProductoCard(
                                    producto = producto,
                                    onClick = {
                                        // TODO: navegar a detalle si quieren
                                    },
                                    bottomContent = {
                                        AddToCartButton(producto = producto,
                                            onAdded = {
                                                scope.launch {
                                                    snackbarHostState.showSnackbar("¡producto agregado!")
                                                }
                                            })
                                    }
                                )
                            }
                        }// Items
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = 15.dp),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            SnackbarHost(hostState = snackbarHostState) { data: SnackbarData ->
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = MaterialTheme.colorScheme.surface,       // usa tu theme
                                    tonalElevation = 6.dp,
                                    shadowElevation = 6.dp,
                                    modifier = Modifier
                                        .widthIn(max = 200.dp)                       // ancho compacto
                                        .padding(horizontal = 16.dp)
                                ){
                                    Text(
                                        text = data.visuals.message,
                                        style = MaterialTheme.typography.labelLarge,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        textAlign = TextAlign.Center,                 // ← centrado
                                        modifier = Modifier
                                            .fillMaxWidth()                           // ← necesario para centrar
                                            .padding(horizontal = 20.dp, vertical = 8.dp)
                                    )
                                }
                            }
                        }// Segundo Box
                    }// Box
                }
            }
        }

        // overlay oscuro cuando se muestran filtros
        if (estado.mostrarFiltros) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.scrim.copy(alpha = 0.5f))
                    .clickable { viewModel.cerrarFiltros() }
            )
        }

        // Panel de filtros deslizante
        if (estado.mostrarFiltros) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 0.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                FiltrosComponent(
                    filtros = estado.filtros,
                    onCategoriaChange = { viewModel.cambiarCategoria(it) },
                    onSubcategoriaToggle = { viewModel.toggleSubcategoria(it) },
                    onTextoBusquedaChange = { viewModel.actualizarTextoBusqueda(it) },
                    onPrecioMinimoChange = { viewModel.actualizarPrecioMinimo(it) },
                    onPrecioMaximoChange = { viewModel.actualizarPrecioMaximo(it) },
                    onSoloDisponiblesToggle = { viewModel.toggleSoloDisponibles() },
                    onRatingMinimoChange = { viewModel.actualizarRatingMinimo(it) },
                    onOrdenamientoChange = { viewModel.cambiarOrdenamiento(it) },
                    onLimpiarFiltros = { viewModel.limpiarFiltros() },
                    onCerrar = { viewModel.cerrarFiltros() }
                )
            }
        }

        // Loading overlay
        if (estado.isLoading) {
            LevelUpLoadingOverlay()
        }
    }
}

