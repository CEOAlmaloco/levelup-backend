package com.example.levelupprueba.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import coil.compose.rememberAsyncImagePainter
import com.example.levelupprueba.ui.theme.Dimens
import com.example.levelupprueba.ui.theme.LocalDimens

@Composable
fun LevelUpProfileImage(
    image : String,
    iconSize: Dp = LocalDimens.current.avatarSize
){
    Image(
        painter = rememberAsyncImagePainter(image),
        contentDescription = "Avatar",
        modifier = Modifier
            .size(iconSize)
            .clip(CircleShape)
    )
}
