package com.example.levelupprueba.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.levelupprueba.data.repository.NotificacionesRepositoryRemote
import com.example.levelupprueba.data.repository.UsuarioRepository

class ProfileViewModelFactory(
    private val usuarioRepository: UsuarioRepository,
    private val notificacionesRepository: NotificacionesRepositoryRemote
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(usuarioRepository, notificacionesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}