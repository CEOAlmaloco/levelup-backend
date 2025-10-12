package com.example.levelupprueba.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow
import com.example.levelupprueba.navigation.NavigationEvents
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class MainViewModel : ViewModel() {
    private val _navigationEvent = MutableSharedFlow<NavigationEvents>()
    val navigationEvent: SharedFlow<NavigationEvents> = _navigationEvent

    suspend fun navigateTo(route: String) {
        _navigationEvent.emit(NavigationEvents.NavigateTo(route))
    }

    suspend fun navigateBack() {
        _navigationEvent.emit(NavigationEvents.NavigateBack)
    }

    suspend fun navigateUp() {
        _navigationEvent.emit(NavigationEvents.NavigateUp)
    }
}