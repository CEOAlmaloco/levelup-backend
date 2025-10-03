package com.example.levelupprueba.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.example.levelupprueba.model.RegistroUiEstado
import com.example.levelupprueba.viewmodel.UsuarioViewModel
import com.example.levelupprueba.ui.theme.LocalDimens
import com.example.levelupprueba.ui.theme.MenuButton
import com.example.levelupprueba.ui.theme.levelUpTextFieldColors
import kotlinx.coroutines.launch

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

    // ScrollState para hacer la columna desplazable
    val scrollState = rememberScrollState()

    //Utilizamos las dimensiones de Theme
    val dimens = LocalDimens.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .imePadding()
    ) {
        Column(
            modifier = Modifier
                .padding(dimens.screenPadding)   // Usa padding adaptativo
                .fillMaxSize()                   // Ocupa toda la pantalla disponible
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Center // Centra los elementos verticalmente
        ) {
            Text(
                text = "¡Bienvenido a LevelUp-Gamer!",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = dimens.titleSpacing) // Espaciado adaptativo para títulos
            )
            Text(
                text = "Completa tus datos para registrarte",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = dimens.fieldSpacing) // Espaciado adaptativo entre título y campos
            )

            // Campo nombre
            OutlinedTextField(
                value = estado.nombre,
                onValueChange = viewModel::onNombreChange,
                colors = levelUpTextFieldColors(),
                label = { Text("Nombre") },
                isError = estado.errores.nombre != null,
                supportingText = {
                    estado.errores.nombre?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(dimens.fieldSpacing)) // Espaciado adaptativo entre campos

            // Campo apellidos
            OutlinedTextField(
                value = estado.apellidos,
                onValueChange = viewModel::onApellidosChange,
                colors = levelUpTextFieldColors(),
                label = { Text("Apellidos") },
                isError = estado.errores.apellidos != null,
                supportingText = {
                    estado.errores.apellidos?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(dimens.fieldSpacing))

            // Campo email
            OutlinedTextField(
                value = estado.email,
                onValueChange = viewModel::onEmailChange,
                colors = levelUpTextFieldColors(),
                label = { Text("Correo Electrónico") },
                isError = estado.errores.email != null,
                supportingText = {
                    estado.errores.email?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(dimens.fieldSpacing))

            // Campo password
            OutlinedTextField(
                value = estado.password,
                onValueChange = viewModel::onPasswordChange,
                colors = levelUpTextFieldColors(),
                label = { Text("Contraseña") },
                isError = estado.errores.password != null,
                supportingText = {
                    estado.errores.password?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(dimens.fieldSpacing))

            // Campo confirmPassword
            OutlinedTextField(
                value = estado.confirmPassword,
                onValueChange = viewModel::onConfirmPasswordChange,
                colors = levelUpTextFieldColors(),
                label = { Text("Confirmar Contraseña") },
                isError = estado.errores.confirmPassword != null,
                supportingText = {
                    estado.errores.confirmPassword?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(dimens.fieldSpacing))

            // Campo teléfono
            OutlinedTextField(
                value = estado.telefono,
                onValueChange = viewModel::onTelefonoChange,
                colors = levelUpTextFieldColors(),
                label = { Text("Teléfono") },
                isError = estado.errores.telefono != null,
                supportingText = {
                    estado.errores.telefono?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(dimens.fieldSpacing))

            // Campo fecha de nacimiento
            OutlinedTextField(
                value = estado.fechaNacimiento,
                onValueChange = viewModel::onFechaNacimientoChange,
                colors = levelUpTextFieldColors(),
                label = { Text("Fecha de Nacimiento") },
                isError = estado.errores.fechaNacimiento != null,
                supportingText = {
                    estado.errores.fechaNacimiento?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(dimens.fieldSpacing))

            // Campo región
            OutlinedTextField(
                value = estado.region,
                onValueChange = viewModel::onRegionChange,
                colors = levelUpTextFieldColors(),
                label = { Text("Región") },
                isError = estado.errores.region != null,
                supportingText = {
                    estado.errores.region?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(dimens.fieldSpacing))

            // Campo comuna
            OutlinedTextField(
                value = estado.comuna,
                onValueChange = viewModel::onComunaChange,
                colors = levelUpTextFieldColors(),
                label = { Text("Comuna") },
                isError = estado.errores.comuna != null,
                supportingText = {
                    estado.errores.comuna?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(dimens.fieldSpacing))

            // Campo dirección
            OutlinedTextField(
                value = estado.direccion,
                onValueChange = viewModel::onDireccionChange,
                colors = levelUpTextFieldColors(),
                label = { Text("Dirección") },
                isError = estado.errores.direccion != null,
                supportingText = {
                    estado.errores.direccion?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(dimens.fieldSpacing))

            // Campo aceptar términos con Switch
            Row(verticalAlignment = Alignment.CenterVertically) {
                Switch(
                    checked = estado.terminos,
                    onCheckedChange = viewModel::onTerminosChange
                )
                Spacer(Modifier.width(dimens.fieldSpacing)) // Espaciado adaptativo
                Text("Acepto los términos y condiciones")
            }
            // Mensaje de error si no acepta los términos
            estado.errores.terminos?.let {
                Text(it, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
            }
            Spacer(Modifier.height(dimens.fieldSpacing * 2)) // Extra espacio antes del botón

            // Botón de registro
            MenuButton(
                text = "Registrar",
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

        // Scrim con Loading
        when (registroEstado){
            is RegistroUiEstado.Loading -> {
                // Overlay oscuro y spinner
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.4f)),
                    contentAlignment = Alignment.Center
                ){
                    CircularProgressIndicator()
                }
            }
            is RegistroUiEstado.Success -> {
                // Popup de éxito (AlertDialog)
                AlertDialog(
                    onDismissRequest = {
                        viewModel.resetRegistroEstado()
                    },
                    title = {Text("¡Registro exitoso!")},
                    text = {Text("Tu usuario se ha creado correctamente.")},
                    confirmButton = {
                        Button(
                            onClick = {
                                viewModel.resetRegistroEstado()
                                //navController.navigate("login")
                            }
                        ) {
                            Text("Aceptar")
                        }
                    }
                )
            }
            is RegistroUiEstado.Error -> {
                // Popup de error (AlertDialog)
                val mensajeError = (registroEstado as RegistroUiEstado.Error).mensajeError
                AlertDialog(
                    onDismissRequest = {viewModel.resetRegistroEstado()},
                    title = {Text("Error")},
                    text = {Text(mensajeError)},
                    confirmButton = {
                        Button(onClick = {viewModel.resetRegistroEstado()}) {
                            Text("Cerrar")
                        }
                    }
                )
            }

            RegistroUiEstado.Idle -> {}
        }
    }
}