package com.example.levelupprueba.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.levelupprueba.ui.theme.Dimens
import com.example.levelupprueba.ui.theme.LocalDimens

@Composable
fun LevelUpDrawerItem(
    imageVector: ImageVector,
    contentDescription: String,
    text: String,
    onClick: () -> Unit,
    dimens: Dimens = LocalDimens.current
){
    NavigationDrawerItem(
        icon = {
            Icon(
                imageVector = imageVector,
                contentDescription = contentDescription,
                modifier = Modifier
                    .size(dimens.iconSize)
            )
        },
        label = {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        selected = false,
        onClick = onClick,
        colors = levelUpDrawerItemColors()
    )
}