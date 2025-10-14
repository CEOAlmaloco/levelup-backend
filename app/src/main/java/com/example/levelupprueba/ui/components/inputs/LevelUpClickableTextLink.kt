package com.example.levelupprueba.ui.components.inputs

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import com.example.levelupprueba.ui.theme.Dimens

@Composable
fun LevelUpClickableTextLink(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onBackground,
    underline: Boolean = true,
    fontWeight: FontWeight = FontWeight.Bold,
    dimens: Dimens,
){
    Text(
        text = text,
        color = color,
        fontSize = dimens.captionSize,
        fontWeight = fontWeight,
        textDecoration = if (underline) TextDecoration.Underline else TextDecoration.None,
        modifier = modifier.clickable { onClick() },
        style = MaterialTheme.typography.bodyMedium
    )
}