package com.example.levelupprueba.model.admin.users

import com.example.levelupprueba.model.usuario.Usuario

/**
 * Estado de la lista de usuarios.
 * @property Idle Estado inicial.
 * @property Loading Estado mientras se cargan los usuarios.
 * @property Deleting Estado mientras se eliminan los usuarios.
 * @property Empty Estado cuando no hay usuarios.
 * @property Success Estado cuando se cargan los usuarios correctamente.
 * @property Error Estado cuando hay un error al cargar los usuarios.
 *
 * @author Christian Mesa
 * */
sealed class UsuariosStatus {
    object Idle : UsuariosStatus()
    object Loading : UsuariosStatus()
    object Deleting : UsuariosStatus()
    object Empty : UsuariosStatus()
    data class Success(val usuarios: List<Usuario>) : UsuariosStatus()
    data class Error(val errorMessage: String) : UsuariosStatus()
}
