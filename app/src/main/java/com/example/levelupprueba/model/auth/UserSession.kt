package com.example.levelupprueba.model.auth

data class UserSession (
    val displayName: String,
    val loginAt: Long,
    val userId: String,
    val role: String
)