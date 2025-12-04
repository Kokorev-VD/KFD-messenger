package dev.kokorev.model.rq

data class AuthRequest(
    val login: String,
    val password: String
)