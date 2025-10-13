package com.example.levelupprueba.ui.components.forms

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import com.example.levelupprueba.ui.theme.Dimens

@Composable
fun LevelUpFormSection(
    title: String,
    dimens: Dimens,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Column(
        modifier = modifier
    ) {

        if (!isLandscape) {
            // Espacio arriba del título (solo en vertical)
            Spacer(modifier = Modifier.height(dimens.smallSpacing))
        }

        // Divider centrado
        LevelUpSectionDivider(title = title, dimens = dimens)

        // Espacio después del divider antes de los campos
        Spacer(modifier = Modifier.height(dimens.mediumSpacing))

        // Contenido animado (campos)
        AnimatedVisibility(visible = true) {
            Column(
                verticalArrangement = Arrangement.spacedBy(dimens.fieldSpacing)
            ){
                content()
            }
        }
    }
}