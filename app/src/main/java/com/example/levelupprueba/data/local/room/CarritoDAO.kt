package com.example.levelupprueba.data.local.room

import androidx.room.*

@Dao
interface CarritoDao {

    @Query("SELECT * FROM carrito_items")
    suspend fun getAll(): List<CarritoItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: CarritoItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(items: List<CarritoItemEntity>)

    @Query("UPDATE carrito_items SET cantidad = :cantidad WHERE id = :itemId")
    suspend fun updateCantidad(itemId: String, cantidad: Int)

    @Query("DELETE FROM carrito_items WHERE id = :itemId")
    suspend fun delete(itemId: String)

    @Query("DELETE FROM carrito_items")
    suspend fun clear()
}
