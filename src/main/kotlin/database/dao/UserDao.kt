package dev.kokorev.database.dao

import dev.kokorev.database.entity.UserEntity
import dev.kokorev.model.domain.CreateUser

interface UserDao {
    suspend fun create(user: CreateUser): Int
    suspend fun findByLogin(login: String): UserEntity?
    suspend fun findById(id: Int): UserEntity?
}