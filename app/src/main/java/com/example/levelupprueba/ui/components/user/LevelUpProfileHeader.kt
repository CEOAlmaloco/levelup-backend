package com.example.levelupprueba.ui.components.user

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.levelupprueba.model.profile.ProfileUiState
import com.example.levelupprueba.ui.components.common.LevelUpBadge
import com.example.levelupprueba.ui.screens.profile.PerfilEditable
import com.example.levelupprueba.ui.theme.Dimens
import com.example.levelupprueba.ui.theme.LocalDimens
import com.example.levelupprueba.utils.debouncedClickable

@Composable
fun LevelUpProfileHeader(
    isEditing: Boolean,
    perfilEditable: PerfilEditable,
    estado: ProfileUiState,
    displayName: String,
    isLoggedIn: Boolean,
    launcher: ManagedActivityResultLauncher<String, Uri?>,
    dimens: Dimens = LocalDimens.current,
    userId: String
) {
    Column(
        modifier = Modifier
            .padding(horizontal = dimens.screenPadding),
        verticalArrangement = Arrangement.spacedBy(dimens.mediumSpacing),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isEditing) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .debouncedClickable{ launcher.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                LevelUpProfileAvatar(
                    avatar = perfilEditable.avatar,
                    nombre = displayName,
                    isLoggedIn = isLoggedIn,
                    iconSize = 120.dp
                )
                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = "Editar avatar",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(dimens.iconSize)
                )
            }
            LevelUpBadge(
                text = "Cambiar avatar",
                textColor = MaterialTheme.colorScheme.onBackground.copy(0.87f),
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer.copy(0.50f),
            )
        } else {
            LevelUpProfileAvatar(
                avatar = estado.avatar,
                nombre = displayName,
                isLoggedIn = isLoggedIn,
                iconSize = 120.dp
            )
        }

        Text(
            text = "${estado.nombre.valor} ${estado.apellidos.valor}",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        LevelUpBadge(
            text = "ID No: $userId",
            textColor = MaterialTheme.colorScheme.onSurface.copy(0.50f),
            backgroundColor = MaterialTheme.colorScheme.surface.copy(0.50f),
        )
    }
}