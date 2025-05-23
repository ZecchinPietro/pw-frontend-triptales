package com.example.pw_frontend_triptales.models

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String
)