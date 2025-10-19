package com.example.levelupprueba.utils

import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

private val clpFormatter: NumberFormat = NumberFormat
    .getCurrencyInstance(Locale("es", "CL"))
    .apply {
        maximumFractionDigits = 0
        currency = Currency.getInstance("CLP")
    }
fun formatCLP(valor: Double): String = clpFormatter.format(valor)
