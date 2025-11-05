package com.example.levelupprueba.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


/* Interfaz de API para autenticación
 * Endpoints: /api/v1/auth/

 */
interface AuthApiService {
    
    /**
     * Login de usuario
     * POST /api/v1/auth/login
     */
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>
    
    /**
     * Registro de usuario
     * POST /api/v1/auth/register
     */
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>
    
    /**
     * Refresca el token JWT
     * POST /api/v1/auth/refresh
     */
    @POST("auth/refresh")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): Response<AuthResponse>
    
    /**
     * Cerrar sesión
     * POST /api/v1/auth/logout
     */
    @POST("auth/logout")
    suspend fun logout(): Response<Map<String, String>>
}

/**
 * Request de login
 * DTO: LoginRequestDTO
 */
data class LoginRequest(
    val correoUsuario: String,
    val password: String
)

/**
 * Request de registro
 * DTO: RegisterRequestDTO
 */
data class RegisterRequest(
    val runUsuario: String,
    val nombreUsuario: String,
    val apellidosUsuario: String,
    val correoUsuario: String,
    val password: String,
    val fechaNacimiento: String,
    val region: String,
    val comuna: String,
    val direccionUsuario: String
)

/**
 * Request para refresh token
 * DTO: RefreshTokenRequestDTO
 */
data class RefreshTokenRequest(
    val refreshToken: String
)

/**
 * Respuesta de autenticación
 * DTO: AuthResponseDTO
 */
data class AuthResponse(
    val tokenType: String = "Bearer",
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long,
    val usuario: UsuarioInfo
) {
    /**
     * Información del usuario
     * DTO: AuthResponseDTO.UsuarioInfoDTO
     */
    data class UsuarioInfo(
        val id: Long,
        val nombre: String,
        val apellidos: String,
        val correo: String,
        val tipoUsuario: String,
        val descuentoDuoc: Boolean?,
        val region: String?,
        val comuna: String?
    )
}
