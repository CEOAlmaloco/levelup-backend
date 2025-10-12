package com.example.levelupprueba.ui.components.inputs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.levelupprueba.ui.theme.Dimens
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * Funcion que permite reutilizar el campo de texto para la fecha de nacimiento
 *
 * @param fechaNacimiento Valor de la fecha actual
 * @param onFechaNacimientoChange Callback para actualizar la fecha al seleccionar
 * @param isError Muestra un indicador de error en el campo
 * @param isSuccess Muestra un indicador de exito en el campo
 * @param supportingText Mensaje de ayuda o error debajo del campo
 * @param modifier Modificador para personalizar el campo
 */
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LevelUpFechaNacimientoField(
    fechaNacimiento: String,                    // Valor de la fecha actual (lo muestra el campo)
    onFechaNacimientoChange: (String) -> Unit,  // Callback para actualizar la fecha al seleccionar
    isError: Boolean = false,                   // Indica si el campo está en estado de error
    isSuccess: Boolean = false,                 // Indica si el campo está en estado de éxito
    supportingText: (@Composable (() -> Unit))? = null, // Mensaje de ayuda o error debajo del campo
    modifier: Modifier = Modifier,                        // Modificador para personalizar el campo (por ej. ancho)
    dimens: Dimens
) {
    // Estado local para mostrar/ocultar el DatePicker
    var showDatePicker by remember { mutableStateOf(false) }

    // Campo de texto donde se muestra la fecha
    OutlinedTextField(
        value = fechaNacimiento,                // Muestra la fecha actual
        onValueChange = {},                     // Deshabilitado, solo se cambia por el DatePicker
        colors = levelUpTextFieldColors(isSuccess = isSuccess),                        // Aplica los colores personalizados
        label = {                               // Etiqueta del campo
            Text(
                text = "Fecha de Nacimiento",
                fontSize = dimens.bodySize)
            },
        isError = isError,                      // Marca el campo como error si corresponde
        readOnly = true,
        supportingText = supportingText,        // Muestra texto de ayuda/error debajo del campo         // El usuario no puede editar manualmente
        trailingIcon = {                        // Ícono de calendario para abrir el selector
            IconButton(
                onClick = {
                    showDatePicker = true
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.CalendarToday,
                    contentDescription = "Seleccionar Fecha",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(dimens.iconSize)
                )
            }
        },
        modifier = modifier
            .fillMaxWidth()
    )

    // Si hay que mostrar el DatePicker (cuando se toca el ícono)
    if (showDatePicker) {
        // Intenta parsear la fecha actual para mostrarla seleccionada en el DatePicker
        val initialDate = runCatching {
            LocalDate.parse(fechaNacimiento, DateTimeFormatter.ISO_LOCAL_DATE)
        }.getOrNull()

        val todayMillis = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

        // Estado del DatePicker, define la fecha inicial seleccionada si existe
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = initialDate?.atStartOfDay(ZoneId.systemDefault())
                ?.toInstant()
                ?.toEpochMilli(),
            // Permite seleccionar solo fechas anteriores a la actual
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    return utcTimeMillis <= todayMillis
                }
            }
        )

        // Diálogo del DatePicker (Material3)
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false }, // Cierra el diálogo si se cancela
            confirmButton = { // Botón "Aceptar"
                TextButton(
                    onClick = {
                        val millis = datePickerState.selectedDateMillis
                        if (millis != null) {
                            // Formatea la fecha seleccionada a dd/MM/yyyy
                            val formatter = DateTimeFormatter.ISO_LOCAL_DATE
                            val selectedDate = Instant.ofEpochMilli(millis)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                                .format(formatter)
                            onFechaNacimientoChange(selectedDate) // Actualiza el campo externo
                        }
                        showDatePicker = false // Cierra el diálogo
                    }
                ) {
                    Text(
                        "Aceptar",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            },
            dismissButton = { // Botón "Cancelar"
                TextButton(onClick = { showDatePicker = false }) {
                    Text(
                        "Cancelar",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        ) {
            DatePicker(state = datePickerState) // Componente calendario
        }
    }
}