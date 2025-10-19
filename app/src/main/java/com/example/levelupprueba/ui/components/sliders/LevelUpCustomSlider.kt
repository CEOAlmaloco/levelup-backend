package com.example.levelupprueba.ui.components.sliders

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.levelupprueba.ui.theme.Dimens
import com.example.levelupprueba.ui.theme.LocalDimens
import com.example.levelupprueba.ui.theme.SemanticColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LevelUpCustomSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    valueRange: ClosedFloatingPointRange<Float> = 0f..100f,
    steps: Int = 0,
    enabled: Boolean = true,
    showValue: Boolean = true,
    colors: SliderColors = levelUpCustomSliderColors(),
    startIcon: @Composable (() -> Unit)? = null,
    endIcon: @Composable (() -> Unit)? = null,
    dimens: Dimens = LocalDimens.current
){
    Row (
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ){
        startIcon?.let {
            Box(
                modifier = Modifier
                    .size(dimens.iconSize)
            ) {
                it()
            }
            Spacer(modifier = Modifier
                .width(dimens.smallSpacing)
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Slider(
                value = value,
                onValueChange = onValueChange,
                valueRange = valueRange,
                steps = steps,
                enabled = enabled,
                colors = colors,
                modifier = Modifier
                    .fillMaxWidth(),
                thumb = {
                    Box(
                        Modifier
                            .size(dimens.smallIconSize)
                            .clip(CircleShape)
                            .background(SemanticColors.AccentBlue)
                    )
                }
            )
            if (showValue){
                Text(
                    text = value.toInt().toString(),
                    fontSize = dimens.bodySize,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
        endIcon?.let {
            Spacer(modifier = Modifier.width(dimens.smallSpacing))
            Box(modifier = Modifier.size(dimens.iconSize)) { it() }
        }
    }
}