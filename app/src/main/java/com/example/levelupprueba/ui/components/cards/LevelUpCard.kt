package com.example.levelupprueba.ui.components.cards

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.levelupprueba.ui.theme.LocalDimens

@Composable
fun LevelUpCard(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(LocalDimens.current.screenPadding),
    content: @Composable () -> Unit
) {
    val dimens = LocalDimens.current
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(dimens.cardCornerRadius),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = dimens.cardElevation)
    ) {
        // Padding interno configurable
        Surface(
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.padding(paddingValues)
        ) {
            content()
        }
    }
}