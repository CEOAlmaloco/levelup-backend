package com.example.levelupprueba.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.levelupprueba.model.usuario.Usuario
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

// Define el nombre del archivo de preferencias
val Context.userDataStore by preferencesDataStore(name = "user_prefs")

// Clase para acceder a las preferencias del usuario
class UserDataStore(private val context: Context) {

    // Obtiene el token de acceso del usuario
    companion object{
        val USERS_JSON = stringPreferencesKey("users_json")
    }

    // Guarda el Usuario
    suspend fun saveUsuarios(usuarios: List<Usuario>){
        context.userDataStore.edit { prefs ->
            prefs[USERS_JSON] = Json.encodeToString(usuarios)
        }
    }

    // Leer la lista completa de usuarios como Flow
    val usuariosFlow: Flow<List<Usuario>> = context.userDataStore.data.map { prefs ->
        prefs[USERS_JSON]?.let { json ->
            try {
                Json.decodeFromString<List<Usuario>>(json)
            } catch (e: Exception) {
                emptyList()
            }
        } ?: emptyList()
    }

    suspend fun getUsuarios(): List<Usuario>{
        val prefs = context.userDataStore.data
            .map { it }
            .first()
        return prefs[USERS_JSON]?.let { json ->
            try {
                Json.decodeFromString<List<Usuario>>(json)
            } catch (e: Exception){
                emptyList()
            }
        }?: emptyList()
    }

    // Agregar un nuevo usuario
    suspend fun addUsuario(nuevoUsuario: Usuario){
        val usuarios = getUsuarios().toMutableList()
        usuarios.add(nuevoUsuario)
        saveUsuarios(usuarios)
    }

    suspend fun clearUsuarios(){
        context.userDataStore.edit { prefs ->
            prefs.remove(USERS_JSON)
        }
    }

}