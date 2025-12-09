package dev.kokorev.model.domain

data class Message(
    val id: Int,
    val text: String,
    val authorId: Int,
    val recipientId: Int,
)