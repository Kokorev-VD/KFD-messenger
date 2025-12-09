package dev.kokorev.service.impl

import dev.kokorev.database.dao.UserDao
import dev.kokorev.model.domain.CreateUser
import dev.kokorev.model.domain.User
import dev.kokorev.service.UserService
import org.koin.core.annotation.Single

@Single
class UserServiceImpl(
    private val userDao: UserDao,
): UserService {
    override suspend fun create(user: CreateUser): Int = userDao.create(user)

    override suspend fun get(login: String): User? = userDao.findByLogin(login)?.let { entity ->
        User(entity.id.value, entity.login, entity.hash, entity.salt)
    }
}