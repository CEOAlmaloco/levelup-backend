package com.example.levelupprueba.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.levelupprueba.data.repository.UsuarioRepository

/**
 * Clase que implementa la interfaz ViewModelProvider.Factory para crear instancias de UsuariosViewModel.
 * @param usuarioRepository Repositorio de usuarios utilizado por el ViewModel.
 * @author Christian Mesa
 * */
class UsuariosViewModelFactory(
    private val usuarioRepository: UsuarioRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsuariosViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UsuariosViewModel(usuarioRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}