package com.example.levelupprueba.ui.screens.auth
import com.example.levelupprueba.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.levelupprueba.ui.components.buttons.LevelUpAuthButtons
import com.example.levelupprueba.ui.components.LevelUpSpacedColumn
import com.example.levelupprueba.ui.theme.LocalDimens

@Composable
fun WelcomeScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onGuestClick: () -> Unit
){
    //Utilizamos las dimensiones de Theme
    val dimens = LocalDimens.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(dimens.screenPadding),
        contentAlignment = Alignment.Center
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
        ) {

            Image(
                painter = painterResource(id = R.drawable.levelup_logo),
                contentDescription = "Ilustración de bienvenida",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimens.imageHeight)
            )

            Spacer(modifier = Modifier.height(dimens.largeSpacing))

            LevelUpSpacedColumn(
                spacing = dimens.mediumSpacing
            ){
                Text(
                    text = "¡Bienvenido a Level-Up Gamer!",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = dimens.titleSize
                )

                Text(
                    text = "Lo líder en productos gamers",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = dimens.subtitleSize
                )
            }

            Spacer(modifier = Modifier.height(dimens.largeSpacing))

            LevelUpAuthButtons(
                onLoginClick = onLoginClick,
                onRegisterClick = onRegisterClick,
                onGuestClick = onGuestClick,
                buttonHeight = dimens.buttonHeight,
                textColor = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}