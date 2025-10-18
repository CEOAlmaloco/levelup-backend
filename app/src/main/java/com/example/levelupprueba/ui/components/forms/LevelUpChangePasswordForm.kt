package com.example.levelupprueba.ui.components.forms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.levelupprueba.model.password.PasswordStatus
import com.example.levelupprueba.model.usuario.isSuccess
import com.example.levelupprueba.ui.components.buttons.LevelUpButton
import com.example.levelupprueba.ui.components.buttons.LevelUpOutlinedButton
import com.example.levelupprueba.ui.components.cards.LevelUpCard
import com.example.levelupprueba.ui.components.inputs.LevelUpPasswordField
import com.example.levelupprueba.ui.components.inputs.errorSupportingText
import com.example.levelupprueba.ui.theme.LocalDimens
import com.example.levelupprueba.viewmodel.ChangePasswordViewModel

@Composable
fun LevelUpChangePasswordForm(
    email: String,
    viewModel: ChangePasswordViewModel,
    onCancelClick: () -> Unit,
    focusManager: FocusManager
){
    val estado by viewModel.estado.collectAsState()
    val status by viewModel.status.collectAsState()

    val focusRequesters = remember {
        List(3) { FocusRequester() }
    }

    val dimens = LocalDimens.current

    LevelUpCard(
        modifier = Modifier
            .padding(horizontal = dimens.screenPadding)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(dimens.sectionSpacing)
        ) {
            LevelUpFormSection(
                title = "Cambiar contraseña"
            ) {
                LevelUpPasswordField(
                    value = estado.actual.valor,
                    onValueChange = viewModel::onActualChange,
                    label = "Contraseña actual",
                    isError = estado.actual.error != null,
                    isSuccess = estado.actual.isSuccess,
                    supportingText = errorSupportingText(
                        error = estado.actual.error,
                        fontSize = dimens.captionSize
                    ),
                    focusRequester = focusRequesters[0],
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusRequesters[1].requestFocus() }
                    )
                )

                LevelUpPasswordField(
                    value = estado.nueva.valor,
                    onValueChange = viewModel::onNuevaChange,
                    label = "Contraseña nueva",
                    isError = estado.nueva.error != null,
                    isSuccess = estado.nueva.isSuccess,
                    supportingText = errorSupportingText(
                        error = estado.nueva.error,
                        fontSize = dimens.captionSize
                    ),
                    focusRequester = focusRequesters[1],
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusRequesters[2].requestFocus() }
                    )
                )

                LevelUpPasswordField(
                    value = estado.confirmar.valor,
                    onValueChange = viewModel::onConfirmarChange,
                    label = "Confirmar contraseña",
                    isError = estado.confirmar.error != null,
                    isSuccess = estado.confirmar.isSuccess,
                    supportingText = errorSupportingText(
                        error = estado.confirmar.error,
                        fontSize = dimens.captionSize
                    ),
                    focusRequester = focusRequesters[2],
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    )
                )

                when (status){
                    is PasswordStatus.Error -> {
                    }
                    is PasswordStatus.Success -> {

                    }
                    else -> {}
                }

                LevelUpButton(
                    text = if (status == PasswordStatus.Saving) "Guardando..." else "Guardar",
                    icon = Icons.Default.Lock,
                    onClick = { viewModel.cambiarPassword(email) },
                    enabled = viewModel.puedeGuardar() && status != PasswordStatus.Saving
                )

                LevelUpOutlinedButton(
                    text = "Cancelar",
                    icon = Icons.Default.Cancel,
                    onClick = onCancelClick
                )
            }
        }
    }

}