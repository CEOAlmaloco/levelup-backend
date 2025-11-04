package com.example.levelupprueba.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interfaz de API para autenticación
 */
interface AuthApiService {
    
    /**
     * Login de usuario
     */
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>
    
    /**
     * Registro de usuario
     * Nota: El registro se hace a través de /api/v1/usuarios (Gateway reescribe /usuarios)
     * O usar /auth/register si está disponible
     */
    @POST("usuarios")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>
    
    /**
     * Registro de usuario (alternativa usando /auth/register)
     */
    @POST("auth/register")
    suspend fun registerAuth(@Body request: RegisterRequest): Response<AuthResponse>
    
    /**
     * Refresca el token JWT
     */
    @POST("auth/refresh")
    suspend fun refreshToken(): Response<AuthResponse>
}

/**
 * Request de login
 */
data class LoginRequest(
    val emailOrUsername: String,
    val password: String
)

/**
 * Request de registro
 */
data class RegisterRequest(
    val nombre: String,
    val email: String,
    val password: String,
    val fechaNacimiento: String,
    val telefono: String? = null,
    val direccion: String? = null
)

/**
 * Respuesta de autenticación
 */
data class AuthResponse(
    val token: String,
    val userId: String,
    val displayName: String,
    val role: String,
    val expiresIn: Long
)

