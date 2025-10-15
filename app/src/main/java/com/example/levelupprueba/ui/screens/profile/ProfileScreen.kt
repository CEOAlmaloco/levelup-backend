package com.example.levelupprueba.ui.screens.profile

import android.content.Intent
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.levelupprueba.MainActivity
import com.example.levelupprueba.model.auth.LoginStatus
import com.example.levelupprueba.model.profile.ProfileStatus
import com.example.levelupprueba.model.usuario.UsuarioValidator
import com.example.levelupprueba.model.usuario.isSuccess
import com.example.levelupprueba.ui.components.LevelUpBadge
import com.example.levelupprueba.ui.components.LevelUpListItem
import com.example.levelupprueba.ui.components.LevelUpProfileAvatar
import com.example.levelupprueba.ui.components.buttons.LevelUpButton
import com.example.levelupprueba.ui.components.buttons.LevelUpOutlinedButton
import com.example.levelupprueba.ui.components.buttons.MenuButton
import com.example.levelupprueba.ui.components.cards.LevelUpCard
import com.example.levelupprueba.ui.components.dialogs.LevelUpAlertDialog
import com.example.levelupprueba.ui.components.forms.LevelUpSectionDivider
import com.example.levelupprueba.ui.components.inputs.LevelUpOutlinedTextField
import com.example.levelupprueba.ui.components.inputs.errorSupportingText
import com.example.levelupprueba.ui.components.overlays.LevelUpLoadingOverlay
import com.example.levelupprueba.ui.theme.LocalDimens
import com.example.levelupprueba.ui.theme.SemanticColors
import com.example.levelupprueba.utils.ImageUtils
import com.example.levelupprueba.viewmodel.MainViewModel
import com.example.levelupprueba.viewmodel.ProfileViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    mainViewModel: MainViewModel,
    isLoggedIn: Boolean,
    userId: String,
    displayName: String
){
    val dimens = LocalDimens.current
    val estado by viewModel.estado.collectAsState()

    val isEditing = remember { mutableStateOf(false) }
    val nombreFocus = remember { FocusRequester() }
    val apellidosFocus = remember { FocusRequester() }

    var perfilEditable by remember(estado) {
        mutableStateOf(
            PerfilEditable(
                nombre = estado.nombre.valor,
                apellidos = estado.apellidos.valor,
                email = estado.email.valor,
                telefono = estado.telefono.valor,
                fechaNacimiento = estado.fechaNacimiento.valor,
                region = estado.region.valor,
                comuna = estado.comuna.valor,
                direccion = estado.direccion.valor,
                avatar = estado.avatar
            )
        )
    }

    val nombreError = UsuarioValidator.validarNombre(perfilEditable.nombre)
    val isSaveEnabled = perfilEditable.nombre.isNotBlank() &&
            nombreError == null
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
            onClick =  {
                isEditing.value = true
            }
        ),
        Option(
            label = "Cambiar contraseña",
            icon = Icons.Default.Lock,
            onClick =  { }
        )
    )
    LaunchedEffect(userId) {
        viewModel.cargarDatosUsuario(userId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(dimens.screenPadding)
            .imePadding(),                  // Padding para teclado
        contentAlignment = Alignment.Center
    ){
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimens.mediumSpacing)
        ) {

            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(dimens.mediumSpacing),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (isEditing.value) {
                        Box(
                            modifier = Modifier
                                .size(120.dp)
                                .clickable { launcher.launch("image/") },
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

            when (estado.profileStatus){
                is ProfileStatus.Saved -> {

                }
                is ProfileStatus.Error -> {

                }
                is ProfileStatus.ValidationError -> {

                }
                else -> {}
            }

            item {
                if (!isEditing.value) {
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
            item {
                if (isEditing.value){
                    LevelUpCard(
                        modifier = Modifier
                            .fillMaxWidth()
                    ){
                        Column(
                            verticalArrangement = Arrangement.spacedBy(dimens.sectionSpacing)
                        ){

                            Column(
                                verticalArrangement = Arrangement.spacedBy(dimens.fieldSpacing)

                            ) {
                                LevelUpOutlinedTextField(
                                    value = perfilEditable.nombre,
                                    onValueChange = { perfilEditable = perfilEditable.copy(nombre = it) },
                                    label = "Nombre",
                                    isError = nombreError != null,
                                    isSuccess = nombreError == null && perfilEditable.nombre.isNotBlank(),
                                    supportingText = errorSupportingText(
                                        fontSize = dimens.captionSize,
                                        error = nombreError
                                    ),
                                    modifier = Modifier.focusRequester(nombreFocus),
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Text,
                                        imeAction = ImeAction.Next
                                    ),
                                    keyboardActions = KeyboardActions(
                                        onNext = { apellidosFocus.requestFocus() }
                                    )
                                )
                            }

                            LevelUpButton(
                                text = "Guardar",
                                icon = Icons.Default.Edit,
                                onClick = {
                                    viewModel.actualizarPerfil(perfilEditable)
                                    viewModel.actualizarAvatarGlobal(perfilEditable.avatar ?: "", mainViewModel)
                                    viewModel.guardarPerfil()
                                },
                                enabled = isSaveEnabled
                            )

                            LevelUpOutlinedButton(
                                text = "Cancelar",
                                icon = Icons.Default.Cancel,
                                onClick = { isEditing.value = false }
                            )
                        }
                    }
                }
            }
        }
    }
}