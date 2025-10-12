package com.example.levelupprueba.ui.screens.auth

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.levelupprueba.model.registro.RegisterStatus
import com.example.levelupprueba.model.usuario.isSuccess
import com.example.levelupprueba.ui.components.LevelUpCard
import com.example.levelupprueba.ui.components.LevelUpFormSection
import com.example.levelupprueba.ui.components.LevelUpSpacedColumn
import com.example.levelupprueba.ui.components.dialogs.LevelUpAlertDialog
import com.example.levelupprueba.ui.components.buttons.LevelUpButton
import com.example.levelupprueba.ui.components.dropdown.LevelUpDropdownMenu
import com.example.levelupprueba.ui.components.inputs.LevelUpFechaNacimientoField
import com.example.levelupprueba.ui.components.overlays.LevelUpLoadingOverlay
import com.example.levelupprueba.ui.components.inputs.LevelUpPasswordField
import com.example.levelupprueba.ui.components.inputs.LevelUpSwitchField
import com.example.levelupprueba.ui.components.inputs.LevelUpTextField
import com.example.levelupprueba.ui.components.inputs.errorSupportingText
import com.example.levelupprueba.ui.components.inputs.supportingTextOrError
import com.example.levelupprueba.ui.components.topbars.LevelUpTopBar
import com.example.levelupprueba.viewmodel.UsuarioViewModel
import com.example.levelupprueba.ui.theme.LocalDimens
import com.example.levelupprueba.ui.theme.compactDimens
import com.example.levelupprueba.utils.formatFecha
import com.example.levelupprueba.viewmodel.MainViewModel
import com.example.levelupprueba.viewmodel.UbicacionViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    viewModel: UsuarioViewModel,
    mainViewModel: MainViewModel
){
    // Observa el estado del formulario y errores en tiempo real desde el ViewModel
    val estado by viewModel.estado.collectAsState()

    // Observa el estado de proceso de registro
    val registroEstado by viewModel.registroEstado.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    val puedeRegistrar by remember(estado) {
        derivedStateOf {
            viewModel.puedeRegistrar()
        }
    }

    //Utilizamos las dimensiones de Theme
    val dimens = LocalDimens.current

    val ubicacionViewModel: UbicacionViewModel = viewModel()

    Scaffold(
        topBar = {
            Column {
                LevelUpTopBar(
                    title = "Registro",
                    onBackClick = {
                        coroutineScope.launch {
                            mainViewModel.navigateBack()
                        }
                    },
                    dimens = dimens
                )
                Spacer(modifier = Modifier.height(dimens.sectionSpacing))
            }
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(innerPadding)
                    .imePadding(),                  // Padding para teclado
                contentAlignment = Alignment.Center
            ) {
                LevelUpCard(
                    modifier = Modifier
                        .padding(horizontal = dimens.screenPadding)
                ) {
                    //Usamos lazy column que es scrolleable por defecto
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()                   // Ocupa toda la pantalla disponible
                            .navigationBarsPadding(),         // Padding para la barra de navegación
                        verticalArrangement = Arrangement.spacedBy(dimens.sectionSpacing),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        item {
                            LevelUpSpacedColumn(
                                spacing = dimens.mediumSpacing
                            ) {
                                Text(
                                    text = "¡Bienvenido a Level-Up Gamer!",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontSize = dimens.titleSize
                                )

                                Text(
                                    text = "Completa tus datos para registrarte",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = dimens.subtitleSize
                                )
                            }
                        }

                        item {
                            LevelUpFormSection(
                                title = "Datos Personales",
                                dimens = dimens
                            ) {
                                // Campo nombre
                                LevelUpTextField(
                                    value = estado.nombre.valor,
                                    onValueChange = viewModel::onNombreChange,
                                    label = "Nombre",
                                    isError = estado.nombre.error != null,
                                    isSuccess = estado.nombre.isSuccess,
                                    supportingText = errorSupportingText(fontSize = dimens.captionSize, error = estado.nombre.error),
                                    dimens = dimens
                                )

                                // Campo apellidos
                                LevelUpTextField(
                                    value = estado.apellidos.valor,
                                    onValueChange = viewModel::onApellidosChange,
                                    label = "Apellidos",
                                    isError = estado.apellidos.error != null,
                                    isSuccess = estado.apellidos.isSuccess,
                                    supportingText = errorSupportingText(fontSize = dimens.captionSize, error = estado.apellidos.error),
                                    dimens = dimens
                                )

                                // Campo fechaNacimiento
                                LevelUpFechaNacimientoField(
                                    fechaNacimiento = formatFecha(estado.fechaNacimiento.valor),
                                    onFechaNacimientoChange = viewModel::onFechaNacimientoChange,
                                    isError = estado.fechaNacimiento.error != null,
                                    isSuccess = estado.fechaNacimiento.isSuccess,
                                    supportingText = errorSupportingText(fontSize = dimens.captionSize, error = estado.fechaNacimiento.error),
                                    dimens = dimens
                                )

                            }
                        }

                        item {
                            LevelUpFormSection(
                                title = "Datos de contacto",
                                dimens = dimens
                            ) {

                                // Campo email
                                LevelUpTextField(
                                    value = estado.email.valor,
                                    onValueChange = viewModel::onEmailChange,
                                    label = "Correo Electrónico",
                                    isError = estado.email.error != null,
                                    isSuccess = estado.email.isSuccess,
                                    supportingText = supportingTextOrError(
                                        error = estado.email.error,
                                        helperText = "@gmail.com o @duoc.cl",
                                        isSuccess = estado.email.isSuccess,
                                        fontSize = dimens.captionSize,
                                    ),
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Email
                                    ),
                                    dimens = dimens
                                )

                                // Campo teléfono
                                LevelUpTextField(
                                    value = estado.telefono.valor,
                                    onValueChange = { nuevoNumero ->
                                        if (nuevoNumero.all { it.isDigit() }) {
                                            viewModel.onTelefonoChange(nuevoNumero)
                                        }
                                    },
                                    label = "Teléfono (Opcional)",
                                    isError = estado.telefono.error != null,
                                    isSuccess = estado.telefono.isSuccess,
                                    supportingText = errorSupportingText(fontSize = dimens.captionSize, error = estado.telefono.error),
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Phone
                                    ),
                                    dimens = dimens
                                )

                                // Campo dirección
                                LevelUpTextField(
                                    value = estado.direccion.valor,
                                    onValueChange = viewModel::onDireccionChange,
                                    label = "Dirección (Opcional)",
                                    isError = estado.direccion.error != null,
                                    isSuccess = estado.direccion.isSuccess,
                                    supportingText = errorSupportingText(fontSize = dimens.captionSize, error = estado.direccion.error),
                                    dimens = dimens
                                )

                            }
                        }

                        item {
                            LevelUpFormSection(
                                title = "Ubicación",
                                dimens = dimens
                            ) {
                                // Campo región
                                LevelUpDropdownMenu(
                                    label = "Región",
                                    options = ubicacionViewModel.regiones.map { it.nombre },
                                    selectedOption = ubicacionViewModel.selectedRegion?.nombre,
                                    onOptionSelected = { nombre ->
                                        ubicacionViewModel.selectRegion(nombre)
                                        viewModel.onRegionChange(nombre)   // Actualiza Region en UsuarioViewModel
                                        viewModel.onComunaChange("")       // Limpia comuna en UsuarioViewModel
                                    },
                                    isError = estado.region.error != null,
                                    isSuccess = estado.region.isSuccess,
                                    supportingText = errorSupportingText(fontSize = dimens.captionSize, error = estado.region.error),
                                    dimens = dimens
                                )

                                // Campo comuna
                                LevelUpDropdownMenu(
                                    label = "Comuna",
                                    options = ubicacionViewModel.selectedRegion?.comunas?.map { it.nombre }
                                        ?: emptyList(),
                                    selectedOption = ubicacionViewModel.selectedComuna?.nombre,
                                    onOptionSelected = { nombre ->
                                        ubicacionViewModel.selectComuna(nombre)
                                        viewModel.onComunaChange(nombre) // Actualiza Comuna en UsuarioViewModel
                                    },
                                    isError = estado.comuna.error != null,
                                    isSuccess = estado.comuna.isSuccess,
                                    supportingText = errorSupportingText(fontSize = dimens.captionSize, error = estado.comuna.error),
                                    enabled = ubicacionViewModel.selectedRegion != null,
                                    placeholder = if (ubicacionViewModel.selectedRegion == null) "Selecciona región primero" else null,
                                    dimens = dimens
                                )
                            }
                        }


                        item {
                            LevelUpFormSection(
                                title = "Seguridad",
                                dimens = dimens
                            ) {
                                // Campo password
                                LevelUpPasswordField(
                                    value = estado.password.valor,
                                    onValueChange = viewModel::onPasswordChange,
                                    label = "Contraseña",
                                    isError = estado.password.error != null,
                                    isSuccess = estado.password.isSuccess,
                                    supportingText = supportingTextOrError(
                                        error = estado.password.error,
                                        helperText = "La contraseña debe tener entre 4 y 10 caracteres",
                                        isSuccess = estado.password.isSuccess,
                                        fontSize = dimens.captionSize,
                                    ),
                                    dimens = dimens
                                )

                                // Campo confirmPassword
                                LevelUpPasswordField(
                                    value = estado.confirmPassword.valor,
                                    onValueChange = viewModel::onConfirmPasswordChange,
                                    label = "Confirmar Contraseña",
                                    isError = estado.confirmPassword.error != null,
                                    isSuccess = estado.confirmPassword.isSuccess,
                                    supportingText = errorSupportingText(
                                        error = estado.confirmPassword.error,
                                        fontSize = dimens.captionSize
                                    ),
                                    dimens = dimens
                                )

                                LevelUpSwitchField(
                                    checked = estado.terminos.valor == "true",
                                    onCheckedChange = {
                                        viewModel.onTerminosChange(it)
                                    },
                                    label = "Acepto los términos y condiciones",
                                    error = estado.terminos.error,
                                    dimens = dimens
                                )
                            }
                        }

                        item {
                            // Botón de registro
                            LevelUpButton(
                                text = "Registrar",
                                icon = Icons.Filled.Check,
                                enabled = puedeRegistrar && registroEstado != RegisterStatus.Loading,
                                onClick = {
                                    // Valida el formulario usando el ViewModel
                                    if (viewModel.validarRegistro()) {
                                        coroutineScope.launch {
                                            viewModel.registrarUsuario()
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(dimens.buttonHeight), // Altura adaptativa del botón
                                dimens = dimens
                            )
                        }
                    }
                }
                LevelUpLoadingOverlay(
                    visible = registroEstado is RegisterStatus.Loading
                )
                // Scrim con Loading
                when (registroEstado){
                    is RegisterStatus.Success -> {
                        // Popup de éxito (AlertDialog)
                        LevelUpAlertDialog(
                            onDismissRequest = {
                                viewModel.resetRegistroEstado()
                            },
                            title = "¡Registro exitoso!",
                            text = "Tu usuario se ha creado correctamente.",
                            confirmText = "Aceptar",
                            onConfirm = {
                                viewModel.resetRegistroEstado()
                                coroutineScope.launch {
                                    mainViewModel.navigateTo("login")
                                }
                            },
                            dimens = dimens
                        )
                    }
                    is RegisterStatus.Error -> {
                        // Popup de error (AlertDialog)
                        val mensajeError = (registroEstado as RegisterStatus.Error).mensajeError
                        LevelUpAlertDialog(
                            onDismissRequest = {
                                viewModel.resetRegistroEstado()
                            },
                            title = "Error",
                            text = mensajeError,
                            confirmText = "Cerrar",
                            onConfirm = {
                                viewModel.resetRegistroEstado()
                            },
                            dimens = dimens
                        )
                    }

                    else -> {}
                }
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview(showBackground = true, name = "RegisterScreen Preview")
fun PreviewRegisterScreen() {
    val viewModel = UsuarioViewModel()
    val mainViewModel = MainViewModel()

    CompositionLocalProvider(
        LocalDimens provides compactDimens // o mediumDimens, expandedDimens
    ) {
        RegisterScreen(
            viewModel = viewModel,
            mainViewModel = mainViewModel
        )
    }
}