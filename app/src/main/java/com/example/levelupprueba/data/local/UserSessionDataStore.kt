package com.example.levelupprueba.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.levelupprueba.model.auth.UserSession
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.userSessionDataStore by preferencesDataStore(name = "user_session")

object UserSessionKeys {
    val DISPLAY_NAME = stringPreferencesKey("display_name")
    val LOGIN_AT = longPreferencesKey("login_at")
    val USER_ID = longPreferencesKey("user_id")
    val ROLE = stringPreferencesKey("role")
    val ACCESS_TOKEN = stringPreferencesKey("access_token")
    val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    val EXPIRES_IN = longPreferencesKey("expires_in")
    val EMAIL = stringPreferencesKey("email")
    val NOMBRE = stringPreferencesKey("nombre")
    val APELLIDOS = stringPreferencesKey("apellidos")
    val TIPO_USUARIO = stringPreferencesKey("tipo_usuario")
    val DESCUENTO_DUOC = booleanPreferencesKey("descuento_duoc")
    val REGION = stringPreferencesKey("region")
    val COMUNA = stringPreferencesKey("comuna")
}

suspend fun saveUserSession(context: Context, session: UserSession) {
    context.userSessionDataStore.edit { prefs ->
        prefs[UserSessionKeys.DISPLAY_NAME] = session.displayName
        prefs[UserSessionKeys.LOGIN_AT] = session.loginAt
        prefs[UserSessionKeys.USER_ID] = session.userId
        prefs[UserSessionKeys.ROLE] = session.role
        prefs[UserSessionKeys.ACCESS_TOKEN] = session.accessToken
        prefs[UserSessionKeys.REFRESH_TOKEN] = session.refreshToken
        prefs[UserSessionKeys.EXPIRES_IN] = session.expiresIn
        prefs[UserSessionKeys.EMAIL] = session.email
        prefs[UserSessionKeys.NOMBRE] = session.nombre
        prefs[UserSessionKeys.APELLIDOS] = session.apellidos
        prefs[UserSessionKeys.TIPO_USUARIO] = session.tipoUsuario
        session.descuentoDuoc?.let { prefs[UserSessionKeys.DESCUENTO_DUOC] = it }
        session.region?.let { prefs[UserSessionKeys.REGION] = it }
        session.comuna?.let { prefs[UserSessionKeys.COMUNA] = it }
    }
}

fun getUserSessionFlow(context: Context) = context.userSessionDataStore.data.map { prefs ->
    UserSession(
        displayName = prefs[UserSessionKeys.DISPLAY_NAME] ?: "",
        loginAt = prefs[UserSessionKeys.LOGIN_AT] ?: 0L,
        userId = prefs[UserSessionKeys.USER_ID] ?: 0L,
        role = prefs[UserSessionKeys.ROLE] ?: "",
        accessToken = prefs[UserSessionKeys.ACCESS_TOKEN] ?: "",
        refreshToken = prefs[UserSessionKeys.REFRESH_TOKEN] ?: "",
        expiresIn = prefs[UserSessionKeys.EXPIRES_IN] ?: 0L,
        email = prefs[UserSessionKeys.EMAIL] ?: "",
        nombre = prefs[UserSessionKeys.NOMBRE] ?: "",
        apellidos = prefs[UserSessionKeys.APELLIDOS] ?: "",
        tipoUsuario = prefs[UserSessionKeys.TIPO_USUARIO] ?: "",
        descuentoDuoc = prefs[UserSessionKeys.DESCUENTO_DUOC],
        region = prefs[UserSessionKeys.REGION],
        comuna = prefs[UserSessionKeys.COMUNA]
    )
}

suspend fun getUserSession(context: Context): UserSession? {
    val prefs = context.userSessionDataStore.data.first()
    val accessToken = prefs[UserSessionKeys.ACCESS_TOKEN]
    return if (accessToken != null && accessToken.isNotEmpty()) {
        UserSession(
            displayName = prefs[UserSessionKeys.DISPLAY_NAME] ?: "",
            loginAt = prefs[UserSessionKeys.LOGIN_AT] ?: 0L,
            userId = prefs[UserSessionKeys.USER_ID] ?: 0L,
            role = prefs[UserSessionKeys.ROLE] ?: "",
            accessToken = accessToken,
            refreshToken = prefs[UserSessionKeys.REFRESH_TOKEN] ?: "",
            expiresIn = prefs[UserSessionKeys.EXPIRES_IN] ?: 0L,
            email = prefs[UserSessionKeys.EMAIL] ?: "",
            nombre = prefs[UserSessionKeys.NOMBRE] ?: "",
            apellidos = prefs[UserSessionKeys.APELLIDOS] ?: "",
            tipoUsuario = prefs[UserSessionKeys.TIPO_USUARIO] ?: "",
            descuentoDuoc = prefs[UserSessionKeys.DESCUENTO_DUOC],
            region = prefs[UserSessionKeys.REGION],
            comuna = prefs[UserSessionKeys.COMUNA]
        )
    } else {
        null
    }
}

suspend fun clearUserSession(context: Context) {
    context.userSessionDataStore.edit { prefs ->
        prefs.clear()
    }
}