package com.example.levelupprueba.data.remote

import com.example.levelupprueba.data.remote.MediaUrlResolver
import com.example.levelupprueba.model.producto.Categoria
import com.example.levelupprueba.model.producto.Producto
import com.example.levelupprueba.model.producto.Subcategoria
import com.example.levelupprueba.model.producto.Review

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
        val imagenUrl = MediaUrlResolver.resolveFirst(
            listOf(
                dto.imagenUrl,
                dto.imagenS3Key,
                dto.imagen
            )
        )
        
        // Parsear galería de imágenes desde los campos disponibles
        val imagenesUrls: List<String> = run {
            val jsonSources = dto.imagenesUrls
                ?: dto.imagenesS3Keys
                ?: dto.imagenes

            val parsed = MediaUrlResolver.resolveJsonList(jsonSources)
            if (parsed.isNotEmpty()) {
                parsed
            } else {
                val resolvedCandidates = MediaUrlResolver.resolveList(
                    listOf(
                        dto.imagenUrl,
                        dto.imagenS3Key,
                        dto.imagen
                    )
                )
                if (resolvedCandidates.isNotEmpty()) {
                    resolvedCandidates
                } else if (imagenUrl.isNotBlank()) {
                    listOf(imagenUrl)
                } else {
                    emptyList<String>()
                }
            }
        }
        
        // Mapear categoría desde categoriaId
        val categoriaId = dto.categoriaId ?: ""
        val categoria = mapCategoria(categoriaId)
        val categoriaNombre = dto.categoriaNombre ?: categoria.nombre
        
        // Mapear subcategoría desde subcategoriaId
        val subcategoriaId = dto.subcategoriaId
        val subcategoria = subcategoriaId?.let { mapSubcategoria(it) }
        val subcategoriaNombre = dto.subcategoriaNombre ?: subcategoria?.nombre
        
        // Obtener nombre del producto (priorizar nombreProducto del backend)
        val nombre = dto.nombreProducto ?: dto.nombre ?: dto.titulo ?: ""
        
        // Obtener descripción (priorizar descripcionProducto del backend)
        val descripcion = dto.descripcionProducto ?: dto.descripcion ?: ""
        
        // Obtener precio (priorizar precioProducto del backend)
        val precio = dto.precioProducto ?: dto.precio ?: 0.0
        
        // Obtener ID (priorizar idProducto del backend)
        val id = (dto.idProducto ?: dto.id)?.toString() ?: ""
        val codigoProducto = dto.codigoProducto
        
        // Obtener disponibilidad (priorizar activo del backend)
        val disponible = dto.activo ?: dto.disponible ?: true

        val descuentoOrigen = dto.descuento
        val descuento = descuentoOrigen?.toInt()
        val precioConDescuento = dto.precioConDescuento
        val enOferta = dto.enOferta ?: (descuentoOrigen != null && descuentoOrigen > 0.0)
        val ratingPromedio = (dto.ratingPromedio ?: dto.rating)?.toFloat() ?: 0f
        val reviews = dto.reviews?.mapNotNull { mapReseniaDto(it, id) } ?: emptyList()
        
        return Producto(
            id = id,
            codigo = codigoProducto,
            nombre = nombre,
            descripcion = descripcion,
            precio = precio,
            precioConDescuentoBackend = precioConDescuento,
            imagenUrl = imagenUrl,
            categoria = categoria,
            categoriaNombre = categoriaNombre,
            subcategoria = subcategoria,
            subcategoriaNombre = subcategoriaNombre,
            rating = ratingPromedio,
            ratingPromedioBackend = ratingPromedio,
            disponible = disponible,
            destacado = dto.destacado ?: false,
            enOferta = enOferta,
            stock = dto.stock ?: 0,
            imagenesUrls = imagenesUrls,
            fabricante = dto.fabricante,
            distribuidor = dto.distribuidor,
            descuento = descuento,
            reviews = reviews,
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
    private fun mapSubcategoria(subcategoriaId: String): Subcategoria? {
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

    private fun mapReseniaDto(dto: ReseniaResumenDto, productoId: String): Review? {
        val reviewId = dto.id?.toString() ?: return null
        val rating = dto.rating ?: return null

        return Review(
            id = reviewId,
            productoId = productoId,
            usuarioNombre = dto.usuarioNombre ?: "Usuario",
            rating = rating.toFloat(),
            comentario = dto.comentario ?: "",
            fecha = dto.fechaCreacion ?: ""
        )
    }
}

