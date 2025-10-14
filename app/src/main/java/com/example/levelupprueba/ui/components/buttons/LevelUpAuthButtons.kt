package com.example.levelupprueba.ui.components.buttons

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.example.levelupprueba.ui.components.LevelUpSpacedColumn
import com.example.levelupprueba.ui.theme.Dimens
import com.example.levelupprueba.ui.theme.LocalDimens

@Composable
fun LevelUpAuthButtons(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onGuestClick: () -> Unit,
    buttonHeight: Dp,
    textColor: Color,
) {
    val dimens = LocalDimens.current

    LevelUpSpacedColumn(spacing = LocalDimens.current.mediumSpacing) {
        LevelUpButton(
            text = "Iniciar Sesión",
            onClick = onLoginClick,
            modifier = Modifier.height(buttonHeight),
            dimens = dimens
        )
        LevelUpOutlinedButton(
            text = "Registrarse",
            onClick = onRegisterClick,
            modifier = Modifier.height(buttonHeight),
            dimens = dimens
        )
        LevelUpTextButton(
            text = "Continuar como invitado",
            onClick = onGuestClick,
            icon = null,
            textColor = textColor,
            modifier = Modifier.height(buttonHeight),
            dimens = dimens
        )
    }
}