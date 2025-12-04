package dev.kokorev.model.domain

data class CreateUser(
    val login: String,
    val hash: String,
    val salt: String,
)