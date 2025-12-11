package com.example.levelupprueba.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.levelupprueba.data.repository.NotificacionesRepositoryRemote

class ProfileViewModelFactory(
    private val notificacionesRepository: NotificacionesRepositoryRemote
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(notificacionesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}