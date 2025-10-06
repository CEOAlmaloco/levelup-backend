package com.example.levelupprueba.data.remote

import com.example.levelupprueba.model.ubicacion.Comuna
import com.example.levelupprueba.model.ubicacion.Region
import retrofit2.http.GET
import retrofit2.http.Path

interface ChileApiService {
    @GET("regiones")
    suspend fun getRegiones(): List<Region>

    @GET("regiones/{codigo}(comunas")
    suspend fun getComunas(@Path("codigo") codigoRegion: String): List<Comuna>
}

