package com.example.levelupprueba.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.levelupprueba.data.Converters
import com.example.levelupprueba.model.usuario.Usuario
import com.example.levelupprueba.model.producto.Review

//aca agregamos la entidad Review a la bd, ahora tenemos usuarios y reviews
@Database(entities = [Usuario::class, Review::class], version = 3, exportSchema = false) //version 3 pq agregamos una tabla nueva
@TypeConverters(Converters::class) //los converters son para tipos de datos q Room no soporta nativamente
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao //el dao de usuarios q ya teniamos
    abstract fun reviewDao(): ReviewDao //el dao de reviews q acabamos de crear
    
    companion object{ //el companion object es como un singleton, solo hay una instancia de la bd
        @Volatile private var INSTANCE: AppDatabase? = null //volatile para q se actualice en todos los threads

        fun getInstance(context: Context): AppDatabase = //funcion para obtener la instancia de la bd
            INSTANCE ?: synchronized(this) { //synchronized para q solo un thread pueda crear la instancia
                INSTANCE ?: Room.databaseBuilder( //si no existe la instancia, la creamos
                    context.applicationContext,
                    AppDatabase::class.java,
                    "levelup.db" //cambiamos el nombre de usuarios.db a levelup.db pq ahora tiene mas cosas
                )
                .fallbackToDestructiveMigration() //esto borra la bd si cambia la version, en produccion no se hace asi TODO
                .build().also { INSTANCE = it }
            }
    }
}
//ahora la bd tiene usuarios y reviews, si agregamos mas tablas solo las ponemos en entities y creamos sus DAOs
