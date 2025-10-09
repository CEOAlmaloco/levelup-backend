package com.example.levelupprueba.model.producto

enum class Categoria(val id: String, val nombre: String) {//enum significa una lista de constantes en  este caso para las categorias
    TODAS("todos", "Todas las categorías"),
    ENTRETENIMIENTO("EN", "Entretenimiento"),
    CONSOLA("CO", "Consola"),
    PERIFERICOS("PE", "Periféricos"),
    ROPA("RO", "Ropa")//ya habia echo una similar en el blog asi q no la comento tanto 
}

enum class Subcategoria(val id: String, val nombre: String, val categoria: Categoria) {
    // Entretenimiento
    JUEGOS_MESA("JM", "Juegos de Mesa", Categoria.ENTRETENIMIENTO),
    
    // Consola
    MANDOS("MA", "Mandos", Categoria.CONSOLA),
    HARDWARE("HA", "Hardware", Categoria.CONSOLA),
    ACCESORIOS("AC", "Accesorios", Categoria.CONSOLA),
    
    // Periféricos
    TECLADOS("TE", "Teclados", Categoria.PERIFERICOS),
    MOUSES("MO", "Mouses", Categoria.PERIFERICOS),
    AURICULARES("AU", "Auriculares", Categoria.PERIFERICOS),
    MONITORES("MT", "Monitores", Categoria.PERIFERICOS),
    MICROFONOS("MI", "Microfonos", Categoria.PERIFERICOS),
    CAMARAS_WEB("CW", "Camaras web", Categoria.PERIFERICOS),
    MOUSEPAD("MP", "Mousepad", Categoria.PERIFERICOS),
    SILLAS_GAMERS("SI", "Sillas Gamers", Categoria.PERIFERICOS),
    
    // Ropa
    POLERAS("PR", "Poleras Personalizadas", Categoria.ROPA),
    POLERONES("PG", "Polerones Gamers Personalizados", Categoria.ROPA)
}

