package com.example.levelupprueba.data.remote

import retrofit2.Response
import retrofit2.http.*

/**
 * Interfaz de API para usuarios y perfiles
 */
interface UsuarioApiService {
    
    /**
     * Obtiene el perfil del usuario actual
     */
    @GET("usuarios/perfil")
    suspend fun getPerfil(): Response<UsuarioDto>
    
    /**
     * Obtiene un usuario por ID
     */
    @GET("usuarios/{id}")
    suspend fun getUsuarioById(@Path("id") id: String): Response<UsuarioDto>
    
    /**
     * Actualiza el perfil del usuario
     */
    @PUT("usuarios/perfil")
    suspend fun actualizarPerfil(@Body request: ActualizarPerfilRequest): Response<UsuarioDto>
    
    /**
     * Cambia la contraseña
     */
    @PUT("usuarios/cambiar-password")
    suspend fun cambiarPassword(@Body request: CambiarPasswordRequest): Response<Unit>
    
    /**
     * Obtiene los referidos del usuario
     */
    @GET("usuarios/referidos")
    suspend fun getReferidos(): Response<List<ReferidoDto>>
    
    /**
     * Obtiene todos los usuarios (solo admin)
     */
    @GET("usuarios")
    suspend fun getAllUsuarios(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): Response<UsuarioResponsePage>
    
    /**
     * Elimina un usuario (solo admin)
     */
    @DELETE("usuarios/{id}")
    suspend fun eliminarUsuario(@Path("id") id: String): Response<Unit>
}

/**
 * DTO de Usuario
 */
data class UsuarioDto(
    val id: String? = null,
    val idUsuario: String? = null,
    val nombre: String? = null,
    val apellido: String? = null,
    val apellidos: String? = null,
    val correo: String? = null,
    val email: String? = null,
    val telefono: String? = null,
    val direccion: String? = null,
    val ciudad: String? = null,
    val region: String? = null,
    val comuna: String? = null,
    val pais: String? = null,
    val fechaNacimiento: String? = null,
    val role: String? = null,
    val tipoUsuario: String? = null,
    val nivel: Int? = null,
    val nivelUsuario: String? = null,
    val puntos: Int? = null,
    val puntosLevelUp: Int? = null,
    val avatar: String? = null,
    val avatarUrl: String? = null,
    val codigoReferido: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

/**
 * Request para actualizar perfil
 */
data class ActualizarPerfilRequest(
    val nombre: String? = null,
    val telefono: String? = null,
    val direccion: String? = null,
    val avatar: String? = null
)

/**
 * Request para cambiar contraseña
 */
data class CambiarPasswordRequest(
    val passwordActual: String,
    val passwordNueva: String
)

/**
 * Respuesta paginada de usuarios
 */
data class UsuarioResponsePage(
    val content: List<UsuarioDto>,
    val totalElements: Int,
    val totalPages: Int,
    val number: Int,
    val size: Int
)