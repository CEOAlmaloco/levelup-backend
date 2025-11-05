package com.example.levelupprueba.data.repository

import com.example.levelupprueba.data.remote.ApiConfig
import com.example.levelupprueba.data.remote.ArticuloResponse
import com.example.levelupprueba.model.blog.Blog

class BlogRepository {
    
    /**
     * Obtiene los blogs desde el backend usando ContenidoApiService
     */
    suspend fun obtenerBlogs(): List<Blog> {
        return try {
            val response = ApiConfig.contenidoService.getArticulosPublicados()
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.map { articulo ->
                    articulo.toBlog()
                }
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    /**
     * Obtiene blogs destacados desde el backend
     */
    suspend fun obtenerBlogsDestacados(): List<Blog> {
        return try {
            val response = ApiConfig.contenidoService.getArticulosDestacados()
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.map { articulo ->
                    articulo.toBlog()
                }
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    /**
     * Obtiene blogs por categoría desde el backend
     */
    suspend fun obtenerBlogsPorCategoria(categoria: String): List<Blog> {
        return try {
            val response = ApiConfig.contenidoService.getArticulosPorCategoria(categoria)
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.map { articulo ->
                    articulo.toBlog()
                }
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}

/**
 * Función de extensión para convertir ArticuloResponse del backend a Blog del modelo local
 */
private fun ArticuloResponse.toBlog(): Blog {
    // Parsear etiquetas si vienen como string separado por comas
    val tags = etiquetasArticulo?.split(",")?.map { it.trim() }?.filter { it.isNotBlank() } ?: emptyList()
    
    return Blog(
        id = idArticulo.toString(),
        titulo = tituloArticulo,
        contenido = contenidoArticulo,
        resumen = resumenArticulo,
        categoria = categoriaArticulo.lowercase(),
        imagenUrl = imagenArticulo ?: "", // Si es Base64, usar directamente; si es URL, usar la URL
        autor = autorArticulo,
        fechaPublicacion = fechaPublicacion,
        tags = tags,
        destacado = esDestacado
    )
}
