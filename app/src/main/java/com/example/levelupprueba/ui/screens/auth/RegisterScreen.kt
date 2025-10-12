package com.example.levelupprueba.ui.screens.auth

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.levelupprueba.model.registro.RegisterStatus
import com.example.levelupprueba.ui.components.LevelUpCard
import com.example.levelupprueba.ui.components.LevelUpClickableTextLink
import com.example.levelupprueba.ui.components.LevelUpRegisterForm
import com.example.levelupprueba.ui.components.LevelUpSpacedColumn
import com.example.levelupprueba.ui.components.dialogs.LevelUpAlertDialog
import com.example.levelupprueba.ui.components.buttons.LevelUpButton
import com.example.levelupprueba.ui.components.overlays.LevelUpLoadingOverlay
import com.example.levelupprueba.ui.components.topbars.LevelUpTopBar
import com.example.levelupprueba.viewmodel.UsuarioViewModel
import com.example.levelupprueba.ui.theme.LocalDimens
import com.example.levelupprueba.ui.theme.SemanticColors
import com.example.levelupprueba.ui.theme.compactDimens
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

    // Contexto para el registro
    val context = LocalContext.current

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
                            LevelUpRegisterForm(
                                estado = estado,
                                usuarioViewModel = viewModel,
                                ubicacionViewModel = ubicacionViewModel,
                                dimens = dimens
                            )
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
                                            viewModel.registrarUsuario(context)
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(dimens.buttonHeight), // Altura adaptativa del botón
                                dimens = dimens
                            )
                        }

                        item {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = "¿Ya tienes una cuenta?",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = dimens.captionSize
                                )

                                Spacer(modifier = Modifier.width(4.dp))

                                LevelUpClickableTextLink(
                                    text = "Inicia Sesión",
                                    color = SemanticColors.AccentBlue,
                                    onClick = {
                                        coroutineScope.launch {
                                            mainViewModel.navigateTo("login")
                                        }
                                    },
                                    dimens = dimens
                                )
                            }
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

@SuppressLint("ViewModelConstructorInComposable")
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