package com.example.levelupprueba.model.blog

class BlogRepository {

    fun obtenerBlogs(): List<Blog> {
        return listOf(
            Blog(
                id = "1",
                titulo = "PlayStation 5 Pro: Todo lo que necesitas saber",
                contenido = "Sony anuncia oficialmente la PlayStation 5 Pro con mejoras significativas en rendimiento y gráficos. Descubre todas las características y fecha de lanzamiento.",
                resumen = "Sony anuncia oficialmente la PlayStation 5 Pro con mejoras significativas en rendimiento y gráficos.",
                categoria = "noticias",
                imagenUrl = "play5",
                autor = "Miguel Torres",
                fechaPublicacion = "15 Dic 2024",
                tags = listOf("PlayStation", "Consolas", "Gaming"),
                destacado = true
            ),
            Blog(
                id = "2",
                titulo = "Guía completa para armar tu setup gaming perfecto en 2025",
                contenido = "¿Quieres crear el setup gaming definitivo? Te mostramos los componentes esenciales, periféricos recomendados y tips de configuración para que tengas la mejor experiencia de juego posible.",
                resumen = "Te mostramos los componentes esenciales, periféricos recomendados y tips de configuración.",
                categoria = "guias",
                imagenUrl = "armarpcpaso",
                autor = "Ana López",
                fechaPublicacion = "22 Dic 2024",
                tags = listOf("Setup", "Hardware", "Gaming"),
                destacado = false
            ),
            Blog(
                id = "3",
                titulo = "El crecimiento de los esports en Chile: Una industria en expansión",
                contenido = "Los deportes electrónicos han experimentado un crecimiento exponencial en Chile durante los últimos años. Analizamos las tendencias, los equipos más destacados y las oportunidades que ofrece esta industria.",
                resumen = "Analizamos las tendencias, los equipos más destacados y las oportunidades que ofrece esta industria.",
                categoria = "comunidad",
                imagenUrl = "evento",
                autor = "Carlos Rivera",
                fechaPublicacion = "20 Dic 2024",
                tags = listOf("Esports", "Chile", "Comunidad"),
                destacado = false
            ),
            Blog(
                id = "4",
                titulo = "Los juegos más esperados del resto del 2025",
                contenido = "El año 2025 promete ser increíble para los videojuegos. Desde secuelas muy esperadas hasta nuevas IPs que revolucionarán la industria.",
                resumen = "El año 2025 promete ser increíble para los videojuegos. Desde secuelas muy esperadas hasta nuevas IPs.",
                categoria = "noticias",
                imagenUrl = "juegos_esperados",
                autor = "María Torres",
                fechaPublicacion = "18 Dic 2024",
                tags = listOf("Lanzamientos", "2025", "Videojuegos"),
                destacado = false
            ),
            Blog(
                id = "5",
                titulo = "Gaming móvil: La revolución que llegó para quedarse",
                contenido = "El gaming móvil ha evolucionado de simples juegos casuales a experiencias AAA completas. Exploramos cómo los smartphones modernos están cambiando la industria.",
                resumen = "El gaming móvil ha evolucionado de simples juegos casuales a experiencias AAA completas.",
                categoria = "noticias",
                imagenUrl = "movil",
                autor = "Level Up Team",
                fechaPublicacion = "15 Dic 2024",
                tags = listOf("Mobile", "Gaming", "Smartphones"),
                destacado = false
            ),
            Blog(
                id = "6",
                titulo = "Realidad Virtual: El futuro del gaming inmersivo",
                contenido = "La realidad virtual ha alcanzado un punto de madurez impresionante. Desde los últimos headsets hasta los juegos más innovadores.",
                resumen = "La realidad virtual ha alcanzado un punto de madurez impresionante. Desde los últimos headsets hasta los juegos más innovadores.",
                categoria = "noticias",
                imagenUrl = "ralidadv",
                autor = "Ana López",
                fechaPublicacion = "12 Dic 2024",
                tags = listOf("VR", "Realidad Virtual", "Inmersivo"),
                destacado = false
            )
        )
    }
}
