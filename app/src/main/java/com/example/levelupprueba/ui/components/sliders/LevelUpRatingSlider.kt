package com.example.levelupprueba.ui.components.sliders

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.levelupprueba.ui.theme.Dimens
import com.example.levelupprueba.ui.theme.LocalDimens
import com.example.levelupprueba.ui.theme.SemanticColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LevelUpRatingSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    valueRange: ClosedFloatingPointRange<Float> = 1f..5f,
    steps: Int = 3,
    dimens: Dimens = LocalDimens.current
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Estrellas arriba del slider
        Row {
            for (i in 1..5) {
                Icon(
                    imageVector = if (i <= value.toInt()) Icons.Filled.Star else Icons.Outlined.StarOutline,
                    contentDescription = "Estrella",
                    tint = if (i <= value.toInt()) SemanticColors.AccentYellow else SemanticColors.AccentYellow,
                    modifier = modifier
                        .size(dimens.iconSize)
                )
            }
        }
        Spacer(Modifier.height(dimens.smallSpacing))
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            steps = steps,
            colors = SliderDefaults.colors(
                thumbColor = SemanticColors.AccentGreen,
                activeTrackColor = SemanticColors.AccentGreen,
                inactiveTrackColor = MaterialTheme.colorScheme.outline
            )
        )
        Text("${value.toInt()} estrellas", Modifier.padding(top = dimens.smallSpacing))
    }
}