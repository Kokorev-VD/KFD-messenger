package dev.kokorev.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object Users: IntIdTable() {
    val login = varchar("login", 50)
    val hash = varchar("hash", 255)
    val salt = varchar("salt", 255)
}