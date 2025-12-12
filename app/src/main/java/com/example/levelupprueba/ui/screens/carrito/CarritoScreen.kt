package com.example.levelupprueba.ui.screens.carrito

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.levelupprueba.ui.components.buttons.LevelUpOutlinedButton
import com.example.levelupprueba.ui.components.forms.LevelUpSectionDivider
import com.example.levelupprueba.ui.theme.Dimens
import com.example.levelupprueba.ui.theme.LocalDimens
import com.example.levelupprueba.utils.formatCLP
import com.example.levelupprueba.viewmodel.CarritoViewModel
import com.example.levelupprueba.ui.theme.TextHigh
import com.example.levelupprueba.ui.theme.TextMedium
import com.example.levelupprueba.viewmodel.MainViewModel
import androidx.compose.runtime.LaunchedEffect


// Screen del Carrito: lista de ítems, resumen y acciones básicas.
@Composable
fun CarritoScreen(
    viewModel: CarritoViewModel,
    contentPadding: PaddingValues,
    mainViewModel: MainViewModel? = null,
    dimens: Dimens = LocalDimens.current
) {
    // Observa el estado del VM
    val loading by viewModel.loading.collectAsState()
    val carrito by viewModel.carrito.collectAsState()
    val error by viewModel.error.collectAsState()
    val successMessage by viewModel.successMessage.collectAsState()
    
    // Mostrar mensaje de éxito cuando se complete el pago
    LaunchedEffect(successMessage) {
        successMessage?.let { message ->
            mainViewModel?.showSuccessSnackbar(message)
            viewModel.clearSuccessMessage()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .padding(horizontal = dimens.screenPadding, vertical = dimens.screenPadding)
    ) {
        when {
            loading -> {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
            error != null -> {
                Text(
                    text = error ?: "Error",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            carrito.items.isEmpty() -> {
                Text(
                    text = "Tu carrito está vacío",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {
                Column(Modifier.fillMaxSize()) {
                    // Lista de ítems
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ){
                        val items = carrito.items
                        val n = items.size
                        val distinctCount = items.map { it.producto.id }.distinct().size
                        itemsIndexed(items) { index, item ->
                            // Fila
                            CarritoItemRow(
                                item = item,
                                onIncrement = viewModel::onIncrement,
                                onDecrement = viewModel::onDecrement,
                                onRemove = viewModel::onEliminar,
                                modifier = Modifier.fillMaxWidth()
                            )

                            if (
                                distinctCount > 1 &&
                                index < n - 1 &&
                                items[index + 1].producto.id != item.producto.id
                            ) {
                                Spacer(modifier = Modifier.height(dimens.smallSpacing))
                                LevelUpSectionDivider()
                                Spacer(modifier = Modifier.height(dimens.smallSpacing))
                            }
                        }
                    }
                    // Resumen + Pagar
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant   // ← como Eventos
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Subtotal", color = TextMedium)                     // ← secundario
                                Text(formatCLP(carrito.subtotal), color = TextHigh)
                            }

                            Spacer(Modifier.height(8.dp))

                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Total", color = TextMedium)
                                Text(
                                    formatCLP(carrito.total),
                                    color = TextHigh,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(Modifier.height(16.dp))

                            LevelUpOutlinedButton(
                                onClick = viewModel::onCheckout,
                                text = "Pagar",
                                modifier = Modifier.fillMaxWidth(),
                                dimens = dimens
                            )
                        }
                    }
                }
            }
        }
    }
}

