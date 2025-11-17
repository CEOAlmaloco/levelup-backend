package com.example.levelupprueba.data.repository

import android.util.Log
import com.example.levelupprueba.data.remote.ApiConfig
import com.example.levelupprueba.data.remote.MediaUrlResolver
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
                val blogs = response.body()!!.map { articulo ->
                    articulo.toBlog()
                }
                Log.d("BlogRepository", "Blogs publicados recibidos: ${blogs.size}")
                blogs
            } else {
                val errorBody = try {
                    response.errorBody()?.string()
                } catch (e: Exception) {
                    "Error al leer errorBody: ${e.message}"
                }
                Log.w(
                    "BlogRepository",
                    "Error al obtener blogs publicados: code=${response.code()} body=$errorBody"
                )
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("BlogRepository", "Excepción al obtener blogs publicados", e)
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
                val blogs = response.body()!!.map { articulo ->
                    articulo.toBlog()
                }
                Log.d("BlogRepository", "Blogs destacados recibidos: ${blogs.size}")
                blogs
            } else {
                val errorBody = try {
                    response.errorBody()?.string()
                } catch (e: Exception) {
                    "Error al leer errorBody: ${e.message}"
                }
                Log.w(
                    "BlogRepository",
                    "Error al obtener blogs destacados: code=${response.code()} body=$errorBody"
                )
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("BlogRepository", "Excepción al obtener blogs destacados", e)
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
                val blogs = response.body()!!.map { articulo ->
                    articulo.toBlog()
                }
                Log.d("BlogRepository", "Blogs por categoría '$categoria' recibidos: ${blogs.size}")
                blogs
            } else {
                val errorBody = try {
                    response.errorBody()?.string()
                } catch (e: Exception) {
                    "Error al leer errorBody: ${e.message}"
                }
                Log.w(
                    "BlogRepository",
                    "Error al obtener blogs por categoría '$categoria': code=${response.code()} body=$errorBody"
                )
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("BlogRepository", "Excepción al obtener blogs por categoría '$categoria'", e)
            emptyList()
        }
    }
}

/**
 * Función de extensión para convertir ArticuloResponse del backend a Blog del modelo local
 */
private fun ArticuloResponse.toBlog(): Blog {
    // Determinar ID del artículo considerando compatibilidad
    val resolvedId = when {
        !id.isNullOrBlank() -> id
        idArticulo != null -> idArticulo.toString()
        else -> ""
    }
    
    // Resolver título, contenido y resumen
    val resolvedTitulo = titulo ?: tituloArticulo ?: ""
    val resolvedContenido = contenido ?: contenidoArticulo ?: ""
    val resolvedResumen = resumen ?: resumenArticulo ?: ""
    
    // Resolver categoría
    val resolvedCategoria = (categoria ?: categoriaArticulo ?: "general").lowercase()
    
    // Resolver autor
    val resolvedAutor = autor ?: autorArticulo ?: "LevelUp Gamer"
    
    // Resolver fecha (priorizar string ya formateada)
    val resolvedFecha = fechaPublicacionString
        ?: fechaPublicacion
        ?: fecha
        ?: ""
    
    // Resolver tags
    val resolvedTags: List<String> = when {
        !tags.isNullOrEmpty() -> tags.map { it.trim() }.filter { it.isNotBlank() }
        !etiquetasArticulo.isNullOrBlank() -> etiquetasArticulo.split(",").map { it.trim() }.filter { it.isNotBlank() }
        else -> emptyList()
    }
    
    // Resolver imagen priorizando URLs remotas (S3) sobre Base64
    // Misma estructura que ProductoMapper: priorizar imagenUrl (URL completa de S3)
    val candidateImages = listOf(
        imagenUrl,              // Primera prioridad: URL completa de S3 desde backend
        imagenPreviewUrl,
        imagenMiniaturaUrl,
        imagenStorageUrl,
        imagenArticulo          // Última prioridad: compatibilidad con formato antiguo
    )
    
    val resolvedImagen = MediaUrlResolver.resolveFirst(candidateImages)
    
    // Log para debugging (similar a ProductoMapper)
    Log.d("BlogRepository", "Resolviendo imagen para blog: $resolvedTitulo")
    Log.d("BlogRepository", "  - imagenUrl: $imagenUrl")
    Log.d("BlogRepository", "  - imagenArticulo: ${imagenArticulo?.take(100)}...")
    Log.d("BlogRepository", "  - imagen resuelta: $resolvedImagen")
    
    val isDestacadoResolved = destacado ?: esDestacado ?: false
    
    return Blog(
        id = resolvedId,
        titulo = resolvedTitulo,
        contenido = resolvedContenido,
        resumen = resolvedResumen,
        categoria = resolvedCategoria,
        imagenUrl = resolvedImagen,
        autor = resolvedAutor,
        fechaPublicacion = resolvedFecha,
        tags = resolvedTags,
        destacado = isDestacadoResolved
    )
}
