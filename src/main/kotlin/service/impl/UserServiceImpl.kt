package dev.kokorev.service.impl

import dev.kokorev.database.tables.Users
import dev.kokorev.model.domain.CreateUser
import dev.kokorev.model.domain.User
import dev.kokorev.service.UserService
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class UserServiceImpl: UserService {
    override suspend fun create(user: CreateUser): User = newSuspendedTransaction {
        val rawUser = Users.insert {
            it[login] = user.login
            it[hash] = user.hash
            it[salt] = user.salt
        }
        User(rawUser[Users.id].value, rawUser[Users.login], rawUser[Users.hash], rawUser[Users.salt])
    }

    override suspend fun get(login: String): User? = newSuspendedTransaction {
        Users.selectAll()
            .where { Users.login eq login }
            .map { User(it[Users.id].value, it[Users.login], it[Users.hash], it[Users.salt]) }
            .singleOrNull()
    }
}