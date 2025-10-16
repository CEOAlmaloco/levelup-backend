package com.example.levelupprueba.model.puntos

/**
 * Modelo para las recompensas que se pueden canjear con puntos
 */
data class Recompensa(
    val id: String,
    val nombre: String,
    val descripcion: String,
    val costo: Int, // Puntos necesarios para canjear
    val tipo: TipoRecompensa,
    val disponible: Boolean = true,
    val imagen: String? = null
)

/**
 * Tipos de recompensas disponibles
 */
enum class TipoRecompensa {
    DESCUENTO,      // Descuento en productos
    PRODUCTO,       // Producto gratuito
    EVENTO,         // Acceso a eventos especiales
    MERCHANDISE     // Merchandise exclusivo
}

/**
 * Modelo para los niveles del sistema de puntos
 */
data class Nivel(
    val numero: Int,
    val nombre: String,
    val puntosMinimos: Int,
    val beneficios: List<String>,
    val color: String // Color del nivel para la UI
)

/**
 * Sistema de niveles predefinido
 */
object SistemaNiveles {
    val NIVELES = listOf(
        Nivel(
            numero = 1,
            nombre = "Novato",
            puntosMinimos = 0,
            beneficios = listOf("Acceso a eventos básicos"),
            color = "#9E9E9E"
        ),
        Nivel(
            numero = 2,
            nombre = "Gamer",
            puntosMinimos = 100,
            beneficios = listOf("5% descuento en productos", "Acceso a eventos premium"),
            color = "#4CAF50"
        ),
        Nivel(
            numero = 3,
            nombre = "Pro Gamer",
            puntosMinimos = 500,
            beneficios = listOf("10% descuento en productos", "Acceso a eventos VIP", "Merchandise exclusivo"),
            color = "#2196F3"
        ),
        Nivel(
            numero = 4,
            nombre = "Elite",
            puntosMinimos = 1000,
            beneficios = listOf("15% descuento en productos", "Acceso a todos los eventos", "Productos gratuitos"),
            color = "#FF9800"
        ),
        Nivel(
            numero = 5,
            nombre = "Legend",
            puntosMinimos = 2000,
            beneficios = listOf("20% descuento en productos", "Acceso VIP a todos los eventos", "Productos exclusivos gratuitos"),
            color = "#9C27B0"
        )
    )
    
    /**
     * Obtiene el nivel actual del usuario basado en sus puntos
     */
    fun obtenerNivelActual(puntos: Int): Nivel {
        return NIVELES.lastOrNull { puntos >= it.puntosMinimos } ?: NIVELES.first()
    }
    
    /**
     * Obtiene el siguiente nivel del usuario
     */
    fun obtenerSiguienteNivel(puntos: Int): Nivel? {
        val nivelActual = obtenerNivelActual(puntos)
        val indiceActual = NIVELES.indexOf(nivelActual)
        return if (indiceActual < NIVELES.size - 1) {
            NIVELES[indiceActual + 1]
        } else {
            null // Ya esta en el nivel maximo
        }
    }
    
    /**
     * Calcula el progreso hacia el siguiente nivel
     */
    fun calcularProgreso(puntos: Int): Float {
        val nivelActual = obtenerNivelActual(puntos)
        val siguienteNivel = obtenerSiguienteNivel(puntos)
        
        if (siguienteNivel == null) return 1.0f // Nivel maximo alcanzado
        
        val puntosEnNivelActual = puntos - nivelActual.puntosMinimos
        val puntosNecesariosParaSiguiente = siguienteNivel.puntosMinimos - nivelActual.puntosMinimos
        
        return (puntosEnNivelActual.toFloat() / puntosNecesariosParaSiguiente.toFloat()).coerceIn(0f, 1f)
    }
}
