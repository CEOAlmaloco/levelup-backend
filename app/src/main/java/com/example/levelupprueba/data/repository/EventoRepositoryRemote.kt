package com.example.levelupprueba.data.repository

import android.util.Log
import com.example.levelupprueba.data.remote.ApiConfig
import com.example.levelupprueba.data.remote.EventosApiService
import com.example.levelupprueba.data.remote.MediaUrlResolver
import com.example.levelupprueba.model.evento.CodigoEvento
import com.example.levelupprueba.model.evento.Evento
import com.example.levelupprueba.model.evento.RecompensaCanje
import com.example.levelupprueba.model.evento.TipoRecompensa
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// Importar DTOs del paquete remote (clases de nivel superior, no anidadas)
import com.example.levelupprueba.data.remote.EventoDto
import com.example.levelupprueba.data.remote.InscripcionDto

/**
 * Repositorio de eventos que obtiene datos desde el backend Spring Boot usando Retrofit
 */
class EventoRepositoryRemote {
    
    private val eventosService = ApiConfig.eventosService
    
    /**
     * Obtiene todos los eventos desde el backend
     */
    suspend fun obtenerEventos(): List<Evento> = withContext(Dispatchers.IO) {
        try {
            val response = eventosService.getEventos()
            
            if (response.isSuccessful && response.body() != null) {
                val eventos = response.body()!!
                    .map { eventoDto -> mapEventoDtoToEvento(eventoDto) }
                Log.d("EventoRepository", "Eventos recibidos: ${eventos.size}")
                eventos
            } else {
                val errorBody = try {
                    response.errorBody()?.string()
                } catch (e: Exception) {
                    "Error al leer errorBody: ${e.message}"
                }
                Log.w(
                    "EventoRepository",
                    "Error al obtener eventos: code=${response.code()} body=$errorBody"
                )
                // Si hay error, retornar lista vacía
                emptyList()
            }
        } catch (e: java.net.SocketTimeoutException) {
            Log.e("EventoRepository", "Timeout al conectar con el servidor de eventos. El microservicio puede no estar corriendo.", e)
            // Retornar lista vacía en caso de timeout (servicio no disponible)
            emptyList()
        } catch (e: java.net.ConnectException) {
            Log.e("EventoRepository", "No se pudo conectar con el servidor de eventos. Verifica que el microservicio esté corriendo.", e)
            // Retornar lista vacía en caso de error de conexión
            emptyList()
        } catch (e: Exception) {
            Log.e("EventoRepository", "Excepción al obtener eventos: ${e.javaClass.simpleName} - ${e.message}", e)
            // En caso de excepción, retornar lista vacía
            emptyList()
        }
    }
    
    /**
     * Obtiene eventos próximos desde el backend
     */
    suspend fun obtenerEventosProximos(limit: Int = 6): List<Evento> = withContext(Dispatchers.IO) {
        try {
            val response = eventosService.getEventosProximos(limit = limit)
            
            if (response.isSuccessful && response.body() != null) {
                val proximos = response.body()!!.map { eventoDto ->
                    mapEventoDtoToEvento(eventoDto)
                }
                Log.d("EventoRepository", "Eventos próximos recibidos: ${proximos.size}")
                proximos
            } else {
                val errorBody = try {
                    response.errorBody()?.string()
                } catch (e: Exception) {
                    "Error al leer errorBody: ${e.message}"
                }
                Log.w(
                    "EventoRepository",
                    "Error al obtener proximos eventos: code=${response.code()} body=$errorBody"
                )
                emptyList()
            }
        } catch (e: java.net.SocketTimeoutException) {
            Log.e("EventoRepository", "Timeout al conectar con el servidor de eventos. El microservicio puede no estar corriendo.", e)
            emptyList()
        } catch (e: java.net.ConnectException) {
            Log.e("EventoRepository", "No se pudo conectar con el servidor de eventos. Verifica que el microservicio esté corriendo.", e)
            emptyList()
        } catch (e: Exception) {
            Log.e("EventoRepository", "Excepción al obtener proximos eventos: ${e.javaClass.simpleName} - ${e.message}", e)
            emptyList()
        }
    }
    
