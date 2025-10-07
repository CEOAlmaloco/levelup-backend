package com.example.levelupprueba.ui.components.inputs

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.levelupprueba.model.FieldErrors
import com.example.levelupprueba.ui.components.switches.levelUpSwitchColors

/**
 * Funcion que permite reutilizar el Switch LevelUp para opciones o confirmaciones
 *
 * @param checked Valor actual del switch
 * @param onCheckedChange Callback para actualizar el valor del switch al seleccionar
 * @param label Etiqueta del switch
 * @param error Error que se mostrara debajo del campo
 * @param modifier Modificador para personalizar el campo
 * @param labelSpacing Espaciado entre la etiqueta y el switch
 * @param errorFontSize Tamaño de fuente del mensaje de error
 */
@Composable
fun LevelUpSwitchField(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String,
    error: FieldErrors?,
    modifier: Modifier = Modifier,
    labelSpacing: Dp = 8.dp,
    errorFontSize: TextUnit = 12.sp
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = levelUpSwitchColors()
        )
        Spacer(Modifier.width(labelSpacing))
        Text(label)
    }
    if (error != null){
        Text(
            error.mensaje(),
            color = MaterialTheme.colorScheme.error,
            fontSize = errorFontSize
        )
    }
}