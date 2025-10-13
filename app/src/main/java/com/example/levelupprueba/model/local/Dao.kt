package com.example.levelupprueba.model.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Query("SELECT * FROM gastos ORDER BY fecha DESC, id DESC")
    fun observarTodos(): Flow<List<ExpenseEntity>>

    @Query("SELECT * FROM gastos WHERE id = :id")
    suspend fun obtenerPorId(id: Int): ExpenseEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(expense: ExpenseEntity): Long

    @Update
    suspend fun actualizar(expense: ExpenseEntity)

    @Delete
    suspend fun eliminar(expense: ExpenseEntity)

    @Query("DELETE FROM gastos")
    suspend fun eliminarTodos()
}