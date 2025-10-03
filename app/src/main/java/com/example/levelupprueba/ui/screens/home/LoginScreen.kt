package com.example.levelupprueba.ui.screens.home

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.*
import com.example.levelupprueba.viewmodel.LoginViewModel

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel
){
    //Calcula el tamaño de la ventana (compact, medium, expanded)
    val windowSizeClass = calculateWindowSizeClass(
        activity = LocalContext.current as android.app.Activity
    )
    val widthClass = windowSizeClass.widthSizeClass

    // Observa el estado actual del login desde el ViewModel (emailOrName, password, errores, etc.)
    val estado by viewModel.estado.collectAsState()

    // Column organiza los elementos uno debajo del otro (vertical)
    Column(
        modifier = Modifier
            .fillMaxSize()        // Ocupa todo el alto y ancho de la pantalla
            .padding(16.dp),      // Deja un margen de 16dp alrededor
        verticalArrangement = Arrangement.Center // Centra verticalmente
    ) {

        // Campo email o nombre de usuario
        OutlinedTextField(
            value = estado.emailOrName,                  // Valor actual del campo
            onValueChange = viewModel::onEmailOrNameChange,  // Actualiza el campo en el ViewModel
            label = { Text("Nombre o Correo") },             // Etiqueta encima del campo
            isError = estado.errores.emailOrName != null,    // Muestra borde rojo si hay error en este campo
            supportingText = {
                // Si hay error, lo muestra debajo del campo en color de error
                estado.errores.emailOrName?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            },
            singleLine = true,                // Solo una línea
            modifier = Modifier.fillMaxWidth()// Campo ocupa todo el ancho disponible
        )

        // Espaciador (agrega espacio vertical entre componentes)
        Spacer(modifier = Modifier.height(5.dp))

        // Campo de texto para contraseña
        OutlinedTextField(
            value = estado.password,                   // Valor actual del campo de contraseña
            onValueChange = viewModel::onPasswordChange, // Actualiza el campo en el ViewModel
            label = {Text("Contraseña")},                // Etiqueta encima del campo
            visualTransformation = PasswordVisualTransformation(), // Oculta los caracteres
            supportingText = {
                // Si hay error en la contraseña, lo muestra debajo
                estado.errores.password?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            },
            singleLine = true,               // Solo una línea
            modifier = Modifier.fillMaxWidth()// Campo ocupa todo el ancho disponible
        )

        // Espaciador (agrega espacio vertical entre componentes)
        Spacer(modifier = Modifier.height(5.dp))

        // Botón de iniciar sesión
        Button(
            onClick = { // Acción al hacer click
                // Valida el formulario usando el ViewModel
                if (viewModel.validarLogin()){
                    // Si es válido, imprime los datos en consola (aquí iría la lógica real de login)
                    println("Inicio de sesión exitoso!")
                    println("Email: ${estado.emailOrName}, Contraseña ${estado.password}")
                }
            },
            modifier = Modifier.fillMaxWidth() // El botón ocupa todo el ancho disponible
        ) {
            Text("Iniciar sesión")
        }

    }
}