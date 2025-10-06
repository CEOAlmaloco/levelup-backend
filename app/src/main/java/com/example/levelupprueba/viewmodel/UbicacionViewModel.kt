package com.example.levelupprueba.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupprueba.data.remote.ChileApiService
import com.example.levelupprueba.model.ubicacion.Comuna
import com.example.levelupprueba.model.ubicacion.Region
import kotlinx.coroutines.launch

class UbicacionViewModel(private val chileApi: ChileApiService): ViewModel() {
    var regiones by mutableStateOf<List<Region>>(emptyList())
        private set
    var comunas by mutableStateOf<List<Comuna>>(emptyList())
        private set

    fun cargarRegiones(){
        viewModelScope.launch {
            try {
                regiones = chileApi.getRegiones()
            } catch (e: Exception){

            }
        }
    }

    fun cargarComunas(regionCodigo: String) {
        viewModelScope.launch {
            try {
                comunas = chileApi.getComunas(regionCodigo)
            } catch (e: Exception) {
                // manejar error
            }
        }
    }
}