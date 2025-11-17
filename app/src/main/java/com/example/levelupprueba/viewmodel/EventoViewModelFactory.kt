package com.example.levelupprueba.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
class EventoViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T{
        if(modelClass.isAssignableFrom(EventoViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return EventoViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
