package dev.kokorev.model.rq

data class MessageRequest(
    val text: String,
    val recipientId: Int,
)