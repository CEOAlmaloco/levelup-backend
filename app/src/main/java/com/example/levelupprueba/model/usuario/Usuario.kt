package com.example.levelupprueba.model.usuario

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey val id: String,
    val nombre: String,
    val apellidos: String,
    val email: String,
    val password: String,
    val telefono: String?,
    val fechaNacimiento: String?,
    val region: String?,
    val comuna: String?,
    val direccion: String?,
    val referralCode: String,
    val points: Int = 0,
    val redeemedCodes: List<String> = emptyList(),//kitian te agrege esto adicional para el canje de puntos
    val role: String
)