package com.example.levelupprueba.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/* Interfaz de API para autenticación
 * 
 * Nota: Los endpoints usan rutas relativas sin el prefijo /api/v1/
 * porque el API Gateway (puerto 8094) reescribe automáticamente:
 * - /auth/** → /api/v1/auth/**
 * 
 * Endpoints finales en el backend:
 * - POST /api/v1/auth/login
 * - POST /api/v1/auth/register
 * - POST /api/v1/auth/refresh
  - POST /api/v1/auth/logout *//**/**/**/
interface AuthApiService {
    
    /**
     * Login de usuario
     * Gateway reescribe: /auth/login → /api/v1/auth/login
     */
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>
    
    /**
     * Registro de usuario
     * Gateway reescribe: /auth/register → /api/v1/auth/register
     */
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>
    
    /**
     * Refresca el token JWT
     * Gateway reescribe: /auth/refresh → /api/v1/auth/refresh
     */
    @POST("auth/refresh")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): Response<AuthResponse>
    
    /**
     * Cerrar sesión
     * Gateway reescribe: /auth/logout → /api/v1/auth/logout
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