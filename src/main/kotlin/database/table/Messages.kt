package dev.kokorev.database.table

import dev.kokorev.database.tables.Users
import org.jetbrains.exposed.dao.id.IntIdTable

object Messages: IntIdTable() {
    val text = varchar("text", 255)
    val creatorId = integer("creatorId").references(Users.id)
    val recipientId = integer("recipientId").references(Users.id)
}