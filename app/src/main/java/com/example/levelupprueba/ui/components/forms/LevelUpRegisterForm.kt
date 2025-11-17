package com.example.levelupprueba.ui.components.forms

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.levelupprueba.model.usuario.UsuarioUiState
import com.example.levelupprueba.model.usuario.isSuccess
import com.example.levelupprueba.ui.components.dropdown.LevelUpDropdownMenu
import com.example.levelupprueba.ui.components.inputs.LevelUpFechaNacimientoField
import com.example.levelupprueba.ui.components.inputs.LevelUpPasswordField
import com.example.levelupprueba.ui.components.inputs.LevelUpSwitchField
import com.example.levelupprueba.ui.components.inputs.LevelUpOutlinedTextField
import com.example.levelupprueba.ui.components.inputs.errorSupportingText
import com.example.levelupprueba.ui.components.inputs.supportingTextOrError
import com.example.levelupprueba.viewmodel.UsuarioViewModel
import com.example.levelupprueba.viewmodel.UbicacionViewModel
import com.example.levelupprueba.ui.theme.Dimens
import com.example.levelupprueba.utils.formatFecha

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LevelUpRegisterForm(
    estado: UsuarioUiState,
    usuarioViewModel: UsuarioViewModel,
    ubicacionViewModel: UbicacionViewModel,
    dimens: Dimens
) {
    val focusManager = LocalFocusManager.current

    // FocusRequesters
    val runFocus = remember { FocusRequester() }
    val nombreFocus = remember { FocusRequester() }
    val apellidosFocus = remember { FocusRequester() }
    val fechaNacimientoFocus = remember { FocusRequester() }
    val emailFocus = remember { FocusRequester() }
    val telefonoFocus = remember { FocusRequester() }
    val direccionFocus = remember { FocusRequester() }
    val regionFocus = remember { FocusRequester() }
    val comunaFocus = remember { FocusRequester() }
    val passwordFocus = remember { FocusRequester() }
    val confirmPasswordFocus = remember { FocusRequester() }

    Column {
        LevelUpFormSection(title = "Datos Personales", dimens = dimens) {
            LevelUpOutlinedTextField(
                value = estado.run.valor,
                onValueChange = usuarioViewModel::onRunChange,
                label = "RUN",
                isError = estado.run.error != null,
                isSuccess = estado.run.isSuccess,
                supportingText = supportingTextOrError(
                    error = estado.run.error,
                    helperText = "Formato: 12345678-9 o 12345678-K",
                    isSuccess = estado.run.isSuccess,
                    fontSize = dimens.captionSize,
                ),
                modifier = Modifier.focusRequester(runFocus),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { nombreFocus.requestFocus() }
                ),
                dimens = dimens
            )
            LevelUpOutlinedTextField(
                value = estado.nombre.valor,
                onValueChange = usuarioViewModel::onNombreChange,
                label = "Nombre",
                isError = estado.nombre.error != null,
                isSuccess = estado.nombre.isSuccess,
                supportingText = errorSupportingText(
                    fontSize = dimens.captionSize,
                    error = estado.nombre.error
                ),
                modifier = Modifier.focusRequester(nombreFocus),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { apellidosFocus.requestFocus() }
                ),
                dimens = dimens
            )
            LevelUpOutlinedTextField(
                value = estado.apellidos.valor,
                onValueChange = usuarioViewModel::onApellidosChange,
                label = "Apellidos",
                isError = estado.apellidos.error != null,
                isSuccess = estado.apellidos.isSuccess,
                supportingText = errorSupportingText(
                    fontSize = dimens.captionSize,
                    error = estado.apellidos.error
                ),
                modifier = Modifier.focusRequester(apellidosFocus),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { fechaNacimientoFocus.requestFocus() }
                ),
                dimens = dimens
            )
            LevelUpFechaNacimientoField(
                fechaNacimiento = formatFecha(estado.fechaNacimiento.valor),
                onFechaNacimientoChange = usuarioViewModel::onFechaNacimientoChange,
                isError = estado.fechaNacimiento.error != null,
                isSuccess = estado.fechaNacimiento.isSuccess,
                supportingText = errorSupportingText(
                    fontSize = dimens.captionSize,
                    error = estado.fechaNacimiento.error
                ),
                modifier = Modifier
                    .focusRequester(fechaNacimientoFocus),
                dimens = dimens
            )
        }

        Spacer(modifier = Modifier.height(dimens.sectionSpacing))

        LevelUpFormSection(title = "Datos de contacto", dimens = dimens) {
            LevelUpOutlinedTextField(
                value = estado.email.valor,
                onValueChange = usuarioViewModel::onEmailChange,
                label = "Correo Electrónico",
                isError = estado.email.error != null,
                isSuccess = estado.email.isSuccess,
                supportingText = supportingTextOrError(
                    error = estado.email.error,
                    helperText = "@gmail.com o @duoc.cl",
                    isSuccess = estado.email.isSuccess,
                    fontSize = dimens.captionSize,
                ),
                modifier = Modifier.focusRequester(emailFocus),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { telefonoFocus.requestFocus() }
                ),
                dimens = dimens
            )
            LevelUpOutlinedTextField(
                value = estado.telefono.valor,
                onValueChange = { nuevoNumero ->
                    if (nuevoNumero.all { it.isDigit() }) {
                        usuarioViewModel.onTelefonoChange(nuevoNumero)
                    }
                },
                label = "Teléfono (Opcional)",
                isError = estado.telefono.error != null,
                isSuccess = estado.telefono.isSuccess,
                supportingText = errorSupportingText(
                    fontSize = dimens.captionSize,
                    error = estado.telefono.error
                ),
                modifier = Modifier.focusRequester(telefonoFocus),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { direccionFocus.requestFocus() }
                ),
                dimens = dimens
            )
            LevelUpOutlinedTextField(
                value = estado.direccion.valor,
                onValueChange = usuarioViewModel::onDireccionChange,
                label = "Dirección (Opcional)",
                isError = estado.direccion.error != null,
                isSuccess = estado.direccion.isSuccess,
                supportingText = errorSupportingText(
                    fontSize = dimens.captionSize,
                    error = estado.direccion.error
                ),
                modifier = Modifier.focusRequester(direccionFocus),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { regionFocus.requestFocus() }
                ),
                dimens = dimens
            )
        }

        Spacer(modifier = Modifier.height(dimens.sectionSpacing))

        LevelUpFormSection(title = "Ubicación", dimens = dimens) {
            LevelUpDropdownMenu(
                label = "Región",
                options = ubicacionViewModel.regiones.map { it.nombre },
                selectedOption = ubicacionViewModel.selectedRegion?.nombre,
                onOptionSelected = { nombre ->
                    ubicacionViewModel.selectRegion(nombre)
                    usuarioViewModel.onRegionChange(nombre)
                    usuarioViewModel.onComunaChange("")
                },
                isError = estado.region.error != null,
                isSuccess = estado.region.isSuccess,
                supportingText = errorSupportingText(
                    fontSize = dimens.captionSize,
                    error = estado.region.error
                ),
                modifier = Modifier.focusRequester(regionFocus),
                dimens = dimens
            )

            LevelUpDropdownMenu(
                label = "Comuna",
                options = ubicacionViewModel.selectedRegion?.comunas?.map { it.nombre }
                    ?: emptyList(),
                selectedOption = ubicacionViewModel.selectedComuna?.nombre,
                onOptionSelected = { nombre ->
                    ubicacionViewModel.selectComuna(nombre)
                    usuarioViewModel.onComunaChange(nombre)
                },
                isError = estado.comuna.error != null,
                isSuccess = estado.comuna.isSuccess,
                supportingText = errorSupportingText(
                    fontSize = dimens.captionSize,
                    error = estado.comuna.error
                ),
                enabled = ubicacionViewModel.selectedRegion != null,
                placeholder = if (ubicacionViewModel.selectedRegion == null) "Selecciona región primero" else null,
                modifier = Modifier.focusRequester(comunaFocus),
                dimens = dimens
            )
        }

        Spacer(modifier = Modifier.height(dimens.sectionSpacing))

        LevelUpFormSection(title = "Código de Referido (Opcional)", dimens = dimens) {
            LevelUpOutlinedTextField(// campo para codigo de referido
                value = estado.codigoReferido.valor, //valor del campo
                onValueChange = usuarioViewModel::onCodigoReferidoChange,
                label = "Código de Referido", 
                isError = estado.codigoReferido.error != null, 
                isSuccess = estado.codigoReferido.isSuccess, 
                supportingText = supportingTextOrError(//texto de ayuda o error
                    error = estado.codigoReferido.error, 
                    helperText = "Ingresa el código de un amigo para obtener puntos",
                    isSuccess = estado.codigoReferido.isSuccess,
                    fontSize = dimens.captionSize, //tamaño del texto
                ),
                modifier = Modifier.focusRequester(passwordFocus), //solicitar foco al campo
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, //tipo de teclado
                    imeAction = ImeAction.Next //accion al presionar enter
                ),
                keyboardActions = KeyboardActions(
                    onNext = { confirmPasswordFocus.requestFocus() } //solicitar foco al campo
                ),
                dimens = dimens //dimensiones del campo
            )
        }

        Spacer(modifier = Modifier.height(dimens.sectionSpacing)) //espacio entre secciones

        LevelUpFormSection(title = "Seguridad", dimens = dimens) {
            LevelUpPasswordField(
                value = estado.password.valor,
                onValueChange = usuarioViewModel::onPasswordChange,
                label = "Contraseña",
                isError = estado.password.error != null,
                isSuccess = estado.password.isSuccess,
                supportingText = supportingTextOrError(
                    error = estado.password.error,
                    helperText = "La contraseña debe tener entre 4 y 10 caracteres.",
                    isSuccess = estado.password.isSuccess,
                    fontSize = dimens.captionSize,
                ),
                modifier = Modifier.focusRequester(passwordFocus),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { confirmPasswordFocus.requestFocus() }
                ),
                dimens = dimens
            )
            LevelUpPasswordField(
                value = estado.confirmPassword.valor,
                onValueChange = usuarioViewModel::onConfirmPasswordChange,
                label = "Confirmar Contraseña",
                isError = estado.confirmPassword.error != null,
                isSuccess = estado.confirmPassword.isSuccess,
                supportingText = errorSupportingText(
                    error = estado.confirmPassword.error,
                    fontSize = dimens.captionSize
                ),
                modifier = Modifier.focusRequester(confirmPasswordFocus),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
                dimens = dimens
            )

            LevelUpSwitchField(
                checked = estado.terminos.valor == "true",
                onCheckedChange = {
                    usuarioViewModel.onTerminosChange(it)
                },
                label = "Acepto los términos y condiciones",
                error = estado.terminos.error,
                dimens = dimens
            )
        }
    }
}