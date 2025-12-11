package com.example.levelupprueba.utils

import android.os.Build
import androidx.annotation.RequiresApi
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class UtilsTest {

    // --- Tests de formato de dinero ---

    @Test
    fun `formatCLP formatea sin decimales y con separador de miles`() {
        // Formatea un monto en CLP
        val resultado = formatCLP(1000.0)

        // Verifica que contenga el separador de miles esperado
        Assertions.assertTrue(resultado.contains("1.000"))

        // Verifica que no incluya decimales
        Assertions.assertFalse(resultado.contains(","))
    }

    // --- Tests de formato de fechas ---

    @Test
    @RequiresApi(Build.VERSION_CODES.O)
    fun `formatFecha convierte de ISO a ddMMyyyy`() {
        // Formatea una fecha en formato ISO (ddMMyyyy)
        val resultado = formatFecha("2025-11-04")

        // Verifica que el formato resultante sea ddMMyyyy
        Assertions.assertEquals("04/11/2025", resultado)
    }

    @Test
    @RequiresApi(Build.VERSION_CODES.O)
    fun `formatFecha devuelve la misma cadena cuando el formato es invalido`() {
        // Intenta formatear una cadena que no es fecha
        val resultado = formatFecha("no-es-una-fecha")

        // Verifica que se devuelva el mismo valor al no poder parsear
        Assertions.assertEquals("no-es-una-fecha", resultado)
    }
}