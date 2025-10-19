package com.example.levelupprueba.ui.components.forms

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.levelupprueba.model.profile.ProfileStatus
import com.example.levelupprueba.ui.components.buttons.LevelUpButton
import com.example.levelupprueba.ui.components.buttons.LevelUpOutlinedButton
import com.example.levelupprueba.ui.components.cards.LevelUpCard
import com.example.levelupprueba.ui.components.dropdown.LevelUpDropdownMenu
import com.example.levelupprueba.ui.components.inputs.LevelUpFechaNacimientoField
import com.example.levelupprueba.ui.components.inputs.LevelUpOutlinedTextField
import com.example.levelupprueba.ui.components.inputs.errorSupportingText
import com.example.levelupprueba.ui.screens.profile.PerfilEditable
import com.example.levelupprueba.ui.theme.LocalDimens
import com.example.levelupprueba.viewmodel.UbicacionViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LevelUpProfileEditForm(
    perfilEditable: PerfilEditable,
    onPerfilChange: (PerfilEditable) -> Unit,
    ubicacionViewModel: UbicacionViewModel,
    isSaveEnabled: Boolean,
    profileStatus: ProfileStatus,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
    focusRequesters: List<FocusRequester>,
    focusManager: FocusManager
) {
    val dimens = LocalDimens.current
    val errores = perfilEditable.validar()
    val nombreError = errores["nombre"]
    val apellidosError = errores["apellidos"]
    val telefonoError = errores["telefono"]
    val fechaNacimientoError = errores["fechaNacimiento"]
    val regionError = errores["region"]
    val comunaError = errores["comuna"]
    val direccionError = errores["direccion"]

    LevelUpCard(
        modifier = Modifier
            .padding(horizontal = dimens.screenPadding)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(dimens.sectionSpacing)
        ) {
            LevelUpFormSection(
                title = "Editar perfil"
            ) {
                LevelUpOutlinedTextField(
                    value = perfilEditable.nombre,
                    onValueChange = { onPerfilChange(perfilEditable.copy(nombre = it)) },
                    label = "Nombre",
                    isError = nombreError != null,
                    isSuccess = nombreError == null,
                    supportingText = errorSupportingText(
                        fontSize = dimens.captionSize,
                        error = nombreError
                    ),
                    focusRequester = focusRequesters[0],
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusRequesters[1].requestFocus() }
                    )
                )

                LevelUpOutlinedTextField(
                    value = perfilEditable.apellidos,
                    onValueChange = { onPerfilChange(perfilEditable.copy(apellidos = it)) },
                    label = "Apellidos",
                    isError = apellidosError != null,
                    isSuccess = apellidosError == null,
                    supportingText = errorSupportingText(
                        fontSize = dimens.captionSize,
                        error = apellidosError
                    ),
                    focusRequester = focusRequesters[1],
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusRequesters[2].requestFocus() }
                    )
                )

                LevelUpOutlinedTextField(
                    value = perfilEditable.telefono,
                    onValueChange = { nuevoNumero ->
                        if (nuevoNumero.all { it.isDigit() }) {
                            onPerfilChange(perfilEditable.copy(telefono = nuevoNumero))
                        }
                    },
                    label = "Teléfono (Opcional)",
                    isError = telefonoError != null,
                    isSuccess = telefonoError == null && perfilEditable.telefono.isNotBlank(),
                    supportingText = errorSupportingText(
                        fontSize = dimens.captionSize,
                        error = telefonoError
                    ),
                    focusRequester = focusRequesters[2],
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusRequesters[3].requestFocus() }
                    )
                )

                LevelUpFechaNacimientoField(
                    fechaNacimiento = perfilEditable.fechaNacimiento,
                    onFechaNacimientoChange = { onPerfilChange(perfilEditable.copy(fechaNacimiento = it)) },
                    isError = fechaNacimientoError != null,
                    isSuccess = fechaNacimientoError == null && perfilEditable.fechaNacimiento.isNotBlank(),
                    supportingText = errorSupportingText(
                        fontSize = dimens.captionSize,
                        error = fechaNacimientoError
                    ),
                    focusRequester = focusRequesters[3]
                )

                LevelUpDropdownMenu(
                    label = "Región",
                    options = ubicacionViewModel.regiones.map { it.nombre },
                    selectedOption = perfilEditable.region,
                    onOptionSelected = { nombre ->
                        onPerfilChange(perfilEditable.copy(region = nombre, comuna = ""))
                        ubicacionViewModel.selectRegion(nombre)
                    },
                    isError = regionError != null,
                    isSuccess = regionError == null && perfilEditable.region.isNotBlank(),
                    supportingText = errorSupportingText(
                        fontSize = dimens.captionSize,
                        error = regionError
                    )
                )

                LevelUpDropdownMenu(
                    label = "Comuna",
                    options = ubicacionViewModel.selectedRegion?.comunas?.map { it.nombre }
                        ?: emptyList(),
                    selectedOption = perfilEditable.comuna,
                    onOptionSelected = { nombre ->
                        onPerfilChange(perfilEditable.copy(comuna = nombre))
                    },
                    isError = comunaError != null,
                    isSuccess = comunaError == null,
                    supportingText = errorSupportingText(
                        fontSize = dimens.captionSize,
                        error = comunaError
                    ),
                    enabled = ubicacionViewModel.selectedRegion != null,
                    placeholder = if (ubicacionViewModel.selectedRegion == null) "Selecciona región primero" else null
                )

                LevelUpOutlinedTextField(
                    value = perfilEditable.direccion,
                    onValueChange = { onPerfilChange(perfilEditable.copy(direccion = it)) },
                    label = "Dirección (Opcional)",
                    isError = direccionError != null,
                    isSuccess = direccionError == null && perfilEditable.direccion.isNotBlank(),
                    supportingText = errorSupportingText(
                        fontSize = dimens.captionSize,
                        error = direccionError
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    )
                )
            }

            LevelUpButton(
                text = if (profileStatus == ProfileStatus.Saving) "Guardando..." else "Guardar",
                icon = Icons.Default.Edit,
                onClick = onSaveClick,
                enabled = isSaveEnabled && profileStatus != ProfileStatus.Saving,
                modifier = Modifier
                    .fillMaxWidth()
            )

            LevelUpOutlinedButton(
                text = "Cancelar",
                icon = Icons.Default.Cancel,
                onClick = onCancelClick,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}
