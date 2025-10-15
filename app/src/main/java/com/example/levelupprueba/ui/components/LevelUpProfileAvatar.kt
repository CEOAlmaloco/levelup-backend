package com.example.levelupprueba.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import com.example.levelupprueba.ui.theme.LocalDimens

@Composable
fun LevelUpProfileAvatar(
    avatar: String?,
    nombre: String,
    isLoggedIn: Boolean,
    iconSize: Dp = LocalDimens.current.avatarSize
){
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