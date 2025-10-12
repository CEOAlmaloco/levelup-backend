package com.example.levelupprueba.navigation

sealed class NavigationEvents {
    data class NavigateTo(val route: String) : NavigationEvents()
    object NavigateBack : NavigationEvents()
    object NavigateUp : NavigationEvents()
}