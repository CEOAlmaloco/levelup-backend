package com.example.levelupprueba.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.levelupprueba.ui.screens.home.LoginScreen
import com.example.levelupprueba.ui.screens.home.RegisterScreen
import com.example.levelupprueba.viewmodel.LoginViewModel
import com.example.levelupprueba.viewmodel.UsuarioViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    val usuarioViewModel: UsuarioViewModel = viewModel()

    val loginViewModel: LoginViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("registro"){
            RegisterScreen(navController, usuarioViewModel)
        }

        composable("login"){
            LoginScreen(navController, loginViewModel)
        }
    }
}
