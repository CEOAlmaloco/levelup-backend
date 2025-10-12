package com.example.levelupprueba.ui.components.topbars

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import com.example.levelupprueba.ui.theme.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LevelUpTopBar(
    title: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    dimens: Dimens
){
    Surface(
        shape = RoundedCornerShape(
            bottomStart = dimens.cardCornerRadius,
            bottomEnd = dimens.cardCornerRadius
        ),
        modifier = modifier
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = dimens.titleSize
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = onBackClick
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = "Back",
                        modifier = Modifier
                            .size(dimens.iconSize)
                    )
                }
            },
            modifier = modifier,
            colors = levelUpTopBarColors()
        )
    }
}