package dev.kokorev.database.dao.impl

import dev.kokorev.database.dao.UserDao
import dev.kokorev.database.entity.UserEntity
import dev.kokorev.database.tables.Users
import dev.kokorev.model.domain.CreateUser
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.koin.core.annotation.Single

@Single
class UserDaoImpl: UserDao {
    override suspend fun create(user: CreateUser): Int = newSuspendedTransaction {
        UserEntity.new {
            login = user.login
            hash = user.hash
            salt = user.salt
        }.id.value
    }

    override suspend fun findByLogin(login: String): UserEntity? = newSuspendedTransaction {
        UserEntity.find {
            Users.login eq login
        }.singleOrNull()
    }

    override suspend fun findById(id: Int): UserEntity? = newSuspendedTransaction {
        UserEntity.findById(id)
    }
}