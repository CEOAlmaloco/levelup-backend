package com.example.levelupprueba.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LevelUpProfileIcon(
    isLoggedIn: Boolean,
    nombre: String?,
    appelido: String?
){
    val initials = if (isLoggedIn && !nombre.isNullOrBlank() && !appelido.isNullOrBlank())
        "${nombre.first().uppercase()}${appelido.first().uppercase()}"
    else "G"

    Box(
        modifier = Modifier
            .size(32.dp)
            .background(MaterialTheme.colorScheme.onSurface),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = initials,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.surface
        )
    }
}