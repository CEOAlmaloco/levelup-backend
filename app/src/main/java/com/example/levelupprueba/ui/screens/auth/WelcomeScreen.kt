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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.levelupprueba.data.remote.MediaUrlResolver
import com.example.levelupprueba.ui.components.buttons.LevelUpAuthButtons
import com.example.levelupprueba.ui.components.common.LevelUpSpacedColumn
import com.example.levelupprueba.ui.theme.LocalDimens
import com.example.levelupprueba.viewmodel.ProductoViewModel

@Composable
fun WelcomeScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onGuestClick: () -> Unit,
    productoViewModel: ProductoViewModel? = null
){
    //Utilizamos las dimensiones de Theme
    val dimens = LocalDimens.current
    val context = LocalContext.current
    
    // Obtener URL del logo desde el ViewModel si está disponible
    val logoUrl by productoViewModel?.logoUrl?.collectAsState() ?: remember { mutableStateOf("") }
    val resolvedLogoUrl = MediaUrlResolver.resolve(logoUrl)
    
    // Fallback URL: si la principal falla, usar levelup_logo.png
    val fallbackLogoUrl = MediaUrlResolver.resolve("img/levelup_logo.png")

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

            // Mostrar logo desde S3 si está disponible, con fallback automático
            // Coil intentará primero la URL principal, luego el fallback, y finalmente el drawable local
            val logoModel = when {
                resolvedLogoUrl.isNotBlank() -> {
                    // Intentar primero con la URL principal
                    ImageRequest.Builder(context)
                        .data(resolvedLogoUrl)
                        .crossfade(true)
                        .error(R.drawable.levelup_logo)
                        .placeholder(R.drawable.levelup_logo)
                        .build()
                }
                fallbackLogoUrl.isNotBlank() -> {
                    // Si no hay URL principal, intentar con el fallback
                    ImageRequest.Builder(context)
                        .data(fallbackLogoUrl)
                        .crossfade(true)
                        .error(R.drawable.levelup_logo)
                        .placeholder(R.drawable.levelup_logo)
                        .build()
                }
                else -> null
            }
            
            if (logoModel != null) {
                AsyncImage(
                    model = logoModel,
                    contentDescription = "Logo LevelUp",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimens.imageHeight)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.levelup_logo),
                    contentDescription = "Ilustración de bienvenida",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimens.imageHeight)
                )
            }

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