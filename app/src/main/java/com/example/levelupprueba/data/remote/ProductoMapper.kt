package com.example.levelupprueba.data.remote

import com.example.levelupprueba.model.producto.Categoria
import com.example.levelupprueba.model.producto.Producto
import com.example.levelupprueba.model.producto.Subcategoria
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// ProductoDto está en el mismo paquete, accesible directamente

/**
 * Utilidades para mapear DTOs del backend a modelos Kotlin
 */
object ProductoMapper {
    
    /**
     * Mapea un ProductoDto del backend a un Producto del modelo Kotlin
     */
    fun mapProductoDto(dto: ProductoDto): Producto {
        // Priorizar imagenUrl del backend (URL completa de S3 construida)
        // Si no existe, usar imagenS3Key (referencia S3) o imagen (compatibilidad con Base64)
        val imagenUrl = when {
            !dto.imagenUrl.isNullOrBlank() -> dto.imagenUrl // URL completa de S3 desde el backend
            !dto.imagenS3Key.isNullOrBlank() -> dto.imagenS3Key // Referencia S3 (se construirá la URL si es necesario)
            !dto.imagen.isNullOrBlank() -> dto.imagen // Base64 o ruta legacy
            else -> ""
        }
        
        // Parsear imagenesUrls (JSON array de URLs completas de S3) o imagenesS3Keys (referencias)
        val imagenesUrls = try {
            // Priorizar imagenesUrls (URLs completas de S3)
            val imagenesJson = when {
                !dto.imagenesUrls.isNullOrBlank() -> dto.imagenesUrls
                !dto.imagenesS3Keys.isNullOrBlank() -> dto.imagenesS3Keys
                !dto.imagenes.isNullOrBlank() -> dto.imagenes
                else -> null
            }
            
            if (imagenesJson != null && imagenesJson.isNotBlank()) {
                val gson = Gson()
                val type = object : TypeToken<List<String>>() {}.type
                val imagenesList: List<String> = gson.fromJson(imagenesJson, type)
                imagenesList.ifEmpty { listOf(imagenUrl) }
            } else {
                listOf(imagenUrl)
            }
        } catch (e: Exception) {
            listOf(imagenUrl)
        }
        
        // Mapear categoría desde categoriaId
        val categoriaId = dto.categoriaId ?: ""
        val categoria = mapCategoria(categoriaId)
        
        // Mapear subcategoría desde subcategoriaId
        val subcategoriaId = dto.subcategoriaId
        val subcategoria = subcategoriaId?.let { mapSubcategoria(it, categoria) }
        
        // Obtener nombre del producto (priorizar nombreProducto del backend)
        val nombre = dto.nombreProducto ?: dto.nombre ?: dto.titulo ?: ""
        
        // Obtener descripción (priorizar descripcionProducto del backend)
        val descripcion = dto.descripcionProducto ?: dto.descripcion ?: ""
        
        // Obtener precio (priorizar precioProducto del backend)
        val precio = dto.precioProducto ?: dto.precio ?: 0.0
        
        // Obtener ID (priorizar idProducto del backend)
        val id = (dto.idProducto ?: dto.id)?.toString() ?: ""
        
        // Obtener disponibilidad (priorizar activo del backend)
        val disponible = dto.activo ?: dto.disponible ?: true
        
        return Producto(
            id = id,
            nombre = nombre,
            descripcion = descripcion,
            precio = precio,
            imagenUrl = imagenUrl,
            categoria = categoria,
            subcategoria = subcategoria,
            rating = (dto.rating ?: 0.0).toFloat(),
            disponible = disponible,
            destacado = false, // El backend no tiene este campo, se puede calcular después
            stock = dto.stock ?: 0,
            imagenesUrls = imagenesUrls,
            fabricante = null, // El backend no tiene este campo
            distribuidor = null, // El backend no tiene este campo
            descuento = null, // El backend no tiene este campo
            reviews = emptyList(), // Las reviews se cargan por separado
            productosRelacionados = emptyList() // Se cargan por separado
        )
    }
    
    /**
     * Mapea una lista de ProductoDto a una lista de Producto
     */
    fun mapProductosDto(dtos: List<ProductoDto>): List<Producto> {
        return dtos.map { mapProductoDto(it) }
    }
    
    /**
     * Mapea un categoriaId a un enum Categoria
     */
    private fun mapCategoria(categoriaId: String): Categoria {
        return when (categoriaId.uppercase()) {
            "CO" -> Categoria.CONSOLA
            "PE" -> Categoria.PERIFERICOS
            "RO" -> Categoria.ROPA
            "EN" -> Categoria.ENTRETENIMIENTO
            else -> Categoria.TODAS
        }
    }
    
    /**
     * Mapea un subcategoriaId a un enum Subcategoria
     */
    private fun mapSubcategoria(subcategoriaId: String, categoria: Categoria): Subcategoria? {
        return when (subcategoriaId.uppercase()) {
            // Consola
            "HA" -> Subcategoria.HARDWARE
            "MA" -> Subcategoria.MANDOS
            "AC" -> Subcategoria.ACCESORIOS
            // Periféricos
            "TE" -> Subcategoria.TECLADOS
            "MO" -> Subcategoria.MOUSES
            "AU" -> Subcategoria.AURICULARES
            "MT" -> Subcategoria.MONITORES
            "MI" -> Subcategoria.MICROFONOS
            "CW" -> Subcategoria.CAMARAS_WEB
            "MP" -> Subcategoria.MOUSEPAD
            "SI" -> Subcategoria.SILLAS_GAMERS
            // Ropa
            "PR" -> Subcategoria.POLERAS
            "PG" -> Subcategoria.POLERONES
            // Entretenimiento
            "JM" -> Subcategoria.JUEGOS_MESA
            else -> null
        }
    }
}

