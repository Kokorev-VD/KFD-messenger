package dev.kokorev.database.dao.impl

import dev.kokorev.database.dao.MessageDao
import dev.kokorev.database.dao.UserDao
import dev.kokorev.database.entity.MessageEntity
import dev.kokorev.database.table.Messages
import dev.kokorev.model.exception.ResourceNotFoundException
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.koin.core.annotation.Single

@Single
class MessageDaoImpl(
    private val userDao: UserDao,
): MessageDao {
    override suspend fun create(text: String, creatorId: Int, recipientId: Int): MessageEntity {
        userDao.findById(creatorId) ?: throw ResourceNotFoundException("Сущность пользователя с id = $creatorId")
        userDao.findById(recipientId) ?: throw ResourceNotFoundException("Сущность пользователя с id = $recipientId")
        return newSuspendedTransaction {
            MessageEntity.new {
                this.text = text
                this.creatorId = creatorId
                this.recipientId = recipientId
            }
        }
    }

    override suspend fun findById(id: Int): MessageEntity? = newSuspendedTransaction {
        MessageEntity.findById(id)
    }

    override suspend fun findByRecipientId(recipientId: Int): List<MessageEntity> = newSuspendedTransaction {
        MessageEntity.find {
            Messages.recipientId eq recipientId
        }.toList()
    }

    override suspend fun findByAuthorId(authorId: Int): List<MessageEntity> = newSuspendedTransaction {
        MessageEntity.find {
            Messages.creatorId eq authorId
        }.toList()
    }
}