package com.example.levelupprueba.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.levelupprueba.model.auth.UserSession
import kotlinx.coroutines.flow.map

val Context.userSessionDataStore by preferencesDataStore(name = "user_session")

object UserSessionKeys{
    val DISPLAY_NAME = stringPreferencesKey("display_name")
    val LOGIN_AT = longPreferencesKey("login_at")
    val USER_ID = stringPreferencesKey("user_id")
    val ROLE = stringPreferencesKey("role")
}

suspend fun saveUserSession(context: Context, session: UserSession){
    context.userSessionDataStore.edit { prefs ->
        prefs[UserSessionKeys.DISPLAY_NAME] = session.displayName
        prefs[UserSessionKeys.LOGIN_AT] = session.loginAt
        prefs[UserSessionKeys.USER_ID] = session.userId
        prefs[UserSessionKeys.ROLE] = session.role
    }
}

fun getUserSessionFlow(context: Context) = context.userSessionDataStore.data.map { prefs ->
    UserSession(
        displayName = prefs[UserSessionKeys.DISPLAY_NAME] ?: "",
        loginAt = prefs[UserSessionKeys.LOGIN_AT] ?: 0L,
        userId = prefs[UserSessionKeys.USER_ID] ?: "",
        role = prefs[UserSessionKeys.ROLE] ?: ""
    )
}