package com.example.levelupprueba.model.profile

sealed class ProfileStatus {
    object Idle: ProfileStatus()
    object Loading: ProfileStatus()
    object Loaded: ProfileStatus()
    object Editing: ProfileStatus()
    object Saving: ProfileStatus()
    object Saved: ProfileStatus()
    data class Error(val errorMessage: String): ProfileStatus()
    data class ValidationError(val fields: List<String>, val errorMessage: String) : ProfileStatus()
}