package com.example.levelupprueba.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.levelupprueba.data.repository.UsuarioRepository


// Clase de fábrica para UsuarioViewModel
class UsuarioViewModelFactory(
    private val usuarioRepository: UsuarioRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T{
        if(modelClass.isAssignableFrom(UsuarioViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return UsuarioViewModel(usuarioRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}