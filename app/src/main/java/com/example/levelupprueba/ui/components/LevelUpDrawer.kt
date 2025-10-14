package com.example.levelupprueba.ui.components

import android.view.Menu
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.levelupprueba.ui.components.buttons.LevelUpOutlinedButton
import com.example.levelupprueba.ui.components.buttons.MenuButton
import com.example.levelupprueba.ui.components.cards.LevelUpCard
import com.example.levelupprueba.ui.components.forms.LevelUpSectionDivider
import com.example.levelupprueba.ui.theme.Dimens
import com.example.levelupprueba.ui.theme.LocalDimens
import com.example.levelupprueba.ui.theme.SemanticColors

@Composable
fun LevelUpDrawer(
    isLoggedIn: Boolean,
    userName: String?,
    avatar: ImageBitmap?,
    onBackClick: () -> Unit,
    onProfileClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onSectionClick: (DrawerSection) -> Unit,
    sections: List<DrawerSection>,
    dimens: Dimens = LocalDimens.current
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(dimens.screenPadding)
    ) {
        LevelUpCard(
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = dimens.smallSpacing)
                ) {
                    LevelUpIconButton(
                        onClick = onBackClick,
                        imageVector = Icons.Default.Close,
                        contentDescription = "Atrás",
                        buttonSize = dimens.iconSize,
                        iconSize = dimens.iconSize
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(bottom = dimens.mediumSpacing)
                ) {
                    if (avatar != null) {
                        Image(
                            bitmap = avatar,
                            contentDescription = "Avatar",
                            modifier = Modifier
                                .size(dimens.avatarSize)
                                .clip(CircleShape)
                        )
                    } else {
                        LevelUpProfileIcon(
                            isLoggedIn = isLoggedIn,
                            nombre = userName
                        )
                    }
                    Spacer(Modifier.height(dimens.smallSpacing))

                    Text(
                        text = if (userName.isNullOrBlank()) "Invitado" else userName,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(Modifier.height(dimens.smallSpacing))

                    LevelUpOutlinedButton(
                        onClick = onProfileClick,
                        text = if (isLoggedIn) "Ver perfil" else "Iniciar sesión",
                    )
                }

                Spacer(Modifier.height(dimens.sectionSpacing))

                sections.forEach { section ->
                    NavigationDrawerItem(
                        icon = {
                            Icon(
                                imageVector = section.icon,
                                contentDescription = section.label
                            )
                        },
                        label = {
                            Text(
                                text = section.label
                            )
                        },
                        selected = false,
                        onClick = { onSectionClick(section) }
                    )
                    LevelUpSectionDivider()
                }

                Spacer(Modifier.weight(1f))

                if (isLoggedIn) {
                    MenuButton(
                        text = "Cerrar sesión",
                        icon = Icons.Default.ExitToApp,
                        onClick = onLogoutClick,
                        contentColor = MaterialTheme.colorScheme.onBackground,
                        containerColor = SemanticColors.AccentRed
                    )
                } else null
            }
        }
    }
}
