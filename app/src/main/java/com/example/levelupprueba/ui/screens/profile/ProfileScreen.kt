package com.example.levelupprueba.ui.screens.profile

import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.levelupprueba.model.profile.ProfileStatus
import com.example.levelupprueba.ui.components.common.LevelUpBadge
import com.example.levelupprueba.ui.components.lists.LevelUpListItem
import com.example.levelupprueba.ui.components.user.LevelUpProfileAvatar
import com.example.levelupprueba.ui.components.buttons.MenuButton
import com.example.levelupprueba.ui.components.cards.LevelUpCard
import com.example.levelupprueba.ui.components.forms.LevelUpSectionDivider
import com.example.levelupprueba.ui.components.forms.ProfileEditForm
import com.example.levelupprueba.ui.theme.LocalDimens
import com.example.levelupprueba.ui.theme.SemanticColors
import com.example.levelupprueba.utils.ImageUtils
import com.example.levelupprueba.utils.debouncedClickable
import com.example.levelupprueba.viewmodel.MainViewModel
import com.example.levelupprueba.viewmodel.ProfileViewModel
import com.example.levelupprueba.viewmodel.UbicacionViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    mainViewModel: MainViewModel,
    isLoggedIn: Boolean,
    userId: String,
    displayName: String,
    contentPadding: PaddingValues
) {
    val dimens = LocalDimens.current
    val estado by viewModel.estado.collectAsState()
    var isEditing by rememberSaveable { mutableStateOf(false) }

    var perfilEditable by remember { mutableStateOf(PerfilEditable()) }
    val ubicacionViewModel: UbicacionViewModel = viewModel()

    val isSaveEnabled = perfilEditable.esValido()

    val focusManager = LocalFocusManager.current
    val focusRequesters = remember { List(4) { FocusRequester() } }

    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            if (uri != null) {
                val filename = "avatar_${System.currentTimeMillis()}.jpg"
                val path = ImageUtils.copyUriToInternalStorage(
                    context = context,
                    uri = uri,
                    filename = filename
                )
                if (path != null) {
                    perfilEditable = perfilEditable.copy(avatar = path)
                }
            }
        }
    )

    val options = listOf(
        Option(
            label = "Editar perfil",
            icon = Icons.Default.Edit,
            onClick = {
                if (perfilEditable.region.isNotBlank()) {
                    ubicacionViewModel.selectRegion(perfilEditable.region)
                }
                isEditing = true
            }
        ),
        Option(
            label = "Cambiar contraseña",
            icon = Icons.Default.Lock,
            onClick = { }
        )
    )

    LaunchedEffect(estado) {
        perfilEditable = perfilEditable.copy(
            nombre = estado.nombre.valor,
            apellidos = estado.apellidos.valor,
            telefono = estado.telefono.valor,
            fechaNacimiento = estado.fechaNacimiento.valor,
            region = estado.region.valor,
            comuna = estado.comuna.valor,
            direccion = estado.direccion.valor,
            avatar = estado.avatar
        )
    }

    LaunchedEffect(userId) {
        viewModel.cargarDatosUsuario(userId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = contentPadding,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimens.mediumSpacing)
        ) {

            item{
                Spacer(modifier = Modifier.height(dimens.mediumSpacing))
            }

            item {
                Column(
                    modifier = Modifier.padding(horizontal = dimens.screenPadding),
                    verticalArrangement = Arrangement.spacedBy(dimens.mediumSpacing),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (isEditing) {
                        Box(
                            modifier = Modifier
                                .size(120.dp)
                                .debouncedClickable { launcher.launch("image/*") },
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
                            backgroundColor = MaterialTheme.colorScheme.secondaryContainer.copy(
                                0.50f
                            ),
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

            when (estado.profileStatus) {
                is ProfileStatus.Saved -> {

                }
                is ProfileStatus.Error -> {

                }
                is ProfileStatus.ValidationError -> {

                }
                else -> {}
            }

            item {
                if (isEditing) {
                    ProfileEditForm(
                        perfilEditable = perfilEditable,
                        onPerfilChange = { perfilEditable = it },
                        ubicacionViewModel = ubicacionViewModel,
                        isSaveEnabled = isSaveEnabled,
                        profileStatus = estado.profileStatus,
                        onSaveClick = {
                            viewModel.guardarPerfil(perfilEditable, mainViewModel)
                        },
                        onCancelClick = { isEditing = false },
                        focusRequesters = focusRequesters,
                        focusManager = focusManager
                    )
                } else {
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
                                onClick = { }
                            )
                        }
                    }
                }
            }
        }
    }
}
