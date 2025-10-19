package com.example.levelupprueba.ui.components.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.levelupprueba.ui.components.navigation.DrawerSection
import com.example.levelupprueba.ui.components.inputs.LevelUpIconButton
import com.example.levelupprueba.ui.components.user.LevelUpProfileAvatarButton
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
    avatar: String?,
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
            .padding(dimens.screenPadding),
        contentAlignment = Alignment.Center
    ) {
        LevelUpCard(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = dimens.mediumSpacing)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
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
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LevelUpProfileAvatarButton(
                        isLoggedIn = isLoggedIn,
                        nombre = userName,
                        onClick = onProfileClick,
                        avatar = avatar
                    )
                    Spacer(Modifier.height(dimens.smallSpacing))

                    Text(
                        text = if (userName.isNullOrBlank()) "Invitado" else "¡Hola, $userName!",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(Modifier.height(dimens.mediumSpacing))

                    LevelUpOutlinedButton(
                        onClick = onProfileClick,
                        text = if (isLoggedIn) "Ver perfil" else "Iniciar sesión",
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }

                Spacer(Modifier.height(dimens.sectionSpacing))

                sections.forEach { section ->
                    LevelUpDrawerItem(
                        imageVector = section.icon,
                        contentDescription = section.label,
                        text = section.label,
                        onClick = { onSectionClick(section) }
                    )
                    LevelUpSectionDivider()
                }

                Spacer(Modifier.height(dimens.largeSpacing))

                if (isLoggedIn) {
                    MenuButton(
                        text = "Cerrar sesión",
                        icon = Icons.Default.ExitToApp,
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = onLogoutClick,
                        contentColor = MaterialTheme.colorScheme.onBackground,
                        containerColor = SemanticColors.AccentRed
                    )
                }
            }
        }
    }
}
