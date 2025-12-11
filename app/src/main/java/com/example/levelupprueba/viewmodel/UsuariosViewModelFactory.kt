package com.example.levelupprueba.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
class UsuariosViewModelFactory : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsuariosViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UsuariosViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}