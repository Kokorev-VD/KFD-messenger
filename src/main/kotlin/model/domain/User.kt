package dev.kokorev.model.domain

data class User(
    val id: Int,
    val login: String,
    val hash: String,
    val salt: String,
)
