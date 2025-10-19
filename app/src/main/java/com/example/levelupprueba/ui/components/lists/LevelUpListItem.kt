package com.example.levelupprueba.ui.components.lists

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.levelupprueba.ui.theme.Dimens
import com.example.levelupprueba.ui.theme.LocalDimens

/**
 * Item de la lista personalizado.
 *
 * @param icon Icono a mostrar.
 * @param label Texto a mostrar.
 * @param onClick Función a ejecutar al hacer clic en el elemento.
 * @param modifier Modificador para personalizar el diseño del elemento.
 * @param dimens Dimens para personalizar el diseño del elemento.
 *
 * @author Christian Mesa
 * */
@Composable
fun LevelUpListItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    dimens: Dimens = LocalDimens.current
){
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable{ onClick()},
        color = MaterialTheme.colorScheme.surface,
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = dimens.mediumSpacing, horizontal = dimens.mediumSpacing)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .size(dimens.iconSize)

            )
            Spacer(modifier = Modifier.width(dimens.mediumSpacing))
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}