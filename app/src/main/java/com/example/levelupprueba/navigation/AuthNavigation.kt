package com.example.levelupprueba.navigation

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.levelupprueba.MainActivity
import com.example.levelupprueba.ui.screens.auth.LoginScreen
import com.example.levelupprueba.ui.screens.auth.RegisterScreen
import com.example.levelupprueba.ui.screens.auth.WelcomeScreen
import com.example.levelupprueba.viewmodel.LoginViewModel
import com.example.levelupprueba.viewmodel.MainViewModel
import com.example.levelupprueba.viewmodel.UsuarioViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AuthNavigation(
    mainViewModel: MainViewModel,
    navController: NavHostController,
    usuarioViewModel: UsuarioViewModel,
    loginViewModel: LoginViewModel,
    startDestination: String = "welcome"
){


    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()
    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(300)
            )
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -it },
                animationSpec = tween(300)
            )
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { -it },
                animationSpec = tween(300)
            )
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(300)
            )
        }
    ) {
        composable("welcome"){
            WelcomeScreen(
                onLoginClick = {
                    coroutineScope.launch {
                        mainViewModel.navigateTo("login")
                    }
                },
                onRegisterClick = {
                    coroutineScope.launch {
                        mainViewModel.navigateTo("registro")
                    }
                },
                onGuestClick = {
                    // Lanza MainActivity y cierra AuthActivity
                    context.startActivity(Intent(context, MainActivity::class.java))
                    (context as? Activity)?.finish()
                }
            )
        }
        composable("registro"){
            RegisterScreen(usuarioViewModel, mainViewModel)
        }

        composable("login"){
            LoginScreen(loginViewModel, navController, mainViewModel)
        }
    }
}
