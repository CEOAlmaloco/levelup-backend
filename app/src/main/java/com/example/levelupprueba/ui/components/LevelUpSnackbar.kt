package com.example.levelupprueba.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.levelupprueba.ui.theme.Dimens
import com.example.levelupprueba.ui.theme.LocalDimens
import com.example.levelupprueba.ui.theme.SemanticColors

@Composable
fun LevelUpCustomSnackbar(
    snackbarData: SnackbarData,
    snackbarState: GlobalSnackbarState,
    dimens: Dimens = LocalDimens.current
){
    val (background, content) = when (snackbarState) {
        is GlobalSnackbarState.Success -> Pair(
            SemanticColors.Success,
            MaterialTheme.colorScheme.onBackground.copy(0.90f)
        )
        is GlobalSnackbarState.Error -> Pair(
            SemanticColors.Error,
            MaterialTheme.colorScheme.onBackground.copy(0.90f)
        )
        is GlobalSnackbarState.Info -> Pair(
            SemanticColors.Info,
            MaterialTheme.colorScheme.onBackground.copy(0.90f)
        )
        is GlobalSnackbarState.Idle -> Pair(
            SemanticColors.Warning,
            MaterialTheme.colorScheme.onBackground.copy(0.90f)
        )
    }

    Surface(
        color = background,
        contentColor = content,
        shape = RoundedCornerShape(dimens.cardCornerRadius),
        shadowElevation = 4.dp,
        modifier = Modifier
            .padding(horizontal = dimens.mediumSpacing, vertical = dimens.mediumSpacing)
    ) {
        Text(
            text = snackbarData.visuals.message,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = dimens.bodySize),
            modifier = Modifier
                .padding(horizontal = dimens.mediumSpacing, vertical = dimens.mediumSpacing)
        )
    }
}