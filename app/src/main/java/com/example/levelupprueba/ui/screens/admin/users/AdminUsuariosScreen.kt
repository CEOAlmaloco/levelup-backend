package com.example.levelupprueba.ui.screens.admin.users

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.levelupprueba.model.admin.users.UsuariosStatus
import com.example.levelupprueba.ui.theme.Dimens
import com.example.levelupprueba.ui.theme.LocalDimens
import com.example.levelupprueba.viewmodel.UsuariosViewModel

/**
 * Pantalla de administración de usuarios.
 *
 * @param viewModel El [UsuariosViewModel] para manejar la lógica de la pantalla.
 * @param dimens Las dimensiones predefinidas para el diseño de la pantalla.
 * @param contentPadding Los valores de relleno para el contenido de la pantalla.
 *
 * @author Christian Mesa
 * */
@Composable
fun AdminUsuariosScreen(
    viewModel: UsuariosViewModel,
    dimens: Dimens = LocalDimens.current,
    contentPadding: PaddingValues
){

    // Estado de la pantalla
    val estado by viewModel.estado.collectAsState()


    // Cargar usuarios al iniciar la pantalla
    LaunchedEffect(Unit) {
        viewModel.cargarUsuarios()
    }


    // Contenedor principal de la pantalla
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {

        // Contenido de la pantalla según el estado
        when(val state = estado.state){
            // Si hay usuarios cargados se renderiza la lista
            is UsuariosStatus.Success -> {
                LevelUpUsuariosList(
                    usuarios = state.usuarios,
                    onEdit = {viewModel.seleccionarUsuario(it)},
                    onDelete = {viewModel.eliminarUsuario(it.id)},
                    contentPadding =contentPadding
                )
            }
            // Si no hay usuarios registrados se muestra un mensaje
            is UsuariosStatus.Empty -> {
                Text(
                    text = "No hay usuarios registrados",
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = dimens.titleSize)
                )
            }
            // Muestra un mensaje si esta cargando
            is UsuariosStatus.Loading ->{
                Text(
                    text = "Cargando usuarios...",
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = dimens.titleSize)
                )
            }

            // Muestra un mensaje si se esta eliminando un usuario
            is UsuariosStatus.Deleting ->{
                Text(
                    text = "Eliminando Usuario...",
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = dimens.titleSize)
                )
            }
            else -> Unit
        }
    }
}