package dev.kokorev.service

import dev.kokorev.model.domain.Message

interface MessageService {
    suspend fun save(text: String, authorId: Int, recipientId: Int): Message
    suspend fun findById(messageId: Int): Message?
    suspend fun findByRecipientId(recipientId: Int): List<Message>
    suspend fun findByAuthorId(authorId: Int): List<Message>
}