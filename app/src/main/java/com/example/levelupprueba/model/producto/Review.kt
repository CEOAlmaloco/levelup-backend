package com.example.levelupprueba.model.producto

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "reviews") //esta es la tabla reviews en la bd en sql lite con room 
data class Review(
    @PrimaryKey //la clave primaria
    val id: String, //el id de la review, lo generamos con timestamp
    val productoId: String, //el id del producto al q pertenece la review, esto es clave para traerlas
    val usuarioNombre: String, //nombre del usuario q hizo la review
    val rating: Float, //las estrellitas de 1 a 5
    val comentario: String, //el texto de la review
    val fecha: String //la fecha en formato String, despues podemos cambiarla a LocalDate TODO
)
//ahora esto ya es una entidad de Room, se va a guardar en SQLite y ya no en memoria 
