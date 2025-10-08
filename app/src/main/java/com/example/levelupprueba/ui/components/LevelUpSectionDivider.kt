package com.example.levelupprueba.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.levelupprueba.ui.theme.Dimens
import com.example.levelupprueba.ui.theme.LocalDimens

@Composable
fun LevelUpSectionDivider(
    title: String,
    modifier: Modifier = Modifier,
    dimens: Dimens = LocalDimens.current
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = dimens.sectionSpacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(
            modifier = Modifier
                .weight(1f),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outline
        )
        Text(
            text = title.uppercase(),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
            modifier = Modifier
                .padding(horizontal = dimens.fieldSpacing)
        )
        HorizontalDivider(
            modifier = Modifier
                .weight(1f),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outline
        )
    }
}
