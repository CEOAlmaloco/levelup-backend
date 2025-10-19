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
import com.example.levelupprueba.ui.components.inputs.LevelUpIconButton
import com.example.levelupprueba.ui.components.overlays.LevelUpLoadingOverlay
import com.example.levelupprueba.ui.theme.LocalDimens
import com.example.levelupprueba.viewmodel.ProductoViewModel
import com.example.levelupprueba.ui.components.buttons.AddToCartButton
import com.example.levelupprueba.viewmodel.CarritoViewModel
import kotlinx.coroutines.launch


@Composable
fun ProductosScreen(
    viewModel: ProductoViewModel,
    carritoViewModel: CarritoViewModel,
    contentPadding: PaddingValues,
    onProductoClick: (String) -> Unit = {}
) {
    val estado by viewModel.estado.collectAsState()
    val dimens = LocalDimens.current
    Box(modifier = Modifier.fillMaxSize()) {
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
                    .padding(contentPadding)
                    .padding(horizontal = dimens.screenPadding, vertical = dimens.screenPadding)
            ) {
                // header con boton de filtros
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Todos los productos",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )

                    LevelUpIconButton(
                        onClick = { viewModel.toggleMostrarFiltros() },
                        imageVector = Icons.Default.FilterList,
                        contentDescription = "Mostrar filtros"
                    )
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
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 280.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(estado.productos) { producto ->
                            ProductoCard(
                                producto = producto,
                                onClick = {
                                    onProductoClick(producto.id)
                                },
                                onAgregarAlCarro = {carritoViewModel.onAgregar(it)}
                            )
                        }
                    }
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

