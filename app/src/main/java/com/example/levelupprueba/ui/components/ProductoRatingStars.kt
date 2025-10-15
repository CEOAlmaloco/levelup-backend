package com.example.levelupprueba.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable //para pintar las estrellitas
fun ProductoRatingStars(
    rating: Float, //para calcular
    modifier: Modifier = Modifier,
    starSize: Dp = 16.dp,
    tint: Color = MaterialTheme.colorScheme.primary//color de las estrellitas  , deberia ser amarilla 
) {
    Row(modifier = modifier) {
        repeat(5) { index ->
            val starValue = index + 1 //indice + 1 para q empiece en 1
            Icon(
                imageVector = when {
                    rating >= starValue -> Icons.Filled.Star //estrellita llena
                    rating >= starValue - 0.5f -> Icons.Filled.StarHalf //estrellita media
                    else -> Icons.Outlined.StarOutline //estrellita vacia
                },
                contentDescription = null,
                tint = tint, //color de las estrellitas por dentro 
                modifier = Modifier.size(starSize) //tamaño de las estrellitas 16dp osea chikita 
            )
        }
    }
}

