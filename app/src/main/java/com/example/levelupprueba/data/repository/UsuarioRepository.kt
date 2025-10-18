package com.example.levelupprueba.data.repository

import com.example.levelupprueba.data.local.UsuarioDao
import com.example.levelupprueba.model.usuario.Usuario
import java.util.UUID

class UsuarioRepository(private val usuarioDao: UsuarioDao) {


    suspend fun getUsuarioById(id: String): Usuario? {
        return usuarioDao.getUsuarioById(id)
    }

    suspend fun getUsuarioByEmail(email: String): Usuario? {
        return usuarioDao.getUsuarioByEmail(email)
    }

    suspend fun getAllUsuarios(): List<Usuario> {
        return usuarioDao.getAllUsuarios()
    }

    suspend fun updateUsuario(usuario: Usuario) {
        usuarioDao.updateUsuario(usuario)
    }

    suspend fun deleteUsuario(usuario: Usuario) {
        usuarioDao.deleteUsuario(usuario)
    }

    suspend fun deleteAllUsuarios() {
        usuarioDao.deleteAllUsuarios()
    }

    private fun generateIdUsuario(): String {
        val timestamp = System.currentTimeMillis().toString()
        val random = (1..9)
            .map{('a'..'z').random()}
            .joinToString("")
        return timestamp + random
    }

    suspend fun saveUsuario(usuario: Usuario) {
        val usuarioFinal = if (usuario.id.isEmpty()) {
            usuario.copy(id = generateIdUsuario())
        } else {
            usuario
        }

        if (usuario.id.isEmpty()) {
            usuarioDao.insertUsuario(usuarioFinal)
        } else {
            usuarioDao.updateUsuario(usuarioFinal)
        }
    }

    suspend fun changePasswordUsuario(
        email: String,
        currentPassword: String,
        newPassword: String
    ): Boolean{
        val usuario = usuarioDao.getUsuarioByEmail(email)
        if (usuario != null && usuario.password == currentPassword) {
            val updatedUsuario = usuario.copy(password = newPassword)
            usuarioDao.updateUsuario(updatedUsuario)
            return true
        }
        return false
    }



    fun generateReferralCode(nombre: String): String {
        val raw = nombre.replace("\\s+".toRegex(), "").uppercase()
        val rand = (1..4).map { ('A'..'Z').random() }.joinToString("")
        val namepart = raw.take(6)
        return namepart + rand
    }

    suspend fun emailExists(email: String): Boolean {
        return usuarioDao.getUsuarioByEmail(email) != null
    }

    suspend fun getUsuarioByReferralCode(referralCode: String): Usuario? {
        return usuarioDao.getUsuarioByReferralCode(referralCode)
    }

}