package com.example.levelupprueba.ui.screens.home

import com.example.levelupprueba.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.levelupprueba.ui.components.inputs.LevelUpTextField
import com.example.levelupprueba.ui.theme.LocalDimens
import com.example.levelupprueba.viewmodel.LoginViewModel


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel
){

    // Observa el estado actual del login desde el ViewModel (emailOrName, password, errores, etc.)
    val estado by viewModel.estado.collectAsState()

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

                Text(
                    text = "Bienvenido de vuelta, jugador",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = dimens.titleSpacing), // Espaciado adaptativo para títulos
                    textAlign = TextAlign.Center
                )


                Text(
                    text = "Inicia Sesión",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}