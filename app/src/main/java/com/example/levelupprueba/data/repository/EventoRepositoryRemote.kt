package com.example.levelupprueba.data.repository

import com.example.levelupprueba.data.remote.ApiConfig
import com.example.levelupprueba.data.remote.EventosApiService
import com.example.levelupprueba.model.evento.Evento
import com.example.levelupprueba.model.evento.CodigoEvento
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
            val response = eventosService.getEventos(page = 0, size = 100)
            
            if (response.isSuccessful && response.body() != null) {
                val eventoResponsePage = response.body()!!
                eventoResponsePage.content.map { eventoDto ->
                    mapEventoDtoToEvento(eventoDto)
                }
            } else {
                // Si hay error, retornar lista vacía
                emptyList()
            }
        } catch (e: Exception) {
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
                response.body()!!.map { eventoDto ->
                    mapEventoDtoToEvento(eventoDto)
                }
            } else {
                emptyList()
            }
        } catch (e: Exception) {
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
                null
            }
        } catch (e: Exception) {
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
                emptyList()
            }
        } catch (e: Exception) {
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
        // Parsear fecha y hora desde fechaInicio
        val fechaInicio = eventoDto.fechaInicio
        val partes = fechaInicio.split("T")
        val fecha = if (partes.isNotEmpty()) partes[0] else fechaInicio
        val hora = if (partes.size > 1) partes[1].substring(0, 5) else "00:00"
        
        // Parsear ubicación
        val ubicacion = eventoDto.ubicacion
        val partesUbicacion = ubicacion.split(",")
        val lugar = if (partesUbicacion.isNotEmpty()) partesUbicacion[0].trim() else ubicacion
        val ciudad = if (partesUbicacion.size > 1) partesUbicacion.last().trim() else ""
        
        return Evento(
            id = eventoDto.id ?: "",
            titulo = eventoDto.titulo,
            lugar = lugar,
            direccion = ubicacion,
            ciudad = ciudad,
            fecha = fecha,
            hora = hora,
            puntos = eventoDto.puntosRecompensa,
            latitud = eventoDto.latitud ?: 0.0,
            longitud = eventoDto.longitud ?: 0.0,
            descripcion = eventoDto.descripcion
        )
    }
}

