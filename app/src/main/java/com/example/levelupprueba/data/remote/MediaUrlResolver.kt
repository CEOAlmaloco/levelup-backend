package com.example.levelupprueba.data.remote

import com.example.levelupprueba.BuildConfig
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Utilidad centralizada para normalizar rutas de imágenes/medios que pueden venir
 * como URLs completas, claves S3, rutas relativas o cadenas base64.
 */
object MediaUrlResolver {

    private val gson by lazy { Gson() }

    private val mediaBaseUrl: String by lazy {
        val raw = BuildConfig.MEDIA_BASE_URL.trim()
        if (raw.isBlank()) "" else if (raw.endsWith("/")) raw else "$raw/"
    }

    private val base64Regex = Regex("^[A-Za-z0-9+/=\\s]+$")

    /**
     * Resuelve una cadena individual a una URL utilizable por Coil/Compose.
     * - Si ya es HTTP/HTTPS/Content/Data URI se devuelve tal cual.
     * - Si es clave S3 o ruta relativa, se antepone la URL base configurada.
     * - Si parece Base64 se devuelve con prefijo data:image/png;base64.
     */
    fun resolve(raw: String?): String {
        if (raw.isNullOrBlank()) return ""
        val value = raw.trim()

        detectBase64(value)?.let { 
            android.util.Log.d("MediaUrlResolver", "Resolved Base64 image")
            return it 
        }

        if (value.startsWith("http://", ignoreCase = true) ||
            value.startsWith("https://", ignoreCase = true) ||
            value.startsWith("content://", ignoreCase = true) ||
            value.startsWith("file://", ignoreCase = true) ||
            value.startsWith("data:image", ignoreCase = true)
        ) {
            return value
        }

        if (value.startsWith("/storage", ignoreCase = true) ||
            value.startsWith("/data", ignoreCase = true) ||
            value.startsWith("/mnt", ignoreCase = true)
        ) {
            return value
        }

        if (value.startsWith("//")) {
            return "https:$value"
        }

        if (value.startsWith("s3://", ignoreCase = true)) {
            val key = value.removePrefix("s3://")
            val relativePath = key.substringAfter('/', key)
            return buildUrl(relativePath)
        }

        val sanitized = value
            .removePrefix("./")
            .removePrefix("/")

        if (mediaBaseUrl.isBlank()) {
            return sanitized
        }

        return buildUrl(sanitized)
    }

    /**
     * Resuelve la primera URL válida de la lista de candidatos.
     */
    fun resolveFirst(candidates: List<String?>): String {
        candidates.forEach { candidate ->
            val resolved = resolve(candidate)
            if (resolved.isNotBlank()) return resolved
        }
        return ""
    }

    /**
     * Resuelve una lista de cadenas y devuelve solo las URLs válidas (sin duplicados).
     */
    fun resolveList(candidates: List<String?>?): List<String> {
        if (candidates.isNullOrEmpty()) return emptyList()
        return candidates.mapNotNull { candidate ->
            val resolved = resolve(candidate)
            if (resolved.isNotBlank()) resolved else null
        }.distinct()
    }

    /**
     * Intenta interpretar una cadena como JSON (array o lista separada por comas) y devuelve
     * la lista de URLs normalizadas.
     */
    fun resolveJsonList(raw: String?): List<String> {
        if (raw.isNullOrBlank()) return emptyList()
        val trimmed = raw.trim()

        val parsed: List<String> = runCatching {
            val type = object : TypeToken<List<String>>() {}.type
            gson.fromJson<List<String>>(trimmed, type)
        }.getOrElse {
            trimmed
                .split(',', ';', '\n')
                .map { token -> token.trim().trim('"') }
                .filter { it.isNotBlank() }
        }

        return resolveList(parsed)
    }

    private fun detectBase64(value: String): String? {
        if (value.startsWith("data:image", ignoreCase = true)) {
            android.util.Log.d("MediaUrlResolver", "Detectado Base64 (prefijo data:image): ${value.take(50)}...")
            return value
        }

        val sanitized = value.replace("\\s".toRegex(), "")
        val looksLikeBase64 = sanitized.length > 120 && sanitized.length % 4 == 0 && base64Regex.matches(sanitized)

        if (looksLikeBase64) {
            android.util.Log.d("MediaUrlResolver", "Detectado Base64 (patrón): ${value.take(50)}... (length: ${sanitized.length})")
            return "data:image/png;base64,$sanitized"
        }
        
        // Log si parece Base64 pero no cumple todos los criterios
        if (sanitized.length > 120 && base64Regex.matches(sanitized)) {
            android.util.Log.w("MediaUrlResolver", "Cadena parece Base64 pero no cumple criterios (length: ${sanitized.length}, mod 4: ${sanitized.length % 4}): ${value.take(100)}...")
        }
        
        return null
    }

    private fun buildUrl(path: String): String {
        if (mediaBaseUrl.isBlank()) {
            android.util.Log.w("MediaUrlResolver", "mediaBaseUrl is blank, returning path as-is: $path")
            return path
        }
        val cleanPath = path.removePrefix("/")
        val fullUrl = mediaBaseUrl + cleanPath
        android.util.Log.d("MediaUrlResolver", "Resolved S3 URL: $path -> $fullUrl")
        return fullUrl
    }
}


