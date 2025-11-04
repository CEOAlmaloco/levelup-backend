package com.example.levelupprueba.data.remote

import com.example.levelupprueba.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Configuración centralizada de la API
 * Maneja las URLs base y la configuración de Retrofit
 */
object ApiConfig {
    
    // URL base del API Gateway
    // Se obtiene desde BuildConfig (configurado en build.gradle.kts)
    // Para desarrollo: usa http://10.0.2.2:8094/ (emulador Android)
    // Para producción: usa la URL de AWS configurada en build.gradle.kts (release)
    private val BASE_URL = BuildConfig.API_BASE_URL
    
    // URL alternativa para dispositivo físico (desarrollo)
    // Si necesitas probar en dispositivo físico, cambia la IP a la de tu PC
    private const val BASE_URL_DEV_DEVICE = "http://192.168.1.100:8094/" // Dispositivo físico (cambiar IP)
    
    // Timeout de conexión
    private const val TIMEOUT_SECONDS = 30L
    
    /**
     * Token de autenticación (se setea después del login)
     */
    private var authToken: String? = null
    
    /**
     * User ID del usuario autenticado (se setea después del login)
     */
    private var userId: String? = null
    
    /**
     * Establece el token de autenticación
     */
    fun setAuthToken(token: String?) {
        authToken = token
    }
    
    /**
     * Obtiene el token de autenticación actual
     */
    fun getAuthToken(): String? = authToken
    
    /**
     * Establece el User ID del usuario autenticado
     */
    fun setUserId(userId: String?) {
        this.userId = userId
    }
    
    /**
     * Obtiene el User ID del usuario autenticado actual
     */
    fun getUserId(): String? = userId
    
    /**
     * API Key para autenticación con el backend
     * Se obtiene desde BuildConfig (configurado en build.gradle.kts)
     */
    private val API_KEY = BuildConfig.API_KEY
    
    /**
     * Interceptor para agregar el token JWT y API Key a las peticiones
     */
    private val authInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
        
        // Agregar API Key (requerido para todas las peticiones)
        requestBuilder.addHeader("X-API-Key", API_KEY)
        
        // Agregar token JWT si existe (para autenticación de usuario)
        authToken?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }
        
        // Agregar User ID si existe (para endpoints que requieren X-User-Id)
        // El backend espera Long, pero mantenemos String para compatibilidad
        // Si el userId es un número, lo enviamos tal cual; si no, intentamos parsearlo
        userId?.let {
            try {
                // Intentar convertir a Long para validar que es un número
                val userIdLong = it.toLong()
                requestBuilder.addHeader("X-User-Id", userIdLong.toString())
            } catch (e: NumberFormatException) {
                // Si no es un número, enviarlo tal cual (puede causar error en el backend)
                requestBuilder.addHeader("X-User-Id", it)
            }
        }
        
        // Agregar headers comunes
        requestBuilder
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
        
        chain.proceed(requestBuilder.build())
    }
    
    /**
     * Interceptor para logging (solo en debug)
     */
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    
    /**
     * Cliente OkHttp con interceptores
     */
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)
        .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .build()
    
    /**
     * Instancia de Retrofit
     */
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    /**
     * Servicios API
     */
    val authService: AuthApiService by lazy {
        retrofit.create(AuthApiService::class.java)
    }
    
    val usuarioService: UsuarioApiService by lazy {
        retrofit.create(UsuarioApiService::class.java)
    }
    
    val productosService: ProductosApiService by lazy {
        retrofit.create(ProductosApiService::class.java)
    }
    
    val carritoService: CarritoApiService by lazy {
        retrofit.create(CarritoApiService::class.java)
    }
    
    val eventosService: EventosApiService by lazy {
        retrofit.create(EventosApiService::class.java)
    }
    
    val reseniaService: ReseniaApiService by lazy {
        retrofit.create(ReseniaApiService::class.java)
    }
    
    /**
     * Limpia la configuración (usado en logout)
     */
    fun clear() {
        authToken = null
        userId = null
    }
}

