package com.example.levelupprueba.data.remote

import android.util.Log
import com.example.levelupprueba.BuildConfig
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

/**  EL PEPE
 * Configuración centralizada de la API
 * Maneja las URLs base y la configuración de Retrofit
 */
object ApiConfig {
    
    // URLs de fallback en caso de error
    private const val FALLBACK_URL_DEBUG = "http://10.0.2.2:8094/"
    private const val FALLBACK_URL_RELEASE = "https://api.levelup-gamer.com/"
    
    // URL base del API Gateway
    // Se obtiene desde BuildConfig (configurado en build.gradle.kts)
    // Para desarrollo: http://10.0.2.2:8094/ (emulador Android)
    // Para producción: URL de AWS configurada en build.gradle.kts (release)
    private val BASE_URL: String = runCatching {
        val url = BuildConfig.API_BASE_URL
        validateAndNormalizeUrl(url) ?: throw IllegalArgumentException("URL inválida: $url")
    }.getOrElse { exception ->
        Log.e("ApiConfig", "Error al obtener BASE_URL desde BuildConfig", exception)
        val fallback = if (BuildConfig.DEBUG) FALLBACK_URL_DEBUG else FALLBACK_URL_RELEASE
        Log.w("ApiConfig", "Usando URL de fallback: $fallback")
        fallback
    }
    
    // URL alternativa para docker local (desarrollo)
    // Se configura desde BuildConfig (debug usa gateway.url.device)
    private val BASE_URL_DEVICE: String = runCatching {
        val url = BuildConfig.API_BASE_URL_DEVICE
        if (url.isBlank()) BASE_URL else validateAndNormalizeUrl(url) ?: BASE_URL
    }.getOrElse { BASE_URL }
    
    // URL base para el microservicio de carrito (puede ser diferente del gateway)
    private val CARRITO_BASE_URL: String = runCatching {
        val url = BuildConfig.CARRITO_BASE_URL
        validateAndNormalizeUrl(url) ?: throw IllegalArgumentException("URL inválida para carrito: $url")
    }.getOrElse { exception ->
        Log.e("ApiConfig", "Error al obtener CARRITO_BASE_URL desde BuildConfig", exception)
        // Fallback: usar la URL del gateway si no hay URL específica del carrito
        BASE_URL
    }
    
    // URL alternativa del carrito para dispositivo físico
    private val CARRITO_BASE_URL_DEVICE: String = runCatching {
        val url = BuildConfig.CARRITO_BASE_URL_DEVICE
        if (url.isBlank()) CARRITO_BASE_URL else validateAndNormalizeUrl(url) ?: CARRITO_BASE_URL
    }.getOrElse { CARRITO_BASE_URL }
    
    // Timeout de conexión
    private const val TIMEOUT_SECONDS = 30L
    
    // Configuración de reintentos
    private const val MAX_RETRIES = 3
    private const val RETRY_DELAY_MS = 1000L
    
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
     * Devuelve la URL base alternativa pensada para pruebas en dispositivo físico.
     * (Útil para logs o validaciones en tiempo de ejecución).
     */
    fun getDeviceGatewayUrl(): String = BASE_URL_DEVICE
    
    /**
     * API Key para autenticación con el backend
     * Se obtiene desde BuildConfig (configurado en build.gradle.kts)
     */
    private val API_KEY: String = runCatching { BuildConfig.API_KEY }
        .getOrElse { "levelup-2024-secret-api-key-change-in-production" }
    
    /**
     * Lista de endpoints que NO requieren X-User-Id
     */
    private val endpointsWithoutUserId = setOf(
        "auth/login",
        "auth/register",
        "auth/refresh",
        "auth/logout",
        "productos",
        "productos/",
        "productos/buscar",
        "productos/categorias",
        "productos/disponibles"
    )
    
    /**
     * Valida y normaliza una URL
     */
    private fun validateAndNormalizeUrl(url: String): String? {
        if (url.isBlank()) return null
        
        val normalized = url.trim().let { trimmed ->
            if (!trimmed.endsWith("/")) "$trimmed/" else trimmed
        }
        
        return try {
            normalized.toHttpUrlOrNull()?.toString() ?: null
        } catch (e: Exception) {
            Log.e("ApiConfig", "URL inválida: $url", e)
            null
        }
    }
    
    /**
     * Interceptor para agregar el token JWT y API Key a las peticiones
     * Optimizado para no agregar X-User-Id cuando no es necesario
     */
    private val authInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
        val path = originalRequest.url.encodedPath
        
        // Agregar API Key (requerido para todas las peticiones)
        requestBuilder.addHeader("X-API-Key", API_KEY)
        
        // Agregar token JWT si existe (para autenticación de usuario)
        val token = authToken
        if (token.isNullOrBlank()) {
            // Solo loguear en debug para endpoints que requieren autenticación
            if (BuildConfig.DEBUG && !path.contains("auth/login") && !path.contains("auth/register")) {
                Log.d("ApiConfig", "Auth token no configurado para $path")
            }
        } else {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        // Agregar User ID solo si es necesario y existe
        // Verificar si el endpoint requiere X-User-Id
        val requiresUserId = !endpointsWithoutUserId.any { path.contains(it) }
        
        if (requiresUserId) {
            val currentUserId = userId
            currentUserId?.let {
                try {
                    // Intentar convertir a Long para validar que es un número
                    val userIdLong = it.toLong()
                    requestBuilder.addHeader("X-User-Id", userIdLong.toString())
                } catch (e: NumberFormatException) {
                    // Si no es un número, enviarlo tal cual (puede causar error en el backend)
                    Log.w("ApiConfig", "UserId no es un número válido: $it")
                    requestBuilder.addHeader("X-User-Id", it)
                }
            } ?: run {
                if (BuildConfig.DEBUG) {
                    Log.d("ApiConfig", "UserId no configurado para endpoint que lo requiere: $path")
                }
            }
        }
        
        // Agregar headers comunes
        requestBuilder
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
        
        chain.proceed(requestBuilder.build())
    }
    
