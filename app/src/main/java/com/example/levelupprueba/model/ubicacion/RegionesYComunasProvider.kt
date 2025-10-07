package com.example.levelupprueba.model.ubicacion

object RegionesYComunasProvider {
    val regiones: List<Region> = listOf(
        Region(
            nombre = "Region Metropolitana",
            comunas = listOf(
                Comuna(nombre = "Santiago"),
                Comuna(nombre = "Las Condes"),
                Comuna(nombre = "Providencia"),
                Comuna(nombre = "Ñuñoa"),
                Comuna(nombre = "Maipú"),
                Comuna(nombre = "La Florida"),
                Comuna(nombre = "Puente Alto")
            )
        ),
        Region(
            nombre = "Valparaiso",
            comunas = listOf(
                Comuna(nombre = "Valparaíso"),
                Comuna(nombre = "Viña del Mar"),
                Comuna(nombre = "Concón"),
                Comuna(nombre = "Quilpué"),
                Comuna(nombre = "Villa Alemana"),
                Comuna(nombre = "San Antonio"),
            )
        ),
        Region(
            nombre = "Biobío",
            comunas = listOf(
                Comuna(nombre = "Concepción"),
                Comuna(nombre = "Talcahuano"),
                Comuna(nombre = "Chillán"),
                Comuna(nombre = "Los Ángeles"),
                Comuna(nombre = "Coronel"),
            )
        ),
        Region(
            nombre = "Coquimbo",
            comunas = listOf(
                Comuna(nombre = "La Serena"),
                Comuna(nombre = "Coquimbo"),
                Comuna(nombre = "Ovalle"),
                Comuna(nombre = "Illapel"),
            )
        ),
        Region(
            nombre = "Antofagasta",
            comunas = listOf(
                Comuna(nombre = "Antofagasta"),
                Comuna(nombre = "Calama"),
                Comuna(nombre = "Tocopilla"),
                Comuna(nombre = "Mejillones"),
            )
        ),
        Region(
            nombre = "Tarapacá",
            comunas = listOf(
                Comuna(nombre = "Iquique"),
                Comuna(nombre = "Alto Hospicio"),
                Comuna(nombre = "Pozo Almonte"),
            )
        ),
        Region(
            nombre = "Arica y Parinacota",
            comunas = listOf(
                Comuna(nombre = "Arica"),
                Comuna(nombre = "Putre"),
                Comuna(nombre = "Camarones"),
            )
        )


    )
}