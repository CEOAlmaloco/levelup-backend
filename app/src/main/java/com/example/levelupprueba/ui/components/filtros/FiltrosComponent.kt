package com.example.levelupprueba.ui.components.filtros

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.levelupprueba.model.producto.*

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun FiltrosComponent(
    filtros: FiltrosState,
    onCategoriaChange: (Categoria) -> Unit,
    onSubcategoriaToggle: (Subcategoria) -> Unit,
    onTextoBusquedaChange: (String) -> Unit,
    onPrecioMinimoChange: (Double?) -> Unit,
    onPrecioMaximoChange: (Double?) -> Unit,
    onSoloDisponiblesToggle: () -> Unit,
    onRatingMinimoChange: (Float) -> Unit,
    onOrdenamientoChange: (OrdenProductos) -> Unit,
    onLimpiarFiltros: () -> Unit,
    onCerrar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxHeight()
            .width(320.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Filtrar productos",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = onCerrar) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Cerrar filtros"
                    )
                }
            }

            Divider(modifier = Modifier.padding(vertical = 16.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                // categorias
                Text(
                    text = "Categoría",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))

                Categoria.values().forEach { categoria ->
                    FilterChip(
                        selected = filtros.categoriaSeleccionada == categoria,
                        onClick = { onCategoriaChange(categoria) },
                        label = { Text(categoria.nombre) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    )

                    // subcategorias
                    if (filtros.categoriaSeleccionada == categoria && categoria != Categoria.TODAS) {
                        Column(modifier = Modifier.padding(start = 16.dp, top = 8.dp)) {
                            Subcategoria.values()
                                .filter { it.categoria == categoria }
                                .forEach { subcat ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(vertical = 4.dp)
                                    ) {
                                        Checkbox(
                                            checked = subcat in filtros.subcategoriasSeleccionadas,
                                            onCheckedChange = { onSubcategoriaToggle(subcat) }
                                        )
                                        Text(
                                            text = subcat.nombre,
                                            modifier = Modifier.padding(start = 8.dp)
                                        )
                                    }
                                }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // busqueda avanzada
                Text(
                    text = "Búsqueda avanzada",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(12.dp))

                // texto de busqueda
                OutlinedTextField(
                    value = filtros.textoBusqueda,
                    onValueChange = onTextoBusquedaChange,
                    label = { Text("Buscar por nombre") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Rango de precios
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = filtros.precioMinimo?.toString() ?: "",
                        onValueChange = { text ->
                            onPrecioMinimoChange(text.toDoubleOrNull())
                        },
                        label = { Text("Precio mín") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = filtros.precioMaximo?.toString() ?: "",
                        onValueChange = { text ->
                            onPrecioMaximoChange(text.toDoubleOrNull())
                        },
                        label = { Text("Precio máx") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Solo disponibles
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = filtros.soloDisponibles,
                        onCheckedChange = { onSoloDisponiblesToggle() }
                    )
                    Text("Solo disponibles", modifier = Modifier.padding(start = 8.dp))
                }

                Spacer(modifier = Modifier.height(12.dp))

                // rating minimo
                Column {
                    Text(
                        text = "Rating mínimo: ${filtros.ratingMinimo.toInt()}+",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Slider(
                        value = filtros.ratingMinimo,
                        onValueChange = onRatingMinimoChange,
                        valueRange = 0f..5f,
                        steps = 4
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Ordenamiento
                var expandedOrden by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = expandedOrden,
                    onExpandedChange = { expandedOrden = !expandedOrden }
                ) {
                    OutlinedTextField(
                        value = filtros.ordenamiento.etiqueta,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Ordenar por") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedOrden) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedOrden,
                        onDismissRequest = { expandedOrden = false }
                    ) {
                        OrdenProductos.values().forEach { orden ->
                            DropdownMenuItem(
                                text = { Text(orden.etiqueta) },
                                onClick = {
                                    onOrdenamientoChange(orden)
                                    expandedOrden = false
                                }
                            )
                        }
                    }
                }
            }

            // boton limpiar filtros
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onLimpiarFiltros,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Limpiar filtros")
            }
        }
    }
}

