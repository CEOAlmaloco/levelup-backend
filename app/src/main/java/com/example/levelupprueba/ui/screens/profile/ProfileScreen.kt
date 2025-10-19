package com.example.levelupprueba.ui.screens.profile

import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.MaterialTheme
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.levelupprueba.model.profile.ProfileStatus
import com.example.levelupprueba.ui.components.forms.LevelUpChangePasswordForm
import com.example.levelupprueba.ui.components.forms.LevelUpProfileEditForm
import com.example.levelupprueba.ui.components.user.LevelUpProfileContent
import com.example.levelupprueba.ui.components.user.LevelUpProfileHeader
import com.example.levelupprueba.ui.theme.LocalDimens
import com.example.levelupprueba.utils.ImageUtils
import com.example.levelupprueba.viewmodel.MainViewModel
import com.example.levelupprueba.viewmodel.ChangePasswordViewModel
import com.example.levelupprueba.viewmodel.ProfileViewModel
import com.example.levelupprueba.viewmodel.UbicacionViewModel

@OptIn(ExperimentalAnimationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    changePasswordViewModel: ChangePasswordViewModel,
    mainViewModel: MainViewModel,
    isLoggedIn: Boolean,
    userId: String,
    displayName: String,
    contentPadding: PaddingValues
) {
    val dimens = LocalDimens.current
    val estado by viewModel.estado.collectAsState()
    var currentMode by rememberSaveable { mutableStateOf(ProfileMode.VIEW) }
    var showConfirmDeleteDialog by remember {mutableStateOf(false)}
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
                currentMode = ProfileMode.EDIT
            }
        ),
        Option(
            label = "Cambiar contraseña",
            icon = Icons.Default.Lock,
            onClick = {
                currentMode = ProfileMode.CHANGE_PASSWORD
            }
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
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
            contentPadding = contentPadding,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimens.mediumSpacing)
        ) {
            item { Spacer(modifier = Modifier.height(dimens.mediumSpacing)) }
            item {
                LevelUpProfileHeader(
                    isEditing = currentMode == ProfileMode.EDIT,
                    perfilEditable = perfilEditable,
                    estado = estado,
                    displayName = displayName,
                    isLoggedIn = isLoggedIn,
                    launcher = launcher,
                    dimens = dimens,
                    userId = userId
                )
            }
            item {
                AnimatedContent(
                    targetState = currentMode,
                    transitionSpec = {
                        fadeIn() with fadeOut()
                    }
                ) { mode ->
                    when (mode) {
                        ProfileMode.EDIT -> LevelUpProfileEditForm(
                            perfilEditable = perfilEditable,
                            onPerfilChange = { perfilEditable = it },
                            ubicacionViewModel = ubicacionViewModel,
                            isSaveEnabled = isSaveEnabled,
                            profileStatus = estado.profileStatus,
                            onSaveClick = {
                                viewModel.guardarPerfil(perfilEditable, mainViewModel)
                            },
                            onCancelClick = { currentMode = ProfileMode.VIEW },
                            focusRequesters = focusRequesters,
                            focusManager = focusManager
                        )
                        ProfileMode.VIEW -> LevelUpProfileContent(
                            estado = estado,
                            options = options,
                            showConfirmDeleteDialog = showConfirmDeleteDialog,
                            onDeleteClick = { showConfirmDeleteDialog = true },
                            onDeleteConfirm = {
                                showConfirmDeleteDialog = false
                                viewModel.eliminarUsuario(userId)
                            },
                            onDeleteDismiss = { showConfirmDeleteDialog = false },
                            context = context,
                            dimens = dimens
                        )
                        ProfileMode.CHANGE_PASSWORD -> LevelUpChangePasswordForm(
                            email = estado.email.valor,
                            viewModel = changePasswordViewModel,
                            onCancelClick = {
                                currentMode = ProfileMode.VIEW
                            },
                            focusManager = focusManager
                        )
                    }
                }
            }
        }
    }
}

