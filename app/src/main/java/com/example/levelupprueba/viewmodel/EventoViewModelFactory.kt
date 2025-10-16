package com.example.levelupprueba.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.levelupprueba.data.repository.UsuarioRepository

// Clase de fabrica para EventoViewModel
class EventoViewModelFactory(
    private val usuarioRepository: UsuarioRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T{
        if(modelClass.isAssignableFrom(EventoViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return EventoViewModel(usuarioRepository = usuarioRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
