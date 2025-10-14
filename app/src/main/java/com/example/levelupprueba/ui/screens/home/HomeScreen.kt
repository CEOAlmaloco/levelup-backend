package com.example.levelupprueba.ui.screens.home

//odio esto
//tengo q hacer el css html version kotlin 
//TO-DO CORRECCIONAR CIERTAS COSAS 
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.levelupprueba.ui.components.cards.ProductoCard
import com.example.levelupprueba.ui.components.carrusel.CarruselComponent
import com.example.levelupprueba.ui.components.overlays.LevelUpLoadingOverlay
import com.example.levelupprueba.viewmodel.ProductoViewModel

@Composable
fun HomeScreenProductos(
    viewModel: ProductoViewModel,
    onVerMasClick: () -> Unit = {}//esto es para que se pueda navegar a la pantalla de productos
) {
    val estado by viewModel.estado.collectAsState()
    val imagenesCarrusel by viewModel.imagenesCarrusel.collectAsState()
    var mostrarTodosProductos by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Carrusel
            if (imagenesCarrusel.isNotEmpty()) {
                CarruselComponent(
                    imagenes = imagenesCarrusel,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(32.dp))
            }

            // seccion de productos destacados
            Text(
                text = "Productos destacados",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Grid de productos
            val productosAMostrar = if (mostrarTodosProductos) {
                estado.productosDestacados
            } else {
                estado.productosDestacados.take(6)
            }

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 280.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.height(((productosAMostrar.size / 2 + 1) * 340).dp),
                userScrollEnabled = false
            ) {
                items(productosAMostrar) { producto ->
                    ProductoCard(
                        producto = producto,
                        onClick = {
                            // TODO: Navegar a detalle del producto
                        }
                    )
                }
            }

            // boton "Mostrar mas"
            if (!mostrarTodosProductos && estado.productosDestacados.size > 6) {
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    OutlinedButton(
                        onClick = { mostrarTodosProductos = true },
                        modifier = Modifier.widthIn(min = 200.dp)
                    ) {
                        Text("Mostrar más")
                    }
                }
            }

            // boton "Ver todos los productos"
            if (mostrarTodosProductos) {
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = onVerMasClick,
                        modifier = Modifier.widthIn(min = 200.dp)
                    ) {
                        Text("Ver todos los productos")
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // seccion de impacto comunitario (similar al blog)
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
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

        // loading overlay
        if (estado.isLoading) {
            LevelUpLoadingOverlay()
        }
    }
}

