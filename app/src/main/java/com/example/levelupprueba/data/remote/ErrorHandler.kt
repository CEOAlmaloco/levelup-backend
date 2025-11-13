package com.example.levelupprueba.data.remote

import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import retrofit2.HttpException

/**
 * Helper para manejar errores de conexión y mostrar mensajes amigables al usuario
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
                    else -> {
                        "Error de conexión. Por favor, intenta más tarde."
                    }
                }
            }
            is HttpException -> {
                when (exception.code()) {
                    400 -> "Solicitud inválida. Por favor, verifica los datos ingresados."
                    401 -> "Credenciales inválidas. Verifica tu correo y contraseña."
                    403 -> "No tienes permisos para realizar esta acción."
                    404 -> "Recurso no encontrado en el servidor."
                    409 -> "Conflicto: El recurso ya existe (posiblemente el correo electrónico ya está registrado)."
                    500, 502, 503 -> "Error del servidor. Por favor, intenta más tarde."
                    else -> "Error del servidor (${exception.code()}). Por favor, intenta más tarde."
                }
            }
            else -> {
                "Error inesperado: ${exception.message ?: "Error desconocido"}"
            }
        }
    }
    
    /**
     * Verifica si el error es un error de conexión
     */
    fun isConnectionError(exception: Throwable): Boolean {
        return exception is SocketTimeoutException ||
               exception is UnknownHostException ||
               (exception is IOException && (
                   exception.message?.contains("Failed to connect", ignoreCase = true) == true ||
                   exception.message?.contains("timeout", ignoreCase = true) == true ||
                   exception.message?.contains("network", ignoreCase = true) == true
               ))
    }
}

