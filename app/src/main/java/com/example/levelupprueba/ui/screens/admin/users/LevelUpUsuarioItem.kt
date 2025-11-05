package com.example.levelupprueba.ui.screens.admin.users

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.levelupprueba.model.usuario.Usuario
import com.example.levelupprueba.ui.components.cards.LevelUpCard
import com.example.levelupprueba.ui.components.dialogs.LevelUpAlertDialog
import com.example.levelupprueba.ui.components.inputs.LevelUpIconButton
import com.example.levelupprueba.ui.theme.Dimens
import com.example.levelupprueba.ui.theme.LocalDimens
import com.example.levelupprueba.ui.theme.SemanticColors

/**
 * Item del usuario en la lista de usuarios.
 * @param usuario Usuario a mostrar.
 * @param onEdit Función a ejecutar al hacer click en el botón de edición.
 * @param onDelete Función a ejecutar al hacer click en el botón de eliminación.
 * @param dimens Dimens a utilizar.
 *
 * @author Christian Mesa.
 * */
@Composable
fun LevelUpUsuarioItem(
    usuario: Usuario,
    onEdit: (Usuario) -> Unit,
    onDelete: (Usuario) -> Unit,
    dimens: Dimens = LocalDimens.current
){
    // Muestra un diálogo de confirmación al hacer click en el botón de eliminación
    var showConfirmDeleteDialog by remember {mutableStateOf(false)}

    // Card con el usuario
    LevelUpCard(
        modifier = Modifier
            .clickable{onEdit(usuario)}
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = "${usuario.nombre} ${usuario.apellidos}",
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = dimens.bodySize),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = usuario.email,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = dimens.badgeTextSize)
                )
            }

            Row {
                LevelUpIconButton(
                    imageVector = Icons.Default.Edit,
                    onClick = {onEdit(usuario)},
                    contentDescription = "Editar usuario",
                    tint = SemanticColors.AccentGreen
                )
                LevelUpIconButton(
                    imageVector = Icons.Default.Delete,
                    onClick = {showConfirmDeleteDialog = true},
                    contentDescription = "Eliminar usuario",
                    tint = SemanticColors.AccentRed
                )
            }
        }
    }

    // Muestra un diálogo de confirmación al hacer click en el botón de eliminación
    if (showConfirmDeleteDialog){
        LevelUpAlertDialog(
            title = "Eliminar usuario",
            text = "¿Estás seguro de que quieres eliminar a ${usuario.nombre} ${usuario.apellidos}?",
            confirmText = "Eliminar",
            dismissText = "Cancelar",
            onConfirm = {
                showConfirmDeleteDialog = false
                onDelete(usuario)
            },
            onDismiss = {showConfirmDeleteDialog = false},
            confirmButtonColor = SemanticColors.AccentRed
        )
    }

}