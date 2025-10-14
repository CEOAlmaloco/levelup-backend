package com.example.levelupprueba.ui.components.topbars

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.levelupprueba.ui.theme.Dimens
import com.example.levelupprueba.ui.theme.LocalDimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LevelUpTopBar(
    showNavigationIcon: Boolean = true,
    navigationIcon: @Composable (() -> Unit)? = null,
    titleText: String? = null,
    title: (@Composable () -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    shadowElevation: Dp = 4.dp,
    modifier: Modifier = Modifier,
    dimens: Dimens = LocalDimens.current
){
    Surface(
        color = backgroundColor,
        shadowElevation = shadowElevation,
        shape = RoundedCornerShape(
            bottomStart = dimens.cardCornerRadius,
            bottomEnd = dimens.cardCornerRadius
        ),
        modifier = modifier
    ) {
        CenterAlignedTopAppBar(
            navigationIcon = {
                if (showNavigationIcon && navigationIcon != null) {
                    navigationIcon()
                }
            },
            title = {
                when {
                    title != null -> title()
                    titleText != null -> Text(
                        text = titleText,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    else -> {}
                }
            },
            actions = actions,
            colors = levelUpTopBarColors()
        )
    }
}