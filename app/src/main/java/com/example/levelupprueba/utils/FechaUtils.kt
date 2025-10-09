package com.example.levelupprueba.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Funcion que formatea fecha a dd/MM/yyyy
 *
 * @param fecha Fecha en formato ISO
 *
 * Retorna fecha en formato dd/MM/yyyy
 */
@RequiresApi(Build.VERSION_CODES.O)
fun formatFecha(fecha: String): String {
    return try {
        val date = LocalDate.parse(fecha, DateTimeFormatter.ISO_LOCAL_DATE)
        date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    } catch (e: Exception) {
        fecha
    }
}