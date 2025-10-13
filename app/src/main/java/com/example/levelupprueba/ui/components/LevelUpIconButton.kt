package com.example.levelupprueba.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.levelupprueba.ui.theme.LocalDimens

@Composable
fun LevelUpIconButton(
    onClick: () -> Unit,
    imageVector: ImageVector,
    tint: Color = MaterialTheme.colorScheme.onSurface,
    modifier: Modifier = Modifier,
    contentDescription: String
){
    val dimens = LocalDimens.current

    IconButton(
        onClick = onClick,
        modifier = modifier
            .padding(horizontal = dimens.smallSpacing)
            .size(dimens.iconSize)
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = tint,
            modifier = Modifier
                .size(dimens.iconSize)
        )
    }
}
