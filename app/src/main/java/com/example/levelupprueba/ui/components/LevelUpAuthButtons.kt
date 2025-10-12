package com.example.levelupprueba.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.example.levelupprueba.ui.components.buttons.LevelUpButton
import com.example.levelupprueba.ui.components.buttons.LevelUpOutlinedButton
import com.example.levelupprueba.ui.components.buttons.LevelUpTextButton
import com.example.levelupprueba.ui.theme.Dimens
import com.example.levelupprueba.ui.theme.LocalDimens

@Composable
fun LevelUpAuthButtons(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onGuestClick: () -> Unit,
    buttonHeight: Dp,
    textColor: Color,
    dimens: Dimens
) {
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