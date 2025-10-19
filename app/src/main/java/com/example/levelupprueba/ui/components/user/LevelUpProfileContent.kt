package com.example.levelupprueba.ui.components.user

import android.content.ClipData
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.levelupprueba.model.profile.ProfileUiState
import com.example.levelupprueba.ui.components.cards.LevelUpCard
import com.example.levelupprueba.ui.components.common.LevelUpBadge
import com.example.levelupprueba.ui.components.forms.LevelUpSectionDivider
import com.example.levelupprueba.ui.components.inputs.LevelUpIconButton
import com.example.levelupprueba.ui.screens.profile.Option
import com.example.levelupprueba.ui.theme.Dimens
import com.example.levelupprueba.ui.theme.LocalDimens
import com.example.levelupprueba.ui.theme.SemanticColors
import android.content.ClipboardManager
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Text
import com.example.levelupprueba.ui.components.buttons.MenuButton
import com.example.levelupprueba.ui.components.dialogs.LevelUpAlertDialog
import com.example.levelupprueba.ui.components.lists.LevelUpListItem

@Composable
fun LevelUpProfileContent(
    estado: ProfileUiState,
    options: List<Option>,
    showConfirmDeleteDialog: Boolean,
    onDeleteClick: () -> Unit,
    onDeleteConfirm: () -> Unit,
    onDeleteDismiss: () -> Unit,
    context: android.content.Context,
    dimens: Dimens = LocalDimens.current
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimens.mediumSpacing)
    ) {
        // Código de referido y puntos
        LevelUpCard(
            modifier = Modifier
                .padding(horizontal = dimens.screenPadding)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(dimens.smallSpacing),
                verticalArrangement = Arrangement.spacedBy(dimens.smallSpacing)
            ) {
                LevelUpSectionDivider(title = "Código de referido")
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LevelUpBadge(
                        text = estado.referralCode.ifEmpty { "Generando..." },
                        textColor = SemanticColors.AccentGreen,
                        backgroundColor = SemanticColors.AccentGreen.copy(0.2f),
                    )
                    if (estado.referralCode.isNotEmpty()) {
                        LevelUpIconButton(
                            onClick = {
                                val clipboard = context.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("Código de Referido", estado.referralCode)
                                clipboard.setPrimaryClip(clip)
                                Toast.makeText(context, "Código copiado al portapapeles", Toast.LENGTH_SHORT).show()
                            },
                            imageVector = Icons.Filled.ContentCopy,
                            contentDescription = "Copiar código",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
                Text(
                    text = "Comparte este código con tus amigos para obtener 50 puntos cuando se registren",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(0.7f),
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(dimens.smallSpacing))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Puntos actuales:",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    LevelUpBadge(
                        text = "${estado.points} pts",
                        textColor = MaterialTheme.colorScheme.secondary,
                        backgroundColor = MaterialTheme.colorScheme.secondaryContainer.copy(0.3f)
                    )
                }
            }
        }
        LevelUpCard(
            modifier = Modifier
                .padding(horizontal = dimens.screenPadding)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(dimens.smallSpacing)
            ) {
                options.forEachIndexed { index, option ->
                    LevelUpListItem(
                        icon = option.icon,
                        label = option.label,
                        onClick = option.onClick
                    )
                    if (index != options.lastIndex) {
                        LevelUpSectionDivider()
                    }
                }
                Spacer(modifier = Modifier.height(dimens.mediumSpacing))
                MenuButton(
                    text = "Eliminar cuenta",
                    icon = Icons.Default.Delete,
                    containerColor = SemanticColors.AccentRed,
                    contentColor = MaterialTheme.colorScheme.onBackground,
                    shape = MaterialTheme.shapes.extraSmall,
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = onDeleteClick
                )
                if (showConfirmDeleteDialog) {
                    LevelUpAlertDialog(
                        title = "Eliminar cuenta",
                        text = "¿Estás seguro que deseas eliminar tu cuenta? Esta acción es irreversible",
                        confirmText = "Eliminar",
                        dismissText = "Cancelar",
                        onConfirm = onDeleteConfirm,
                        onDismiss = onDeleteDismiss,
                        confirmButtonColor = SemanticColors.AccentRed
                    )
                }
            }
        }
    }
}