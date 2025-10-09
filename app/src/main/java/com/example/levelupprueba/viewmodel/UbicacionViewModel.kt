package com.example.levelupprueba.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.levelupprueba.model.ubicacion.Comuna
import com.example.levelupprueba.model.ubicacion.Region
import com.example.levelupprueba.model.ubicacion.RegionesYComunasProvider

class UbicacionViewModel: ViewModel() {
    val regiones: List<Region> = RegionesYComunasProvider.regiones

    var selectedRegion by mutableStateOf<Region?>(null)
    var selectedComuna by mutableStateOf<Comuna?>(null)

    fun selectRegion(nombre: String){
        selectedRegion = regiones.find { it.nombre == nombre }
        selectedComuna = null
    }

    fun selectComuna(nombre: String){
        selectedComuna = selectedRegion?.comunas?.find { it.nombre == nombre }

    }
}