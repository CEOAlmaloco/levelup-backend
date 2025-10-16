package com.example.levelupprueba.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.levelupprueba.model.producto.Review

//el DAO es el Data Access Object, aca ponemos todas las queries para acceder a la bd
@Dao //le decimos a Room q esta es una interfaz de acceso a datos
interface ReviewDao {
    
    @Query("SELECT * FROM reviews WHERE productoId = :productoId ORDER BY fecha DESC") //traemos todas las reviews de un producto
    suspend fun getReviewsByProductoId(productoId: String): List<Review> //ordenadas por fecha, las mas recientes primero
    //el suspend es pq estas operaciones son asyncronas y se ejecutan en un thread separado
    
    @Query("SELECT * FROM reviews WHERE id = :id") //traemos una review por su id
    suspend fun getReviewById(id: String): Review? //puede ser null si no existe
    
    @Query("SELECT * FROM reviews") //traemos todas las reviews de todos los productos
    suspend fun getAllReviews(): List<Review> //esto es por si necesitamos mostrar todas las reviews en algun lado
    
    @Insert(onConflict = OnConflictStrategy.REPLACE) //insertamos una review, si ya existe la reemplaza
    suspend fun insertReview(review: Review) //esto es para agregar nuevas reviews
    
    @Update //actualizamos una review existente
    suspend fun updateReview(review: Review) //por si el usuario quiere editar su review
    
    @Delete //borramos una review
    suspend fun deleteReview(review: Review) //por si el usuario quiere borrar su review
    
    @Query("DELETE FROM reviews WHERE productoId = :productoId") //borramos todas las reviews de un producto
    suspend fun deleteReviewsByProductoId(productoId: String) //esto es util si eliminamos un producto
    
    @Query("DELETE FROM reviews") //borramos todas las reviews de la bd
    suspend fun deleteAllReviews() //esto es mas q nada para testing o resetear la bd
    
    @Query("SELECT AVG(rating) FROM reviews WHERE productoId = :productoId") //calculamos el rating promedio de un producto
    suspend fun getAverageRating(productoId: String): Float? //puede ser null si no hay reviews
    
    @Query("SELECT COUNT(*) FROM reviews WHERE productoId = :productoId") //contamos cuantas reviews tiene un producto
    suspend fun getReviewCount(productoId: String): Int //esto es util para mostrar "X reseñas"
}
//para menejar todas las operaciones de reviews en SQLite