    /**
     * Obtiene un evento por ID desde el backend
     */
    suspend fun obtenerEventoPorId(id: String): Evento? = withContext(Dispatchers.IO) {
        try {
            val response = eventosService.getEventoById(id)
            
            if (response.isSuccessful && response.body() != null) {
                mapEventoDtoToEvento(response.body()!!)
            } else {
                val errorBody = try {
                    response.errorBody()?.string()
                } catch (e: Exception) {
                    "Error al leer errorBody: ${e.message}"
                }
                Log.w(
                    "EventoRepository",
                    "Error al obtener evento $id: code=${response.code()} body=$errorBody"
                )
                null
            }
        } catch (e: Exception) {
            Log.e("EventoRepository", "Excepción al obtener evento $id", e)
            null
        }
    }
    
    /**
     * Inscribe al usuario en un evento
     */
    suspend fun inscribirseEvento(eventoId: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val response = eventosService.inscribirseEvento(eventoId)
            response.isSuccessful
        } catch (e: Exception) {
            Log.e("EventoRepository", "Excepción al inscribirse en evento $eventoId", e)
            false
        }
    }
    
    /**
     * Cancela inscripción a un evento
     */
    suspend fun cancelarInscripcion(eventoId: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val response = eventosService.cancelarInscripcion(eventoId)
            response.isSuccessful
        } catch (e: Exception) {
            Log.e("EventoRepository", "Excepción al cancelar inscripción $eventoId", e)
            false
        }
    }
    
    /**
     * Obtiene las inscripciones del usuario
     */
    suspend fun obtenerMisInscripciones(): List<InscripcionDto> = withContext(Dispatchers.IO) {
        try {
            val response = eventosService.getMisInscripciones()
            
            if (response.isSuccessful && response.body() != null) {
                response.body()!!
            } else {
                val errorBody = try {
                    response.errorBody()?.string()
                } catch (e: Exception) {
                    "Error al leer errorBody: ${e.message}"
                }
                Log.w(
                    "EventoRepository",
                    "Error al obtener inscripciones: code=${response.code()} body=$errorBody"
                )
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("EventoRepository", "Excepción al obtener inscripciones", e)
            emptyList()
            }
    }
    
    /**
     * Obtiene recompensas (hardcodeadas por ahora, se puede mover al backend)
     */
    fun obtenerRecompensas(): List<RecompensaCanje> {
        return listOf(
            RecompensaCanje(
                id = "1",
                titulo = "Descuento 5%",
                descripcion = "5% de descuento en tu próxima compra",
                costo = 200,
                tipo = TipoRecompensa.DESCUENTO
            ),
            RecompensaCanje(
                id = "2",
                titulo = "Descuento 10%",
                descripcion = "10% de descuento en tu próxima compra",
                costo = 400,
                tipo = TipoRecompensa.DESCUENTO
            ),
            RecompensaCanje(
                id = "3",
                titulo = "Descuento 15%",
                descripcion = "15% de descuento en tu próxima compra",
                costo = 800,
                tipo = TipoRecompensa.DESCUENTO
            ),
            RecompensaCanje(
                id = "4",
                titulo = "Gift Card \$10.000",
                descripcion = "Gift Card \$10.000 CLP",
                costo = 1200,
                tipo = TipoRecompensa.GIFT_CARD
            )
        )
    }
    
    /**
     * Valida un código de evento (hardcodeado por ahora, se puede mover al backend)
     */
    fun validarCodigo(codigo: String): CodigoEvento? {
        val codigosValidos = mapOf(
            "LVUP-SANTIAGO-100" to 100,
            "LVUP-VINA-80" to 80,
            "LVUP-CONCE-70" to 70,
            "LVUP-SERENA-60" to 60,
            "LVUP-ANTOF-80" to 80,
            "LVUP-TEMUCO-60" to 60,
            "LVUP-PMONT-90" to 90
        )
        
        val puntos = codigosValidos[codigo.uppercase()]
        return if (puntos != null) {
            CodigoEvento(
                codigo = codigo,
                puntos = puntos,
                usado = false
            )
        } else {
            null
        }
    }
    
    /**
     * Mapea EventoDto del backend a Evento del modelo
     */
    private fun mapEventoDtoToEvento(eventoDto: EventoDto): Evento {
        // Resolver título y descripción
        val titulo = eventoDto.titulo ?: eventoDto.nombreEvento ?: ""
        val descripcion = eventoDto.descripcion ?: eventoDto.descripcionEvento ?: ""
        
        // Resolver fechas
        val fechaInicioRaw = eventoDto.fechaInicioString
            ?: eventoDto.fechaInicio
            ?: eventoDto.fechaFinString
            ?: eventoDto.fechaFin
            ?: ""
        
        val fechaHora = when {
            fechaInicioRaw.contains("T") -> fechaInicioRaw.split("T")
            fechaInicioRaw.contains(" ") -> fechaInicioRaw.split(" ")
            else -> listOf(fechaInicioRaw)
        }
        val fecha = fechaHora.getOrNull(0)?.takeIf { it.isNotBlank() } ?: fechaInicioRaw
        val hora = fechaHora.getOrNull(1)?.take(5)
            ?: eventoDto.fechaInicio?.takeLast(5)
            ?: "00:00"
        
        // Resolver ubicación y ciudad
        val ubicacion = eventoDto.ubicacion
            ?: eventoDto.ubicacionEvento
            ?: ""
        val ciudad = eventoDto.ciudad ?: run {
            val partesUbicacion = ubicacion.split(",")
            if (partesUbicacion.size > 1) partesUbicacion.last().trim() else ""
        }
        val lugar = when {
            ubicacion.isNotBlank() -> ubicacion.split(",").first().trim()
            ciudad.isNotBlank() -> ciudad
            else -> "Por confirmar"
        }
        
        // Resolver coordenadas
        val latitud = eventoDto.latitud ?: eventoDto.coordenadasLatitud ?: 0.0
        val longitud = eventoDto.longitud ?: eventoDto.coordenadasLongitud ?: 0.0
        
        // Resolver puntos, capacidad y participantes
        val puntos = eventoDto.puntosRecompensa
            ?: eventoDto.puntosLevelUp
            ?: 0
        
        val capacidadMaxima = eventoDto.capacidadMaxima
            ?: eventoDto.cuposMaximos
            ?: 0
        
        val participantesActuales = eventoDto.participantesActuales
            ?: if (eventoDto.cuposMaximos != null && eventoDto.cuposDisponibles != null) {
                (eventoDto.cuposMaximos - eventoDto.cuposDisponibles).coerceAtLeast(0)
            } else {
                0
            }
        
        val cuposDisponibles = eventoDto.cuposDisponibles
            ?: if (capacidadMaxima > 0) (capacidadMaxima - participantesActuales).coerceAtLeast(0) else 0
        
        // Resolver precios y estado
        val precio = eventoDto.precio ?: eventoDto.costoEntrada ?: 0.0
        val estado = when {
            !eventoDto.estado.isNullOrBlank() -> eventoDto.estado
            eventoDto.activo == true -> "ACTIVO"
            eventoDto.activo == false -> "INACTIVO"
            else -> ""
        }
        
        // Resolver categoría
        val categoria = eventoDto.categoria ?: eventoDto.tipoEvento ?: ""
        
        // Resolver imágenes (priorizar URLs remotas)
        val candidateImages = listOf(
            eventoDto.imagenUrl,
            eventoDto.bannerUrl,
            eventoDto.imagenStorageUrl,
            eventoDto.imagen
        )
        
        val imagenUrl = MediaUrlResolver.resolveFirst(candidateImages)
        
        val imagenesUrls: List<String> = buildList {
            addAll(MediaUrlResolver.resolveList(eventoDto.imagenesUrls))
            if (isEmpty()) {
                addAll(MediaUrlResolver.resolveJsonList(eventoDto.imagenes))
            }
        }.ifEmpty {
            if (imagenUrl.isNotBlank()) listOf(imagenUrl) else emptyList()
        }
        
        val bannerUrl = MediaUrlResolver.resolve(eventoDto.bannerUrl ?: eventoDto.imagenStorageUrl)
        
        val resolvedId = eventoDto.id
            ?: eventoDto.idEvento?.toString()
            ?: titulo.lowercase().replace(" ", "-")
        
        return Evento(
            id = resolvedId,
            titulo = titulo,
            lugar = lugar,
            direccion = ubicacion,
            ciudad = ciudad,
            fecha = fecha,
            hora = hora,
            puntos = puntos,
            latitud = latitud,
            longitud = longitud,
            descripcion = descripcion,
            categoria = categoria,
            capacidadMaxima = capacidadMaxima,
            participantesActuales = participantesActuales,
            cuposDisponibles = cuposDisponibles,
            precio = precio,
            estado = estado,
            imagenUrl = imagenUrl,
            bannerUrl = bannerUrl.takeIf { it.isNotBlank() },
            imagenesUrls = imagenesUrls
        )
    }
}

