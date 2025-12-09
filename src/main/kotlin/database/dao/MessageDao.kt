package dev.kokorev.database.dao

import dev.kokorev.database.entity.MessageEntity

interface MessageDao {
    suspend fun create(text: String, creatorId: Int, recipientId: Int): MessageEntity
    suspend fun findById(id: Int): MessageEntity?
    suspend fun findByRecipientId(recipientId: Int): List<MessageEntity>
    suspend fun findByAuthorId(authorId: Int): List<MessageEntity>
}