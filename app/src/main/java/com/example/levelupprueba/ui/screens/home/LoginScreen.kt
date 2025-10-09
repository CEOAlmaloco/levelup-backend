package com.example.levelupprueba.ui.screens.home

import com.example.levelupprueba.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Start
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.example.levelupprueba.model.auth.LoginStatus
import com.example.levelupprueba.model.auth.isSuccess
import com.example.levelupprueba.model.registro.RegisterStatus
import com.example.levelupprueba.ui.components.buttons.LevelUpButton
import com.example.levelupprueba.ui.components.dialogs.LevelUpAlertDialog
import com.example.levelupprueba.ui.components.inputs.LevelUpPasswordField
import com.example.levelupprueba.ui.components.inputs.LevelUpTextField
import com.example.levelupprueba.ui.components.inputs.errorSupportingText
import com.example.levelupprueba.ui.components.overlays.LevelUpLoadingOverlay
import com.example.levelupprueba.ui.theme.LocalDimens
import com.example.levelupprueba.viewmodel.LoginViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel
){

    // Observa el estado actual del login desde el ViewModel (emailOrName, password, errores, etc.)
    val estado by viewModel.estado.collectAsState()

    val loginEstado by viewModel.loginEstado.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    //Utilizamos las dimensiones de Theme
    val dimens = LocalDimens.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(dimens.screenPadding)
            .imePadding(),                  // Padding para teclado
    ){
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(dimens.screenPadding)
                    .fillMaxSize()                   // Ocupa toda la pantalla disponible
                    .navigationBarsPadding(),         // Padding para la barra de navegación
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                Image(
                    painter = painterResource(id = R.drawable.levelup_logo),
                    contentDescription = "Logo LevelUp",
                    modifier = Modifier
                        .size(120.dp)
                )

                Spacer(modifier = Modifier.height(dimens.smallSpacing))

                Text(
                    text = "Bienvenido de vuelta, jugador",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(dimens.mediumSpacing))

                Text(
                    text = "Inicia Sesión",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(dimens.largeSpacing))

                Column(
                    verticalArrangement = Arrangement.spacedBy(dimens.fieldSpacing),
                ){
                    LevelUpTextField(
                        value = estado.emailOrName.valor,
                        onValueChange = viewModel::onEmailOrNameChange,
                        label = "Correo o Nombre",
                        isError = estado.emailOrName.error != null,
                        isSuccess = estado.emailOrName.isSuccess,
                        supportingText = errorSupportingText(estado.emailOrName.error)
                    )

                    LevelUpPasswordField(
                        value = estado.password.valor,
                        onValueChange = viewModel::onPasswordChange,
                        label = "Contraseña",
                        isError = estado.password.error != null,
                        isSuccess = estado.password.isSuccess,
                        supportingText = errorSupportingText(estado.password.error)
                    )
                }

                Spacer(modifier = Modifier.height(dimens.sectionSpacing))

                LevelUpButton(
                    text = "Iniciar Sesión",
                    icon = Icons.Filled.Start,
                    iconSize = dimens.iconSize,
                    enabled = viewModel.puedeIniciarSesion(),
                    onClick = {
                        if (viewModel.validarLogin()) {
                            coroutineScope.launch {
                                viewModel.loginUsuario()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimens.buttonHeight) // Altura adaptativa del botón

                )
            }

            // Scrim con Loading
            when (loginEstado){
                is LoginStatus.Loading -> {
                    // Overlay oscuro y spinner
                }
                is LoginStatus.Success -> {
                    // Popup de éxito (AlertDialog)
                    LevelUpAlertDialog(
                        onDismissRequest = {
                            viewModel.resetLoginEstado()
                        },
                        title = "¡Inicio de sesion exitoso!",
                        text = "Has iniciado sesión correctamente.",
                        confirmText = "Aceptar",
                        onConfirm = {
                            viewModel.resetLoginEstado()
                            navController.navigate("home")
                        }
                    )
                }
                is LoginStatus.Error -> {
                    // Popup de error (AlertDialog)
                    val mensajeError = (loginEstado as LoginStatus.Error).mensajeError
                    LevelUpAlertDialog(
                        onDismissRequest = {
                            viewModel.resetLoginEstado()
                        },
                        title = "Error",
                        text = mensajeError,
                        confirmText = "Cerrar",
                        onConfirm = {
                            viewModel.resetLoginEstado()
                        }
                    )
                }

                LoginStatus.Idle -> {}
            }

        }
    }
    LevelUpLoadingOverlay(
        visible = loginEstado is LoginStatus.Loading
    )
}