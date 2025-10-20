package com.example.levelupprueba.ui.components.filtros

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.levelupprueba.ui.components.overlays.LevelUpLoadingOverlay
import com.example.levelupprueba.viewmodel.ProductoViewModel

@Composable
fun LevelUpFiltersOverlay(
    productoViewModel: ProductoViewModel,
    modifier: Modifier = Modifier
) {
    val estado by productoViewModel.estado.collectAsState()

    // --- Fondo semitransparente con animación independiente ---
    AnimatedVisibility(
        visible = estado.mostrarFiltros,
        enter = fadeIn(animationSpec = tween(300)),
        exit = fadeOut(animationSpec = tween(300))
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.scrim)
                .clickable { productoViewModel.cerrarFiltros() }
        )
    }

    // --- Panel lateral con animación slide ---
    AnimatedVisibility(
        visible = estado.mostrarFiltros,
        enter = slideInHorizontally(
            initialOffsetX = { it },
            animationSpec = tween(durationMillis = 400)
        ),
        exit = slideOutHorizontally(
            targetOffsetX = { it },
            animationSpec = tween(durationMillis = 400)
        )
    ) {
        Box(modifier.fillMaxSize()) {
            FiltrosComponent(
                filtros = estado.filtros,
                onCategoriaChange = productoViewModel::cambiarCategoria,
                onSubcategoriaToggle = productoViewModel::toggleSubcategoria,
                onTextoBusquedaChange = productoViewModel::actualizarTextoBusqueda,
                onPrecioMinimoChange = productoViewModel::actualizarPrecioMinimo,
                onPrecioMaximoChange = productoViewModel::actualizarPrecioMaximo,
                onSoloDisponiblesToggle = productoViewModel::toggleSoloDisponibles,
                onRatingMinimoChange = productoViewModel::actualizarRatingMinimo,
                onOrdenamientoChange = productoViewModel::cambiarOrdenamiento,
                onLimpiarFiltros = productoViewModel::limpiarFiltros,
                onCerrar = productoViewModel::cerrarFiltros,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }

    }
    // Loading overlay
    if (estado.isLoading) {
        LevelUpLoadingOverlay()
    }
}