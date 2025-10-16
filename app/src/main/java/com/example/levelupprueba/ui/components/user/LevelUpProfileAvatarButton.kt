package com.example.levelupprueba.ui.components.user

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.levelupprueba.ui.theme.LocalDimens

@Composable
fun LevelUpProfileAvatarButton(
    avatar: String?,
    isLoggedIn: Boolean,
    nombre: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconSize: Dp = LocalDimens.current.avatarSize,

){
    val dimens = LocalDimens.current

    IconButton(
        onClick = onClick,
        modifier = modifier
            .padding(horizontal = dimens.smallSpacing)
            .size(50.dp)
    ) {
        if (avatar != null && avatar.isNotBlank()) {
            LevelUpProfileImage(
                image = avatar,
                iconSize = iconSize
            )
        } else {
            LevelUpProfileIcon(
                isLoggedIn = isLoggedIn,
                nombre = nombre,
                iconSize = iconSize
            )
        }
    }
}