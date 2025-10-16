package com.example.levelupprueba.ui.components.navigation
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.levelupprueba.navigation.Screen
import com.example.levelupprueba.ui.theme.Dimens
import com.example.levelupprueba.ui.theme.LocalDimens
import com.example.levelupprueba.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun LevelUpNavigationBar(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    bottomNavItems: List<Screen>,
    coroutineScope: CoroutineScope,
    modifier: Modifier = Modifier,
    dimens: Dimens = LocalDimens.current
) {
    Surface(
        shadowElevation = 4.dp,
        shape = RoundedCornerShape(
            topStart = dimens.cardCornerRadius,
            topEnd = dimens.cardCornerRadius
        )
    ){
        NavigationBar(
            modifier = modifier,
            containerColor = MaterialTheme.colorScheme.surface,
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            bottomNavItems.forEach { screen ->
                NavigationBarItem(
                    icon = { Icon(screen.icon, contentDescription = screen.title) },
                    label = { Text(screen.title) },
                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                    onClick = {
                        coroutineScope.launch {
                            mainViewModel.navigateTo(screen.route)

                        }
                    },
                    colors = levelUpNavigationBarItemsColors()
                )
            }
        }
    }
}