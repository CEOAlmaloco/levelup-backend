package com.example.levelupprueba.ui.screens.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.levelupprueba.model.registro.RegistroUiState
import com.example.levelupprueba.model.usuario.isSuccess
import com.example.levelupprueba.ui.components.dialogs.LevelUpAlertDialog
import com.example.levelupprueba.ui.components.buttons.LevelUpButton
import com.example.levelupprueba.ui.components.inputs.LevelUpFechaNacimientoField
import com.example.levelupprueba.ui.components.overlays.LevelUpLoadingOverlay
import com.example.levelupprueba.ui.components.inputs.LevelUpPasswordField
import com.example.levelupprueba.ui.components.inputs.LevelUpSwitchField
import com.example.levelupprueba.ui.components.inputs.LevelUpTextField
import com.example.levelupprueba.ui.components.inputs.errorSupportingText
import com.example.levelupprueba.viewmodel.UsuarioViewModel
import com.example.levelupprueba.ui.theme.LocalDimens
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: UsuarioViewModel
){
    // Observa el estado del formulario y errores en tiempo real desde el ViewModel
    val estado by viewModel.estado.collectAsState()

    // Observa el estado de proceso de registro
    val registroEstado by viewModel.registroEstado.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    //Utilizamos las dimensiones de Theme
    val dimens = LocalDimens.current


    val puedeRegistrar by remember(estado) {
        derivedStateOf { viewModel.puedeRegistrar() }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        //Usamos lazy column que es scrolleable por defecto
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()                   // Ocupa toda la pantalla disponible
                .navigationBarsPadding()         // Padding para la barra de navegación
                .imePadding(),                  // Padding para teclado
            contentPadding = PaddingValues(dimens.screenPadding),
            verticalArrangement = Arrangement.spacedBy(dimens.fieldSpacing) // Centra los elementos verticalmente
        ) {
            item {
                Text(
                    text = "¡Bienvenido a LevelUp-Gamer!",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .padding(bottom = dimens.titleSpacing), // Espaciado adaptativo para títulos
                    textAlign = TextAlign.Start // Se alinea al principio del margn
                )
            }
            item {
                Text(
                    text = "Completa tus datos para registrarte",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .padding(bottom = dimens.fieldSpacing), // Espaciado adaptativo entre título y campos
                    textAlign = TextAlign.Start // Se alinea al principio del margen
                )
            }

            item {
                // Campo nombre
                LevelUpTextField(
                    value = estado.nombre.valor,
                    onValueChange = viewModel::onNombreChange,
                    label = "Nombre",
                    isError = estado.nombre.error != null,
                    isSuccess = estado.nombre.isSuccess,
                    supportingText = errorSupportingText(estado.nombre.error)
                )
            }

            item {
                // Campo apellidos
                LevelUpTextField(
                    value = estado.apellidos.valor,
                    onValueChange = viewModel::onApellidosChange,
                    label = "Apellidos",
                    isError = estado.apellidos.error != null,
                    isSuccess = estado.apellidos.isSuccess,
                    supportingText = errorSupportingText(estado.apellidos.error)
                )
            }

            item {
                // Campo email
                LevelUpTextField(
                    value = estado.email.valor,
                    onValueChange = viewModel::onEmailChange,
                    label = "Correo Electrónico",
                    isError = estado.email.error != null,
                    isSuccess = estado.email.isSuccess,
                    supportingText = errorSupportingText(estado.email.error),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    )
                )
            }

            item {
                // Campo password
                LevelUpPasswordField(
                    value = estado.password.valor,
                    onValueChange = viewModel::onPasswordChange,
                    label = "Contraseña",
                    isError = estado.password.error != null,
                    isSuccess = estado.password.isSuccess,
                    supportingText = errorSupportingText(estado.password.error)
                )
            }

            item {
                // Campo confirmPassword
                LevelUpPasswordField(
                    value = estado.confirmPassword.valor,
                    onValueChange = viewModel::onConfirmPasswordChange,
                    label = "Confirmar Contraseña",
                    isError = estado.confirmPassword.error != null,
                    isSuccess = estado.confirmPassword.isSuccess,
                    supportingText = errorSupportingText(estado.confirmPassword.error)
                )
            }

            item {
                // Campo teléfono
                LevelUpTextField(
                    value = estado.telefono.valor,
                    onValueChange = { nuevoNumero ->
                        if (nuevoNumero.all { it.isDigit() }) {
                            viewModel.onTelefonoChange(nuevoNumero)
                        }
                    },
                    label = "Teléfono",
                    isError = estado.telefono.error != null,
                    isSuccess = estado.telefono.isSuccess,
                    supportingText = errorSupportingText(estado.telefono.error),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone
                    )
                )
            }
            item {
                LevelUpFechaNacimientoField(
                    fechaNacimiento = estado.fechaNacimiento.valor,
                    onFechaNacimientoChange = { viewModel.onFechaNacimientoChange(it) },
                    isError = estado.fechaNacimiento.error != null,
                    isSuccess = estado.fechaNacimiento.isSuccess,
                    supportingText = errorSupportingText(estado.fechaNacimiento.error)
                )
            }

            item {
                // Campo región
                LevelUpTextField(
                    value = estado.region.valor,
                    onValueChange = viewModel::onRegionChange,
                    label = "Región",
                    isError = estado.region.error != null,
                    isSuccess = estado.region.isSuccess,
                    supportingText = errorSupportingText(estado.region.error)
                )
            }

            item {
                // Campo comuna
                LevelUpTextField(
                    value = estado.comuna.valor,
                    onValueChange = viewModel::onComunaChange,
                    label = "Comuna",
                    isError = estado.comuna.error != null,
                    isSuccess = estado.comuna.isSuccess,
                    supportingText = errorSupportingText(estado.comuna.error)
                )
            }

            item {
                // Campo dirección
                LevelUpTextField(
                    value = estado.direccion.valor,
                    onValueChange = viewModel::onDireccionChange,
                    label = "Dirección",
                    isError = estado.direccion.error != null,
                    isSuccess = estado.direccion.isSuccess,
                    supportingText = errorSupportingText(estado.direccion.error)
                )
            }

            item {
                LevelUpSwitchField(
                    checked = estado.terminos.valor == "true",
                    onCheckedChange = {
                        viewModel.onTerminosChange(it)
                    },
                    label = "Acepto los términos y condiciones",
                    error = estado.terminos.error,
                    labelSpacing = dimens.fieldSpacing // tu valor personalizado de espaciado
                )
            }

            item {
                // Botón de registro
                LevelUpButton(
                    text = "Registrar",
                    icon = Icons.Filled.Check,
                    iconSize = dimens.iconSize,
                    enabled = puedeRegistrar && registroEstado != RegistroUiState.Loading,
                    onClick = {
                        // Valida el formulario usando el ViewModel
                        if (viewModel.validarRegistro()) {
                            coroutineScope.launch {
                                viewModel.registrarUsuario()
                            }
                            // Aquí podrías navegar con navController, por ejemplo:
                            // navController.navigate("login")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimens.buttonHeight) // Altura adaptativa del botón
                )
            }
        }

        // Scrim con Loading
        when (registroEstado){
            is RegistroUiState.Loading -> {
                // Overlay oscuro y spinner
                LevelUpLoadingOverlay()
            }
            is RegistroUiState.Success -> {
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
                        //navController.navigate("login")
                    }
                )
            }
            is RegistroUiState.Error -> {
                // Popup de error (AlertDialog)
                val mensajeError = (registroEstado as RegistroUiState.Error).mensajeError
                LevelUpAlertDialog(
                    onDismissRequest = {
                        viewModel.resetRegistroEstado()
                    },
                    title = "Error",
                    text = mensajeError,
                    confirmText = "Cerrar",
                    onConfirm = {
                        viewModel.resetRegistroEstado()
                    }
                )
            }

            RegistroUiState.Idle -> {}
        }
    }
}