package com.example.levelupprueba.ui.components.user

import android.content.ClipData
import android.content.ClipboardManager
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.levelupprueba.model.profile.ProfileUiState
import com.example.levelupprueba.ui.components.buttons.MenuButton
import com.example.levelupprueba.ui.components.cards.LevelUpCard
import com.example.levelupprueba.ui.components.common.LevelUpBadge
import com.example.levelupprueba.ui.components.dialogs.LevelUpAlertDialog
import com.example.levelupprueba.ui.components.forms.LevelUpSectionDivider
import com.example.levelupprueba.ui.components.inputs.LevelUpIconButton
import com.example.levelupprueba.ui.components.lists.LevelUpListItem
import com.example.levelupprueba.ui.screens.profile.Option
import com.example.levelupprueba.ui.theme.Dimens
import com.example.levelupprueba.ui.theme.LocalDimens
import com.example.levelupprueba.ui.theme.SemanticColors

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
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(dimens.smallSpacing),
                verticalArrangement = Arrangement.spacedBy(dimens.smallSpacing)
            ) {
                LevelUpSectionDivider(title = "Notificaciones recientes")
                if (estado.notificaciones.isEmpty()) {
                    Text(
                        text = "No tienes notificaciones pendientes.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    val notificacionesRecientes = estado.notificaciones.take(5)
                    notificacionesRecientes.forEachIndexed { index, notificacion ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Notifications,
                                contentDescription = "Notificación",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(dimens.iconSize)
                            )
                            Spacer(modifier = Modifier.width(dimens.smallSpacing))
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                Text(
                                    text = notificacion.titulo,
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = notificacion.mensaje,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                                val fechaLegible = listOfNotNull(
                                    notificacion.fechaEntrega,
                                    notificacion.fechaEnvio,
                                    notificacion.fechaProgramada
                                ).firstOrNull()
                                if (!fechaLegible.isNullOrBlank()) {
                                    Text(
                                        text = fechaLegible.replace("T", " "),
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            val estadoUpper = notificacion.estado.uppercase()
                            val badgeBackground = when {
                                notificacion.prioridad.equals("ALTA", true) -> SemanticColors.AccentRed.copy(alpha = 0.35f)
                                estadoUpper.contains("ERROR") || estadoUpper.contains("FALL") -> SemanticColors.AccentRed.copy(alpha = 0.3f)
                                estadoUpper.contains("PEND") -> SemanticColors.AccentYellow.copy(alpha = 0.3f)
                                estadoUpper.contains("ENTREG") || estadoUpper.contains("ENVI") || estadoUpper.contains("ABIERT") -> SemanticColors.AccentGreen.copy(alpha = 0.3f)
                                else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                            }
                            LevelUpBadge(
                                text = estadoUpper,
                                backgroundColor = badgeBackground,
                                textColor = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        if (index != notificacionesRecientes.lastIndex) {
                            LevelUpSectionDivider()
                        }
                    }
                    if (estado.notificaciones.size > notificacionesRecientes.size) {
                        Text(
                            text = "+${estado.notificaciones.size - notificacionesRecientes.size} notificaciones adicionales",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
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