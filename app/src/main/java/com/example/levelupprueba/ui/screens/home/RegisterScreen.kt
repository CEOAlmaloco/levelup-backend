package com.example.levelupprueba.ui.screens.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.levelupprueba.model.registro.RegisterStatus
import com.example.levelupprueba.model.usuario.isSuccess
import com.example.levelupprueba.ui.components.LevelUpFormSection


import com.example.levelupprueba.ui.components.dialogs.LevelUpAlertDialog
import com.example.levelupprueba.ui.components.buttons.LevelUpButton
import com.example.levelupprueba.ui.components.dropdown.LevelUpDropdownMenu

import com.example.levelupprueba.ui.components.inputs.LevelUpFechaNacimientoField
import com.example.levelupprueba.ui.components.overlays.LevelUpLoadingOverlay
import com.example.levelupprueba.ui.components.inputs.LevelUpPasswordField
import com.example.levelupprueba.ui.components.inputs.LevelUpSwitchField
import com.example.levelupprueba.ui.components.inputs.LevelUpTextField
import com.example.levelupprueba.ui.components.inputs.errorSupportingText
import com.example.levelupprueba.viewmodel.UsuarioViewModel
import com.example.levelupprueba.ui.theme.LocalDimens
import com.example.levelupprueba.ui.theme.compactDimens
import com.example.levelupprueba.utils.formatFecha
import com.example.levelupprueba.viewmodel.UbicacionViewModel
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

    val ubicacionViewModel: UbicacionViewModel = viewModel()

    val puedeRegistrar by remember(estado) {
        derivedStateOf { viewModel.puedeRegistrar() }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(dimens.screenPadding)
            .imePadding(),                  // Padding para teclado
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            //Usamos lazy column que es scrolleable por defecto
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()                   // Ocupa toda la pantalla disponible
                    .navigationBarsPadding(),         // Padding para la barra de navegación
                contentPadding = PaddingValues(dimens.screenPadding),
                verticalArrangement = Arrangement.spacedBy(dimens.sectionSpacing) // Centra los elementos verticalmente
            ) {
                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(dimens.smallSpacing)
                    ) {
                        Text(
                            text = "¡Bienvenido a LevelUp-Gamer!",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier,
                            textAlign = TextAlign.Start // Se alinea al principio del margn
                        )

                        Text(
                            text = "Completa tus datos para registrarte",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier,
                            textAlign = TextAlign.Start // Se alinea al principio del margen
                        )
                    }
                }

                item {
                    LevelUpFormSection(
                        title = "Datos Personales",
                        dimens = dimens
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(dimens.fieldSpacing)
                        ) {
                            // Campo nombre
                            LevelUpTextField(
                                value = estado.nombre.valor,
                                onValueChange = viewModel::onNombreChange,
                                label = "Nombre",
                                isError = estado.nombre.error != null,
                                isSuccess = estado.nombre.isSuccess,
                                supportingText = errorSupportingText(estado.nombre.error)
                            )

                            // Campo apellidos
                            LevelUpTextField(
                                value = estado.apellidos.valor,
                                onValueChange = viewModel::onApellidosChange,
                                label = "Apellidos",
                                isError = estado.apellidos.error != null,
                                isSuccess = estado.apellidos.isSuccess,
                                supportingText = errorSupportingText(estado.apellidos.error)
                            )

                            // Campo fechaNacimiento
                            LevelUpFechaNacimientoField(
                                fechaNacimiento = formatFecha(estado.fechaNacimiento.valor),
                                onFechaNacimientoChange = viewModel::onFechaNacimientoChange,
                                isError = estado.fechaNacimiento.error != null,
                                isSuccess = estado.fechaNacimiento.isSuccess,
                                supportingText = errorSupportingText(estado.fechaNacimiento.error)
                            )
                        }
                    }
                }

                item {
                    LevelUpFormSection(
                        title = "Datos de contacto",
                        dimens = dimens
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(dimens.fieldSpacing)
                        ) {
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
                                supportingText = errorSupportingText(estado.telefono.error),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Phone
                                )
                            )

                            // Campo dirección
                            LevelUpTextField(
                                value = estado.direccion.valor,
                                onValueChange = viewModel::onDireccionChange,
                                label = "Dirección (Opcional)",
                                isError = estado.direccion.error != null,
                                isSuccess = estado.direccion.isSuccess,
                                supportingText = errorSupportingText(estado.direccion.error)
                            )
                        }
                    }
                }

                item {
                    LevelUpFormSection(
                        title = "Ubicación",
                        dimens = dimens
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(dimens.fieldSpacing)
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
                                supportingText = errorSupportingText(estado.region.error)
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
                                supportingText = errorSupportingText(estado.comuna.error),
                                enabled = ubicacionViewModel.selectedRegion != null,
                                placeholder = if (ubicacionViewModel.selectedRegion == null) "Selecciona región primero" else null
                            )
                        }
                    }
                }


                item {
                    LevelUpFormSection(
                        title = "Seguridad",
                        dimens = dimens
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(dimens.fieldSpacing)
                        ) {
                            // Campo password
                            LevelUpPasswordField(
                                value = estado.password.valor,
                                onValueChange = viewModel::onPasswordChange,
                                label = "Contraseña",
                                isError = estado.password.error != null,
                                isSuccess = estado.password.isSuccess,
                                supportingText = errorSupportingText(estado.password.error)
                            )

                            // Campo confirmPassword
                            LevelUpPasswordField(
                                value = estado.confirmPassword.valor,
                                onValueChange = viewModel::onConfirmPasswordChange,
                                label = "Confirmar Contraseña",
                                isError = estado.confirmPassword.error != null,
                                isSuccess = estado.confirmPassword.isSuccess,
                                supportingText = errorSupportingText(estado.confirmPassword.error)
                            )

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
                    }
                }

                item {
                    // Botón de registro
                    LevelUpButton(
                        text = "Registrar",
                        icon = Icons.Filled.Check,
                        iconSize = dimens.iconSize,
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
                            .height(dimens.buttonHeight) // Altura adaptativa del botón
                    )
                }
            }
        }

        // Scrim con Loading
        when (registroEstado){
            is RegisterStatus.Loading -> {
                // Overlay oscuro y spinner
            }
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
                        navController.navigate("login")
                    }
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
                    }
                )
            }

            RegisterStatus.Idle -> {}
        }
    }
    LevelUpLoadingOverlay(
        visible = registroEstado is RegisterStatus.Loading
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, name = "RegisterScreen Preview")
@Composable
@Preview(showBackground = true, name = "RegisterScreen Preview")
fun PreviewRegisterScreen() {
    val navController = rememberNavController()
    val viewModel = UsuarioViewModel()

    CompositionLocalProvider(
        LocalDimens provides compactDimens // o mediumDimens, expandedDimens
    ) {
        RegisterScreen(
            navController = navController,
            viewModel = viewModel
        )
    }
}