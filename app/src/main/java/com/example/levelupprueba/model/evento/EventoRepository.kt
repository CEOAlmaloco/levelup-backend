package com.example.levelupprueba.model.evento

//modelo evento luego IMPLEMENTAR CON SQL LITE
class EventoRepository {

    // Copia exacta de eventosLevelUp del JS - COORDENADAS EXACTAS
    fun obtenerEventos(): List<Evento> {
        //funcion obtener eventos sera una lista que contiene eventos 
        return listOf(
            Evento(
                id = "1",
                titulo = "Santiago Gaming Fest",
                lugar = "Centro Cultural Estación Mapocho",
                direccion = "Av. Presidente Balmaceda 850, Santiago",
                ciudad = "Santiago",
                fecha = "12 Jul 2025",
                hora = "11:00",
                puntos = 100,
                latitud = -33.4346,
                longitud = -70.6514,
                descripcion = "El evento gaming más grande de Santiago con torneos, stands y actividades para toda la comunidad gamer."
            ),
            Evento(
                id = "2",
                titulo = "Viña eSports Meetup",
                lugar = "Quinta Vergara",
                direccion = "Quinta Vergara, Viña del Mar",
                ciudad = "Viña del Mar",
                fecha = "26 Jul 2025",
                hora = "16:00",
                puntos = 80,
                latitud = -33.0253,
                longitud = -71.543,
                descripcion = "Encuentro de entusiastas de los eSports con torneos de League of Legends y Valorant."
            ),
            Evento(
                id = "3",
                titulo = "Concepción Retro Game Day",
                lugar = "Plaza de la Independencia",
                direccion = "Plaza de la Independencia, Concepción",
                ciudad = "Concepción",
                fecha = "09 Ago 2025",
                hora = "12:00",
                puntos = 70,
                latitud = -36.8269,
                longitud = -73.0498,
                descripcion = "Día dedicado a los juegos retro con consolas clásicas y competencias nostálgicas."
            ),
            Evento(
                id = "4",
                titulo = "La Serena LAN Party",
                lugar = "Mall Plaza La Serena",
                direccion = "Mall Plaza La Serena, La Serena",
                ciudad = "La Serena",
                fecha = "23 Ago 2025",
                hora = "14:00",
                puntos = 60,
                latitud = -29.9045,
                longitud = -71.2506,
                descripcion = "LAN Party con torneos de CS2, Dota 2 y premios increíbles."
            ),
            Evento(
                id = "5",
                titulo = "Antofagasta Arena Gaming",
                lugar = "Plaza Colón",
                direccion = "Plaza Colón, Antofagasta",
                ciudad = "Antofagasta",
                fecha = "06 Sep 2025",
                hora = "15:00",
                puntos = 80,
                latitud = -23.6509,
                longitud = -70.4005,
                descripcion = "Arena gaming con actividades para jugadores profesionales y casuales."
            ),
            Evento(
                id = "6",
                titulo = "Temuco Indie Dev Showcase",
                lugar = "Plaza Aníbal Pinto",
                direccion = "Plaza Aníbal Pinto, Temuco",
                ciudad = "Temuco",
                fecha = "20 Sep 2025",
                hora = "10:00",
                puntos = 60,
                latitud = -38.7359,
                longitud = -72.5904,
                descripcion = "Exhibición de desarrolladores indie chilenos con demos jugables."
            ),
            Evento(
                id = "7",
                titulo = "Puerto Montt Game Night",
                lugar = "Arena Puerto Montt",
                direccion = "Arena Puerto Montt, Puerto Montt",
                ciudad = "Puerto Montt",
                fecha = "04 Oct 2025",
                hora = "18:00",
                puntos = 90,
                latitud = -41.4723,
                longitud = -72.9369,
                descripcion = "Noche gaming con múltiples actividades y sorteos especiales."
            )
        )
    }

    /* adaptacion de las recompensas del js para el canje de puntos */
    fun obtenerRecompensas(): List<RecompensaCanje> {//funcion obtener recompensas sera una lista que contiene recompensas
        return listOf(
            RecompensaCanje(//estos luego se procesaran con otra funcion para el canje de puntos en el viewmodel pero eso a futuro 
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
                titulo = "Gift Card \$10.000", // \$ es para el signo de dolar en el titulo pq sino lo toma como una variable
                descripcion = "Gift Card \$10.000 CLP",
                costo = 1200,
                tipo = TipoRecompensa.GIFT_CARD
            )
        )
    }

   //esto simula la validacion de un codigo de evento en la api
    fun validarCodigo(codigo: String): CodigoEvento? {// para validar el codigo necesitamos el string del codigo y la funcion devuelve un valor tipo codigoevento que puede ser null si no es valido

        val codigosValidos = mapOf(//mapa de codigos validos para el canje de puntos obviamente son simulaciones  no reales pero para tener algo
            "LVUP-SANTIAGO-100" to 100,//otro copia pega de js 
            "LVUP-VINA-80" to 80,
            "LVUP-CONCE-70" to 70,
            "LVUP-SERENA-60" to 60,
            "LVUP-ANTOF-80" to 80,
            "LVUP-TEMUCO-60" to 60,
            "LVUP-PMONT-90" to 90
        )

        val puntos = codigosValidos[codigo.uppercase()]// el valor puntos sera el valor del codigo en el mapa de codigos validos y lo convertimos a mayusculas para que no haya problemas con la mayuscula o minuscula
        return if (puntos != null) {//retornamos el codigo valido si es diferente a null 
            CodigoEvento(codigo = codigo, puntos = puntos, usado = false)//entonces el codigoevento sera igual a codigo, puntos y usado con sus valores 
        } else {
            null
        }
    }
}

