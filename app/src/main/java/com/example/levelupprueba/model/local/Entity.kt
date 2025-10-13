package com.example.levelupprueba.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gastos")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val descripcion: String,
    val fecha: String,
    val monto: Double,
    val categoria: String,
    val responsable: String
)