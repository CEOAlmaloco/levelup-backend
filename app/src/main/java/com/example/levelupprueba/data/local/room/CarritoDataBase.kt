package com.example.levelupprueba.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [CarritoItemEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ProductoTypeConverters::class)
abstract class CarritoDatabase : RoomDatabase() {
    abstract fun carritoDao(): CarritoDao

    companion object {
        @Volatile private var INSTANCE: CarritoDatabase? = null

        fun get(context: Context): CarritoDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    CarritoDatabase::class.java,
                    "carrito.db"
                ).build().also { INSTANCE = it }
            }
    }
}
