package com.example.levelupprueba.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupprueba.data.local.getUserSessionFlow
import com.example.levelupprueba.data.remote.MediaUrlResolver
import com.example.levelupprueba.data.repository.UsuarioRepository
import com.example.levelupprueba.model.auth.UserSession
import kotlinx.coroutines.flow.StateFlow
import com.example.levelupprueba.navigation.NavigationEvents
import com.example.levelupprueba.ui.components.GlobalSnackbarState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val usuarioRepository: UsuarioRepository,
    context: Context) : ViewModel() {
    private val _navigationEvent = MutableSharedFlow<NavigationEvents>()
    val navigationEvent: SharedFlow<NavigationEvents> = _navigationEvent

    private val _userSessionFlow = MutableStateFlow<UserSession?>(null)
    val userSessionFlow: StateFlow<UserSession?> get() = _userSessionFlow.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    private val _avatar = MutableStateFlow<String?>(null)
    val avatar: StateFlow<String?> get() = _avatar.asStateFlow()

    private val _globalSnackbarState = MutableStateFlow<GlobalSnackbarState>(GlobalSnackbarState.Idle)
    val globalSnackbarState: StateFlow<GlobalSnackbarState> = _globalSnackbarState

    init {
        viewModelScope.launch {
            getUserSessionFlow(context).collect { session ->
                _userSessionFlow.value = session
                if (session.accessToken.isNotBlank() && session.userId > 0) {
                    com.example.levelupprueba.data.remote.ApiConfig.setAuthToken(session.accessToken)
                    com.example.levelupprueba.data.remote.ApiConfig.setUserId(session.userId.toString())
                } else {
                    com.example.levelupprueba.data.remote.ApiConfig.clear()
                }
                // Si hay sesión, carga el avatar del usuario de Room
                if (session.userId > 0) {
                    val usuario = usuarioRepository.getUsuarioById(session.userId.toString())
                    _avatar.value = usuario?.avatar?.let { MediaUrlResolver.resolve(it) }
                } else {
                    _avatar.value = null
                }
            }
        }
    }

    fun setUserSession(session: UserSession?) {
        _userSessionFlow.value = session
        if (session != null && session.accessToken.isNotBlank() && session.userId > 0) {
            com.example.levelupprueba.data.remote.ApiConfig.setAuthToken(session.accessToken)
            com.example.levelupprueba.data.remote.ApiConfig.setUserId(session.userId.toString())
        } else {
            com.example.levelupprueba.data.remote.ApiConfig.clear()
        }
    }

    fun updateAvatar(path: String?) {
        _avatar.value = path?.let { MediaUrlResolver.resolve(it) }
    }

    suspend fun navigateTo(route: String) {
        _navigationEvent.emit(NavigationEvents.NavigateTo(route))
    }

    suspend fun navigateBack() {
        _navigationEvent.emit(NavigationEvents.NavigateBack)
    }

    suspend fun navigateUp() {
        _navigationEvent.emit(NavigationEvents.NavigateUp)
    }

    fun showSuccessSnackbar(message: String) {
        _globalSnackbarState.value = GlobalSnackbarState.Success(message)
    }

    fun showErrorSnackbar(message: String) {
        _globalSnackbarState.value = GlobalSnackbarState.Error(message)
    }

    fun showInfoSnackbar(message: String) {
        _globalSnackbarState.value = GlobalSnackbarState.Info(message)
    }
    fun clearSnackbar() {
        _globalSnackbarState.value = GlobalSnackbarState.Idle
    }
}