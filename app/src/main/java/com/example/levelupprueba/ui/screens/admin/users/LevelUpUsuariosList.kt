package com.example.levelupprueba.ui.screens.admin.users

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.levelupprueba.model.usuario.Usuario
import com.example.levelupprueba.ui.theme.Dimens
import com.example.levelupprueba.ui.theme.LocalDimens

/**
 * Muestra la lista de usuarios registrados en un LazyColumn.
 * @param usuarios La lista de usuarios a mostrar.
 * @param onEdit Función que se llama cuando se desea editar un usuario.
 * @param onDelete Función que se llama cuando se desea eliminar un usuario.
 * @param contentPadding Valores de relleno para el contenido.
 * @param dimens Dimens para el diseño.
 *
 * @author Christian Mesa
 * */
@Composable
fun LevelUpUsuariosList(
    usuarios: List<Usuario>,
    onEdit: (Usuario) -> Unit,
    onDelete: (Usuario) -> Unit,
    contentPadding: PaddingValues,
    dimens: Dimens = LocalDimens.current
){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .padding(dimens.screenPadding),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(dimens.mediumSpacing)
    ) {
        items(usuarios){ usuario ->
            LevelUpUsuarioItem(
                usuario = usuario,
                onEdit = onEdit,
                onDelete = onDelete
            )
        }
    }
}