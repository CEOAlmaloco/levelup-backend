package com.example.levelupprueba.data.remote

import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import retrofit2.HttpException

/**
 * Helper centralizado para manejar errores de conexión y mostrar mensajes amigables al usuario
 */
object ErrorHandler {
    
    /**
     * Convierte excepciones técnicas en mensajes amigables para el usuario
     */
    fun getErrorMessage(exception: Throwable): String {
        return when (exception) {
            is SocketTimeoutException -> {
                "Tiempo de espera agotado. Verifica tu conexión a internet e intenta nuevamente."
            }
            is UnknownHostException -> {
                "Error de conexión. Verifica tu conexión a internet e intenta nuevamente."
            }
            is IOException -> {
                when {
                    exception.message?.contains("Failed to connect", ignoreCase = true) == true -> {
                        "Error de conexión con el servidor. Por favor, intenta más tarde."
                    }
                    exception.message?.contains("timeout", ignoreCase = true) == true -> {
                        "Tiempo de espera agotado. Verifica tu conexión e intenta nuevamente."
                    }
                    exception.message?.contains("network", ignoreCase = true) == true -> {
                        "Error de red. Verifica tu conexión a internet."
                    }
                    exception.message?.contains("Canceled", ignoreCase = true) == true -> {
                        "Operación cancelada."
                    }
                    else -> {
                        "Error de conexión. Por favor, intenta más tarde."
                    }
                }
            }
            is HttpException -> {
                getHttpErrorMessage(exception.code(), exception)
            }
            else -> {
                "Error inesperado: ${exception.message ?: "Error desconocido"}"
            }
        }
    }
    
    /**
     * Obtiene mensaje de error específico para códigos HTTP
     */
    private fun getHttpErrorMessage(code: Int, exception: HttpException?): String {
        return when (code) {
            400 -> "Solicitud inválida. Por favor, verifica los datos ingresados."
            401 -> "Credenciales inválidas. Verifica tu correo y contraseña."
            403 -> "No tienes permisos para realizar esta acción."
            404 -> "Recurso no encontrado en el servidor."
            408 -> "Tiempo de espera agotado. Por favor, intenta nuevamente."
            409 -> "Conflicto: El recurso ya existe (posiblemente el correo electrónico ya está registrado)."
            422 -> "Datos inválidos. Por favor, verifica la información ingresada."
            429 -> "Demasiadas solicitudes. Por favor, espera un momento e intenta nuevamente."
            500 -> "Error interno del servidor. Por favor, intenta más tarde."
            502 -> "Servidor no disponible temporalmente. Por favor, intenta más tarde."
            503 -> "Servicio no disponible. Por favor, intenta más tarde."
            504 -> "Tiempo de espera del servidor agotado. Por favor, intenta más tarde."
            in 500..599 -> "Error del servidor ($code). Por favor, intenta más tarde."
            else -> "Error del servidor ($code). Por favor, intenta más tarde."
        }
    }
    
    /**
     * Verifica si el error es un error de conexión (puede reintentarse)
     */
    fun isConnectionError(exception: Throwable): Boolean {
        return exception is SocketTimeoutException ||
               exception is UnknownHostException ||
               (exception is IOException && (
                   exception.message?.contains("Failed to connect", ignoreCase = true) == true ||
                   exception.message?.contains("timeout", ignoreCase = true) == true ||
                   exception.message?.contains("network", ignoreCase = true) == true
               )) ||
               (exception is HttpException && exception.code() in 500..599)
    }
    
    /**
     * Verifica si el error es transitorio y puede reintentarse
     */
    fun isRetryableError(exception: Throwable): Boolean {
        return when (exception) {
            is SocketTimeoutException -> true
            is UnknownHostException -> true
            is IOException -> {
                exception.message?.let { message ->
                    message.contains("Failed to connect", ignoreCase = true) ||
                    message.contains("timeout", ignoreCase = true) ||
                    message.contains("network", ignoreCase = true)
                } ?: false
            }
            is HttpException -> {
                exception.code() in 500..599 || exception.code() == 408 || exception.code() == 429
            }
            else -> false
        }
    }
    
    /**
     * Verifica si el error requiere que el usuario se autentique nuevamente
     */
    fun requiresReauthentication(exception: Throwable): Boolean {
        return exception is HttpException && exception.code() == 401
    }
}

