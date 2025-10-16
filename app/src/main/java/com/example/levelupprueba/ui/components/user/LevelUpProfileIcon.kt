package com.example.levelupprueba.ui.components.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.example.levelupprueba.ui.theme.LocalDimens
import com.example.levelupprueba.ui.theme.SemanticColors

@Composable
fun LevelUpProfileIcon(
    isLoggedIn: Boolean,
    nombre: String?,
    iconSize: Dp = LocalDimens.current.avatarSize
){
    val initials = if (isLoggedIn && !nombre.isNullOrBlank()) {
        val trimmed = nombre.trim()
        if (trimmed.length >= 2)
            "${trimmed[0].uppercase()}${trimmed[1].uppercase()}"
        else
            "${trimmed[0].uppercase()}"
    } else "G"

    val textSizeSp = (iconSize.value * 0.45).sp

    Box(
        modifier = Modifier
            .size(iconSize)
            .clip(CircleShape)
            .background(SemanticColors.AccentBlue),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = initials,
            style = MaterialTheme.typography.titleMedium.copy(fontSize = textSizeSp),
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.87f)
        )
    }
}