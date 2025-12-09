package dev.kokorev.database.entity

import dev.kokorev.database.table.Messages
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class MessageEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<MessageEntity>(Messages)

    var text by Messages.text
    var creatorId by Messages.creatorId
    var recipientId by Messages.recipientId
}