package dev.kokorev.service.impl

import dev.kokorev.database.dao.MessageDao
import dev.kokorev.model.domain.Message
import dev.kokorev.service.MessageService
import org.koin.core.annotation.Single

@Single
class MessageServiceImpl(
    private val messageDao: MessageDao,
) : MessageService {
    override suspend fun save(
        text: String,
        authorId: Int,
        recipientId: Int
    ): Message {
        val entity = messageDao.create(text, authorId, recipientId)
        return Message(
            id = entity.id.value,
            text = entity.text,
            authorId = entity.creatorId,
            recipientId = entity.recipientId,
        )
    }

    override suspend fun findById(messageId: Int): Message? {
        val entity = messageDao.findById(messageId)
        return entity?.let {
            Message(
                id = entity.id.value,
                text = entity.text,
                authorId = entity.creatorId,
                recipientId = entity.recipientId,
            )
        }
    }

    override suspend fun findByRecipientId(recipientId: Int): List<Message> =
        messageDao.findByRecipientId(recipientId).map { entity ->
            Message(
                id = entity.id.value,
                text = entity.text,
                authorId = entity.creatorId,
                recipientId = entity.recipientId,
            )
        }

    override suspend fun findByAuthorId(authorId: Int): List<Message> =
        messageDao.findByRecipientId(authorId).map { entity ->
            Message(
                id = entity.id.value,
                text = entity.text,
                authorId = entity.creatorId,
                recipientId = entity.recipientId,
            )
        }
}