    /**
     * Interceptor para reintentos automáticos en errores transitorios
     */
    private val retryInterceptor = Interceptor { chain ->
        val request = chain.request()
        var response: okhttp3.Response? = null
        var retryCount = 0
        
        try {
            response = chain.proceed(request)
            
            while (!response!!.isSuccessful && retryCount < MAX_RETRIES) {
                val shouldRetry = when {
                    // Errores de cliente (4xx) - NO reintentar
                    response.code in 400..499 -> false
                    // Errores de servidor (5xx) - reintentar
                    response.code in 500..599 -> true
                    // Timeout - reintentar
                    response.code == 408 -> true
                    // Otros errores - no reintentar
                    else -> false
                }
                
                if (!shouldRetry) break
                
                retryCount++
                Log.d("ApiConfig", "Reintentando petición ${request.url} (intento $retryCount/$MAX_RETRIES)")
                
                // Cerrar la respuesta anterior de forma segura
                try {
                    response.close()
                } catch (e: Exception) {
                    // Ignorar errores al cerrar
                }
                
                // Esperar antes de reintentar (backoff exponencial)
                Thread.sleep(RETRY_DELAY_MS * retryCount)
                
                // Reintentar la petición
                response = chain.proceed(request)
            }
            
            response
        } catch (e: Exception) {
            // Si hay una excepción, cerrar la respuesta si existe y relanzar
            response?.close()
            throw e
        }
    }
    
    /**
     * Interceptor para logging (solo en debug)
     */
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.HEADERS
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }
    
    /**
     * Cliente OkHttp con interceptores
     * Orden de interceptores:
     * 1. Retry interceptor (más externo)
     * 2. Auth interceptor (agrega headers)
     * 3. Logging interceptor (más interno, para ver requests finales)
     */
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(retryInterceptor)
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)
        .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .build()
    
    /**
     * Instancia de Retrofit para el gateway (productos, eventos, blogs, etc.)
     * Con validación mejorada de URL base y fallback
     */
    private val retrofit: Retrofit by lazy {
        val httpUrl = BASE_URL.toHttpUrlOrNull()
            ?: run {
                val fallback = if (BuildConfig.DEBUG) FALLBACK_URL_DEBUG else FALLBACK_URL_RELEASE
                Log.e("ApiConfig", "BASE_URL inválida: $BASE_URL, usando fallback: $fallback")
                fallback.toHttpUrlOrNull()
                    ?: throw IllegalStateException("No se pudo inicializar Retrofit: URLs inválidas")
            }

        Retrofit.Builder()
            .baseUrl(httpUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    /**
     * Instancia de Retrofit específica para el microservicio de carrito
     * Usa una URL base diferente (puede apuntar directamente al microservicio local)
     */
    private val carritoRetrofit: Retrofit by lazy {
        val httpUrl = CARRITO_BASE_URL.toHttpUrlOrNull()
            ?: run {
                Log.e("ApiConfig", "CARRITO_BASE_URL inválida: $CARRITO_BASE_URL, usando BASE_URL como fallback")
                BASE_URL.toHttpUrlOrNull()
                    ?: throw IllegalStateException("No se pudo inicializar Retrofit del carrito: URLs inválidas")
            }
        
        Log.d("ApiConfig", "Inicializando Retrofit del carrito con URL: $httpUrl")

        Retrofit.Builder()
            .baseUrl(httpUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    /**
     * Obtiene la URL base actual (útil para debugging)
     */
    fun getBaseUrl(): String = BASE_URL
    
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
        carritoRetrofit.create(CarritoApiService::class.java)
    }
    
    val eventosService: EventosApiService by lazy {
        retrofit.create(EventosApiService::class.java)
    }
    
    val reseniaService: ReseniaApiService by lazy {
        retrofit.create(ReseniaApiService::class.java)
    }
    
    val inventarioService: InventarioApiService by lazy {
        retrofit.create(InventarioApiService::class.java)
    }
    
    val referidosService: ReferidosApiService by lazy {
        retrofit.create(ReferidosApiService::class.java)
    }
    
    val pedidosService: PedidosApiService by lazy {
        retrofit.create(PedidosApiService::class.java)
    }
    
    val pagosService: PagosApiService by lazy {
        retrofit.create(PagosApiService::class.java)
    }
    
    val promocionesService: PromocionesApiService by lazy {
        retrofit.create(PromocionesApiService::class.java)
    }
    
    val contenidoService: ContenidoApiService by lazy {
        retrofit.create(ContenidoApiService::class.java)
    }
    
    val notificacionesService: NotificacionesApiService by lazy {
        retrofit.create(NotificacionesApiService::class.java)
    }
    
    /**
     * Limpia la configuración (usado en logout)
     */
    fun clear() {
        authToken = null
        userId = null
    }
}

