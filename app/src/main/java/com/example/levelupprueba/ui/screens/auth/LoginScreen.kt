package com.example.levelupprueba.ui.screens.auth

import android.content.Intent
import androidx.activity.ComponentActivity
import com.example.levelupprueba.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Start
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.levelupprueba.MainActivity
import com.example.levelupprueba.model.auth.LoginStatus
import com.example.levelupprueba.model.auth.isSuccess
import com.example.levelupprueba.ui.components.LevelUpCard
import com.example.levelupprueba.ui.components.LevelUpClickableTextLink
import com.example.levelupprueba.ui.components.LevelUpSpacedColumn
import com.example.levelupprueba.ui.components.buttons.LevelUpButton
import com.example.levelupprueba.ui.components.dialogs.LevelUpAlertDialog
import com.example.levelupprueba.ui.components.inputs.LevelUpPasswordField
import com.example.levelupprueba.ui.components.inputs.LevelUpTextField
import com.example.levelupprueba.ui.components.inputs.errorSupportingText
import com.example.levelupprueba.ui.components.overlays.LevelUpLoadingOverlay
import com.example.levelupprueba.ui.components.topbars.LevelUpTopBar
import com.example.levelupprueba.ui.theme.LocalDimens
import com.example.levelupprueba.ui.theme.SemanticColors
import com.example.levelupprueba.viewmodel.LoginViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    navController: NavHostController
){

    // Observa el estado actual del login desde el ViewModel (emailOrName, password, errores, etc.)
    val estado by viewModel.estado.collectAsState()

    val loginEstado by viewModel.loginEstado.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    // Contexto para el inicio de sesión
    val context = LocalContext.current

    //Utilizamos las dimensiones de Theme
    val dimens = LocalDimens.current


    Scaffold(
        topBar = {
            Column {
                LevelUpTopBar(
                    title = "Inicio de Sesión",
                    onBackClick = {
                        coroutineScope.launch {
                            val canGoBack = navController.popBackStack()
                            if (!canGoBack) {
                                context.startActivity(Intent(context, MainActivity::class.java))
                                (context as? ComponentActivity)?.finish()
                            }
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
            ){
                LevelUpCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = dimens.screenPadding)
                ) {
                    // Scroll state para el Column
                    val scrollState = rememberScrollState()
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()                   // Ocupa toda la pantalla disponible
                            .navigationBarsPadding()        // Padding para la barra de navegación
                            .verticalScroll(scrollState),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){

                        Image(
                            painter = painterResource(id = R.drawable.levelup_logo),
                            contentDescription = "Logo LevelUp",
                            modifier = Modifier
                                .size(dimens.imageHeight / 2)
                        )

                        Spacer(modifier = Modifier.height(dimens.sectionSpacing))

                        LevelUpSpacedColumn(
                            spacing = dimens.mediumSpacing
                        ) {
                            Text(
                                text = "Bienvenido de vuelta, jugador",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onBackground,
                                fontSize = dimens.titleSize
                            )

                            Text(
                                text = "Inicia Sesión",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = dimens.subtitleSize
                            )
                        }

                        Spacer(modifier = Modifier.height(dimens.sectionSpacing))

                        Column(
                            verticalArrangement = Arrangement.spacedBy(dimens.fieldSpacing),
                        ){
                            LevelUpTextField(
                                value = estado.emailOrName.valor,
                                onValueChange = viewModel::onEmailOrNameChange,
                                label = "Correo o Nombre",
                                isError = estado.emailOrName.error != null,
                                isSuccess = estado.emailOrName.isSuccess,
                                supportingText = errorSupportingText(fontSize = dimens.captionSize, error = estado.emailOrName.error),
                                dimens = dimens
                            )

                            LevelUpPasswordField(
                                value = estado.password.valor,
                                onValueChange = viewModel::onPasswordChange,
                                label = "Contraseña",
                                isError = estado.password.error != null,
                                isSuccess = estado.password.isSuccess,
                                supportingText = errorSupportingText(fontSize = dimens.captionSize, error = estado.password.error),
                                dimens = dimens
                            )
                        }

                        Spacer(modifier = Modifier.height(dimens.sectionSpacing))

                        LevelUpButton(
                            text = "Iniciar Sesión",
                            icon = Icons.Filled.Start,
                            enabled = viewModel.puedeIniciarSesion() && loginEstado != LoginStatus.Loading,
                            onClick = {
                                if (viewModel.validarLogin()) {
                                    coroutineScope.launch {
                                        viewModel.loginUsuario(context, estado.emailOrName.valor, estado.password.valor)
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(dimens.buttonHeight), // Altura adaptativa del botón
                            dimens = dimens
                        )

                        Spacer(modifier = Modifier.height(dimens.sectionSpacing))

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "¿No tienes una cuenta?",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = dimens.captionSize
                            )

                            Spacer(modifier = Modifier.width(4.dp))

                            LevelUpClickableTextLink(
                                text = "Regístrate",
                                color = SemanticColors.AccentBlue,
                                onClick = { navController.navigate("registro") },
                                dimens = dimens
                            )
                        }
                    }

                    // Scrim con Loading
                    when (loginEstado){
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
                                    // Navega a MainActivity
                                    context.startActivity(Intent(context, MainActivity::class.java))
                                    // Opcional: Cierra AuthActivity para que no vuelva atrás
                                    (context as? ComponentActivity)?.finish()
                                },
                                dimens = dimens
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
                                },
                                dimens = dimens
                            )
                        }

                        else -> {}
                    }

                }
            }
        }
    )
    LevelUpLoadingOverlay(
        visible = loginEstado is LoginStatus.Loading
    )
}