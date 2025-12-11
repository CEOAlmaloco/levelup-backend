package com.example.levelupprueba.data.remote

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.*

/**
 * Interfaz de API para contenido (blogs/artículos)
 * Endpoints: /api/v1/contenido/
 */
interface ContenidoApiService {

    /**
     * Obtener todos los artículos
     * GET /api/v1/contenido/articulos
     */
    @GET("contenido/articulos")
    suspend fun getAllArticulos(): Response<List<ArticuloResponse>>

    /**
     * Obtener artículos publicados
     * GET /api/v1/contenido/articulos/publicados
     */
    @GET("contenido/articulos/publicados")
    suspend fun getArticulosPublicados(): Response<List<ArticuloResponse>>

    /**
     * Obtener artículos destacados
     * GET /api/v1/contenido/articulos/destacados
     */
    @GET("contenido/articulos/destacados")
    suspend fun getArticulosDestacados(): Response<List<ArticuloResponse>>

    /**
     * Obtener artículos populares
     * GET /api/v1/contenido/articulos/populares
     */
    @GET("contenido/articulos/populares")
    suspend fun getArticulosPopulares(): Response<List<ArticuloResponse>>

    /**
     * Obtener artículos recientes
     * GET /api/v1/contenido/articulos/recientes
     */
    @GET("contenido/articulos/recientes")
    suspend fun getArticulosRecientes(): Response<List<ArticuloResponse>>

    /**
     * Obtener artículo por ID
     * GET /api/v1/contenido/articulos/{id}
     */
    @GET("contenido/articulos/{id}")
    suspend fun getArticuloById(@Path("id") id: Long): Response<ArticuloResponse>

    /**
     * Obtener artículos por categoría
     * GET /api/v1/contenido/articulos/categoria/{categoria}
     */
    @GET("contenido/articulos/categoria/{categoria}")
    suspend fun getArticulosPorCategoria(@Path("categoria") categoria: String): Response<List<ArticuloResponse>>

    /**
     * Buscar artículos
     * GET /api/v1/contenido/articulos/buscar?q={busqueda}
     */
    @GET("contenido/articulos/buscar")
    suspend fun buscarArticulos(@Query("q") busqueda: String): Response<List<ArticuloResponse>>

    /**
     * Incrementar vistas
     * POST /api/v1/contenido/articulos/{id}/vista
     */
    @POST("contenido/articulos/{id}/vista")
    suspend fun incrementarVistas(@Path("id") id: Long): Response<ArticuloResponse>

    /**
     * Dar like a artículo
     * POST /api/v1/contenido/articulos/{id}/like
     */
    @POST("contenido/articulos/{id}/like")
    suspend fun darLikeArticulo(@Path("id") id: Long): Response<ArticuloResponse>

    /**
     * Obtener comentarios por artículo
     * GET /api/v1/contenido/comentarios/articulo/{idArticulo}
     */
    @GET("contenido/comentarios/articulo/{idArticulo}")
    suspend fun getComentariosPorArticulo(@Path("idArticulo") idArticulo: Long): Response<List<ComentarioResponse>>

    /**
     * Crear comentario
     * POST /api/v1/contenido/comentarios
     */
    @POST("contenido/comentarios")
    suspend fun crearComentario(@Body request: ComentarioCreationRequest): Response<ComentarioResponse>
}

/**
 * Response de artículo
 */
data class ArticuloResponse(
    @SerializedName("idArticulo")
    val idArticulo: Long? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("tituloArticulo")
    val tituloArticulo: String? = null,
    @SerializedName("titulo")
    val titulo: String? = null,
    @SerializedName("contenidoArticulo")
    val contenidoArticulo: String? = null,
    @SerializedName("contenido")
    val contenido: String? = null,
    @SerializedName("resumenArticulo")
    val resumenArticulo: String? = null,
    @SerializedName("resumen")
    val resumen: String? = null,
    @SerializedName("imagenArticulo")
    val imagenArticulo: String? = null,
    @SerializedName("imagenUrl")
    val imagenUrl: String? = null,
    @SerializedName("imagenPreviewUrl")
    val imagenPreviewUrl: String? = null,
    @SerializedName("imagenMiniaturaUrl")
    val imagenMiniaturaUrl: String? = null,
    @SerializedName("imagenStorageUrl")
    val imagenStorageUrl: String? = null,
    @SerializedName("categoriaArticulo")
    val categoriaArticulo: String? = null,
    @SerializedName("categoria")
    val categoria: String? = null,
    @SerializedName("etiquetasArticulo")
    val etiquetasArticulo: String? = null,
    @SerializedName("tags")
    val tags: List<String>? = null,
    @SerializedName("autorArticulo")
    val autorArticulo: String? = null,
    @SerializedName("autor")
    val autor: String? = null,
    @SerializedName("fechaPublicacion")
    val fechaPublicacion: String? = null,
    @SerializedName("fechaPublicacionString")
    val fechaPublicacionString: String? = null,
    @SerializedName("fecha")
    val fecha: String? = null,
    @SerializedName("fechaActualizacion")
    val fechaActualizacion: String? = null,
    @SerializedName("estadoArticulo")
    val estadoArticulo: String? = null,
    @SerializedName("estado")
    val estado: String? = null,
    @SerializedName("vistasArticulo")
    val vistasArticulo: Int? = null,
    @SerializedName("likesArticulo")
    val likesArticulo: Int? = null,
    @SerializedName("compartidosArentario")
    val compartidosArentario: Int? = null,
    @SerializedName("tiempoLectura")
    val tiempoLectura: Int? = null,
    @SerializedName("esDestacado")
    val esDestacado: Boolean? = null,
    @SerializedName("destacado")
    val destacado: Boolean? = null,
    @SerializedName("esPremium")
    val esPremium: Boolean? = null,
    @SerializedName("activo")
    val activo: Boolean? = null,
    @SerializedName("enlaceExterno")
    val enlaceExterno: String? = null,
    @SerializedName("link")
    val link: String? = null
)

/**
 * Request para crear comentario
 */
data class ComentarioCreationRequest(
    val idArticulo: Long,
    val idUsuario: Long,
    val contenidoComentario: String,
    val idComentarioPadre: Long? = null
)

/**
 * Response de comentario
 */
data class ComentarioResponse(
    val idComentario: Long,
    val idArticulo: Long,
    val idUsuario: Long,
    val nombreUsuario: String,
    val contenidoComentario: String,
    val fechaComentario: String,
    val estadoComentario: String,
    val likesComentario: Int,
    val idComentarioPadre: Long?,
    val activo: Boolean
)