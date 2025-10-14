package com.example.levelupprueba.ui.components.forms

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.levelupprueba.ui.theme.Dimens
import com.example.levelupprueba.ui.theme.LocalDimens

@Composable
fun LevelUpSectionDivider(
    title: String? = null,
    modifier: Modifier = Modifier,
    dimens: Dimens = LocalDimens.current
) {
    if (title.isNullOrBlank()){
        HorizontalDivider(
            modifier = modifier.fillMaxWidth(),
            thickness = dimens.dividerThickness,
            color = MaterialTheme.colorScheme.outline
        )
    } else {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                thickness = dimens.dividerThickness,
                color = MaterialTheme.colorScheme.outline
            )

            Text(
                text = title.uppercase(),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                modifier = Modifier.padding(horizontal = dimens.fieldSpacing),
                fontSize = dimens.captionSize
            )

            HorizontalDivider(
                modifier = Modifier.weight(1f),
                thickness = dimens.dividerThickness,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}


