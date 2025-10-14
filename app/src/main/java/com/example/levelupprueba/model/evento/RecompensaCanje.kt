package com.example.levelupprueba.model.evento

/*lo mismo que js 
*/
data class RecompensaCanje(
    val id: String = "",
    val titulo: String = "",
    val descripcion: String = "",
    val costo: Int = 0,
    val tipo: TipoRecompensa = TipoRecompensa.DESCUENTO,//si tiene recompenza se llama al tipo de recompensa descuento
    val disponible: Boolean = true
)

enum class TipoRecompensa {
    DESCUENTO,
    PRODUCTO,
    GIFT_CARD
}

