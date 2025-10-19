package com.example.levelupprueba.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.levelupprueba.ui.theme.Dimens
import com.example.levelupprueba.ui.theme.LocalDimens

@Composable
fun LevelUpProductBadge(
    text: String,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    modifier: Modifier = Modifier,
    dimens: Dimens = LocalDimens.current,
    font: FontWeight = FontWeight.Bold
) {
    Surface(
        modifier = modifier
            .padding(dimens.smallSpacing),
        color = backgroundColor.copy(),
        shape = RoundedCornerShape(dimens.smallSpacing)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = contentColor,
            fontWeight = font,
            modifier = Modifier.padding(horizontal = dimens.smallSpacing, vertical = dimens.badgeVerticalPadding)
        )
    }
}