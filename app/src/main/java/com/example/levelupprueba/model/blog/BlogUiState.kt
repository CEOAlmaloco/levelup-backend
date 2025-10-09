package com.example.levelupprueba.model.blog

data class BlogUiState(
    val blogs: List<Blog> = emptyList(),
    val filtroActivo: String = "todas",
    val isLoading: Boolean = false,
    val error: String? = null
)

enum class FiltroBlog(val valor: String, val etiqueta: String) {
    TODAS("todas", "Todas"),
    NOTICIAS("noticias", "Noticias"),
    GUIAS("guias", "Guías"),
    COMUNIDAD("comunidad", "Comunidad")
}